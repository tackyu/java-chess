package chess.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface DataProcessor {
    int SUCCESS = 1;

    int process(ResultSet resultSet) throws SQLException;
}
