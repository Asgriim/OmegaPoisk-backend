package org.omega.omegapoisk.data;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@NoArgsConstructor
@Data
public class ContentDTO<T> {
    private T content;
}
