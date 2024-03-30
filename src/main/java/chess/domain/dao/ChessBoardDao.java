package chess.domain.dao;

import chess.domain.chesspiece.Piece;
import chess.domain.chesspiece.Role;
import chess.domain.position.Position;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class ChessBoardDao {
    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "chess";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void save(int gameId, Map<Position, Piece> board) {
        final var query = "INSERT INTO chessboard VALUES(?, ?, ?)";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            for (Position position : board.keySet()) {
                preparedStatement.setInt(1, gameId);
                preparedStatement.setString(2, position.getName());
                preparedStatement.setString(3, board.get(position).getRole().name());
                preparedStatement.executeUpdate();
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Position, Piece> load(int gameId) {
        Map<Position, Piece> board = new LinkedHashMap<>();
        final var query = "SELECT position,piece  FROM chessboard WHERE chess_game_id = ?";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, gameId);
            final var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Position position = Position.of(resultSet.getString("position"));
                String pieceData = resultSet.getString("piece");
                board.put(position, Role.findType(pieceData));
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        return board;
    }

    public void update(Piece piece, Position position) {
        final var query = "UPDATE chessboard SET piece=? where position=?";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, piece.getRole().name());
            preparedStatement.setString(2, position.getName());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int gameId) {
        final var query = "DELETE from chessboard where chess_game_id=?";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, gameId);
            preparedStatement.executeUpdate();

        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
