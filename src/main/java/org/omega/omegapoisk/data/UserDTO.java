package org.omega.omegapoisk.data;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
@Data
public class UserDTO {
    private int id = 0;
    private String login;
    private String email;
    private String pass;
    private String role;
    private String token;
}
