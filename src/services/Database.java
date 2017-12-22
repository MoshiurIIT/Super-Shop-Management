package services;

import com.sun.org.apache.xpath.internal.operations.Bool;
import models.*;
import net.proteanit.sql.DbUtils;
import utilities.Util;

import javax.swing.table.TableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class Database {

    public static TableModel get(String tableName) {

        try {
            String query = "select * from " + tableName;
            ResultSet resultSet = QueryExecutor.executeQuery(query,  new String[]{});
            return DbUtils.resultSetToTableModel(resultSet);
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }

    public static TableModel get(String tableName, String searchKey, String searchText) {

        try {
            String query = "select * from " + tableName + " where (lower(" + searchKey  + ") like '" + searchText + "')";
            ResultSet resultSet = QueryExecutor.executeQuery(query, new String[]{});
            return DbUtils.resultSetToTableModel(resultSet);
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }

    }

    public static TableModel getSales() {

        try {
            String query = "select BillPay.id, BillPay.b_id, BillPay.p_id, Product.p_name, BillPay.c_id, Customer.c_name, BillPay.date from BillPay, Customer, Product where BillPay.c_id=Customer.c_id and BillPay.p_id=Product.p_id";
            ResultSet resultSet = QueryExecutor.executeQuery(query,  new String[]{});
            return DbUtils.resultSetToTableModel(resultSet);
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }

    public static TableModel getSales(String searchKey, String searchText) {

        if (Arrays.asList(new String[]{"id", "b_id", "c_id", "p_id", "date"}).contains(searchKey)) {
            return get("BillPay", searchKey, searchText);
        }

        if (searchKey.equals("c_name")) {
            try {
                String q = "select * from Customer where (lower(c_name) like '" + searchText + "')";
                ResultSet resultSet = QueryExecutor.executeQuery(q, new String[]{});

                ArrayList<String> idList = new ArrayList<String>();
                while (resultSet.next()) {
                    String c_id = resultSet.getString(1);
                    idList.add(c_id);
                }

                String ids[] = idList.toArray(new String[idList.size()]);
                String commaSeparatedValues = String.join(",", ids);
                String query = "select BillPay.id, BillPay.b_id, BillPay.p_id, Product.p_name, BillPay.c_id, Customer.c_name, BillPay.date from BillPay, Customer, Product where BillPay.c_id=Customer.c_id and BillPay.p_id=Product.p_id and BillPay.c_id in (" + commaSeparatedValues + ")";
                resultSet = QueryExecutor.executeQuery(query, new String[]{});
                return DbUtils.resultSetToTableModel(resultSet);
            } catch (SQLException e) {
                //e.printStackTrace();
            }
        }

        if (searchKey.equals("p_name")) {
            try {
                String q = "select * from Product where (lower(p_name) like '" + searchText + "')";
                ResultSet resultSet = QueryExecutor.executeQuery(q, new String[]{});

                ArrayList<String> idList = new ArrayList<String>();
                while (resultSet.next()) {
                    String p_id = resultSet.getString(1);
                    idList.add(p_id);
                }

                String ids[] = idList.toArray(new String[idList.size()]);
                String commaSeparatedValues = String.join(",", ids);
                String query = "select BillPay.id, BillPay.b_id, BillPay.p_id, Product.p_name, BillPay.c_id, Customer.c_name, BillPay.date from BillPay, Customer, Product where BillPay.c_id=Customer.c_id and BillPay.p_id=Product.p_id and BillPay.p_id in (" + commaSeparatedValues + ")";
                resultSet = QueryExecutor.executeQuery(query, new String[]{});
                return DbUtils.resultSetToTableModel(resultSet);
            } catch (SQLException e) {
                //e.printStackTrace();
            }
        }

        return null;
    }

    public static ResultSet getIn(String tableName, String prop, String values[]) {
        String commaSeparatedValues = String.join(",", values);
        String query = "select * from " + tableName + " where " + prop + " in (" + commaSeparatedValues + ")";
        ResultSet resultSet = QueryExecutor.executeQuery(query, new String[]{});
        return resultSet;
    }

    public static Boolean addOwner(Owner owner) {
        try {
            String query = "insert into AdminLogin (Username, Password) values(?, ?)";
            return QueryExecutor.execute(query, new String[]{owner.userName, owner.Password});
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
    }

    public static Boolean updateOwner(Owner owner) {
        try {
            String query = "update AdminLogin set Password=? where Username=?";
            return QueryExecutor.execute(query, new String[]{owner.Password, owner.userName});
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
    }

    public static Boolean deleteOwner(Owner owner) {
        try {
            String query = "delete from AdminLogin where Username=?";
            return QueryExecutor.execute(query, new String[]{owner.userName});
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
    }

    public static Boolean addSalesStaff(SalesStaff salesStaff) {
        try {
            String query = "insert into Login (Username, Password) values(?, ?)";
            return QueryExecutor.execute(query, new String[]{salesStaff.userName, salesStaff.Password});
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
    }

    public static Boolean updateSalesStaff(SalesStaff salesStaff) {
        try {
            String query = "update Login set Password=? where Username=?";
            return QueryExecutor.execute(query, new String[]{salesStaff.Password, salesStaff.userName});
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
    }

    public static Boolean deleteSalesStaff(SalesStaff salesStaff) {
        try {
            String query = "delete from Login where Username=?";
            return QueryExecutor.execute(query, new String[]{salesStaff.userName});
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
    }

    public static Boolean addProduct(Product product) {
        try {
            String query = "insert into Product (p_name, p_catagory, p_price, p_unit, p_count, barcode) values(?, ?, ?, ?, ?, ?)";
            return QueryExecutor.execute(query, new String[]{product.p_name, product.p_catagory,
                    product.p_price, product.p_unit, product.p_count, product.barcode});
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
    }

    public static Boolean updateProduct(Product product) {
        try {
            String query = "update Product set p_name=?, p_catagory=?, p_price=?, p_unit=?, p_count=?, barcode=? where p_id=?";
            return QueryExecutor.execute(query, new String[]{product.p_name, product.p_catagory, product.p_price,
                    product.p_unit, product.p_count, product.barcode, product.p_id});
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
    }

    public static Boolean deleteProduct(Product product) {
        try {
            String query = "delete from Product where p_id=?";
            return QueryExecutor.execute(query, new String[]{product.p_id});
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
    }

    public static Boolean addCustomer(Customer customer) {
        try {
            String query = "insert into Customer (c_name, c_contact, c_address) values(?, ?, ?)";
            return QueryExecutor.execute(query, new String[]{customer.c_name, customer.c_contact,
                    customer.c_address});
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
    }

    public static Boolean updateCustomer(Customer customer) {
        try {
            String query = "update Customer set c_name=?, c_contact=?, c_address=? where c_id=?";
            return QueryExecutor.execute(query, new String[]{customer.c_name, customer.c_contact, customer.c_address,
                    customer.c_id});
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
    }

    public static Boolean deleteCustomer(Customer customer) {
        try {
            String query = "delete from Customer where c_id=?";
            return QueryExecutor.execute(query, new String[]{customer.c_id});
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
    }

    public static Boolean addPurchase(Purchase purchase) {
        try {
            String query = "insert into BillPay (b_id, c_id, p_id, date) values(?, ?, ?, ?)";
            return QueryExecutor.execute(query, new String[]{purchase.b_id, purchase.c_id,
                    purchase.p_id, new SimpleDateFormat("yyyy-MM-dd").format(purchase.date)});
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
    }

    public static Boolean decrementProductCount(Purchase purchase) {
        try {
            String query = "update Product set p_count = p_count - 1 where p_id=?";
            return QueryExecutor.execute(query, new String[]{purchase.p_id});
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
    }

    public static Boolean validateProductCount(Purchase purchase) {
        try {
            String query = "select * from Product where p_id=? and p_count < 0";
            ResultSet resultSet = QueryExecutor.executeQuery(query,  new String[]{purchase.p_id});
            TableModel tm = DbUtils.resultSetToTableModel(resultSet);
            return tm.getRowCount() == 0;
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
    }

    public static Boolean incrementProductCount(Purchase purchase) {
        try {
            String query = "update Product set p_count = p_count + 1 where p_id=?";
            return QueryExecutor.execute(query, new String[]{purchase.p_id});
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
    }

    public static Boolean updatePurchase(Purchase purchase) {
        try {
            String query = "update BillPay set b_id=?, c_id=?, p_id=?, date=? where id=?";
            return QueryExecutor.execute(query, new String[]{purchase.b_id, purchase.c_id, purchase.p_id,
                    new SimpleDateFormat("yyyy-MM-dd").format(purchase.date), purchase.id});
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
    }

    public static Boolean deletePurchase(Purchase purchase) {
        try {
            String query = "delete from BillPay where id=?";
            return QueryExecutor.execute(query, new String[]{purchase.id});
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
    }

    public static int getMaxColumnValue(String tableName, String columnName) {
        try {
            String query = "select * from " + tableName;
            ResultSet resultSet = QueryExecutor.executeQuery(query,  new String[]{});
            int res = 0;
            while (resultSet.next()) {
                res = Math.max(res, resultSet.getInt(columnName));
            }
            return res;
        } catch (Exception e) {
            //e.printStackTrace();
            return -1;
        }
    }

    public static double getTotalCost(ArrayList<Purchase> purchases) {

        String productIds[] = new String[purchases.size()];
        int len = 0;
        for(Purchase purchase: purchases)
            productIds[len++] = purchase.p_id;

        try {
            ResultSet resultSet = getIn("Product", "p_id", productIds);

            double res = 0;
            while (resultSet.next())
                res += resultSet.getDouble("p_price") * Util.countOccurrences(resultSet.getString("p_id"), productIds);
            return res;
        } catch (Exception exception) {
            return 0;
        }
    }

    public static String updatePassword(String pasword, String newPassword) {
        if(!Authentication.verifyAuthentication()) return "Not Authenticated";

        String username = Authentication.getUsername();
        if(!Authentication.match(pasword)) return "Permission Denied";
        String userType = Authentication.getUserType();
        String tableName = (userType == "Owner") ? "AdminLogin" : "Login";
        try {
            String query = "update " + tableName + " set Password=? where Username=?";
            Boolean success = QueryExecutor.execute(query, new String[]{newPassword, username});
            if(success) return "Success";
            else return "Error";
        } catch (Exception e) {
            //e.printStackTrace();
            return "Success";
        }
    }
}

