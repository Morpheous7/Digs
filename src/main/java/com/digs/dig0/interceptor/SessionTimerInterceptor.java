package com.digs.dig0.interceptor;

import com.digs.dig0.config.SecurityConTextResolver;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Copyright to Digs LLC
 * @author Ike Kennedy
 */

@Component
@RequiredArgsConstructor
public class SessionTimerInterceptor implements HandlerInterceptor {

        private static final Logger log = LoggerFactory.getLogger(SessionTimerInterceptor.class);

        private static final long MAX_INACTIVE_SESSION_TIME = 5 * 10000;
        private SecurityConTextResolver securityConTextResolver;
        @Autowired
        private HttpSession session;

        /**
         * Executed before actual handler is executed
         **/
        @Override
        public boolean preHandle(final HttpServletRequest request, final @NotNull HttpServletResponse response,
                                 final @NotNull Object handler) throws Exception {
            log.info("Pre handle method - check handling start time");
            long startTime = System.currentTimeMillis();
            request.setAttribute("executionTime", startTime);
            if (UserInterceptor.isUserLogged()) {
                session = request.getSession();
                log.info("Time since last request in this session: {} ms", System.currentTimeMillis() - request.getSession()
                        .getLastAccessedTime());
                if (System.currentTimeMillis() - session.getLastAccessedTime() > MAX_INACTIVE_SESSION_TIME) {
                    log.warn("Logging out, due to inactive session");
                    SecurityContextHolder.clearContext();
                    request.logout();
                    response.sendRedirect("/logout");
                }
            }

            return true;
        }

        /**
         * Executed before after handler is executed
         **/
        @Override
        public void postHandle(final HttpServletRequest request, final @NotNull HttpServletResponse response,
                               final @NotNull Object handler, final ModelAndView model) {
            log.info("Post handle method - check execution time of handling");
            long startTime = (Long) request.getAttribute("executionTime");
            log.info("Execution time for handling the request was: {} ms", System.currentTimeMillis() - startTime);
            if (UserInterceptor.isUserLogged()) {
                if (request.getRequestURI().equals("/")){
                     securityConTextResolver = new SecurityConTextResolver();
                     Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                    securityConTextResolver.setSecurityContextHolderStrategy(auth,request, response);
                    log.info("Authentication and contextHolder set and repository saved: {}", securityConTextResolver);
                }
            }
        }

}
