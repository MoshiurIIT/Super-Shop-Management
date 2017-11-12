package views;

import models.Product;
import services.Database;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;

public class PurchasesPanel extends JPanel {

    private JTable productsTable = new JTable();
    private JTextField textField_id;
    private JTextField textField_name;
    private JTextField textField_category;
    private JTextField textField_price;
    private JTextField textField_unit;
    private JComboBox comboBox;
    private JTextField txtSearchProducts;

    public PurchasesPanel() {
        setBackground(Color.WHITE);
        setBounds(0, 0, 700, 700);
        setLayout(null);

        init();
    }

    public void init() {

        JDesktopPane purchasePane = getPurchasePane();
        add(purchasePane);

        JDesktopPane purchasesPane = getProductsPen();
        add(purchasesPane);

        addSearchOption();

        textField_id = new JTextField();
        textField_id.setBounds(120, 127, 120, 20);
        add(textField_id);

        textField_name = new JTextField();
        textField_name.setBounds(120, 159, 120, 20);
        add(textField_name);

        textField_category = new JTextField();
        textField_category.setBounds(120, 193, 120, 20);
        add(textField_category);

        textField_price = new JTextField();
        textField_price.setBounds(120, 227, 120, 20);
        add(textField_price);

        textField_unit = new JTextField();
        textField_unit.setBounds(120, 261, 120, 20);
        add(textField_unit);

        JButton btnAddProduct = new JButton("Add");
        btnAddProduct.setBounds(5, 318, 88, 44);
        btnAddProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Product product = getProduct();

                Boolean isAdded = Database.addProduct(product);
                if(isAdded){
                    JOptionPane.showMessageDialog(null, "Data Added");
                    TableModel products = Database.get("Product");
                    if(products != null) productsTable.setModel(products);
                }
                else JOptionPane.showMessageDialog(null, "Error");
            }
        });
        add(btnAddProduct);

        JButton btnUpdateProduct = new JButton("Update");
        btnUpdateProduct.setBounds(100, 318, 88, 44);
        btnUpdateProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Product product = getProduct();

                Boolean isAdded = Database.updateProduct(product);
                if(isAdded){
                    JOptionPane.showMessageDialog(null, "Data Updated");
                    TableModel products = Database.get("Product");
                    if(products != null) productsTable.setModel(products);
                }
                else JOptionPane.showMessageDialog(null, "Error");
            }
        });
        add(btnUpdateProduct);

        JButton btnDeleteProduct = new JButton("Delete");
        btnDeleteProduct.setBounds(200, 318, 88, 44);
        btnDeleteProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Product product = getProduct();

                Boolean isAdded = Database.deleteProduct(product);
                if(isAdded){
                    JOptionPane.showMessageDialog(null, "Data Deleted");
                    TableModel products = Database.get("Product");
                    if(products != null) productsTable.setModel(products);
                }
                else JOptionPane.showMessageDialog(null, "Error");
            }
        });
        add(btnDeleteProduct);
    }

    public JDesktopPane getPurchasePane() {

        JDesktopPane purchasePane = new JDesktopPane();
        purchasePane.setBorder(BorderFactory.createTitledBorder("Purchase Data"));
        purchasePane.setBounds(3, 97, 286, 210);

        JLabel lblPurchaseId = new JLabel("Purchase ID");
        lblPurchaseId.setBounds(10, 26, 80, 24);
        purchasePane.add(lblPurchaseId);

        JLabel lblPurchaseName = new JLabel("Name");
        lblPurchaseName.setBounds(10, 52, 42, 30);
        purchasePane.add(lblPurchaseName);

        JLabel lblProductCatagory = new JLabel("Catagory");
        lblProductCatagory.setBounds(10, 90, 107, 29);
        purchasePane.add(lblProductCatagory);

        JLabel lblProductPrice = new JLabel("Price");
        lblProductPrice.setBounds(10, 125, 107, 29);
        purchasePane.add(lblProductPrice);

        JLabel lblUnit = new JLabel("Unit");
        lblUnit.setBounds(10, 160, 107, 29);
        purchasePane.add(lblUnit);

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
                TableModel tableModel = productsTable.getModel();
                String data[] = new String[5];
                for (int i = 0; i < 5; i++) {
                    data[i] = tableModel.getValueAt(row, i).toString();
                }
                textField_id.setText(data[0]);
                textField_name.setText(data[1]);
                textField_category.setText(data[2]);
                textField_price.setText(data[3]);
                textField_unit.setText(data[4]);
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

    public Product getProduct() {
        Product product = new Product();

        product.p_id = textField_id.getText();
        product.p_name = textField_name.getText();
        product.p_catagory = textField_category.getText();
        product.p_price = textField_price.getText();
        product.p_unit = textField_unit.getText();

        return product;
    }

}
