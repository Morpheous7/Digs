package com.digs.dig0.config;



import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.io.IOException;
import java.net.URI;
import java.util.Collections;

@Configuration
public class RestTemplateWithInterceptorsConfig {
    @Qualifier("restTemplateWithInterceptors")
    @Bean
    public RestTemplate restTemplateWithInterceptors() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(new UriEncodingInterceptor()));
        return restTemplate;
    }

    public static class UriEncodingInterceptor implements ClientHttpRequestInterceptor {
        @Override
        public @NotNull ClientHttpResponse intercept(@NotNull HttpRequest request, byte @NotNull [] body, ClientHttpRequestExecution execution) throws IOException {
            HttpRequest encodedRequest = new HttpRequestWrapper(request) {
                @Override
                public @NotNull URI getURI() {
                    URI uri = super.getURI();
                    String escapedQuery = uri.getRawQuery().replace("+", "%2B");
                    return UriComponentsBuilder.fromUri(uri)
                            .replaceQuery(escapedQuery)
                            .build(true).toUri();
                }
            };
            return execution.execute(encodedRequest, body);
        }
    }
}