package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class UsersProjectsDB {
    private String username;

    protected static UsersProjectsDB getUsersProjectsDB(String userName) {
        return new UsersProjectsDB(userName);
    }

    private UsersProjectsDB(String userName) {
        this.username = userName;
        try {
            Statement statement = DB.getDB().getConnection().createStatement();
            statement.
                    executeUpdate("create table if not exists" +
                            " users_projects." + userName+ "_projects(uuid CHAR(36) NOT NULL," +
                            " projectName CHAR(8) not null," +
                            " PRIMARY KEY (uuid))");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Map<String, String> getProjects(){
        Map<String, String> result = null;
        PreparedStatement statement;
        ResultSet rs;

        try{
            statement = DB.getDB()
                    .getConnection()
                    .prepareStatement("select count(*) as projects_count from users_projects."+username+"_projects");
            statement.execute();
            rs = statement.getResultSet();
            rs.next();
            int i = rs.getInt("projects_count");
            System.out.println(i);
            if(i == 0){
                return null;
            }
            statement = DB.getDB()
                    .getConnection()
                    .prepareStatement("select * from users_projects."+username+"_projects");
            result = new HashMap();
            statement.execute();
            rs = statement.getResultSet();
            while(rs.next()){
                result.put(rs.getString("uuid"), rs.getString("projectName"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return result;
    }

    public void addProject(String uuid, String projectName){
        try {
            PreparedStatement statement =
                    DB
                            .getDB()
                            .getConnection()
                            .prepareStatement("insert into users_projects.username_projects set uuid='"+
                                    uuid+"', projectName=?;");
            statement.setString(1, projectName);
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
