CREATE TABLE roles
(
    id           BIGSERIAL PRIMARY KEY,
    display_name VARCHAR(50) UNIQUE NOT NULL,
    code         VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE users
(
    id       BIGSERIAL PRIMARY KEY,
    email    VARCHAR(70) UNIQUE  NOT NULL,
    password VARCHAR(255) UNIQUE NOT NULL,
    name     VARCHAR(100)        NOT NULL,
    role_id  BIGINT REFERENCES roles (id)
);

CREATE TABLE user_admins
(
    id BIGINT NOT NULL
        CONSTRAINT pk__users_admin__id PRIMARY KEY REFERENCES users (id)
);

CREATE TABLE user_players
(
    score INT8    NOT NULL DEFAULT 0,
    id    BIGINT NOT NULL
        CONSTRAINT pk__users_player__id PRIMARY KEY REFERENCES users (id)
);

CREATE TABLE words
(
    id          BIGSERIAL PRIMARY KEY,
    value       VARCHAR(100) UNIQUE NOT NULL,
    description VARCHAR(200),
    max_scores  INT                 NOT NULL DEFAULT 0,
    status      VARCHAR(50)         NOT NULL
);

CREATE TABLE user_current_words
(
    id          BIGSERIAL PRIMARY KEY,
    player_id   BIGINT  NOT NULL REFERENCES user_players (id),
    word_id     BIGINT  NOT NULL REFERENCES words (id),
    tries_count INT     NOT NULL DEFAULT 0,
    is_last_try BOOLEAN NOT NULL DEFAULT FALSE,
    UNIQUE (player_id, word_id)
);

CREATE TABLE player_word_statistics
(
    id               BIGSERIAL PRIMARY KEY,
    player_id        BIGINT NOT NULL REFERENCES user_players (id),
    word_id          BIGINT NOT NULL REFERENCES words (id),
    best_tries_count INT    NOT NULL DEFAULT 0,
    attempts_failed  INT    NOT NULL DEFAULT 0,
    attempts_success INT    NOT NULL DEFAULT 0,
    scores_achieved  INT    NOT NULL DEFAULT 0,
    UNIQUE (player_id, word_id)
);