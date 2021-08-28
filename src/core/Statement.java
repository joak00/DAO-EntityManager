package core;

import java.sql.PreparedStatement;
import java.sql.SQLException;


public interface Statement<T> {
    void run(PreparedStatement prStatement, T entity) throws SQLException;
}
