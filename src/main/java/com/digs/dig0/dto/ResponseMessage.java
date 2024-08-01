package com.digs.dig0.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Copyright to Digs LLC
 * @author Ike Kennedy
 */
@Setter
@Getter
public class ResponseMessage {
    private String message;

    public ResponseMessage(String message) {
        this.message = message;
    }

}