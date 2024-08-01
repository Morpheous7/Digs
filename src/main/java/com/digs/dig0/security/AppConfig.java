
package com.digs.dig0.security;

import com.digs.dig0.dto.EventDto;
import com.digs.dig0.model.ImageData;
import com.digs.dig0.service.CustomUserDetailsServices;
import com.digs.dig0.utils.MultipartHttpServReq;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.SerializationUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.WebUtils;

import java.time.LocalDateTime;
import java.util.*;

import static java.util.Arrays.stream;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    @Autowired
    private final CustomUserDetailsServices customUserDetailsServices;


    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
/*
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsServices).passwordEncoder(encoder());
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsServices(userRepository);
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.userDetailsService(userDetailsService()).passwordEncoder(encoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:8088"));
        config.setAllowedMethods(List.of("GET", "POST"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public RememberMeAuthenticationProvider rememberMeAuthenticationProvider() {
        return new RememberMeAuthenticationProvider("app");
    }
*/

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsServices);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(customUserDetailsServices)
                .passwordEncoder(encoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        DefaultUriBuilderFactory defaultUriBuilderFactory = new DefaultUriBuilderFactory();
        defaultUriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);
        restTemplate.setUriTemplateHandler(defaultUriBuilderFactory);
        return restTemplate;
    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new MultipartResolver() {
            MultipartResolver multipartResolver;
            MultiValueMap<String, MultipartFile> multiparts = new LinkedMultiValueMap<>();
            MultipartHttpServReq aReq;
            boolean hasMultipart;

            @Override
            public boolean isMultipart(@NotNull HttpServletRequest request) {
                String method = request.getMethod().toLowerCase();
                if (!Arrays.asList("put", "post").contains(method)) {
                    return false;
                } else {
                    if (request.getParameterMap().containsKey("inputFile")) {
                        hasMultipart = true;
                        return hasMultipart;
                    }
                }
                return false;
            }

            @Override
            public MultipartHttpServletRequest resolveMultipart(HttpServletRequest request) throws MultipartException {
                aReq = new MultipartHttpServReq(request);
                for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
                    EventDto eventDto = new EventDto();
                    // add every item as a property to the json
                    if (entry.getKey().equals("inputFile")) {
                        ImageData file = new ImageData();
                        file.setName(stream(stream(entry.getValue()).toArray()).toString());
                        file.setType("image/jpg");
                        file.setImageData(SerializationUtils.serialize(stream(entry.getValue()).toArray()));
                        List<MultipartFile> file2 = new ArrayList<>();
                        file2.add((MultipartFile) file);
                        multiparts.put(entry.getKey(), file2);
                        aReq.setMultipartFiles(multiparts);
                    } else if (entry.getKey().equals("eventStart_Time")) {
                        LocalDateTime formatter;
                        String name = entry.getKey();
                        String d_Time = Arrays.toString(entry.getValue());
                        String dateTime = d_Time.substring(1,17);
                        formatter = LocalDateTime.parse(dateTime);
                        eventDto.setEventStart_Time(formatter);
                        aReq.setParameter(name, String.valueOf(eventDto.getEventStart_Time()));
                    } else if ((entry.getKey().equals("privateEvent")) || (entry.getKey().equals("repetitive_Event")) || (entry.getKey().equals("single_Event"))) {
                        String name = entry.getKey();
                        Boolean value;
                        if (entry.getValue() == null) {
                            value = false;
                            aReq.setParameter(name, String.valueOf(value));
                        } else if ((Arrays.toString(entry.getValue())).equals("[on]")) {
                            value = true;
                            aReq.setParameter(name, String.valueOf(value));
                        } else {
                            String val = Arrays.toString(entry.getValue());
                            value = Boolean.getBoolean(String.valueOf(Boolean.parseBoolean(val.substring(0, val.length() - 1))));
                            aReq.setParameter(name, String.valueOf(value));
                        }
                    } else {
                        aReq.setParameter(entry.getKey(), Arrays.toString(entry.getValue()));
                    }

                }
                return aReq;
            }

            @Override
            public void cleanupMultipart (MultipartHttpServletRequest request){
                    if (this.multipartResolver != null) {
                        MultipartHttpServletRequest multipartRequest =
                                WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
                        if (multipartRequest != null) {
                            this.multipartResolver.cleanupMultipart(multipartRequest);
                        }
                    }
            }
        };
    }
}