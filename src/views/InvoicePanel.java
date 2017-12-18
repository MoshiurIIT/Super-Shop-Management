package views;

import models.Purchase;
import services.Database;
import utilities.Util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class InvoicePanel extends JFrame {

    private ArrayList<Purchase> cartList = new ArrayList<Purchase>();
    private JTable cartTable = new JTable();
    JLabel totalCostLabel = new JLabel();
    JLabel customerNamelbl = new JLabel("Customer Name");
    JLabel billIdlbl = new JLabel("Bill Id");
    JLabel purchaseDatelbl = new JLabel("Purchase date");

    public InvoicePanel() {
        super("Invoice");
        setBackground(Color.WHITE);
        setBounds(500, 0, 530, 700);
        setLayout(null);

        init();
    }

    public void init() {
        JDesktopPane cartPane = getCartPen();
        add(cartPane);

        totalCostLabel.setBounds(10, 550, 200, 30);
        add(totalCostLabel);

        customerNamelbl.setBounds(30, 10, 200, 30);
        add(customerNamelbl);

        billIdlbl.setBounds(30, 60, 200, 30);
        add(billIdlbl);

        purchaseDatelbl.setBounds(30, 110, 200, 30);
        add(purchaseDatelbl);
    }

    public void setCartList(ArrayList<Purchase> _cartList) {
        cartList = _cartList;
        if(!cartList.get(0).c_id.equals("")) {
            TableModel tableModel = Database.get("Customer", "c_id", cartList.get(0).c_id);
            customerNamelbl.setText("Customer Name: " + tableModel.getValueAt(0, 1).toString());
        }
        billIdlbl.setText("Bill Id: " + cartList.get(0).b_id);
        purchaseDatelbl.setText("Purchase Date: " + new SimpleDateFormat("yyyy-MM-dd").format(cartList.get(0).date));
    }

    public void updateCartList() {
        String col[] = {"Product Name", "Unit Price", "Quantity"};

        DefaultTableModel tableModel = new DefaultTableModel(col, 0);

        String productIds[] = new String[cartList.size()];
        int len = 0;
        for(Purchase purchase: cartList)
            productIds[len++] = purchase.p_id;

        try {
            ResultSet resultSet = Database.getIn("Product", "p_id", productIds);

            while (resultSet.next()) {
                tableModel.addRow(new String[]{resultSet.getString("p_name"),
                        resultSet.getString("p_price"),
                        Integer.toString(Util.countOccurrences(resultSet.getString("p_id"), productIds))});
            }
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Error");
        }
        cartTable.setModel(tableModel);

        totalCostLabel.setText("Total Cost = " + Database.getTotalCost(cartList));
    }

    public JDesktopPane getCartPen() {

        JDesktopPane cartPane = new JDesktopPane();

        cartPane.setBorder(BorderFactory.createTitledBorder("Cart Table"));

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 20, 480, 270);

        updateCartList();

        scrollPane.setViewportView(cartTable);
        cartPane.add(scrollPane);

        cartPane.setBounds(5, 250, 500, 300);

        return cartPane;
    }



}
