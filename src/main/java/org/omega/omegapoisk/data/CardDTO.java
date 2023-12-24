package org.omega.omegapoisk.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.omega.omegapoisk.entity.Tag;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardDTO <T>{
    private T content;
    private double avgRating;
    private List<Tag> tags;
}
