package org.omega.omegapoisk.data;

import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class ContentPageDTO<T> {
    private T content;
}
