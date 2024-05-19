package com.digs.dig0.utils;

import java.util.Calendar;
import java.util.Date;


/**
 * Copyright to Digs LLC
 *
 * @author Ike Kennedy
 */



public class TokenExpirationTime {
    private static final int EXPIRATION_TIME = 10*60*1000;

    public static Date getExpirationTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.HOUR, EXPIRATION_TIME);
        return new Date(calendar.getTime().getTime());
    }
}