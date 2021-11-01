package db;

import java.sql.*;

public class UsersDB {
    private static UsersDB instance;

    protected static UsersDB getDB() {
        if (instance == null) {
            instance = new UsersDB();
        }
        return instance;
    }

    private UsersDB() {
        try {
            Statement statement = DB.getDB().getConnection().createStatement();
            statement.
                    executeUpdate("create table if not exists" +
                            " users(id MEDIUMINT NOT NULL AUTO_INCREMENT," +
                            " loginName CHAR(8) not null," +
                            " password CHAR(8) not null," +
                            " PRIMARY KEY (id))");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Status login(String login, String password) {
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            statement = DB.getDB().getConnection().prepareStatement("select * from users where loginName = ?;");
            statement.setString(1, login);
            statement.execute();
            rs = statement.getResultSet();
            if (rs == null) {
                return Status.DENIED;
            }
            rs.next();
            if (!rs.getString("password").equals(password)) {
                return Status.DENIED;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return Status.DENIED;
        }
        return Status.ACCESS;
    }

    public Status register(String login, String password) {
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = DB.getDB().getConnection().prepareStatement("select * from users where loginName = ?;");
            statement.setString(1, login);
            statement.execute();
            rs = statement.getResultSet();
        if (rs.next()) {
            return Status.DENIED;
        }
        statement = DB.getDB().getConnection().prepareStatement("insert into users set loginName=?, password=?;");
        statement.setString(1, login);
        statement.setString(2, password);
        statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Status.CREATED;
    }
}

