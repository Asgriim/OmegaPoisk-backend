package org.omega.omegapoisk.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("isFree")
    public boolean isFree() {
        return isFree;
    }

    @Override
    public String TableName() {
        return "game";
    }
}
