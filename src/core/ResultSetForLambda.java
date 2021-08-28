package core;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetForLambda<T>{
    public void run(ResultSet resultSet, T entity) throws SQLException;
}
