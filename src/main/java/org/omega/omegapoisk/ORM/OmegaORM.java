package org.omega.omegapoisk.ORM;

import org.omega.omegapoisk.entity.OmegaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Component
public class OmegaORM implements ORM{

    @Autowired
    JdbcTemplate jdbcTemplate;

    private List<Field> getFieldsOfClass(Class<?> cl) {
        List<Field> supClassFields = Arrays.asList(cl.getSuperclass().getDeclaredFields());
        List<Field> clFields = Arrays.asList(cl.getDeclaredFields());
        return Stream.concat(supClassFields.stream(), clFields.stream()).toList();
    }

    @Override
    public List<?> getListOf(Class<? extends OmegaEntity> cl) {
        try {
            Method getTableMethod = cl.getMethod("TableName");
            String tableName = (String) getTableMethod.invoke(cl.getConstructor().newInstance());

            String req = String.format("select * from %s", tableName);

            SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(req);
            return getListFromRowSet(cl, sqlRowSet);

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getTableName(Class<? extends OmegaEntity> cl) {
        try {
            Method getTableMethod = cl.getMethod("TableName");
            return  (String) getTableMethod.invoke(cl.getConstructor().newInstance());
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<?> getListFromRowSet(Class<? extends OmegaEntity> cl, SqlRowSet sqlRowSet) {
        List<Object> omegaEntities = new ArrayList<>();
        List<Field> allFields = getFieldsOfClass(cl);
        try {
            while (sqlRowSet.next()) {
                OmegaEntity entity = cl.getConstructor().newInstance();
                for (var field : allFields) {
                    String fieldName = field.getName();
                    field.setAccessible(true);
                    field.set(entity, sqlRowSet.getObject(fieldName));
                    field.setAccessible(false);
                }
                omegaEntities.add(entity);
            }
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return omegaEntities;
    }
}
