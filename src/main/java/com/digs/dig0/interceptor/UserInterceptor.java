package com.digs.dig0.interceptor;



import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.antlr.v4.runtime.misc.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.SmartView;
import org.springframework.web.servlet.View;

import java.io.IOException;

/**
 * Copyright to Digs LLC
 * @author Ike Kennedy
 */

@Component
public class UserInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(UserInterceptor.class);
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    HttpServletRequest request;
    HttpServletResponse response;



    /**
     * Executed before actual handler is executed
     **/
    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object object) throws ServletException, IOException {
        if (isUserLogged()) {
            addToModelUserDetails(request.getSession());
        } return true;
    }

    /**
     * Executed before after handler is executed. If view is a redirect view, we don't need to execute postHandle
     **/
    @Override
    public void postHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
                           @NotNull Object object, ModelAndView modelAndView) {
        if (modelAndView != null && !isRedirectView(modelAndView)) {
            if (isUserLogged()) {
                addToModelUserDetails(modelAndView);
                //addToModelUserDetails((ModelAndView) object);
                request.setAttribute(request.getRemoteUser(), modelAndView.getModel());
                request.setAttribute("username", modelAndView.getModel());
            }
        }

    }

    /**
     * Used before model is generated, based on session
     */

    private void addToModelUserDetails(HttpSession session) {
        log.info("================= addToModelUserDetails ============================");
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        session.setAttribute("user", username);
        log.info("user(" + username + ") session : " + session);
        log.info("================= addToModelUserDetails ============================");

    }

    /**
     * Used when model is available
     */

    private void addToModelUserDetails(ModelAndView model) {
        log.info("================= addToModelUserDetails ============================");
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        model.addObject("username", username);
        log.trace("session : " + model.getModel());
        log.info("================= addToModelUserDetails ============================");
       /* if (request.getCookies() != null) {
            if (Arrays.stream(request.getCookies()).toList().contains("Token")) {
                try {
                    redirectStrategy.sendRedirect(request, response, "/user.html");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }*/
    }

    private void addToModelUserDetails(Model model) {
        log.info("================= addToModelUserDetails ============================");
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        model.addAttribute("username", username);
        log.trace("session : " + model.getAttribute("userForm"));
        log.info("================= addToModelUserDetails ============================");

    }

    public static boolean isRedirectView(ModelAndView mv) {
        String viewName = mv.getViewName();
        assert viewName != null;
        if (viewName.startsWith("redirect:/user")) {
            return true;
        }

        View view = mv.getView();
        return (view instanceof SmartView && ((SmartView) view).isRedirectView());
    }

    public static boolean isUserLogged() {
        try {
            return !SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getName()
                    .equals("anonymousUser");
        } catch (Exception e) {
            return false;
        }
    }
}
