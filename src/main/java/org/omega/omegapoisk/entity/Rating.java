package org.omega.omegapoisk.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Rating implements OmegaEntity{
    private int id;
    private int value;
    private int userId;
    private int contentId;

    @Override
    public String TableName() {
        return "rating";
    }
}
