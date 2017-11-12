package views;

import models.Purchase;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import services.Database;
import utilities.DateLabelFormatter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;

public class PurchasesPanel extends JPanel {

    private JTable productsTable = new JTable();
    private JTextField textField_pid;
    private JTextField textField_cid;
    private JDatePickerImpl jDatePicker;
    private JComboBox comboBox;
    private JTextField txtSearchProducts;

    private JTable cartTable = new JTable();
    JLabel totalCostLabel;

    private ArrayList<Purchase> cartList = new ArrayList<Purchase>();
    private int bill_id = Database.getMaxColumnValue("BillPay", "b_id") + 1;

    public PurchasesPanel() {
        setBackground(Color.WHITE);
        setBounds(0, 0, 700, 700);
        setLayout(null);

        init();
    }

    public void init() {

        JDesktopPane purchasePane = getPurchasePane();
        add(purchasePane);

        JDesktopPane productsPen = getProductsPen();
        add(productsPen);

        addSearchOption();

        textField_pid = new JTextField();
        textField_pid.setBounds(120, 127, 120, 20);
        add(textField_pid);

        textField_cid = new JTextField();
        textField_cid.setBounds(120, 159, 120, 20);
        add(textField_cid);

        Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");

        jDatePicker = new JDatePickerImpl(new JDatePanelImpl(new UtilDateModel(), properties), new DateLabelFormatter());
        jDatePicker.setBounds(120, 193, 140, 30);
        add(jDatePicker);

        JButton btnAddToCart = new JButton("Add To Cart");
        btnAddToCart.setBounds(5, 250, 100, 30);
        btnAddToCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Purchase purchase = getPurchase();
                cartList.add(purchase);
                updateCartList();
            }
        });
        add(btnAddToCart);

        totalCostLabel = new JLabel();
        totalCostLabel.setBounds(10, 550, 200, 30);
        add(totalCostLabel);

        JDesktopPane cartPane = getCartPen();
        add(cartPane);

        JButton purchaseBtn = new JButton("Purchase");
        purchaseBtn.setBounds(10, 590, 100, 30);
        purchaseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                purchaseAll();
            }
        });
        add(purchaseBtn);
    }

    public JDesktopPane getCartPen() {

        JDesktopPane cartPane = new JDesktopPane();

        cartPane.setBorder(BorderFactory.createTitledBorder("Cart Table"));

        updateCartList();

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 20, 275, 220);
        scrollPane.setViewportView(cartTable);
        cartPane.add(scrollPane);

        cartPane.setBounds(5, 300, 295, 250);

        return cartPane;
    }

    public JDesktopPane getPurchasePane() {

        JDesktopPane purchasePane = new JDesktopPane();
        purchasePane.setBorder(BorderFactory.createTitledBorder("Purchase Data"));
        purchasePane.setBounds(3, 97, 286, 150);

        JLabel lblProductId = new JLabel("Product ID");
        lblProductId.setBounds(10, 25, 80, 30);
        purchasePane.add(lblProductId);

        JLabel lblCustomerId = new JLabel("Customer ID");
        lblCustomerId.setBounds(10, 55, 107, 30);
        purchasePane.add(lblCustomerId);

        JLabel lblDate = new JLabel("Date");
        lblDate.setBounds(10, 90, 107, 30);
        purchasePane.add(lblDate);

        return purchasePane;
    }

    public JDesktopPane getProductsPen() {

        JDesktopPane productsPen = new JDesktopPane();

        productsPen.setBorder(BorderFactory.createTitledBorder("Products Table"));

        TableModel products = Database.get("Product");
        if(products != null) productsTable.setModel(products);

        productsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = productsTable.getSelectedRow();
                textField_pid.setText(productsTable.getModel().getValueAt(row, 0).toString());
            }
        });

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 20, 440, 480);
        scrollPane.setViewportView(productsTable);
        productsPen.add(scrollPane);

        productsPen.setBounds(300, 100, 470, 500);

        return productsPen;
    }

    public void addSearchOption() {

        JLabel searchKeyLabel = new JLabel("Search Key");
        searchKeyLabel.setBounds(350, 10, 100, 30);
        add(searchKeyLabel);

        comboBox = new JComboBox<String>(new String[] {"p_id", "p_name", "p_catagory"});
        comboBox.setBounds(500, 10, 200, 30);
        add(comboBox);

        JLabel searchTextLabel = new JLabel("Search Text");
        searchTextLabel.setBounds(350, 50, 100, 30);
        add(searchTextLabel);

        txtSearchProducts = new JTextField();
        txtSearchProducts.setBounds(500, 50, 200, 30);
        txtSearchProducts.setToolTipText("Search Products");

        txtSearchProducts.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent event) {
                String searchKey = (String) comboBox.getSelectedItem();
                String searchText = txtSearchProducts.getText();

                if(searchText.isEmpty()) {
                    TableModel products = Database.get("Product");
                    if(products != null) productsTable.setModel(products);
                }
                else {
                    TableModel products = Database.get("Product", searchKey, searchText);
                    if(products != null) productsTable.setModel(products);
                }
            }
        });

        add(txtSearchProducts);

    }

    public Purchase getPurchase() {
        Purchase purchase = new Purchase();

        try {
            purchase.b_id = Integer.toString(bill_id);
            purchase.p_id = textField_pid.getText();
            purchase.c_id = textField_cid.getText();
            purchase.date = (new SimpleDateFormat("yyyy-MM-dd")).parse(jDatePicker.getJFormattedTextField().getText());
        } catch (Exception exception) {
            return null;
        }

        return purchase;
    }

    public void updateCartList() {
        String col[] = {"b_id","c_id","p_id", "date"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);

        for (Purchase purchase: cartList)
            tableModel.addRow(purchase.toArray());
        cartTable.setModel(tableModel);

        totalCostLabel.setText("Total Cost = " + Database.getTotalCost(cartList));
        setVisible(true);
    }

    public void purchaseAll() {

        for (Purchase purchase: cartList)
            if(!Database.addPurchase(purchase)) {
                JOptionPane.showMessageDialog(null, "Error");
                return;
            }

        cartList.clear();
        JOptionPane.showMessageDialog(null, "Successful");
        updateCartList();
    }

}
