CREATE SEQUENCE IF NOT EXISTS content_id_seq;
-- Таблица user
CREATE TABLE IF NOT EXISTS user_ (
                                     id SERIAL PRIMARY KEY,
                                     email TEXT NOT NULL,
                                     login TEXT NOT NULL UNIQUE,
                                     pass TEXT NOT NULL,
                                     role INT
);

