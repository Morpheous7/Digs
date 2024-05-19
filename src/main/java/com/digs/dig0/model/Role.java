package com.digs.dig0.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * Copyright to Digs LLC
 * @author Ike Kennedy
 */

@Getter
@Entity
@NoArgsConstructor
@Table(name = "roles", schema = "world")
public class Role extends BaseEntity {
    @Column
    private String authority;
    public Role(String authority) {
        this.authority = authority;
    }
}
