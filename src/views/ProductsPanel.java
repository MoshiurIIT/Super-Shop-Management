package views;

import services.Database;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;

public class ProductsPanel extends JPanel {

    JTable productsTable = new JTable();

    public ProductsPanel() {
        setBackground(Color.WHITE);
        setBounds(0, 0, 700, 700);
        setLayout(null);

        init();
    }

    public void init() {

        JDesktopPane desktopPane = getProductPane();
        add(desktopPane);

        JDesktopPane productsPane = getProductsPen();
        add(productsPane);
    }

    public JDesktopPane getProductPane() {

        JDesktopPane desktopPane = new JDesktopPane();
        desktopPane.setBorder(BorderFactory.createTitledBorder("Product Data"));
        desktopPane.setBounds(3, 97, 286, 210);

        JLabel lblProductId = new JLabel("Product ID");
        lblProductId.setBounds(10, 26, 80, 24);
        desktopPane.add(lblProductId);

        JLabel lblProductName = new JLabel("Name");
        lblProductName.setBounds(10, 52, 42, 30);
        desktopPane.add(lblProductName);

        JLabel lblProductCatagory = new JLabel("Catagory");
        lblProductCatagory.setBounds(10, 90, 107, 29);
        desktopPane.add(lblProductCatagory);

        JLabel lblProductPrice = new JLabel("Price");
        lblProductPrice.setBounds(10, 125, 107, 29);
        desktopPane.add(lblProductPrice);

        JLabel lblUnit = new JLabel("Unit");
        lblUnit.setBounds(10, 160, 107, 29);
        desktopPane.add(lblUnit);

        return desktopPane;
    }

    public JDesktopPane getProductsPen() {

        JDesktopPane productsPen = new JDesktopPane();

        productsPen.setBorder(BorderFactory.createTitledBorder("Products Table"));

        TableModel products = Database.getProducts();
        if(products != null) productsTable.setModel(products);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 20, 440, 480);
        scrollPane.setViewportView(productsTable);
        productsPen.add(scrollPane);

        productsPen.setBounds(300, 100, 470, 500);

        return productsPen;
    }

}
