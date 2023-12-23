package org.omega.omegapoisk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Game extends Content implements OmegaEntity{
    private boolean isFree;

    @Override
    public String getTableName() {
        return "game";
    }
}
