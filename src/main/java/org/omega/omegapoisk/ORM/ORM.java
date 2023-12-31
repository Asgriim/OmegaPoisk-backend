package org.omega.omegapoisk.ORM;

import org.omega.omegapoisk.data.CardDTO;
import org.omega.omegapoisk.data.ReviewDTO;
import org.omega.omegapoisk.entity.Content;
import org.omega.omegapoisk.entity.OmegaEntity;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.List;

public interface ORM {
    int nextVal(String seq);
    String getTableName(Class<? extends OmegaEntity> cl);
    List<ReviewDTO> getAllReview(int contentId);
    List<?> getListOf(Class<? extends OmegaEntity> cl);
    List<?> getListFromRowSet(Class<? extends OmegaEntity> cl, SqlRowSet sqlRowSet);
    <T extends OmegaEntity> void deleteById(Class<T> cl, int id);
    <T extends OmegaEntity> T getEntityById(Class<? extends OmegaEntity> cl, int id);
    <T extends Content> List<CardDTO<T>> getAllCards(Class<? extends OmegaEntity> cl);
    <T extends Content> CardDTO<T> getCardById(Class<? extends OmegaEntity> cl, int contentId);
    <T extends Content> List<CardDTO<T>> getCards(Class<? extends OmegaEntity> cl, SqlRowSet sqlRowSet);
    <T extends Content> Object getContentExtraField(T content);
    <T extends  OmegaEntity> T createObj(Class<T> cl);


    //todo get cards of owner
    //select * from anime join owner_of_content on owner_of_content.contentid = anime.id
    //    left join content_tags on content_tags.contentid = anime.id
    //                    left join tags ON tags.id = content_tags.tagid
    //                    left join avg_rating ON avg_rating.contentid = content_tags.contentid where owner_of_content.userid = 1 order by anime.id;
}
