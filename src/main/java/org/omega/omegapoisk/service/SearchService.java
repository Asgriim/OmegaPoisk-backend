package org.omega.omegapoisk.service;

import org.omega.omegapoisk.ORM.OmegaORM;
import org.omega.omegapoisk.data.CardDTO;
import org.omega.omegapoisk.entity.Content;
import org.omega.omegapoisk.entity.OmegaEntity;
import org.omega.omegapoisk.entity.User;
import org.omega.omegapoisk.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    SearchRepository searchRepository;

    @Autowired
    OmegaORM omegaORM;
    public <T extends Content> List<CardDTO<T>> searchByTitle(Class<T> cl, String title) {
        String templ = "select * from %s left join content_tags on content_tags.contentid = %s\n" +
                "                    left join tags ON tags.id = content_tags.tagid\n" +
                "                    left join avg_rating ON avg_rating.contentid = %s where  lower(%s) ";
        String tableName = omegaORM.getTableName((Class<? extends OmegaEntity>) cl);
        String tbId = tableName + ".id";
        String contTitle = tableName + ".title";
        String quer = String.format(templ,
                tableName,
                tbId,
                tbId,
                contTitle,
                tbId
        );
        List<CardDTO<T>> cardDTOS = searchRepository.searchByTitle(cl, title, quer);

        return cardDTOS;

    }




    //select * from anime join owner_of_content on owner_of_content.contentid = anime.id
    //                    left join content_tags on content_tags.contentid = anime.id
    //                    left join tags ON tags.id = content_tags.tagid
    //                    left join avg_rating ON avg_rating.contentid = anime.id where owner_of_content.userid = 9
    public <T extends Content> List<CardDTO<T>> searchByTitleCreator(Class<T> cl, String title, User user) {
        String templ = "select * from %s join owner_of_content on owner_of_content.contentid = %s\n" +
                "                    left join content_tags on content_tags.contentid = %s\n" +
                "                    left join tags ON tags.id = content_tags.tagid\n" +
                "                    left join avg_rating ON avg_rating.contentid = %s where owner_of_content.userid = %s ";

        String tableName = omegaORM.getTableName((Class<? extends OmegaEntity>) cl);
        String tbId = tableName + ".id";
        String quer = String.format(templ,
                tableName,
                tbId,
                tbId,
                tbId,
                user.getId()
        );
        System.out.println(quer);
        List<CardDTO<T>> cardDTOS = searchRepository.searchByTitle(cl, title, quer);
        return cardDTOS;
//        return null;

    }
}
