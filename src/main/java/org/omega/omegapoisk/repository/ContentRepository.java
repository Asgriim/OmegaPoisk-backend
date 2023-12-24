package org.omega.omegapoisk.repository;

import org.omega.omegapoisk.ORM.OmegaORM;
import org.omega.omegapoisk.data.AddContentDTO;
import org.omega.omegapoisk.data.CardDTO;
import org.omega.omegapoisk.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
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


    private String createFileName(MultipartFile file) {
        String prefix = System.currentTimeMillis() + "_";
        return prefix + file.getOriginalFilename();
    }

    private String saveFile(MultipartFile file, String dir) throws IOException {
        String path = System.getProperty("user.home") + "/omega/" + dir + "/" + createFileName(file);
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        fileOutputStream.write(file.getInputStream().readAllBytes());
        fileOutputStream.close();
        return path;
    }

    public void addAnime(AddContentDTO<Anime> contentDTO, MultipartFile file, User user) {
        try {
            Anime content = contentDTO.getContent();
            String path = saveFile(file, content.TableName());
            String query = "select add_comic_as_creator(" + "?, nextval('content_id_seq')::int, ?, ?, ?, ?, ?" + ")";
            System.out.println(query);
            jdbcTemplate.queryForRowSet(query, user.getId(), content.getTitle(), content.getDescription(),
                    content.getSeriesNum(),path, contentDTO.getStudio()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void addComic(AddContentDTO<Comic> contentDTO, MultipartFile file, User user) {
        try {
            Comic content = contentDTO.getContent();
            String path = saveFile(file, content.TableName());
            String query = "select add_comic_as_creator(" + "?, nextval('content_id_seq')::int, ?, ?, ?, ?, ?" + ")";
            System.out.println(query);
            jdbcTemplate.queryForRowSet(query, user.getId(), content.getTitle(), content.getDescription(),
                    content.isColored(),path, contentDTO.getStudio()
                    );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void addGame(AddContentDTO<Game> contentDTO, MultipartFile file, User user) {
        try {
            Game content = contentDTO.getContent();
            String path = saveFile(file, content.TableName());
            String query = "select add_game_as_creator(" + "?, nextval('content_id_seq')::int, ?, ?, ?, ?, ?" + ")";
            System.out.println(query);
            jdbcTemplate.queryForRowSet(query, user.getId(), content.getTitle(), content.getDescription(),
                    content.isFree(), path, contentDTO.getStudio()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addTvShow(AddContentDTO<TvShow> contentDTO, MultipartFile file, User user) {
        try {
            TvShow content = contentDTO.getContent();
            String path = saveFile(file, content.TableName());
            String query = "select add_tv_show_as_creator(" + "?, nextval('content_id_seq')::int, ?, ?, ?, ?, ?" + ")";
            System.out.println(query);
            jdbcTemplate.queryForRowSet(query, user.getId(), content.getTitle(), content.getDescription(),
                    content.getSeriesNum(), path, contentDTO.getStudio()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addMovie(AddContentDTO<Movie> contentDTO, MultipartFile file, User user) {
        try {
            Movie content = contentDTO.getContent();
            String path = saveFile(file, content.TableName());
            String query = "select add_movie_as_creator(" + "?, nextval('content_id_seq')::int, ?, ?, ?, ?, ?" + ")";
            System.out.println(query);
            jdbcTemplate.queryForRowSet(query, user.getId(), content.getTitle(), content.getDescription(),
                    content.getDuration(), path, contentDTO.getStudio()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
