package com.digs.dig0.Page;


import lombok.*;

/**
 * Copyright to Digs LLC
 * @author Ike Kennedy
 */

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageItem {

    private PageItemType pageItemType;

    private int index;

    private boolean active;

}