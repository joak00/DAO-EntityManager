package core;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface IRunnables {
    public String getSQL();
    public void run(final PreparedStatement prStatement) throws SQLException;
}
