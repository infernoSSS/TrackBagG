package db;

public class Config {
    private static Config instance;
    private final String username = "root";
    private final String password = "ADMIN";
    private final String connectionUrl = "jdbc:mysql://localhost:3306/coursework";

    public static Config getConfig() {
        if(instance == null){
            instance = new Config();
        }
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getConnectionUrl() {
        return connectionUrl;
    }
}
