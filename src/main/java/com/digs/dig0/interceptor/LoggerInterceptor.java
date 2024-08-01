package com.digs.dig0.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import java.util.*;

import static org.springframework.web.servlet.DispatcherServlet.MULTIPART_RESOLVER_BEAN_NAME;


/**
 * Copyright to Digs LLC
 * @author Ike Kennedy
 */
@Component
public class LoggerInterceptor implements HandlerInterceptor {
    MultipartResolver multipartResolver;

    private static final Logger log = LoggerFactory.getLogger(LoggerInterceptor.class);
    /**
     * Executed before actual handler is executed
     **/

    @Override
    public boolean preHandle(final @NotNull HttpServletRequest request, final @NotNull HttpServletResponse response,
                             final @NotNull Object handler) {
        log.info("[preHandle][{}][{}]{}{}", request, request.getMethod(), request.getRequestURI(), getParameters(request));
      return true;
    }

    /**
   * Executed before after handler is executed
     **/
    @Override
    public void postHandle(final @NotNull HttpServletRequest request, final @NotNull HttpServletResponse response,
                           final @NotNull Object handler, ModelAndView modelAndView) {
        log.info("[postHandle is: ][{}]", request);
        log.info("Request-ServletPath is : {}", request.getServletPath());
        log.info("Request-URI() is gotten as follows : {}", request.getRequestURI());
        log.info("Request-Session displays as : {}", request.getSession());
        if (modelAndView != null) {
            log.info("modeView model, found. Contains map<String, Object> : ");
            modelAndView.addObject("requestURI", request.getRequestURI());
        }
    }


    /**
     * Executed after complete request is finished
     **/

    @Override
    public void afterCompletion(final @NotNull HttpServletRequest request, final @NotNull HttpServletResponse response,
                                final @NotNull Object handler, final Exception ex) {
        log.info("[postHandle][{}]", request);

        if (ex != null)
            log.warn("Exception warning...", ex);

        log.info("[afterCompletion][{}][exception: {}]", request, ex);
    }
    private String getParameters(final HttpServletRequest request) {
        final StringBuilder posted = new StringBuilder();
        final Enumeration<?> e = request.getParameterNames();
        String username;
        if (e != null)
            posted.append("?");
        while (e != null && e.hasMoreElements()) {
            if (posted.length() > 1)
                posted.append("&");
            final String curr = (String) e.nextElement();
            posted.append(curr)
                    .append("=");
            if (curr.contains("password") || curr.contains("answer") || curr.contains("pwd")) {
                posted.append("*****");
            } else {
                posted.append(request.getParameter(curr));
                username = request.getParameter(curr);
                return username;
            }
        }

        final String ip = request.getHeader("X-FORWARDED-FOR");
        final String ipAddr = (ip == null) ? getRemoteAddr(request) : ip;
        if (Strings.isNotEmpty(ipAddr)) posted.append("&_psip=").append(ipAddr);
        return posted.toString();
    }

    private String getRemoteAddr(final HttpServletRequest request) {
        final String ipFromHeader = request.getHeader("X-FORWARDED-FOR");
        if (ipFromHeader != null && !ipFromHeader.isEmpty()) {
            log.debug("ip from proxy - X-FORWARDED-FOR : {}", ipFromHeader);
            return ipFromHeader;
        }
        return request.getRemoteAddr();
    }

}
