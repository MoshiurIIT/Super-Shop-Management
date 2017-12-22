package views;

import models.Purchase;
import services.Database;
import utilities.Util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

public class PurchasesPanel extends JPanel {

    private JTextField textField_pid;
    private JTextField textField_cid;
    JTextField barcodeField;
    InvoicePanel invoicePanel = new InvoicePanel();

    private JTable cartTable = new JTable();
    JLabel totalCostLabel;
    TableModel products;

    private ArrayList<Purchase> cartList = new ArrayList<Purchase>();
    private int bill_id = Database.getMaxColumnValue("BillPay", "b_id") + 1;

    public PurchasesPanel() {
        setBackground(Color.WHITE);
        setBounds(0, 0, 1000, 700);
        setLayout(null);

        init();
    }

    public void init() {

        JDesktopPane purchasePane = getPurchasePane();
        add(purchasePane);

        JDesktopPane cartsPen = getCartsPen();
        add(cartsPen);

        JLabel barcodelbl = new JLabel("Barcode");
        barcodelbl.setBounds(10, 30, 120, 20);
        add(barcodelbl);

        barcodeField = new JTextField();
        barcodeField.setBounds(120, 30, 160, 20);
        barcodeField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent event) {
                String barcode = barcodeField.getText();
                TableModel tm = Database.get("Product", "barcode", barcode);
                if(tm.getRowCount() == 0) return;
                String p_id = tm.getValueAt(0, 0).toString();
                textField_pid.setText(p_id);
            }
        });
        add(barcodeField);

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
        totalCostLabel.setBounds(300, 550, 200, 30);
        add(totalCostLabel);

        JButton previewBtn = new JButton("Preview");
        previewBtn.setBounds(300, 590, 80, 30);
        previewBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                viewInvoice();
            }
        });
        add(previewBtn);

        JButton confirmBtn = new JButton("Confirm");
        confirmBtn.setBounds(385, 590, 80, 30);
        confirmBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                purchaseAll();
                cartList.clear();
                updateCartList();
            }
        });
        add(confirmBtn);

        JButton pdfBtn = new JButton("PDF");
        pdfBtn.setBounds(470, 590, 80, 30);
        pdfBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                generatePdf();
            }
        });
        add(pdfBtn);
    }

    public JDesktopPane getPurchasePane() {

        JDesktopPane purchasePane = new JDesktopPane();
        purchasePane.setBorder(BorderFactory.createTitledBorder("Purchase Data"));
        purchasePane.setBounds(3, 97, 286, 120);

        JLabel lblProductId = new JLabel("Product ID");
        lblProductId.setBounds(10, 25, 80, 30);
        purchasePane.add(lblProductId);

        JLabel lblCustomerId = new JLabel("Customer ID");
        lblCustomerId.setBounds(10, 55, 107, 30);
        purchasePane.add(lblCustomerId);

        return purchasePane;
    }

    public JDesktopPane getCartsPen() {

        JDesktopPane cartsPen = new JDesktopPane();

        cartsPen.setBorder(BorderFactory.createTitledBorder("Cart List"));

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 20, 640, 480);
        scrollPane.setViewportView(cartTable);
        cartsPen.add(scrollPane);

        cartsPen.setBounds(300, 20, 670, 500);

        return cartsPen;
    }

    public Purchase getPurchase() {
        Purchase purchase = new Purchase();

        try {
            purchase.b_id = Integer.toString(bill_id);
            purchase.p_id = textField_pid.getText();
            purchase.c_id = textField_cid.getText();
            purchase.date = new Date();
        } catch (Exception exception) {
            return null;
        }

        return purchase;
    }

    public void updateCartList() {
        String col[] = {"Bill ID","Customer ID","Product ID", "Date"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);

        for (Purchase purchase: cartList)
            tableModel.addRow(purchase.toArray());
        cartTable.setModel(tableModel);

        totalCostLabel.setText("Total Cost = " + Database.getTotalCost(cartList)+" TK");
        setVisible(true);
    }

    public void viewInvoice() {
        invoicePanel.setCartList(cartList);
        invoicePanel.updateCartList();
        invoicePanel.setVisible(true);
    }

    public void generatePdf() {

        viewInvoice();
        Boolean success = Util.savePdfFromComponent(invoicePanel, "assets\\OutputReport\\Invoice.pdf");
        invoicePanel.setVisible(false);

        if(success)
            JOptionPane.showMessageDialog(null, "Success");
        else JOptionPane.showMessageDialog(null, "Error");
    }

    public void purchaseAll() {

        for (Purchase purchase: cartList)
            if(!Database.addPurchase(purchase) || !Database.decrementProductCount(purchase)) {
                JOptionPane.showMessageDialog(null, "Error");
                return;
            }
        for (Purchase purchase: cartList)
            if(!Database.validateProductCount(purchase)) {
                JOptionPane.showMessageDialog(null, "Product with id " + purchase.p_id + " is not available.");
                for(Purchase purchase1: cartList) {
                    Database.incrementProductCount(purchase1);
                }
                return;
            }
        JOptionPane.showMessageDialog(null, "Successful");
    }

}
