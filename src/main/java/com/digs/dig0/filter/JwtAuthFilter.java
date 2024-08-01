package com.digs.dig0.filter;

import com.digs.dig0.model.ImageData;
import com.digs.dig0.service.HttpBinService;
import com.digs.dig0.service.TokenServiceImpl;
import com.digs.dig0.utils.ImpleAbsMultiSerReq;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.util.*;
import static com.digs.dig0.utils.Constants.APP_ACCESS_TOKEN;


/**
 * Copyright to Digs LLC
 * @author Ike Kennedy
*/


@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final TokenServiceImpl tokenService;

    @Value("${urls.white-list:/login}")
    private List<String> whiteListedUrls;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)  throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(300);

        if (whiteListedUrls.stream().noneMatch(request.getRequestURI()::endsWith)) {
            tokenService.getTokenFromCookie(request, APP_ACCESS_TOKEN)
                    .filter(tokenService::validateTokens)
                    .map(tokenService::getAuthentication)
                    .ifPresent(SecurityContextHolder.getContext()::setAuthentication);
        }

       /* MultipartResolver multipartResolver = new MultipartResolver() {
            MultipartFile file;
            ImpleAbsMultiSerReq aReq;
            HttpServletResponse response;
            MultipartResolver multipartResolver;
            @Override
            public boolean isMultipart(@NotNull @org.jetbrains.annotations.NotNull HttpServletRequest request) {
                return !request.getParameterMap().isEmpty() && request.getParameterMap().containsKey("inputFile");
            }

            @Override
            public @NotNull @org.jetbrains.annotations.NotNull MultipartHttpServletRequest resolveMultipart
                    (@NotNull @org.jetbrains.annotations.NotNull HttpServletRequest request) throws MultipartException {
                    aReq = new ImpleAbsMultiSerReq(request);
                return (MultipartHttpServletRequest) aReq;
            }
            @Override
            public void cleanupMultipart(@NotNull @org.jetbrains.annotations.NotNull MultipartHttpServletRequest request) {
                if (this.multipartResolver != null) {
                    MultipartHttpServletRequest multipartRequest =
                            WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
                    if (multipartRequest != null) {
                        this.multipartResolver.cleanupMultipart(multipartRequest);
                    }
                }
            }
        };

        if (multipartResolver.isMultipart(request)) {
            if (logger.isTraceEnabled()) {
                logger.trace("Resolving multipart request");
                MultipartHttpServletRequest myreq = multipartResolver.resolveMultipart(request);
                filterChain.doFilter(myreq, response);
            }
        }
        else {
            // A regular request...
            if (logger.isTraceEnabled()) {
                logger.trace("Not a multipart request");
            }
            filterChain.doFilter(request, response);
        }*/

        filterChain.doFilter(request, response);
    }
}

