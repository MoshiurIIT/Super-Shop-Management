package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class QueryExecutor {

    static Connection connection = null;

    public static Boolean checkConnection() {
        if (connection == null) return connect();
        return true;
    }

    public static Boolean connect () {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:EmployeeData.sqlite");
            System.out.println("Connection Successful");
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ResultSet executeQuery(String query) {

        if(!checkConnection()) return null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

}
