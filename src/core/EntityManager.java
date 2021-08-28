package core;

import java.util.ArrayList;
import java.util.List;
import domain.Entity;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;


public class EntityManager implements IEntityManager{
    
    private List<IRunnables> runnables = new ArrayList<IRunnables>();
    private IConfiguration configuration = null;
    
    private EntityManager(IConfiguration configuration){
        this.configuration = configuration;
    }
    
    public static IEntityManager buildConnection(IConfiguration configuration){
        return new EntityManager(configuration);
    }
    
    @Override
    public void save(){
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(
            this.configuration.getUrl(),
            this.configuration.getUser(),
            this.configuration.getPassword()
            );
            connection.setAutoCommit(false);
            for(IRunnables runnable: this.runnables){
                PreparedStatement prStatement = connection.prepareStatement(runnable.getSQL());
                runnable.run(prStatement);
                prStatement.executeUpdate();
            }
            connection.commit();
        }
        catch(SQLException e){
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }finally{
            try {
                if(!connection.isClosed()){
                    connection.close();
                }
                runnables.clear();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }        
    }
    
    @Override
    public <T> IEntityManager addStatement(T entity, String sql, Statement<T> statement) {
        IRunnables runnable = new Runnables<T>(sql, entity, statement);
        this.runnables.add(runnable);
        return this;
    }
    
    @Override
    public <T> IEntityManager addRangeStatement(Iterable<T> iterable, String sql, Statement<T> statement) {
        for(T t: iterable){
            IRunnables runable = new Runnables<T>(sql, t, statement);
            this.runnables.add(runable);
        }
        return this;
    }   
    
    @Override
    public <T extends Entity> T select(Class<T> clazz, ResultSetForLambda<T> resultSetForLambda) {
        Connection connection = null;
        T entity = null;
        try{
            connection = DriverManager.getConnection(
            this.configuration.getUrl(),
            this.configuration.getUser(),
            this.configuration.getPassword()
            );
            for(IRunnables runnable: this.runnables){
                PreparedStatement prStatement = connection.prepareStatement(runnable.getSQL());
                runnable.run(prStatement);
                ResultSet resultSet = prStatement.executeQuery();
                if (resultSet.next()) {
                entity = clazz.getConstructor().newInstance();
                resultSetForLambda.run(resultSet, entity);
                }
            }
        }  catch(InstantiationException  | SQLException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e){
            e.printStackTrace();
        }finally{
            try {
                if(!connection.isClosed()){
                    connection.close();
                }
                this.runnables.clear();
            }catch(SQLException e){
                e.printStackTrace();
            }
        } 
        return entity;
    }
    
}
