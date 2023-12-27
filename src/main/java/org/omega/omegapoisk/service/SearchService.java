package org.omega.omegapoisk.service;

import org.omega.omegapoisk.data.CardDTO;
import org.omega.omegapoisk.entity.Content;
import org.omega.omegapoisk.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    SearchRepository searchRepository;

    public <T extends Content> List<CardDTO<T>> searchByTitle(Class<T> cl, String title) {
        String templ = "select * from %s left join content_tags on content_tags.contentid = %s\n" +
                "                    left join tags ON tags.id = content_tags.tagid\n" +
                "                    left join avg_rating ON avg_rating.contentid = %s where  lower(%s) ";

        List<CardDTO<T>> cardDTOS = searchRepository.searchByTitle(cl, title, templ);
        return cardDTOS;

    }
}
