package services;

import net.proteanit.sql.DbUtils;

import javax.swing.table.TableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Database {

    public static TableModel getProducts() {

        try {
            String query = "select * from Product";
            ResultSet resultSet = QueryExecutor.executeQuery(query);
            return DbUtils.resultSetToTableModel(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static TableModel getProducts(String searchKey, String searchText) {

        try {
            String query = "select * from Product where " + searchKey + "=" + searchText;
            ResultSet resultSet = QueryExecutor.executeQuery(query);
            return DbUtils.resultSetToTableModel(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}

