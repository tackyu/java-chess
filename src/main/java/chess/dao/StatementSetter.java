package chess.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface StatementSetter {
    void setting(PreparedStatement preparedStatement) throws SQLException;
}
