package services;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Authentication {

    public static Boolean authenticate(String userType, String username, String password) {

        if(!Database.checkConnection()) return false;

        Connection connection = Database.connection;

        try {

            String tableName = (userType == "Owner") ? "AdminLogin" : "Login";

            String query = "select * from " + tableName + " where Username=? and Password=?";
            PreparedStatement preparedStatement = connection.prepareStatement (query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);


            ResultSet resultSet = preparedStatement.executeQuery();
            int count = 0;
            while (resultSet.next()) {
                count = count+1;
            }
            resultSet.close();
            preparedStatement.close();

            return count == 1;

        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

}
