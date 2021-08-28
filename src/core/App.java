package core;


import domain.Ingredient;
import domain.Pizza;
import domain.PizzaDAO;

public class App {
    public static void main(String[] args) throws Exception {
        Pizza pizza = new Pizza("Carbonara", "photoUrl");
        PizzaDAO pizzaDao = new PizzaDAO();
        Pizza pizza2 = new Pizza ("Hawaiana", "photo");
        Ingredient pepperoni = new Ingredient("pepperoni", 3.0);
        Ingredient base = new Ingredient ("base", 3.0);

        pizza.addIngredient(pepperoni);
        pizza.addIngredient(base);

        pizzaDao.insert(pizza);
        pizzaDao.insert(pizza2);

        pizza.setName("Pepperoni");
        pizzaDao.update(pizza);
        
        System.out.println("Nombre: "+(pizzaDao.select(pizza.getId()).get().getName())+" - Url: "+pizzaDao.select(pizza.getId()).get().getUrl());
        
        //pizzaDao.delete(pizza);
       
    }
}
