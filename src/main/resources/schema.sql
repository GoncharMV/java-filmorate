DROP ALL OBJECTS DELETE FILES;

create table MPA
(
    MPA_ID   INTEGER auto_increment PRIMARY KEY ,
    MPA_NAME CHARACTER VARYING(5) not null
);

CREATE TABLE GENRE (
    GENRE_ID LONG GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    NAME VARCHAR(64) NOT NULL
);

INSERT INTO MPA (MPA_NAME) VALUES ('G');
INSERT INTO MPA (MPA_NAME) VALUES ('PG');
INSERT INTO MPA (MPA_NAME) VALUES ('PG-13');
INSERT INTO MPA (MPA_NAME) VALUES ('R');
INSERT INTO MPA (MPA_NAME) VALUES ('NC-17');

INSERT INTO GENRE (NAME) VALUES ('Комедия');
INSERT INTO GENRE (NAME) VALUES ('Драма');
INSERT INTO GENRE (NAME) VALUES ('Мультфильм');
INSERT INTO GENRE (NAME) VALUES ('Триллер');
INSERT INTO GENRE (NAME) VALUES ('Документальный');
INSERT INTO GENRE (NAME) VALUES ('Боевик');

create table USER_FMR
(
    USER_ID    BIGINT auto_increment primary key ,
    USER_NAME  CHARACTER VARYING(50),
    LOGIN      CHARACTER VARYING(50) not null,
    EMAIL      CHARACTER VARYING(64) not null,
    BIRTHDAY   DATE,
    FILM_LIKED BIGINT
);

create table FILMS
(
    FILM_ID      BIGINT auto_increment primary key,
    FILM_NAME    CHARACTER VARYING(50),
    DESCRIPTION  CHARACTER VARYING(200) not null,
    RELEASE_DATE DATE,
    DURATION     INTEGER,
    MPA_ID       INTEGER,
    GENRE_ID     INTEGER,
    FOREIGN KEY (MPA_ID) references MPA(MPA_ID),
    FOREIGN KEY (GENRE_ID) references GENRE(GENRE_ID)
);

create table FILMS_GENRES (
    film_id  int NOT NULL,
    genre_id int NOT NULL,
    PRIMARY KEY (film_id, genre_id),
    FOREIGN KEY (film_id) references FILMS(FILM_ID),
    FOREIGN KEY (genre_id) references GENRE(GENRE_ID)
);

create table FILMS_USERS
(
    film_id  int NOT NULL,
    user_id int NOT NULL,
    PRIMARY KEY (film_id, user_id),
    FOREIGN KEY (film_id) references FILMS(FILM_ID),
    FOREIGN KEY (user_id) references USER_FMR(USER_ID)
);

create table FRIENDS (
    user_id    int NOT NULL,
    friends_id int NOT NULL,
    PRIMARY KEY (user_id, friends_id),
    FOREIGN KEY (user_id) references USER_FMR(USER_ID)
);

alter table USER_FMR alter COLUMN user_id RESTART with 1;
alter table FILMS alter COLUMN film_id RESTART with 1;