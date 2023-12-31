package org.omega.omegapoisk.repository;

import org.omega.omegapoisk.ORM.OmegaORM;
import org.omega.omegapoisk.data.AddContentDTO;
import org.omega.omegapoisk.data.CardDTO;
import org.omega.omegapoisk.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${imageDir.path}")
    private String imageDirPath;

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
        List<CardDTO<T>> cards = omegaORM.getAllCards(cl);
        return cards;
    }

    public <T extends Content> CardDTO<T> getCardById(Class<? extends OmegaEntity> cl, int id) {
        return omegaORM.getCardById(cl,id);
    }

    private String createFileName(MultipartFile file) {
        String prefix = System.currentTimeMillis() + "_";
        return prefix + file.getOriginalFilename();
    }

    private String saveFile(MultipartFile file, String dir) throws IOException {
        String fileName = createFileName(file);
        String path = System.getProperty("user.home") + imageDirPath + dir + "/" + fileName;
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        fileOutputStream.write(file.getInputStream().readAllBytes());
        fileOutputStream.close();
        String outPath = "../img/" + dir + "/" + fileName;
        return outPath;
    }

    public void addAnime(AddContentDTO<Anime> contentDTO, MultipartFile file, User user) {
        try {
            int contentId = omegaORM.nextVal("content_id_seq");
            System.out.println("content  id " + contentId);
            Anime content = contentDTO.getContent();
            String path = saveFile(file, content.TableName());
            String query = "select add_anime_as_creator(" + "?, ?, ?, ?, ?, ?, ?" + ")";
            System.out.println(query);
            jdbcTemplate.queryForRowSet(query, user.getId(),contentId ,content.getTitle(), content.getDescription(),
                    content.getSeriesNum(),path, contentDTO.getStudio()
            );
            addContentTags(contentDTO.getTags(), contentId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void addComic(AddContentDTO<Comic> contentDTO, MultipartFile file, User user) {
        try {
            Comic content = contentDTO.getContent();
            int contentId = omegaORM.nextVal("content_id_seq");
            String path = saveFile(file, content.TableName());
            String query = "select add_comic_as_creator(" + "?, ?, ?, ?, ?, ?, ?" + ")";
            System.out.println(query);
            jdbcTemplate.queryForRowSet(query, user.getId(),contentId, content.getTitle(), content.getDescription(),
                    content.isColored(),path, contentDTO.getStudio()
                    );
            addContentTags(contentDTO.getTags(), contentId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void addGame(AddContentDTO<Game> contentDTO, MultipartFile file, User user) {
        try {
            Game content = contentDTO.getContent();
            int contentId = omegaORM.nextVal("content_id_seq");
            String path = saveFile(file, content.TableName());
            String query = "select add_game_as_creator(" + "?, ?, ?, ?, ?, ?, ?" + ")";
            System.out.println(query);
            jdbcTemplate.queryForRowSet(query, user.getId(), contentId ,content.getTitle(), content.getDescription(),
                    content.isFree(), path, contentDTO.getStudio()
            );
            addContentTags(contentDTO.getTags(), contentId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addTvShow(AddContentDTO<TvShow> contentDTO, MultipartFile file, User user) {
        try {
            TvShow content = contentDTO.getContent();
            String path = saveFile(file, content.TableName());
            int contentId = omegaORM.nextVal("content_id_seq");
            String query = "select add_tv_show_as_creator(" + "?, ?, ?, ?, ?, ?, ?" + ")";
            System.out.println(query);
            jdbcTemplate.queryForRowSet(query, user.getId(), contentId, content.getTitle(), content.getDescription(),
                    content.getSeriesNum(), path, contentDTO.getStudio()
            );
            addContentTags(contentDTO.getTags(), contentId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addMovie(AddContentDTO<Movie> contentDTO, MultipartFile file, User user) {
        try {
            Movie content = contentDTO.getContent();
            String path = saveFile(file, content.TableName());
            int contentId = omegaORM.nextVal("content_id_seq");
            String query = "select add_movie_as_creator(" + "?, ?, ?, ?, ?, ?, ?" + ")";
            System.out.println(query);
            jdbcTemplate.queryForRowSet(query, user.getId(),contentId ,content.getTitle(), content.getDescription(),
                    content.getDuration(), path, contentDTO.getStudio()
            );
            addContentTags(contentDTO.getTags(), contentId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addContentTags(List<Tag> tags, int contentId) {
        for (var tag : tags) {
            jdbcTemplate.update("INSERT INTO content_tags(contentid, tagid) VALUES (?,?)", contentId, tag.getId());
        }
    }

    public <T extends Content> List<CardDTO<T>> getOwnerCards(Class<? extends OmegaEntity> cl, int userId) {
        String table = omegaORM.getTableName(cl);
        String tbId = table + ".id";
        String req = String.format("select * from %s join owner_of_content on owner_of_content.contentid = %s\n" +
                "    left join content_tags on content_tags.contentid = %s\n" +
                "                    left join tags ON tags.id = content_tags.tagid\n" +
                "                    left join avg_rating ON avg_rating.contentid = content_tags.contentid where owner_of_content.userid = %s order by %s",
                table,
                tbId,
                tbId,
                userId,
                tbId);
        System.out.println(req);
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(req);
        return omegaORM.getCards(cl,sqlRowSet);
    }


    public Studio getStudioByContentId(int id) {
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("select * from studio_contents join studio on studio_contents.studioid = studio.id where contentid = ?", id);
        List<Studio> listFromRowSet = (List<Studio>) omegaORM.getListFromRowSet(Studio.class, sqlRowSet);
        if (listFromRowSet.isEmpty()) {
            return null;
        }
        return listFromRowSet.get(0);

    }


    public boolean checkOwner(User user, int contentId) {
        Integer i = jdbcTemplate.queryForObject("select count(*) from owner_of_content where contentid = ? and userid = ?", Integer.class, contentId, user.getId());
        return i > 0;
    }

    public <T extends OmegaEntity> void deleteContentById(Class<T> cl, int id) {
        omegaORM.deleteById(cl,id);
    }

    public void deleteContentTags(List<Tag> tags, int contentId) {
        String templ = "delete from content_tags where contentid = ? and tagid = ?";

        for (var tag : tags) {
            jdbcTemplate.update(templ, contentId, tag.getId());
        }

    }

    public void deleteAllContentTags(int contentId) {
        String templ = "delete from content_tags where contentid = ?";
        jdbcTemplate.update(templ, contentId);
    }

    public void updateContentStudio(String studio, int contentId)  {
        String req = "select update_content_studio(?,?)";
        jdbcTemplate.queryForRowSet(req,contentId, studio);
    }


    public <T extends Content> void  updateContent(AddContentDTO<T> contentDTO, MultipartFile file, User user) {
        try {
            T content = contentDTO.getContent();
            String tableName = omegaORM.getTableName((Class<? extends OmegaEntity>) content.getClass());
            String path;
            if (file.isEmpty()) {
                System.out.println("empty file");
                Content tempE = omegaORM.getEntityById((Class<? extends OmegaEntity>) content.getClass(), content.getId());
                path = tempE.getPosterPath();
                System.out.println(path);
            }
            else {
                path = saveFile(file, tableName);
            }
            int contentId = content.getId();
            String query = "select update_"+ tableName +"(" + "?,?,?,?,?,?" + ")";
            System.out.println(query);
            Object contentExtraField = omegaORM.getContentExtraField(content);
            jdbcTemplate.queryForRowSet(query,
                    user.getId(),
                    contentId,
                    content.getTitle(),
                    content.getDescription(),
                    path,
                    contentExtraField
                    );

            deleteAllContentTags(contentId);
            addContentTags(contentDTO.getTags(), contentId);
            updateContentStudio(contentDTO.getStudio(),contentId);
//            throw new IOException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
