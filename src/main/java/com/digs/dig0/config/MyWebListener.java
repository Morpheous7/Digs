package com.digs.dig0.config;


import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * Copyright to Digs LLC
 * @author Ike Kennedy
 */

@WebListener
public class MyWebListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContextListener.super.contextInitialized(sce);

        sce.getServletContext().setSessionTimeout(60 * 24); // session timeout in minutes
    }

}
