package com.digs.dig0.utils;



import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.*;
/**
 * Copyright to Digs LLC
 * @author Ike Kennedy
 */
public class MultipartHttpServReq extends HttpServletRequestWrapper implements MultipartHttpServletRequest {


    /**
     * Constructs a request object wrapping the given request.
     *
     * @throws IllegalArgumentException if the request is null
     */
    public MultipartHttpServReq() {
        this(null);
    }

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request the {@link HttpServletRequest} to be wrapped.
     * @throws IllegalArgumentException if the request is null
     */
    public MultipartHttpServReq( HttpServletRequest request) {
        super(request);
    }

    private MultiValueMap<String, MultipartFile> multipartFiles;

    @Setter
    private String method;

    @Override
    public String getMethod() {
        if (this.method == null) return super.getMethod();
        return this.method;
    }

    private Map<String, String[]> parameters = new LinkedHashMap<String, String[]>();


    public void setParameter(String name, String value) {
        parameters.put(name, new String[]{value});
    }

    @Override
    public String getParameter(String name) {
        if (parameters.get(name) != null) {
            return parameters.get(name)[0];
        }
        HttpServletRequest req = (HttpServletRequest) super.getRequest();
        return req.getParameter(name);
    }

    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> result = new LinkedHashMap<String, String[]>();
        result.putAll(super.getRequest().getParameterMap());
        result.putAll(parameters);
        return Collections.<String, String[]>unmodifiableMap(result);
    }

    public Enumeration<String> getParameterNames() {
        Set<String> result = new LinkedHashSet<String>(Collections.list(super.getRequest().getAttributeNames()));
        result.addAll(parameters.keySet());
        return new Vector<String>(result).elements();
    }

    public String[] getParameterValues(String name) {
        if (parameters.get(name) != null) {
            return parameters.get(name);
        }
        HttpServletRequest req = (HttpServletRequest) super.getRequest();
        return req.getParameterValues(name);
    }


    @Override
    public HttpServletRequest getRequest() {
        return (HttpServletRequest) super.getRequest();
    }

    @Override
    public @NotNull HttpMethod getRequestMethod() {
        return HttpMethod.valueOf(getRequest().getMethod());
    }

    @Override
    public @NotNull HttpHeaders getRequestHeaders() {
        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, Collections.list(getHeaders(headerName)));
        }
        return headers;
    }

    @Override
    public HttpHeaders getMultipartHeaders(String s) {
        return null;
    }

    @Override
    public @NotNull Iterator<String> getFileNames() {
        return getMultipartFiles().keySet().iterator();
    }

    @Override
    public MultipartFile getFile(@NotNull String name) {
        return getMultipartFiles().getFirst(name);
    }

    @Override
    public @NotNull List<MultipartFile> getFiles(@NotNull String name) {
        List<MultipartFile> multipartFiles = getMultipartFiles().get(name);
        if (multipartFiles != null) {
            return multipartFiles;
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public @NotNull Map<String, MultipartFile> getFileMap() {
        return getMultipartFiles().toSingleValueMap();
    }

    @Override
    public @NotNull MultiValueMap<String, MultipartFile> getMultiFileMap() {
        return getMultipartFiles();
    }

    @Override
    public String getMultipartContentType(String s) {
        return null;
    }


    /**
     * Set a Map with parameter names as keys and list of MultipartFile objects as values.
     * To be invoked by subclasses on initialization.
     */
    public final void setMultipartFiles(MultiValueMap<String, MultipartFile> multipartFiles) {
        this.multipartFiles =
                new LinkedMultiValueMap<>(Collections.unmodifiableMap(multipartFiles));
    }

    /**
     * Obtain the MultipartFile Map for retrieval,
     * lazily initializing it if necessary.
     *
     * @see #initializeMultipart()
     */
    protected MultiValueMap<String, MultipartFile> getMultipartFiles() {
        if (this.multipartFiles == null) {
            initializeMultipart();
        }
        return this.multipartFiles;
    }

    /**
     * Lazily initialize the multipart request, if possible.
     * Only called if not already eagerly initialized.
     */
    protected void initializeMultipart() {
        throw new IllegalStateException("Multipart request not initialized");
    }

}
