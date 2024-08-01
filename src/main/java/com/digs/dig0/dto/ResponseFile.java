package com.digs.dig0.dto;


import lombok.Getter;
import lombok.Setter;

/**
 * Copyright to Digs LLC
 * @author Ike Kennedy
 */
@Setter
@Getter
public class ResponseFile {
    private String name;
    private String url;
    private String type;
    private long size;

    public ResponseFile(String name, String url, String type, long size) {
        this.name = name;
        this.url = url;
        this.type = type;
        this.size = size;
    }

    public ResponseFile(String name, String url, String type) {
        this.name = name;
        this.url = url;
        this.type = type;
    }

    public ResponseFile() {

    }
}