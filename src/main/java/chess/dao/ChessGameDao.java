package chess.dao;

import chess.domain.chessboard.State;

public class ChessGameDao {
    private final DaoExecutor executor;

    public ChessGameDao() {
        this.executor = new DaoExecutor();
    }

    public void addGame() {
        final var query = "INSERT INTO chess_game(state) VALUES (?)";
        executor.changeDB(query, preparedStatement
                -> preparedStatement.setString(1, State.GAME_ONGOING.name()));
    }

    public int findGameId() {
        final var query = "SELECT id FROM chess_game WHERE state = ?";
        return executor.processData(query, preparedStatement ->
                        preparedStatement.setString(1, State.GAME_ONGOING.name())
                , resultSet -> {
                    if (resultSet.next()) {
                        return resultSet.getInt("id");
                    }
                    return -1;
                });
    }

    public void delete() {
        final var query = "DELETE from chess_game where state=?";
        executor.changeDB(query, preparedStatement
                -> preparedStatement.setString(1, State.GAME_ONGOING.name())
        );
    }
}
