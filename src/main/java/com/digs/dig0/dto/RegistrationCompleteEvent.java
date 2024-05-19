package com.digs.dig0.dto;

import com.digs.dig0.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;


/**
 * Copyright to Digs LLC
 *
 * @author Ike Kennedy
 */

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {
    private User user;
    private String confirmationUrl;
    public RegistrationCompleteEvent(User user, String confirmationUrl) {
        super(user);
        this.user = user;
        this.confirmationUrl = confirmationUrl;
    }
}