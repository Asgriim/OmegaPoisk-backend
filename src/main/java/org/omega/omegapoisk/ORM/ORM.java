package org.omega.omegapoisk.ORM;

import org.omega.omegapoisk.entity.OmegaEntity;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.List;

public interface ORM {
    List<?> getListOf(Class<? extends OmegaEntity> cl);
    String getTableName(Class<? extends OmegaEntity> cl);
    List<?> getListFromRowSet(Class<? extends OmegaEntity> cl, SqlRowSet sqlRowSet);
}
