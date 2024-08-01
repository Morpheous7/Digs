package com.digs.dig0.controller;

import com.digs.dig0.dto.LoginDTO;
import com.digs.dig0.dto.ResponseFile;
import com.digs.dig0.model.MyUserDetails;
import com.digs.dig0.model.User;
import com.digs.dig0.service.ImageService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.SessionCookieConfig;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.digs.dig0.utils.Constants.*;

/**
 * Copyright to Digs LLC
 * @author Ike Kennedy
 */

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);
    private final ImageService imageService;
    private final AuthenticationManager authenticationManager;

    // handler method to handle home page request
    @GetMapping
    public String homePage(Model model, HttpServletRequest request,
                           @AuthenticationPrincipal User user) {
        String username = request.getParameter(USERNAME);
        ServletContext sc = request.getServletContext();
        String servletContextname = sc.getServletContextName();
        SessionCookieConfig sessionCookieConfig = sc.getSessionCookieConfig();
        Integer sessionTimeoutNumber = sc.getSessionTimeout();
        String vrServername = sc.getVirtualServerName();
        log.info("**************************************************************************");
        log.info("servletContextname is: {}", servletContextname);
        log.info("sessionTimeout Nmmber  is: {}", sessionTimeoutNumber);
        log.info("servletContext is: {}", sc);
        log.info("vrServername is: {}", vrServername);
        log.info("sessionCookieConfig is: {}", sessionCookieConfig);
        log.info("event_organizer is: {}", user);
        log.info("**************************************************************************");
        model.addAttribute(USERNAME, username);
        model.addAttribute("event_organizer", user);
        model.addAttribute("user", user);
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/error")
    public String error(){
        return "error";
    }

    @GetMapping(value = "/about")
    public String about() {
        log.info("ABOUT PAGE: ...attempting logging..  ..");
        return "about";
    }

    @GetMapping(value = "/blog-home")
    public String bloghome() {
        log.info("BLOG-HOME Dashboard: ...attempting logging..  ..");
        return "`blog-home`";
    }

    @GetMapping(value = "/blog-post")
    public String blogpost() {
        log.info("BLOG-POST Dashboard: ...attempting logging..  ..");
        return "blog-post";
    }

    @GetMapping(value = "/contact")
    public String contact() {
        log.info("CONTACT Dashboard: ...attempting logging..  ..");
        return "contact";
    }

    @GetMapping(value = "/faq")
    public String faq() {
        log.info("FAQ Dashboard: ...attempting logging..  ..");
        return "faq";
    }

    @GetMapping(value = "/portfolio-item")
    public String portfolioitem() {
            log.info("PORTFOLIO Dashboard: ...attempting logging..  ..");
        return "portfolio-item";
    }

    @GetMapping(value = "/portfolio-overview")
    public String portfoliooverview() {
        log.info("PORTFOLIO-OVERVIEW Dashboard: ...attempting logging..  ..");
        return "portfolio-overview";
    }

    @GetMapping(value = "/pricing")
    public String pricing() {
        log.info("pricing Dashboard: ...attempting logging..  ..");
        return "pricing";
    }

    @PostMapping("login")
    public ResponseEntity<Void> login(@RequestBody LoginDTO requestDto) {
        try {
            String username = requestDto.getUsername();
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, requestDto.getPassword())
            );
            MyUserDetails authenticatedUser = (MyUserDetails) authentication.getPrincipal();

            //createAndAddCookies( request, response, authenticatedUser.getUsername(), authenticatedUser.getAuthorities() );

            return ResponseEntity.noContent().build();
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid Credentials");
        }
    }
}