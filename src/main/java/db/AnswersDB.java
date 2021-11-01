package db;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class AnswersDB {
    private static AnswersDB instance;

    protected static AnswersDB getDB() {
        if (instance == null) {
            instance = new AnswersDB();
        }
        return instance;
    }

    private AnswersDB() {
        try {
            Statement statement = DB.getDB().getConnection().createStatement();
            statement.
                    executeUpdate("create table if not exists" +
                            " answers(id int not null AUTO_INCREMENT PRIMARY KEY," +
                            " bug_id int not null," +
                            " upload_date DATETIME not null," +
                            " uploaded_by char(8) not null," +
                            " answer TEXT not null," +
                            " is_solution BIT not null," +
                            " bug_or_ficha TEXT not null" +
                            " );");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addAnswer(String bugId, String uploadedBy, String bugOrFicha, String answer){
        try {
            PreparedStatement statement =
                    DB.getDB()
                            .getConnection()
                            .prepareStatement("insert into answers (bug_id," +
                                    "upload_date, uploaded_by, " +
                                    "answer, is_solution, bug_or_ficha) values (?, ?, ?, ?, ?, ?);");
            statement.setInt(1, Integer.parseInt(bugId));
            statement.setDate(2, new Date(System.currentTimeMillis()));
            statement.setString(3, uploadedBy);
            statement.setString(4, answer);
            statement.setBoolean(5, false);
            statement.setString(6, bugOrFicha);
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setSolutionStatus(String id, boolean status){
        try {
            Statement statement = DB.getDB().getConnection().createStatement();
            statement.executeUpdate("update answers set is_solution="+status+
                    " where id="+id+";");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteAnswer(String id){
        try {
            Statement statement = DB.getDB().getConnection().createStatement();
            statement.executeUpdate("delete * from answers where id="+id+";");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteAllAnswersByBug(String bugId){
        Statement statement = null;
        try {
            statement = DB.getDB().getConnection().createStatement();
            statement.executeUpdate("delete * from answers where bug_id="+bugId+";");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Map<String, Object> getAnswerAttributes(String id){
        Map<String, Object> attributeMap = new HashMap();
        ResultSet rs = null;
        try {
            Statement statement = DB.getDB().getConnection().createStatement();
            statement.execute("select * from answers where id="+id+";");
            rs = statement.getResultSet();
            while(rs.next()){
                attributeMap.put("id", id);
                attributeMap.put("bug_id", rs.getInt("bug_id"));
                attributeMap.put("upload_date", rs.getDate("upload_date"));
                attributeMap.put("uploaded_by", rs.getString("uploaded_by"));
                attributeMap.put("is_solution", rs.getBoolean("is_solution"));
                attributeMap.put("answer", rs.getString("answer"));
                attributeMap.put("bug_or_ficha", rs.getString("bug_or_ficha"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return attributeMap;
    }
}
