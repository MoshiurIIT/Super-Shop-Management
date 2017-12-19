package views;

import models.Product;
import services.Database;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;

public class ProductsPanel extends JPanel {

    private JTable productsTable = new JTable();
    private JTextField textField_id;
    private JTextField textField_name;
    private JTextField textField_category;
    private JTextField textField_price;
    private JTextField textField_unit;
    private JTextField textField_count;
    private JTextField textField_barcode;

    private JComboBox comboBox;
    private JTextField txtSearchProducts;
    TableModel products;
    Object[] headers = {"Product ID" , "Name" , "Category" , "Price(TK)" , "Unit" ,  "Count(s)" , "Barcode"};

    public ProductsPanel() {
        setBackground(Color.WHITE);
        setBounds(0, 0, 1000, 700);
        setLayout(null);

        init();
    }

    public void loadProducts() {
        DefaultTableModel products = (DefaultTableModel) Database.get("Product");
        products.setColumnIdentifiers(headers);

        if(products != null) productsTable.setModel(products);
    }

    public void init() {

        JDesktopPane productPane = getProductPane();
        add(productPane);

        JDesktopPane productsPane = getProductsPen();
        add(productsPane);

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

        textField_count = new JTextField();
        textField_count.setBounds(120, 295, 120, 20);
        add(textField_count);

        textField_barcode = new JTextField();
        textField_barcode.setBounds(120, 330, 120, 20);
        add(textField_barcode);

        JButton btnAddProduct = new JButton("Add");
        btnAddProduct.setBounds(5, 388, 88, 44);
        btnAddProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Product product = getProduct();

                Boolean isAdded = Database.addProduct(product);
                if(isAdded){
                    JOptionPane.showMessageDialog(null, "Data Added");
                    DefaultTableModel products = (DefaultTableModel) Database.get("Product");
                    products.setColumnIdentifiers(headers);
                    if(products != null) productsTable.setModel(products);
                }
                else JOptionPane.showMessageDialog(null, "Error");
            }
        });
        add(btnAddProduct);

        JButton btnUpdateProduct = new JButton("Update");
        btnUpdateProduct.setBounds(100, 388, 88, 44);
        btnUpdateProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Product product = getProduct();

                Boolean isAdded = Database.updateProduct(product);
                if(isAdded){
                    JOptionPane.showMessageDialog(null, "Data Updated");
                    DefaultTableModel products = (DefaultTableModel) Database.get("Product");
                    products.setColumnIdentifiers(headers);
                    if(products != null) productsTable.setModel(products);
                }
                else JOptionPane.showMessageDialog(null, "Error");
            }
        });
        add(btnUpdateProduct);

        JButton btnDeleteProduct = new JButton("Delete");
        btnDeleteProduct.setBounds(200, 388, 88, 44);
        btnDeleteProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Product product = getProduct();

                Boolean isAdded = Database.deleteProduct(product);
                if(isAdded){
                    JOptionPane.showMessageDialog(null, "Data Deleted");
                    DefaultTableModel products = (DefaultTableModel) Database.get("Product");
                    products.setColumnIdentifiers(headers);
                    if(products != null) productsTable.setModel(products);
                }
                else JOptionPane.showMessageDialog(null, "Error");
            }
        });
        add(btnDeleteProduct);
    }

    public JDesktopPane getProductPane() {

        JDesktopPane productPane = new JDesktopPane();
        productPane.setBorder(BorderFactory.createTitledBorder("Product Data"));
        productPane.setBounds(3, 97, 286, 280);

        JLabel lblProductId = new JLabel("Product ID");
        lblProductId.setBounds(10, 26, 80, 24);
        productPane.add(lblProductId);

        JLabel lblProductName = new JLabel("Name");
        lblProductName.setBounds(10, 52, 42, 30);
        productPane.add(lblProductName);

        JLabel lblProductCatagory = new JLabel("Catagory");
        lblProductCatagory.setBounds(10, 90, 107, 29);
        productPane.add(lblProductCatagory);

        JLabel lblProductPrice = new JLabel("Price");
        lblProductPrice.setBounds(10, 125, 107, 29);
        productPane.add(lblProductPrice);

        JLabel lblUnit = new JLabel("Unit");
        lblUnit.setBounds(10, 160, 107, 29);
        productPane.add(lblUnit);

        JLabel lblCount = new JLabel("Count");
        lblCount.setBounds(10, 195, 107, 29);
        productPane.add(lblCount);

        JLabel lblBarcode = new JLabel("Barcode");
        lblBarcode.setBounds(10, 230, 107, 29);
        productPane.add(lblBarcode);

        return productPane;
    }

    public JDesktopPane getProductsPen() {

        JDesktopPane productsPen = new JDesktopPane();

        productsPen.setBorder(BorderFactory.createTitledBorder("Products Table"));

        loadProducts();

        productsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = productsTable.getSelectedRow();
                TableModel tableModel = productsTable.getModel();
                String data[] = new String[7];
                for (int i = 0; i < 7; i++) {
                    data[i] = tableModel.getValueAt(row, i).toString();
                }
                textField_id.setText(data[0]);
                textField_name.setText(data[1]);
                textField_category.setText(data[2]);
                textField_price.setText(data[3]);
                textField_unit.setText(data[4]);
                textField_count.setText(data[5]);
                textField_barcode.setText(data[6]);
            }
        });

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 20, 640, 480);
        scrollPane.setViewportView(productsTable);
        productsPen.add(scrollPane);

        productsPen.setBounds(300, 100, 670, 500);

        return productsPen;
    }

    public void addSearchOption() {

        JLabel searchKeyLabel = new JLabel("Search Key");
        searchKeyLabel.setBounds(350, 10, 100, 30);
        add(searchKeyLabel);

        comboBox = new JComboBox<String>(new String[] {"p_id", "p_name", "p_catagory", "barcode"});
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
                    DefaultTableModel products = (DefaultTableModel) Database.get("Product");
                    products.setColumnIdentifiers(headers);
                    if(products != null) productsTable.setModel(products);
                }
                else {
                    DefaultTableModel products = (DefaultTableModel) Database.get("Product", searchKey, searchText);
                    products.setColumnIdentifiers(headers);
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
        product.p_count = textField_count.getText();
        product.barcode = textField_barcode.getText();

        return product;
    }

}
