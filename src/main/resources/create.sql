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
                                    isFree bool NOT NULL
);

-- Таблица tv_show
CREATE TABLE IF NOT EXISTS tv_show (
                                       id INT PRIMARY KEY,
                                       title TEXT,
                                       description TEXT,
                                       seriesNum INT NOT NULL CHECK ( tv_show.seriesNum >0 )
);

CREATE TABLE IF NOT EXISTS comic (
                                     id INT PRIMARY KEY,
                                     title TEXT,
                                     description TEXT,
                                     isColored BOOL NOT NULL
);

CREATE TABLE IF NOT EXISTS movie (
                                     id INT PRIMARY KEY,
                                     title TEXT,
                                     description TEXT,
                                     duration INT NOT NULL CHECK ( duration > 0 )
);

-- Таблица anime
CREATE TABLE IF NOT EXISTS anime (
                                     id INT PRIMARY KEY,
                                     title TEXT,
                                     description TEXT,
                                     seriesNum INT NOT NULL CHECK ( seriesNum > 0 )
);

-- Таблица content
CREATE TABLE IF NOT EXISTS content (
                                       id INT PRIMARY KEY,
                                       type TEXT NOT NULL
);


CREATE OR REPLACE FUNCTION add_to_content()
    RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO content
    VALUES (NEW.id, TG_ARGV[0]);
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