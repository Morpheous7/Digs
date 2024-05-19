package com.digs.dig0.controller;

import com.digs.dig0.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Copyright to Digs LLC
 * @author Ike Kennedy
 */

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);
    private final UserService userService;

    // handler method to handle home page request
    @GetMapping
    public String homePage() {
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

    @GetMapping(value = "/user")
    public String user() {
        log.info("USER Dashboard: ...attempting logging..  ..");
        return "user";
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

    @GetMapping(value = "/event")
    public String event() {
        log.info("Event Dashboard: ...attempting logging..  ..");
        return "event";
    }

/*    // Login form with error
    @RequestMapping("/login-error.html")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login.html";
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exception(final Throwable throwable, final Model model) {
        log.error("Exception during execution of SpringSecurity application", throwable);
        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }*/
}