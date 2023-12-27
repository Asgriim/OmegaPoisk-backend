CREATE INDEX IF NOT EXISTS title_anime ON anime USING hash (title);
CREATE INDEX IF NOT EXISTS title_movie ON movie USING hash (title);
CREATE INDEX IF NOT EXISTS title_tv_show ON tv_show USING hash (title);
CREATE INDEX IF NOT EXISTS title_game ON game USING hash (title);
CREATE INDEX IF NOT EXISTS title_comic ON comic USING hash (title);

CREATE INDEX IF NOT EXISTS tag_id_ind ON tags USING hash (id);
CREATE INDEX IF NOT EXISTS tag_name_ind ON tags USING hash (name);
CREATE INDEX IF NOT EXISTS content_tags_id_ind ON content_tags USING hash (tagid);
CREATE INDEX IF NOT EXISTS content_tags_content_ind ON content_tags USING hash (contentid);
CREATE INDEX IF NOT EXISTS content_id_ind ON content USING hash (id);
CREATE INDEX IF NOT EXISTS content_typet_ind ON content USING hash (type);
CREATE INDEX IF NOT EXISTS user_index ON user_ USING hash (login);
