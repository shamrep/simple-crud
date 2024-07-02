DROP DATABASE IF EXISTS quizzes;

CREATE DATABASE quizzes
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'English_Europe.1252'
    LC_CTYPE = 'English_Europe.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

CREATE TABLE app_user (
  id integer PRIMARY KEY,
  email varchar NOT NULL UNIQUE,
  password varchar NOT NULL
);

INSERT INTO app_user( id, email, password)
VALUES (1, 'test@gmail.com', 'password_1');