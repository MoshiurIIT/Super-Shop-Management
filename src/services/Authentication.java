package services;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Authentication {

    public static Boolean authenticate(String userType, String username, String password) {

        try {

            String tableName = (userType == "Owner") ? "AdminLogin" : "Login";

            String query = "select * from " + tableName + " where Username=" + username + " and Password=" + password;

            ResultSet resultSet = QueryExecutor.executeQuery(query,  new String[]{});
            int count = 0;
            while (resultSet.next()) {
                count = count+1;
            }
            resultSet.close();

            return count == 1;

        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

}
