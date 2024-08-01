package com.digs.dig0.controller;

import com.digs.dig0.dto.LoginDTO;
import com.digs.dig0.dto.RegistrationCompleteEvent;
import com.digs.dig0.dto.UserDTO;
import com.digs.dig0.eventListener.RegistrationCompleteEventListener;
import com.digs.dig0.model.MyUserDetails;
import com.digs.dig0.model.Token;
import com.digs.dig0.model.User;
import com.digs.dig0.password.IPasswordResetTokenService;
import com.digs.dig0.service.JwtTokenService;
import com.digs.dig0.service.TokenServiceImpl;
import com.digs.dig0.service.UserService;
import com.digs.dig0.utils.UrlUtil;
import io.jsonwebtoken.Claims;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.Serial;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.*;

import static com.digs.dig0.utils.Constants.*;


/**
 * Copyright to Digs LLC
 *
 * @author Ike Kennedy
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/register")
public class AuthController implements Serializable {
    @Serial
    private static final long serialVersionUID = 1234567L;
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final UserService userService;
    private final ApplicationEventPublisher publisher;
    private final TokenServiceImpl tokenService;
    private final JwtTokenService jwtTokenService;
    private final IPasswordResetTokenService passwordResetTokenService;
    private final RegistrationCompleteEventListener eventListener;
    private final AuthenticationManager authenticationManager;

    final Set<String> issuedRefreshTokens = Collections.synchronizedSet(new HashSet<>());
    @Value("${jwt.access-token.expires:3600000}")
    private long accessTokenValidityMs;
    @Value("${jwt.refresh-token.expires:86400000}")
    private long refreshTokenValidityMs;

    @GetMapping("/register")
    public String registerAUser(Model model) {
        model.addAttribute("user", new UserDTO());
        return "register";
    }

    @PostMapping("/registers")
    public String registration(@ModelAttribute("user") UserDTO userDto, HttpServletRequest request) {
        log.info("""
                Registration Controller attempting to register : {}""", userDto.getName());
        User user0 = userService.register(userDto);
       publisher.publishEvent(new RegistrationCompleteEvent(user0, UrlUtil.getApplicationUrl(request)));
        return "redirect:/register/register?success";
    }



    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginDTO requestDto, HttpServletRequest request,
                                      HttpServletResponse response) {
        try {
            String username = requestDto.getUsername();
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, requestDto.getPassword())
            );
            MyUserDetails authenticatedUser = (MyUserDetails) authentication.getPrincipal();

            createAndAddCookies( request, response, authenticatedUser.getUsername(), authenticatedUser.getAuthorities() );

            return ResponseEntity.noContent().build();
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid Credentials");
        }
    }

    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token, HttpServletRequest request, HttpServletResponse response) {
        Optional<Token> theToken = tokenService.findByToken(token);
        if (theToken.isPresent() && theToken.get().getUser().isEnabled()) {
            return "redirect:/login?verified";
        }
        String verificationResult = tokenService.validateToken(token);
        User user0 = theToken.get().getUser();
        createAndAddCookies( request, response, user0.getUsername(), user0.getAuthorities() );

        switch (verificationResult.toLowerCase()) {
            case "expired" -> {
                return "redirect:/error?expired";
            }
            case "valid" -> {
                return "redirect:/login?valid";
            }
            default -> {
                return "redirect:/error?invalid";
            }
        }
    }

    @GetMapping("/forgot-password-request")
    public String forgotPasswordForm(){
        return "forgot-password-form";
    }

    @PostMapping("/forgot-password")
    public String resetPasswordRequest(HttpServletRequest request, Model model){
        String username = request.getParameter("username");
        Optional<User> user= userService.findByUsername(username);
        if (user.isEmpty()){
            return  "redirect:/register/forgot-password-request?not_fond";
        }
        String passwordResetToken = UUID.randomUUID().toString();
        passwordResetTokenService.createPasswordResetTokenForUser(user.get(), passwordResetToken);
        //send password reset verification email to the user
        String url = UrlUtil.getApplicationUrl(request)+"/register/password-reset-form?token="+passwordResetToken;
        try {
            eventListener.sendPasswordResetVerificationEmail(url);
        } catch (MessagingException | UnsupportedEncodingException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/register/forgot-password-request?success";
    }

    @GetMapping("/password-reset-form")
    public String passwordResetForm(@RequestParam("token") String token, Model model){
        model.addAttribute("token", token);
        return "password-reset-form";
    }

    @PostMapping("/reset-password")
    public String resetPassword(HttpServletRequest request){
        String theToken = request.getParameter("token");
        String password = request.getParameter("password");
        String tokenVerificationResult = passwordResetTokenService.validatePasswordResetToken(theToken);
        if (!tokenVerificationResult.equalsIgnoreCase("valid")){
            return "redirect:/error?invalid_token";
        }
        Optional<User> theUser = passwordResetTokenService.findUserByPasswordResetToken(theToken);
        if (theUser.isPresent()){
            passwordResetTokenService.resetPassword(theUser.get(), password);
            return "redirect:/login?reset_success";
        }
        return "redirect:/error?not_found";
    }

    @GetMapping("refresh")
    public ResponseEntity<Void> refresh(@CookieValue(value = APP_REFRESH_TOKEN) String refreshToken,
                                        HttpServletRequest request,
                                        HttpServletResponse response) {

        if (issuedRefreshTokens.contains(refreshToken) && jwtTokenService.validateRefreshToken(refreshToken)) {
            issuedRefreshTokens.remove(refreshToken);
            Claims claims = jwtTokenService.getClaims(refreshToken).getPayload();
            String username = claims.getSubject();
            Collection<GrantedAuthority> rolesNames = (Collection<GrantedAuthority>) claims.get(ROLES);

            createAndAddCookies(request, response, username, rolesNames);
            return ResponseEntity.noContent().build();
        }

        throw new BadCredentialsException("Invalid refresh token");
    }

    private void createAndAddCookies(HttpServletRequest request,  HttpServletResponse response, String username,
                                     Collection<GrantedAuthority> roleNames) {
        String accessToken = jwtTokenService.createToken(ACCESS_TOKEN_TYPE, username, roleNames, accessTokenValidityMs);
        String refreshToken = jwtTokenService.createToken(REFRESH_TOKEN_TYPE, username, roleNames, refreshTokenValidityMs);

        int secondsSpentOnProcessingRequest = 1;
        int accessTokenExpiresInSeconds = (int) (accessTokenValidityMs / 1000) - secondsSpentOnProcessingRequest;
        int refreshTokenExpiresInSeconds = (int) (refreshTokenValidityMs / 1000) - secondsSpentOnProcessingRequest;

        issuedRefreshTokens.add(refreshToken);

        // secure flag allows cookies to be transported only via https -> we turn it off on localhost
        boolean secureFlag = !LOCALHOST.equalsIgnoreCase(request.getServerName());

        response.addCookie(createCookie(APP_ACCESS_TOKEN, accessToken, secureFlag, accessTokenExpiresInSeconds));
        response.addCookie(createCookie(APP_REFRESH_TOKEN, refreshToken, secureFlag, refreshTokenExpiresInSeconds));
    }

    public static Cookie createCookie(String cookieName, String cookieValue, boolean secure, int expiresIn) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setPath(DEFAULT_PATH);
        cookie.setHttpOnly(HTTP_ONLY);
        cookie.setSecure(secure);
        cookie.setMaxAge(expiresIn);
        return cookie;
    }

}