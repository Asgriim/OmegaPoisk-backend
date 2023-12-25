package org.omega.omegapoisk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Studio implements OmegaEntity{

    private int id;
    private String name;

    @Override
    public String TableName() {
        return "studio";
    }
}
