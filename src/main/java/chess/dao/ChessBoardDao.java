package chess.dao;

import chess.domain.chesspiece.Piece;
import chess.domain.chesspiece.Role;
import chess.domain.position.Position;

import java.util.LinkedHashMap;
import java.util.Map;

public class ChessBoardDao {
    private final DaoExecutor executor;

    public ChessBoardDao() {
        this.executor = new DaoExecutor();
    }

    public void save(int gameId, Map<Position, Piece> board) {
        final var query = "INSERT INTO chessboard VALUES(?, ?, ?)";
        for (Position position : board.keySet()) {
            executor.changeDB(query, preparedStatement -> {
                preparedStatement.setInt(1, gameId);
                preparedStatement.setString(2, position.getName());
                preparedStatement.setString(3, board.get(position).getRole().name());
            });
        }
    }

    public Map<Position, Piece> load(int gameId) {
        Map<Position, Piece> board = new LinkedHashMap<>();
        final var query = "SELECT position,piece  FROM chessboard WHERE chess_game_id = ?";
        executor.processData(query, preparedStatement -> preparedStatement.setInt(1, gameId)
                , resultSet -> {
                    while (resultSet.next()) {
                        Position position = Position.of(resultSet.getString("position"));
                        String pieceData = resultSet.getString("piece");
                        board.put(position, Role.findType(pieceData));
                    }
                    return DataProcessor.SUCCESS;
                });
        return board;
    }

    public void update(Piece piece, Position position) {
        final var query = "UPDATE chessboard SET piece=? where position=?";
        executor.changeDB(query, preparedStatement -> {
            preparedStatement.setString(1, piece.getRole().name());
            preparedStatement.setString(2, position.getName());
        });
    }

    public void delete(int gameId) {
        final var query = "DELETE from chessboard where chess_game_id=?";
        executor.changeDB(query, preparedStatement
                -> preparedStatement.setInt(1, gameId));
    }
}
