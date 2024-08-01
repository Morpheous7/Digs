package com.digs.dig0.utils;


import com.digs.dig0.model.ImageData;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.SerializationUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.support.AbstractMultipartHttpServletRequest;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.stream;


public class ImpleAbsMultiSerReq  extends AbstractMultipartHttpServletRequest {
    Map<String, Object> amap = new HashMap<>();
    ImageData file;
    HttpServletRequest req;

    public ImpleAbsMultiSerReq(HttpServletRequest request) {
        super(request);
        setSomeMap(request);
    }

    @Override
    public HttpHeaders getMultipartHeaders(@NotNull String paramOrFileName) {HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = this.getHeaderNames();

        while(headerNames.hasMoreElements()) {
            String headerName = (String)headerNames.nextElement();
            headers.put(headerName, Collections.list(this.getHeaders(headerName)));
            if(headerName.equals(paramOrFileName)) {
                headers.add(paramOrFileName, this.getHeader(headerName));
            }
        }
        return headers;
    }

    @Override
    public String getMultipartContentType(@NotNull String paramOrFileName) {
        return file.getType();
    }

    public void setMultiData(@NotNull ImageData file) {
        this.file = file;
    }
    public ImageData getImageData() {
        return this.file;
    }

    public void setSomeMap(HttpServletRequest req) {
        if (req.getParameterMap().containsKey("inputFile")) {
            for (Map.Entry<String, String[]> entry : req.getParameterMap().entrySet()) {
                // add every item as a property to the json
                if (entry.getKey().equals("inputFile")) {
                    this.file = new ImageData();
                    this.file.setName(stream(stream(entry.getValue()).toArray()).toString());
                    this.file.setType("image/jpg");
                    this.file.setImageData( SerializationUtils.serialize(stream(entry.getValue()).toArray()));
                    this.amap.put(entry.getKey(), this.file);
                    setMultiData(file);
                }
                if (!entry.getKey().equals("inputFile")) {
                    this.amap.put(entry.getKey(), entry.getValue());
                }
            }
        }
    }

}
