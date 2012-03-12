# Tasks schema

# --- !Ups

CREATE SEQUENCE user_id_seq;
CREATE TABLE user(
    id integer NOT NULL DEFAULT nextval('user_id_seq'),
    login varchar(16) not null ,
    password varchar(16) not null unique,
    primary key (id)
);


CREATE SEQUENCE story_id_seq;
CREATE TABLE story (
    id integer NOT NULL DEFAULT nextval('story_id_seq'),
    title varchar(255) not null ,
    body varchar(2048) not null ,
    usr_id numeric not null references user(id),
    primary key (id)
);


# --- !Downs

DROP TABLE story;
DROP SEQUENCE story_id_seq;
DROP TABLE user;
DROP SEQUENCE user_id_seq;
