package org.omega.omegapoisk.entity;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public enum Role implements GrantedAuthority {
    ADMIN("ADMIN"), // 1
    USER("USER"), // 2
    CREATOR("CREATOR"); //3


    private final String str;
    
    public static Role fromInt(int i) {
        switch (i) {
            case 1 -> {
                return ADMIN;
            }
            case 2 -> {
                return USER;
            }
            case 3 -> {
                return CREATOR;
            }
            default -> throw new IllegalStateException("Unexpected value: " + i);
        }
    } 
    
    public static int toInt(Role r) {
        switch (r) {
            case ADMIN -> {
                return 1;
            }
            case USER -> {
                return 2;
            }
            case CREATOR -> {
                return 3;
            }
            default -> throw new IllegalStateException("Unexpected value: " + r);
        }
    }
    
    @Override
    public String getAuthority() {
        return str;
    }
}
