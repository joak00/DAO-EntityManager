package domain;
import java.util.UUID;

public class Entity {
    protected UUID id;

    public UUID getId(){
        return this.id;
    }

    public void setId(UUID id){
        this.id = id;
    }
    
    public void validate(){
        try{
            if(this.id == null){
                throw new Exception ("Id is null.");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int hashCode(){
        return id.hashCode();
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof Entity)){
            return false;
        }
        Entity tmp = (Entity) o;
        return tmp.getId().equals(this.getId());
    }
}
