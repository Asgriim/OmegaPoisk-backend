package org.omega.omegapoisk.service;

import org.omega.omegapoisk.data.AddContentDTO;
import org.omega.omegapoisk.data.CardDTO;
import org.omega.omegapoisk.entity.*;
import org.omega.omegapoisk.repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Service
public class ContentService {

    @Autowired
    private ContentRepository contentRepository;

    public List<Tag> getAllTags() {
        return contentRepository.getAllTags();
    }

    public void addAnime(AddContentDTO<Anime> contentDTO, MultipartFile file, User user) {
        contentRepository.addAnime(contentDTO,file,user);
    }

    public void addComic(AddContentDTO<Comic> contentDTO, MultipartFile file, User user) {
        contentRepository.addComic(contentDTO,file,user);
    }

    public void addGame(AddContentDTO<Game> contentDTO, MultipartFile file, User user) {
        contentRepository.addGame(contentDTO,file,user);
    }

    public void addTvShow(AddContentDTO<TvShow> contentDTO, MultipartFile file, User user) {
        contentRepository.addTvShow(contentDTO,file,user);
    }

    public void addMovie(AddContentDTO<Movie> contentDTO, MultipartFile file, User user) {
        contentRepository.addMovie(contentDTO,file,user);
    }

    public <T extends Content> CardDTO<T> getCardById(Class<? extends OmegaEntity> cl, int id) {
        return contentRepository.getCardById(cl, id);
    }

    public List<Tag> getContentTags(int contendId) {
        return contentRepository.getContentTags(contendId);
    }

    public <T extends Content> List<CardDTO<T>> getAllCardsOfContent(Class<? extends OmegaEntity> cl) {
        return contentRepository.getAllCards(cl);
    }

    public <T extends Content> List<CardDTO<T>> getOwnerCards(Class<? extends OmegaEntity> cl, int userId) {
        return contentRepository.getOwnerCards(cl, userId);
    }
}
