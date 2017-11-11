package services;

import net.proteanit.sql.DbUtils;

import javax.swing.table.TableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Database {

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

    public static TableModel getProducts() {

        if(!checkConnection()) return null;

        try {
            String query = "select * from Product";
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            return DbUtils.resultSetToTableModel(rs);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

