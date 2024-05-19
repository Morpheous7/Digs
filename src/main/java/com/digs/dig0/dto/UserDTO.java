package com.digs.dig0.dto;

import com.digs.dig0.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Collection;

/**
 * Copyright to Digs LLC
 * @author Ike Kennedy
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String username;
    private String password;
    private String name;
    private String phn;
    private Collection<Role> roles;
}