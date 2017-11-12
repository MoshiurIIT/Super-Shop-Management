package services;

import models.Customer;
import models.Product;
import net.proteanit.sql.DbUtils;

import javax.swing.table.TableModel;
import java.sql.ResultSet;

public class Database {

    public static TableModel get(String tableName) {

        try {
            String query = "select * from " + tableName;
            ResultSet resultSet = QueryExecutor.executeQuery(query,  new String[]{});
            return DbUtils.resultSetToTableModel(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static TableModel get(String tableName, String searchKey, String searchText) {

        try {
            String query = "select * from " + tableName + " where " + searchKey + "='" + searchText + "'";
            ResultSet resultSet = QueryExecutor.executeQuery(query, new String[]{});
            return DbUtils.resultSetToTableModel(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static Boolean addProduct(Product product) {
        try {
            String query = "insert into Product (p_id, p_name, p_catagory, p_price, p_unit) values(?, ?, ?, ?, ?)";
            return QueryExecutor.execute(query, new String[]{product.p_id, product.p_name, product.p_catagory,
                    product.p_price, product.p_unit});
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Boolean updateProduct(Product product) {
        try {
            String query = "update Product set p_name=?, p_catagory=?, p_price=?, p_unit=? where p_id=?";
            return QueryExecutor.execute(query, new String[]{product.p_name, product.p_catagory, product.p_price,
                    product.p_unit, product.p_id});
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Boolean deleteProduct(Product product) {
        try {
            String query = "delete from Product where p_id=?";
            return QueryExecutor.execute(query, new String[]{product.p_id});
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Boolean addCustomer(Customer customer) {
        try {
            String query = "insert into Customer (c_id, c_name, c_contact, c_address) values(?, ?, ?, ?)";
            return QueryExecutor.execute(query, new String[]{customer.c_id, customer.c_name, customer.c_contact,
                    customer.c_address});
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Boolean updateCustomer(Customer customer) {
        try {
            String query = "update Customer set c_name=?, c_contact=?, c_address=? where c_id=?";
            return QueryExecutor.execute(query, new String[]{customer.c_name, customer.c_contact, customer.c_address,
                    customer.c_id});
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Boolean deleteCustomer(Customer customer) {
        try {
            String query = "delete from Customer where c_id=?";
            return QueryExecutor.execute(query, new String[]{customer.c_id});
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

