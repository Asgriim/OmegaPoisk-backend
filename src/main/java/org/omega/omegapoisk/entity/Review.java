package org.omega.omegapoisk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Review implements OmegaEntity{
    private int id;
    private String txt;
    private int userId;
    private int contentId;

    @Override
    public String TableName() {
        return "review";
    }
}
