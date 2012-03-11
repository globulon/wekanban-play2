# Tasks schema

# --- !Ups

CREATE SEQUENCE story_id_seq;
CREATE TABLE story (
    id integer NOT NULL DEFAULT nextval('story_id_seq'),
    title varchar(255),
    body varchar(2048)
);

# --- !Downs

DROP TABLE story;
DROP SEQUENCE story_id_seq;