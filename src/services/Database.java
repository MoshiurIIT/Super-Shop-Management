package services;

import models.Product;
import net.proteanit.sql.DbUtils;

import javax.swing.table.TableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Database {

    public static TableModel get(String tableName) {

        try {
            String query = "select * from " + tableName;
            ResultSet resultSet = QueryExecutor.executeQuery(query);
            return DbUtils.resultSetToTableModel(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static TableModel get(String tableName, String searchKey, String searchText) {

        try {
            String query = "select * from " + tableName + " where " + searchKey + "='" + searchText + "'";
            ResultSet resultSet = QueryExecutor.executeQuery(query);
            return DbUtils.resultSetToTableModel(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static Boolean addProduct(Product product) {
        try {
            String query = "insert into Product (p_id, p_name, p_catagory, p_price, p_unit) values(?, ?, ?, ?, ?)";
            Connection connection = QueryExecutor.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, product.p_id);
            preparedStatement.setString(2, product.p_name);
            preparedStatement.setString(3, product.p_catagory);
            preparedStatement.setString(4, product.p_price);
            preparedStatement.setString(5, product.p_unit);
            preparedStatement.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

