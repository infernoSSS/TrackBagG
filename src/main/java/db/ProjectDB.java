package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class ProjectDB {
    private static ProjectDB instance;

    protected static ProjectDB getDB() {
        if (instance == null) {
            instance = new ProjectDB();
        }
        return instance;
    }

    private ProjectDB(){
        try {
            Statement statement = DB.getDB().getConnection().createStatement();
            statement.
                    executeUpdate("create table if not exists" +
                            " projects(uuid char(36) NOT NULL PRIMARY KEY," +
                            " name CHAR(8) not null," +
                            " number_of_tickets int not null);");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean registerNewProject(String projectName, String creatorUserName){
        Statement statement = null;
        PreparedStatement prStatement = null;
        ResultSet rs = null;
        String uuid = UUID.randomUUID().toString();
        try {
            statement = DB.getDB().getConnection().createStatement();
            statement.execute("select * from projects where uuid='"+uuid+"';");
            rs = statement.getResultSet();
            if (rs.next()) {
                return false;
            }
            prStatement = DB.getDB().getConnection()
                    .prepareStatement("insert into projects set uuid='"+uuid+"', name=?, number_of_tickets=0;");
            prStatement.setString(1, projectName);
            prStatement.execute();
            DB.getDB().getUsersProjectsDB(creatorUserName).addProject(uuid, projectName);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public int getBugsNumber(String projectUUID){
        Statement statement = null;
        ResultSet rs = null;
        int number = -1;
        try {
            statement = DB.getDB().getConnection().createStatement();
            statement.execute("select number_of_tickets from projects where uuid='"+projectUUID+"';");
            rs = statement.getResultSet();
            rs.next();
            number = rs.getInt("number_of_tickets");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return number;
    }

    public void incrementBugsNumber(String projectUUID){
        Statement statement = null;
        try {
            statement = DB.getDB().getConnection().createStatement();
            statement.executeUpdate("update projects set number_of_tickets = number_of_tickets + 1 where uuid='"+projectUUID+"';");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
