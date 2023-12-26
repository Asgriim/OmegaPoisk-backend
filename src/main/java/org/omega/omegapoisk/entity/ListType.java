package org.omega.omegapoisk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor

public enum ListType {
    WATCHED(1),
    WATCHING(2),
    WILL_WATCH(3);

    private int val;

    public int getVal() {
        return val;
    }
}