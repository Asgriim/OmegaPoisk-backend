package org.omega.omegapoisk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TvShow extends Content implements OmegaEntity{
    private int seriesNum;

    @Override
    public String TableName() {
        return "tv_show";
    }
}
