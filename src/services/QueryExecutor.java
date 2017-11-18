package services;

import com.sun.org.apache.xpath.internal.operations.Bool;

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

    public static Connection getConnection() {
        if(!checkConnection()) return null;
        return connection;
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

    public static ResultSet executeQuery(String query, String[] prop) {

        if(!checkConnection()) return null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            for (int i = 0; i < prop.length; i++) {
                preparedStatement.setString(i + 1, prop[i]);
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static Boolean execute(String query, String[] prop) {

        if(!checkConnection()) return false;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            for (int i = 0; i < prop.length; i++) {
                preparedStatement.setString(i + 1, prop[i]);
            }

            preparedStatement.execute();
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

}
