package views;

import models.Customer;
import models.Purchase;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import services.Database;
import utilities.DateLabelFormatter;
import utilities.Util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class SalesPanel extends JPanel {

    private JTable salesTable = new JTable();
    private JTextField textField_id;
    private JTextField textField_bid;
    private JTextField textField_cid;
    private JTextField textField_pid;
    private JDatePickerImpl jDatePicker;
    JScrollPane scrollPane;

    private JComboBox comboBox;
    private JTextField txtSearchSales;

    TableModel purchases;

    Object [] headers = {"ID" , "Bill ID" , "Product ID" , "Name" , "Customer ID", "Name" , "Date"};

    public SalesPanel() {
        setBackground(Color.WHITE);
        setBounds(0, 0, 700, 700);
        setLayout(null);

        init();
    }

    public void loadPurchases() {
        DefaultTableModel purchases = (DefaultTableModel) Database.getSales();
        purchases.setColumnIdentifiers(headers);
        if(purchases != null) salesTable.setModel(purchases);
    }

    public void init() {

        JDesktopPane salePane = getSalePane();
        add(salePane);

        JDesktopPane salesPane = getSalesPane();
        add(salesPane);

        addSearchOption();

        textField_id = new JTextField();
        textField_id.setBounds(120, 127, 120, 20);
        add(textField_id);

        textField_bid = new JTextField();
        textField_bid.setBounds(120, 159, 120, 20);
        add(textField_bid);

        textField_cid = new JTextField();
        textField_cid.setBounds(120, 193, 120, 20);
        add(textField_cid);

        textField_pid = new JTextField();
        textField_pid.setBounds(120, 227, 120, 20);
        add(textField_pid);

        Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");

        jDatePicker = new JDatePickerImpl(new JDatePanelImpl(new UtilDateModel(), properties), new DateLabelFormatter());
        jDatePicker.setBounds(120, 260, 120, 30);
        add(jDatePicker);

        JButton btnUpdateSale = new JButton("Update");
        btnUpdateSale.setBounds(10, 318, 80, 44);
        btnUpdateSale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Purchase purchase = getPurchase();

                Boolean isUpdated = Database.updatePurchase(purchase);
                if(isUpdated){
                    JOptionPane.showMessageDialog(null, "Data Updated");
                    loadPurchases();
                }
                else JOptionPane.showMessageDialog(null, "Error");
            }
        });
        add(btnUpdateSale);

        JButton btnDeleteCustomer = new JButton("Delete");
        btnDeleteCustomer.setBounds(100, 318, 80, 44);
        btnDeleteCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Purchase purchase = getPurchase();

                Boolean isDeleted = Database.deletePurchase(purchase);
                if(isDeleted){
                    JOptionPane.showMessageDialog(null, "Data Deleted");
                    loadPurchases();
                }
                else JOptionPane.showMessageDialog(null, "Error");
            }
        });
        add(btnDeleteCustomer);

        JButton btnPDF = new JButton("PDF");
        btnPDF.setBounds(170, 538, 80, 44);
        btnPDF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                generatePDF();
            }
        });
        add(btnPDF);
    }

    public JDesktopPane getSalePane() {

        JDesktopPane salePane = new JDesktopPane();
        salePane.setBorder(BorderFactory.createTitledBorder("Sale Data"));
        salePane.setBounds(3, 97, 286, 210);

        JLabel lblPurchaseId = new JLabel("Purchase ID");
        lblPurchaseId.setBounds(10, 26, 80, 24);
        salePane.add(lblPurchaseId);

        JLabel lblBillId = new JLabel("Bill ID");
        lblBillId.setBounds(10, 52, 42, 30);
        salePane.add(lblBillId);

        JLabel lblCustomerId = new JLabel("Customer ID");
        lblCustomerId.setBounds(10, 90, 107, 29);
        salePane.add(lblCustomerId);

        JLabel lblProductId = new JLabel("Product Id");
        lblProductId.setBounds(10, 125, 107, 29);
        salePane.add(lblProductId);

        JLabel lblDate = new JLabel("Date");
        lblDate.setBounds(10, 160, 107, 29);
        salePane.add(lblDate);

        return salePane;
    }

    public JDesktopPane getSalesPane() {

        JDesktopPane salesPane = new JDesktopPane();

        salesPane.setBorder(BorderFactory.createTitledBorder("Sales Table"));

        loadPurchases();

        salesTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = salesTable.getSelectedRow();
                TableModel tableModel = salesTable.getModel();
                String data[] = new String[7];
                for (int i = 0; i < 7; i++) {
                    data[i] = tableModel.getValueAt(row, i).toString();
                }
                textField_id.setText(data[0]);
                textField_bid.setText(data[1]);
                textField_pid.setText(data[2]);
                textField_cid.setText(data[4]);
                try {
                    Date date = (new SimpleDateFormat("yyyy-MM-dd")).parse(data[6]);

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);

                    jDatePicker.getModel().setYear(calendar.get(Calendar.YEAR));
                    jDatePicker.getModel().setMonth(calendar.get(Calendar.MONTH));
                    jDatePicker.getModel().setDay(calendar.get(Calendar.DATE));
                    jDatePicker.getModel().setSelected(true);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }
        });

        scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 20, 640, 480);
        scrollPane.setViewportView(salesTable);
        salesPane.add(scrollPane);

        salesPane.setBounds(300, 100, 670, 500);

        return salesPane;
    }

    public void addSearchOption() {

        JLabel searchKeyLabel = new JLabel("Search Key");
        searchKeyLabel.setBounds(350, 10, 100, 30);
        add(searchKeyLabel);

        comboBox = new JComboBox<String>(new String[] {"id", "b_id", "p_id", "p_name", "c_id", "c_name", "date"});
        comboBox.setBounds(500, 10, 200, 30);
        add(comboBox);

        JLabel searchTextLabel = new JLabel("Search Text");
        searchTextLabel.setBounds(350, 50, 100, 30);
        add(searchTextLabel);

        txtSearchSales = new JTextField();
        txtSearchSales.setBounds(500, 50, 200, 30);
        txtSearchSales.setToolTipText("Search Sales");

        txtSearchSales.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent event) {
                String searchKey = (String) comboBox.getSelectedItem();
                String searchText = txtSearchSales.getText();

                if(searchText.isEmpty()) {
                    loadPurchases();
                }
                else {
                    DefaultTableModel sales = (DefaultTableModel) Database.getSales(searchKey, searchText);
                    sales.setColumnIdentifiers(headers);
                    if(sales != null) salesTable.setModel(sales);
                }
            }
        });

        add(txtSearchSales);

    }

    public Purchase getPurchase() {
        Purchase purchase = new Purchase();

        try {
            purchase.id = textField_id.getText();
            purchase.b_id = textField_bid.getText();
            purchase.c_id = textField_cid.getText();
            purchase.p_id = textField_pid.getText();
            purchase.date = (new SimpleDateFormat("yyyy-MM-dd")).parse(jDatePicker.getJFormattedTextField().getText());
        } catch (ParseException e) {
            return null;
        }

        return purchase;
    }

    public void generatePDF() {
        Boolean success = Util.savePdfFromComponent(scrollPane, "F:/sample.pdf");

        if(success)
            JOptionPane.showMessageDialog(null, "Success");
        else JOptionPane.showMessageDialog(null, "Error");
    }

}
