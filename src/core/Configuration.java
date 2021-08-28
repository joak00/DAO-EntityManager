package core;
public class Configuration implements IConfiguration {
    
    private static IConfiguration configuration = null;

    public static IConfiguration getConfiguration(){
        if(configuration == null){
            configuration = new Configuration();
        }
        return configuration;
    }

    public String getUser(){
        return  System.getenv("userMySQL");
    }
    public String getPassword(){
        return System.getenv("passwordMySQL");
    }
    public String getUrl(){
        return System.getenv("urlMySQL");
    }
}
