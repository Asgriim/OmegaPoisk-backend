package org.omega.omegapoisk.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AvgRating implements OmegaEntity{
    private double avgRate;
    private int contentId;

    @Override
    public String TableName() {
        return "avg_rating";
    }
}
