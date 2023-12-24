package org.omega.omegapoisk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Movie extends Content implements OmegaEntity{
    private int duration;

    @Override
    public String TableName() {
        return "movie";
    }
}
