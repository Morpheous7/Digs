package com.digs.dig0.Page;


import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

/**
 * Copyright to Digs LLC
 * @author Ike Kennedy
 */

@Getter
@AllArgsConstructor
public class Page<T> {
    List<T> content;
    int totalPages;
}