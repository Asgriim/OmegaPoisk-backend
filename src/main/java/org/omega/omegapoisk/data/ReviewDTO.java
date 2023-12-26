package org.omega.omegapoisk.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.omega.omegapoisk.entity.Review;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private Review review;
    private String login;
}
