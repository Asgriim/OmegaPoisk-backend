package org.omega.omegapoisk.repository;

import org.omega.omegapoisk.ORM.OmegaORM;
import org.omega.omegapoisk.data.CardDTO;
import org.omega.omegapoisk.entity.Content;
import org.omega.omegapoisk.entity.OmegaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SearchRepository {
    @Autowired
    OmegaORM omegaORM;

    @Autowired
    JdbcTemplate jdbcTemplate;

    //select * from anime left join content_tags on content_tags.contentid = anime.id
    //                    left join tags ON tags.id = content_tags.tagid
    //                    left join avg_rating ON avg_rating.contentid = anime.id where  lower(anime.title) like '%rt%' order by anime.id;

    public <T extends Content> List<CardDTO<T>> searchByTitle(Class<T> cl, String title, String qTemplate){
        String tableName = omegaORM.getTableName((Class<? extends OmegaEntity>) cl);
        String tbId = tableName + ".id";
//        String contTitle = tableName + ".title";
        String quer;
//        String quer = String.format(qTemplate,
//                tableName,
//                tbId,
//                tbId,
//                contTitle,
//                tbId
//                );
        quer = qTemplate;
        quer += "like concat('%',?,'%') order by " + tbId;

        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(quer, title);
        System.out.println(quer);
        List<CardDTO<T>> cards = omegaORM.getCards((Class<? extends OmegaEntity>) cl, sqlRowSet);
        return cards;
    }

    public <T extends Content> List<CardDTO<T>> searchByTitleCreator(Class<T> cl, String title, String qTemplate) {
        return null;
    }


}
