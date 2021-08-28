package core;
import java.sql.*;

public class Runnables<T> implements IRunnables {
    
    private final String sql;
    private final T entity;
    private final Statement<T> statement;

    public Runnables(String sql, T entity, Statement<T> statement){
        this.sql = sql;
        this.entity = entity;
        this.statement = statement;
    }

    public String getSQL(){
        return this.sql;
    }
    
    public void run(PreparedStatement prStatement) throws SQLException{
        this.statement.run(prStatement, this.entity);
    }
}
