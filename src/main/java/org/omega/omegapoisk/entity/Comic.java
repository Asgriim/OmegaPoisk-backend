package org.omega.omegapoisk.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class Comic extends Content implements OmegaEntity{
    @JsonProperty("isColored")
    private boolean isColored;

    @JsonProperty("isColored")
    public boolean isColored() {
        return isColored;
    }

    @Override
    public String TableName() {
        return "comic";
    }
}
