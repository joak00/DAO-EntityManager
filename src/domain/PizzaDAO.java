package domain;

import java.util.UUID;

import core.Configuration;
import core.EntityManager;
import core.IDAO;
import core.UuidAdapter;

import java.util.Optional;
import java.util.Set;

public class PizzaDAO implements IDAO<Pizza> {
    
    @Override
    public void insert(Pizza pizza){
        EntityManager
        .buildConnection(Configuration.getConfiguration())
        .addStatement(pizza,  """
        INSERT INTO pizza(id, name, url)
        VALUES
        (?,?,?); """, (prStatement, entity)->{
            prStatement.setBytes(1, UuidAdapter.getBytesFromUUID(pizza.getId()));
            prStatement.setString(2, entity.getName());
            prStatement.setString(3, entity.getUrl());
        }).save();
        Set<Ingredient> listIngredients = pizza.getIngredients();
        EntityManager
        .buildConnection(Configuration.getConfiguration())
        .addRangeStatement(listIngredients, """
        INSERT INTO ingredient (id, name, price)
        VALUES (?, ?, ?);""", (prStatement, entity)->{
            prStatement.setBytes(1, UuidAdapter.getBytesFromUUID(entity.getId()));
            prStatement.setString(2, entity.getName());
            prStatement.setDouble(3, entity.getPrice());
        }).save();
        EntityManager
        .buildConnection(Configuration.getConfiguration())
        .addRangeStatement(listIngredients, """
        INSERT INTO pizza_ingredient (id, id_pizza, id_ingredient)
        VALUES (?, ?, ?);""", (prStatement, entity)-> {
            prStatement.setBytes(1 , UuidAdapter.getBytesFromUUID(UUID.randomUUID()));
            prStatement.setBytes(2, UuidAdapter.getBytesFromUUID(pizza.getId()));
            prStatement.setBytes(3, UuidAdapter.getBytesFromUUID(entity.getId()));
        }).save();
        
    }
    
    @Override
    public void update(Pizza pizza){
        EntityManager
        .buildConnection(Configuration.getConfiguration())
        .addStatement(pizza, """
        UPDATE pizza
        SET name = ?, url = ?
        WHERE id = ?;""", (prStatement, entity)->{
            prStatement.setString(1, entity.getName());
            prStatement.setString(2, entity.getUrl());
            prStatement.setBytes(3,  UuidAdapter.getBytesFromUUID(entity.getId()));
        }).save();
    }
    
    @Override
    public void delete(Pizza pizza){
        EntityManager
        .buildConnection(Configuration.getConfiguration())
        .addStatement(pizza, """
        DELETE FROM pizza
        WHERE id = ?;""", (prStatement, entity)->{
            prStatement.setBytes(1,  UuidAdapter.getBytesFromUUID(entity.getId()));
        }).save();
    }
    
    
    @Override
    public Optional<Pizza> select(UUID id) {
        Pizza pizza = new Pizza();
        Pizza pizzaQuery = 
        EntityManager
        .buildConnection(Configuration.getConfiguration())
        .addStatement(pizza, "SELECT id, name, url FROM pizza WHERE id = ?", (prStament, entity) -> {
            prStament.setBytes(1, UuidAdapter.getBytesFromUUID(id));
        })
        .select(Pizza.class, (resultSet, entity) -> {
            entity.setId(UuidAdapter.getUUIDFromBytes(resultSet.getBytes("id")));
            entity.setName(resultSet.getString("name"));
            entity.setUrl(resultSet.getString("url"));
        });
        return Optional.of(pizzaQuery);
    }
}

