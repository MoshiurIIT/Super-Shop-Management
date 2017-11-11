package services;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {

    static Connection connection = null;

    public static Boolean checkConnection() {
        if (connection == null) return connect();
        return true;
    }

    public static Boolean connect ()
    {
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
}

