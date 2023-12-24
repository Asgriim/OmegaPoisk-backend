CREATE OR REPLACE FUNCTION create_studio_if_not_exist(_studio_name TEXT)
    RETURNS INT AS $$
    DECLARE
        out INT;
BEGIN
    IF (SELECT COUNT(*) FROM studio where name = _studio_name) <= 0 THEN
        out := nextval('studio_id_seq');
        insert into studio(id,name) values (out, _studio_name);
        return out;
    end if;
    return (select id from studio where name = _studio_name limit 1)::INT;
END
$$ LANGUAGE plpgsql;




CREATE OR REPLACE FUNCTION add_anime_as_creator(
    _user_id INT,
    _content_id INT,
    _title TEXT,
    _description TEXT,
    _series_num INT,
    _poster_path text,
    _studio_name TEXT)

    RETURNS VOID AS $$
BEGIN
    insert into anime(id, title, description, seriesNum, posterpath)
        VALUES (_content_id,_title,_description,_series_num, _poster_path);

    insert into owner_of_content(userId, contentId)
        VALUES (_user_id, _content_id);

    insert into studio_contents(studioid, contentid)
        VALUES (create_studio_if_not_exist(_studio_name), _content_id);

END;
$$ LANGUAGE plpgsql;



CREATE OR REPLACE FUNCTION add_comic_as_creator(
    _user_id INT,
    _content_id INT,
    _title TEXT,
    _description TEXT,
    _is_colored BOOL,
    _poster_path text,
    _studio_name TEXT)

    RETURNS VOID AS $$
BEGIN
    insert into comic(id, title, description, iscolored, posterpath)
    VALUES (_content_id,_title,_description, _is_colored, _poster_path);

    insert into owner_of_content(userId, contentId)
    VALUES (_user_id, _content_id);

    insert into studio_contents(studioid, contentid)
    VALUES (create_studio_if_not_exist(_studio_name), _content_id);

END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION add_game_as_creator(
    _user_id INT,
    _content_id INT,
    _title TEXT,
    _description TEXT,
    _is_free BOOL,
    _poster_path text,
    _studio_name TEXT)

    RETURNS VOID AS $$
BEGIN
    insert into game(id, title, description, isfree, posterpath)
    VALUES (_content_id,_title,_description, _is_free, _poster_path);

    insert into owner_of_content(userId, contentId)
    VALUES (_user_id, _content_id);

    insert into studio_contents(studioid, contentid)
    VALUES (create_studio_if_not_exist(_studio_name), _content_id);

END;
$$ LANGUAGE plpgsql;



CREATE OR REPLACE FUNCTION add_movie_as_creator(
    _user_id INT,
    _content_id INT,
    _title TEXT,
    _description TEXT,
    _duration INT,
    _poster_path text,
    _studio_name TEXT)

    RETURNS VOID AS $$
BEGIN
    insert into movie(id, title, description, duration, posterpath)
    VALUES (_content_id,_title,_description, _duration, _poster_path);

    insert into owner_of_content(userId, contentId)
    VALUES (_user_id, _content_id);

    insert into studio_contents(studioid, contentid)
    VALUES (create_studio_if_not_exist(_studio_name), _content_id);

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION add_tv_show_as_creator(
    _user_id INT,
    _content_id INT,
    _title TEXT,
    _description TEXT,
    _series_num INT,
    _poster_path text,
    _studio_name TEXT)

    RETURNS VOID AS $$
BEGIN
    insert into tv_show(id, title, description, seriesnum, posterpath)
    VALUES (_content_id,_title,_description, _series_num, _poster_path);

    insert into owner_of_content(userId, contentId)
    VALUES (_user_id, _content_id);

    insert into studio_contents(studioid, contentid)
    VALUES (create_studio_if_not_exist(_studio_name), _content_id);

END;
$$ LANGUAGE plpgsql;
