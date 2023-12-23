package org.omega.omegapoisk.ORM;

import org.omega.omegapoisk.entity.OmegaEntity;

import java.util.List;

public interface ORM {
    List<?> getListOf(Class<? extends OmegaEntity> cl);
}
