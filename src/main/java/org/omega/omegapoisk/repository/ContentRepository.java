package org.omega.omegapoisk.repository;

import org.omega.omegapoisk.ORM.OmegaORM;
import org.omega.omegapoisk.data.CardDTO;
import org.omega.omegapoisk.entity.AvgRating;
import org.omega.omegapoisk.entity.Content;
import org.omega.omegapoisk.entity.OmegaEntity;
import org.omega.omegapoisk.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ContentRepository {

    @Autowired
    private OmegaORM omegaORM;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Tag> getContentTags(int contentId) {
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("select * from content_tags" +
                "    join tags on tags.id = content_tags.tagid" +
                "              where contentid = " + contentId);
        return (List<Tag>) omegaORM.getListFromRowSet(Tag.class, sqlRowSet);
    }

    public List<Tag> getAllTags() {
        return (List<Tag>) omegaORM.getListOf(Tag.class);
    }

    public <T extends Content> List<CardDTO<T>> getAllCards(Class<? extends OmegaEntity> cl) {
        String tableName = omegaORM.getTableName(cl);
        String req = String.format("select * from %s join avg_rating on contentid = id", tableName);
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(req);

        List<T> list = (List<T>) omegaORM.getListFromRowSet(cl,sqlRowSet);
        sqlRowSet.beforeFirst();
        List<AvgRating> avgRatings = (List<AvgRating>) omegaORM.getListFromRowSet(AvgRating.class, sqlRowSet);

        List<CardDTO<T>> cards = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
                CardDTO<T> cardDTO = new CardDTO<>();
                cardDTO.setContent(list.get(i));
                cardDTO.setAvgRating(avgRatings.get(i).getAvgRate());
                cardDTO.setTags(getContentTags(list.get(i).getId()));
                cards.add(cardDTO);
        }
        return cards;
    }
}
