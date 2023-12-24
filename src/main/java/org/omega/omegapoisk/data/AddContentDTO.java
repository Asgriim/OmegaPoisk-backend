package org.omega.omegapoisk.data;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.omega.omegapoisk.entity.Tag;

import java.util.List;

@Data
@NoArgsConstructor
public class AddContentDTO <T>{
    private T content;
    private String studio;
    private List<Tag> tags;
}
