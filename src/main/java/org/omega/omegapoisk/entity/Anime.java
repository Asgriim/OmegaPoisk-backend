package org.omega.omegapoisk.entity;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class Anime extends Content implements OmegaEntity{
    private int seriesNum;

    @Override
    public String TableName() {
        return "anime";
    }
}
