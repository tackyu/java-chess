CREATE table chess_game
(
    id    BIGINT NOT NULL AUTO_INCREMENT,
    state varchar(20),
    PRIMARY KEY (id)
);

CREATE table chessboard
(
    chess_game_id BIGINT,
    position      varchar(2),
    piece         varchar(20)
);

