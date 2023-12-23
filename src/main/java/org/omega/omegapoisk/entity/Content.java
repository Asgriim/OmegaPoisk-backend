package org.omega.omegapoisk.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Content {
    private int id;
    private String title;
    private String description;
}
