package com.digs.dig0.utils;


import jakarta.servlet.http.HttpServletRequest;


/**
 * Copyright to Digs LLC
 *
 * @author Ike Kennedy
 */


public class UrlUtil {
    public static String getApplicationUrl(HttpServletRequest request){
        String appUrl = request.getRequestURL().toString();
        return appUrl.replace(request.getServletPath(), "");

    }
}