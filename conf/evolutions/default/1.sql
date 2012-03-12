# Tasks schema

# --- !Ups

CREATE SEQUENCE story_id_seq;
CREATE TABLE story (
    id integer NOT NULL DEFAULT nextval('story_id_seq'),
    title varchar(255),
    body varchar(2048)
);

CREATE SEQUENCE user_id_seq;
CREATE TABLE user(
    id integer NOT NULL DEFAULT nextval('user_id_seq'),
    login varchar(16),
    password varchar(16)
);

# --- !Downs

DROP TABLE story;
DROP SEQUENCE story_id_seq;
DROP TABLE user;
DROP SEQUENCE user_id_seq;
