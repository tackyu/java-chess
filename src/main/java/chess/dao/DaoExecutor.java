package chess.dao;

import java.sql.*;

public class DaoExecutor {
    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "chess";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private PreparedStatement getPreparedStatement(String query) throws SQLException {
        return getConnection().prepareStatement(query);
    }

    public void changeDB(String query, StatementSetter setter) {
        try (final var preparedStatement = getPreparedStatement(query)) {
            setter.setting(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int processData(String query, StatementSetter setter, DataProcessor processor) {
        try (final var preparedStatement = getPreparedStatement(query)) {
            setter.setting(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            return processor.process(resultSet);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
