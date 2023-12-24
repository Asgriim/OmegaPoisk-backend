package org.omega.omegapoisk.service;

import org.omega.omegapoisk.data.CardDTO;
import org.omega.omegapoisk.data.ContentDTO;
import org.omega.omegapoisk.entity.Content;
import org.omega.omegapoisk.entity.OmegaEntity;
import org.omega.omegapoisk.entity.Tag;
import org.omega.omegapoisk.repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ContentService {

    @Autowired
    private ContentRepository contentRepository;

    public List<Tag> getContentTags(int contendId) {
        return contentRepository.getContentTags(contendId);
    }

    public <T extends Content> List<CardDTO<T>> getAllCardsOfContent(Class<? extends OmegaEntity> cl) {
        return contentRepository.getAllCards(cl);
    }
}
