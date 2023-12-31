CREATE SEQUENCE IF NOT EXISTS content_id_seq;
-- Таблица user
CREATE TABLE IF NOT EXISTS user_ (
                                     id SERIAL PRIMARY KEY,
                                     email TEXT NOT NULL,
                                     login TEXT NOT NULL UNIQUE,
                                     pass TEXT NOT NULL,
                                     role INT
);

CREATE TABLE IF NOT EXISTS game (
                                    id INT PRIMARY KEY,
                                    title TEXT,
                                    description TEXT,
                                    isFree BOOL NOT NULL,
                                    posterPath TEXT
);

-- Таблица tv_show
CREATE TABLE IF NOT EXISTS tv_show (
                                       id INT PRIMARY KEY,
                                       title TEXT,
                                       description TEXT,
                                       seriesNum INT NOT NULL CHECK ( tv_show.seriesNum >0 ),
                                       posterPath TEXT
);

CREATE TABLE IF NOT EXISTS comic (
                                     id INT PRIMARY KEY,
                                     title TEXT,
                                     description TEXT,
                                     isColored BOOL NOT NULL,
                                     posterPath TEXT
);

CREATE TABLE IF NOT EXISTS movie (
                                     id INT PRIMARY KEY,
                                     title TEXT,
                                     description TEXT,
                                     duration INT NOT NULL CHECK ( duration > 0 ),
                                     posterPath TEXT
);

-- Таблица anime
CREATE TABLE IF NOT EXISTS anime (
                                     id INT PRIMARY KEY,
                                     title TEXT,
                                     description TEXT,
                                     seriesNum INT NOT NULL CHECK ( seriesNum > 0 ),
                                     posterPath TEXT
);

-- Таблица content
CREATE TABLE IF NOT EXISTS content (
                                       id INT PRIMARY KEY,
                                       type TEXT NOT NULL
);


CREATE TABLE IF NOT EXISTS rating (
                                      id SERIAL PRIMARY KEY,
                                      value INT CHECK ( value > 0 and value < 11),
                                      userId INT,
                                      contentId INT,
                                      FOREIGN KEY (userId) REFERENCES user_ (id) ON DELETE CASCADE ,
                                      FOREIGN KEY (contentId) REFERENCES content (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS avg_rating (
    avgRate FLOAT,
    contentId INT,
    FOREIGN KEY (contentId) REFERENCES content (id) ON DELETE CASCADE
);

-- Таблица tags
CREATE TABLE IF NOT EXISTS tags (
                                    id SERIAL PRIMARY KEY,
                                    name TEXT NOT NULL
);

-- Таблица content_tags
CREATE TABLE IF NOT EXISTS  content_tags (
                                             contentId INT NOT NULL,
                                             tagId INT NOT NULL,
                                             FOREIGN KEY (contentId) REFERENCES content (id) ON DELETE CASCADE ,
                                             FOREIGN KEY (tagId) REFERENCES tags (id) ON DELETE CASCADE
);

-- Таблица review
CREATE TABLE IF NOT EXISTS review
(
    id        SERIAL PRIMARY KEY,
    txt      TEXT,
    userId    INT,
    contentId INT,
    FOREIGN KEY (userId) REFERENCES user_ (id) ON DELETE CASCADE,
    FOREIGN KEY (contentId) REFERENCES content (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS owner_of_content(
                                               userId    INT,
                                               contentId INT,
                                               FOREIGN KEY (userId) REFERENCES user_ (id) ON DELETE CASCADE,
                                               FOREIGN KEY (contentId) REFERENCES content (id) ON DELETE CASCADE
);

-- Таблица studio
CREATE TABLE IF NOT EXISTS studio (
                                      id SERIAL PRIMARY KEY,
                                      name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS studio_contents (
                                               studioId INT,
                                               contentId INT,
                                               FOREIGN KEY (studioId) REFERENCES studio(id) ON DELETE CASCADE,
                                               FOREIGN KEY (contentId) REFERENCES content(id) ON DELETE CASCADE
                                           );

-- Таблица history
CREATE TABLE IF NOT EXISTS history (
                                       id SERIAL PRIMARY KEY,
                                       userId INT NOT NULL,
                                       contentId INT NOT NULL,
                                       date TIMESTAMP,
                                       title TEXT,
                                       description TEXT,
                                       posterPath TEXT,
                                       FOREIGN KEY (userId) REFERENCES user_ (id) ON DELETE CASCADE ,
                                       FOREIGN KEY (contentId) REFERENCES content (id) ON DELETE CASCADE
);



-- Таблица review
CREATE TABLE IF NOT EXISTS review (
                                      id SERIAL PRIMARY KEY,
                                      txt TEXT,
                                      userId INT NOT NULL,
                                      contentId INT NOT NULL,
                                      FOREIGN KEY (userId) REFERENCES user_ (id) ON DELETE CASCADE ,
                                      FOREIGN KEY (contentId) REFERENCES content (id) ON DELETE CASCADE
);

-- Таблица list
CREATE TABLE IF NOT EXISTS list (
                                    id SERIAL PRIMARY KEY,
                                    name TEXT NOT NULL
);

-- Таблица user_list
CREATE TABLE IF NOT EXISTS user_list (
                                         userId INT NOT NULL,
                                         listId INT NOT NULL,
                                         contentId INT NOT NULL,
                                         contentTitle TEXT NOT NULL,
                                         contentType TEXT,
                                         FOREIGN KEY (contentId) REFERENCES content (id) ON DELETE CASCADE ,
                                         FOREIGN KEY (userId) REFERENCES user_ (id) ON DELETE CASCADE ,
                                         FOREIGN KEY (listId) REFERENCES list (id) ON DELETE CASCADE
);




CREATE OR REPLACE FUNCTION add_to_content()
    RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO content
    VALUES (NEW.id, TG_ARGV[0]);
    INSERT INTO avg_rating VALUES (0, NEW.id);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE  TRIGGER add_anime
    AFTER INSERT ON anime
    FOR EACH ROW EXECUTE PROCEDURE add_to_content('anime');

CREATE OR REPLACE  TRIGGER add_game
    AFTER INSERT ON game
    FOR EACH ROW EXECUTE PROCEDURE add_to_content('game');

CREATE OR REPLACE TRIGGER add_tv_show
    AFTER INSERT ON tv_show
    FOR EACH ROW EXECUTE PROCEDURE add_to_content('tv_show');

CREATE OR REPLACE  TRIGGER add_comic
    AFTER INSERT ON comic
    FOR EACH ROW EXECUTE PROCEDURE add_to_content('comic');

CREATE OR REPLACE TRIGGER add_movies
    AFTER INSERT ON movie
    FOR EACH ROW EXECUTE PROCEDURE add_to_content('movie');



CREATE OR REPLACE FUNCTION calc_avg_rate()
    RETURNS TRIGGER AS $$
BEGIN
    UPDATE avg_rating
    SET avgrate = (SELECT AVG(rating.value) FROM rating WHERE rating.contentId = NEW.contentId)
        WHERE contentId = NEW.contentId;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER add_rate
    AFTER INSERT ON rating
    FOR EACH ROW EXECUTE PROCEDURE calc_avg_rate();


CREATE OR REPLACE FUNCTION remove_from_content()
    RETURNS TRIGGER AS $$
BEGIN
    DELETE FROM content
    WHERE (OLD.id = content.id);
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER remove_anime_trig
    AFTER DELETE ON anime
    FOR EACH ROW EXECUTE PROCEDURE remove_from_content();

CREATE OR REPLACE TRIGGER remove_comic_trig
    AFTER DELETE ON comic
    FOR EACH ROW EXECUTE PROCEDURE remove_from_content();

CREATE OR REPLACE TRIGGER remove_game_trig
    AFTER DELETE ON game
    FOR EACH ROW EXECUTE PROCEDURE remove_from_content();

CREATE OR REPLACE TRIGGER remove_movie_trig
    AFTER DELETE ON movie
    FOR EACH ROW EXECUTE PROCEDURE remove_from_content();

CREATE OR REPLACE TRIGGER remove_tb_show_trig
    AFTER DELETE ON tv_show
    FOR EACH ROW EXECUTE PROCEDURE remove_from_content();