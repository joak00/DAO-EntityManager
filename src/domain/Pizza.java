package domain;

import java.util.Set;
import java.util.HashSet;
import java.util.UUID;

public class Pizza extends Entity{
    private String name;
    private String url;
    private final Set<Ingredient> ingredients = new HashSet<Ingredient>(); 
    private double price;

    public Pizza(){
        
    }
    
    public Pizza(String name, String url){
        this.id = UUID.randomUUID();
        this.name = name;
        this.url = url;
    }
    
    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public String getUrl(){
        return this.url;
    }

    public void addIngredient(Ingredient ingredient){
        ingredients.add(ingredient);
    }

    public void deleteIngredient(Ingredient ingredient){
        ingredients.remove(ingredient);
    }

    public Set<Ingredient> getIngredients(){
        return this.ingredients;
    }

    public Double getPrice(){
        for(Ingredient ing: ingredients){
            this.price += ing.getPrice();
        }
        this.price = 1.2*this.price;
        return this.price;
    }




    @Override
    public void validate(){
        super.validate();
        try {
            if (this.name == null || this.name.equals("")) {
                throw new Exception("Name is empty.");
            }
            if (this.url == null || this.url.equals("")) {
                throw new Exception("Photo url is empty.");
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
