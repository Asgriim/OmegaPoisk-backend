package org.omega.omegapoisk.ORM;

import org.omega.omegapoisk.data.CardDTO;
import org.omega.omegapoisk.data.ReviewDTO;
import org.omega.omegapoisk.entity.Content;
import org.omega.omegapoisk.entity.OmegaEntity;
import org.omega.omegapoisk.entity.Review;
import org.omega.omegapoisk.entity.Tag;
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
    public List<ReviewDTO> getAllReview(int contentId) {
        List<ReviewDTO> reviewDTOS = new ArrayList<>();
        String req = "select * from review join user_ on user_.id = review.userid where contentid =" + contentId;
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(req);
        List<Field> fieldsOfClass = getFieldsOfClass(Review.class);

        try {
            while (sqlRowSet.next()) {
                ReviewDTO reviewDTO = new ReviewDTO();
                Review entity = Review.class.getConstructor().newInstance();

                for (var field : fieldsOfClass) {
                    String fieldName = field.getName();
                    field.setAccessible(true);
                    field.set(entity, sqlRowSet.getObject(fieldName));
                    field.setAccessible(false);
                }
                reviewDTO.setLogin(sqlRowSet.getString("login"));
                reviewDTO.setReview(entity);
                reviewDTOS.add(reviewDTO);
            }
            return reviewDTOS;
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException | InstantiationException e) {
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

    @Override
    public <T extends OmegaEntity> void deleteById(Class<T> cl, int id) {
        String tableName = getTableName(cl);
        String tbId = tableName + ".id";
        String query = String.format("delete from %s where %s = %s", tableName, tbId,id);
        System.out.println(query);
        jdbcTemplate.update(query);
    }

    @Override
    public <T extends OmegaEntity> T getEntityById(Class<? extends OmegaEntity> cl, int id) {

        String templ = "select * from %s where id = %s";
        String req = String.format(templ,getTableName(cl),id);
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(req);
        List<T> listFromRowSet = (List<T>) getListFromRowSet(cl, sqlRowSet);

        if (listFromRowSet.isEmpty()) {
            return null;
        }
        return listFromRowSet.get(0);
    }

    @Override
    public  <T extends Content> List<CardDTO<T>> getCards(Class<? extends OmegaEntity> cl, SqlRowSet sqlRowSet) {
        List<CardDTO<T>> cards = new ArrayList<>();
        try {
            List<Field> fieldsOfClass = getFieldsOfClass(cl);
            int lastId = 0;
            int currId = 0;

            CardDTO<T> currCard = null;

            while (sqlRowSet.next()) {
                currId =  sqlRowSet.getInt("id");
                if (currId == 85) {
                    System.out.println("bug");
                }
                if (currId != lastId) {

                    if (currCard != null) {
                        cards.add(currCard);
                    }

                    currCard = new CardDTO<>();
                    T entity = (T) cl.getConstructor().newInstance();
                    entity.setId(currId);
                    for (var field : fieldsOfClass) {
                        String fieldName = field.getName();
                        field.setAccessible(true);
                        field.set(entity, sqlRowSet.getObject(fieldName));
                        field.setAccessible(false);
                    }
                    currCard.setContent(entity);
                    currCard.setTags(new ArrayList<>());
                    currCard.setAvgRating(sqlRowSet.getDouble("avgrate"));
                    if (sqlRowSet.getString("name") != null) {
                        Tag tag = new Tag();
                        tag.setId(sqlRowSet.getInt("tagid"));
                        tag.setName(sqlRowSet.getString("name"));
                        currCard.getTags().add(tag);
                    }
                }
                else {
                    if (sqlRowSet.getString("name") != null) {
                        Tag tag = new Tag();
                        tag.setId(sqlRowSet.getInt("tagid"));
                        tag.setName(sqlRowSet.getString("name"));
                        currCard.getTags().add(tag);
                    }

                }
                lastId = currId;
            }
            if (currCard != null) {
                cards.add(currCard);
            }
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return cards;
    }

    //select * from anime
    //    JOIN content_tags ON content_tags.contentid = anime.id
    //    JOIN tags ON tags.id = content_tags.tagid order by anime.id;

    @Override
    public <T extends Content> List<CardDTO<T>> getAllCards(Class<? extends OmegaEntity> cl) {
        String tableName = getTableName(cl);
        String objId = tableName + ".id";
        String req = String.format("select * from %s left join content_tags ON content_tags.contentid = %s left join tags ON tags.id = content_tags.tagid left join avg_rating ON avg_rating.contentid = %s order by %s", tableName, objId,objId,objId);
        System.out.println(req);
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(req);

        return getCards(cl,sqlRowSet);
    }

    //select * from %s left join content_tags ON content_tags.contentid = %s left join tags ON tags.id = content_tags.tagid left join avg_rating ON avg_rating.contentid = content_tags.contentid where %s =
    @Override
    public <T extends Content> CardDTO<T> getCardById(Class<? extends OmegaEntity> cl, int contentId) {
        String tableName = getTableName(cl);
        String objId = tableName + ".id";
        String req = String.format("select * from %s left join content_tags ON content_tags.contentid = %s left join tags ON tags.id = content_tags.tagid left join avg_rating ON avg_rating.contentid = %s where %s =", tableName, objId,objId,objId)
                + contentId;
        System.out.println(req);
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(req);
        List<CardDTO<T>> cards = getCards(cl, sqlRowSet);

        if (cards.isEmpty()) {
            return null;
        }

        return cards.get(0);

    }

    @Override
    public int nextVal(String seq) {
        String req = String.format("select nextval('%s')", seq);
        return jdbcTemplate.queryForObject(req, Integer.class);
    }

    @Override
    public <T extends Content> Object getContentExtraField(T content) {
        List<Field> clFields = Arrays.asList(content.getClass().getDeclaredFields());
        try {
            Field field = clFields.get(0);
            field.setAccessible(true);
            Object o = field.get(content);
            field.setAccessible(false);
            return o;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T extends OmegaEntity> T createObj(Class<T> cl) {
        try {
            return cl.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
