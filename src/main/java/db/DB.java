package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    private static DB instance;
    private Connection connection;

    public static DB getDB(){
        if(instance == null){
            instance = new DB();
        }
        return instance;
    }

    private DB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection(Config.getConfig().getConnectionUrl(),
                    Config.getConfig().getUsername(),
                    Config.getConfig().getPassword());
        }catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public UsersDB getUsersDB(){
        return UsersDB.getDB();
    }

    public UsersProjectsDB getUsersProjectsDB(String userName){
        return UsersProjectsDB.getUsersProjectsDB(userName);
    }

    public ProjectDB getProjectDB(){
        return ProjectDB.getDB();
    }

    public BugsDB getBugsDB() {
        return BugsDB.getDB();
    }

    public AnswersDB getAnswersDb(){
        return AnswersDB.getDB();
    }
}
