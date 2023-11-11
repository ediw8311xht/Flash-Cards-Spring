
CREATE TABLE IF NOT EXISTS Users
(
    username   CHAR(15) PRIMARY KEY,
    password   TEXT NOT NULL,
    flashsets  TEXT,
    enabled    BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS Flashcardset
(
    id         CHAR(15) PRIMARY KEY,
    name       TEXT,
    flashcards TEXT,
    owner      CHAR(15) REFERENCES Users(username)
);

