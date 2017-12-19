package views;

import models.Customer;
import models.Product;
import services.Database;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;

public class CustomersPanel extends JPanel {

    private JTable customersTable = new JTable();
    private JTextField textField_id;
    private JTextField textField_name;
    private JTextField textField_contact;
    private JTextField textField_address;
    private JComboBox comboBox;
    private JTextField txtSearchCustomers;

    Object [] headers = {"Customer ID" , "Name" , "Contact" , "Address"};

    public CustomersPanel() {
        setBackground(Color.WHITE);
        setBounds(0, 0, 700, 700);
        setLayout(null);

        init();
    }

    public void init() {

        JDesktopPane customerPane = getCustomerPane();
        add(customerPane);

        JDesktopPane customersPane = getCustomersPane();
        add(customersPane);

        addSearchOption();

        textField_id = new JTextField();
        textField_id.setBounds(120, 127, 120, 20);
        add(textField_id);

        textField_name = new JTextField();
        textField_name.setBounds(120, 159, 120, 20);
        add(textField_name);

        textField_contact = new JTextField();
        textField_contact.setBounds(120, 193, 120, 20);
        add(textField_contact);

        textField_address = new JTextField();
        textField_address.setBounds(120, 227, 120, 20);
        add(textField_address);

        JButton btnAddCutomer = new JButton("Add");
        btnAddCutomer.setBounds(5, 318, 88, 44);
        btnAddCutomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Customer customer = getCustomer();

                Boolean isAdded = Database.addCustomer(customer);
                if(isAdded){
                    JOptionPane.showMessageDialog(null, "Data Added");
                    DefaultTableModel customers = (DefaultTableModel) Database.get("Customer");
                    customers.setColumnIdentifiers(headers);
                    if(customers != null) customersTable.setModel(customers);
                }
                else JOptionPane.showMessageDialog(null, "Error");
            }
        });
        add(btnAddCutomer);

        JButton btnUpdateCustomer = new JButton("Update");
        btnUpdateCustomer.setBounds(100, 318, 88, 44);
        btnUpdateCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Customer customer = getCustomer();

                Boolean isAdded = Database.updateCustomer(customer);
                if(isAdded){
                    JOptionPane.showMessageDialog(null, "Data Updated");
                    DefaultTableModel customers = (DefaultTableModel) Database.get("Customer");
                    customers.setColumnIdentifiers(headers);
                    if(customers != null) customersTable.setModel(customers);
                }
                else JOptionPane.showMessageDialog(null, "Error");
            }
        });
        add(btnUpdateCustomer);

        JButton btnDeleteCustomer = new JButton("Delete");
        btnDeleteCustomer.setBounds(200, 318, 88, 44);
        btnDeleteCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Customer customer = getCustomer();

                Boolean isAdded = Database.deleteCustomer(customer);
                if(isAdded){
                    JOptionPane.showMessageDialog(null, "Data Deleted");
                    DefaultTableModel customers = (DefaultTableModel) Database.get("Customer");
                    customers.setColumnIdentifiers(headers);
                    if(customers != null) customersTable.setModel(customers);
                }
                else JOptionPane.showMessageDialog(null, "Error");
            }
        });
        add(btnDeleteCustomer);
    }

    public JDesktopPane getCustomerPane() {

        JDesktopPane customerPane = new JDesktopPane();
        customerPane.setBorder(BorderFactory.createTitledBorder("Customer Data"));
        customerPane.setBounds(3, 97, 286, 210);

        JLabel lblCustomerId = new JLabel("Customer ID");
        lblCustomerId.setBounds(10, 26, 80, 24);
        customerPane.add(lblCustomerId);

        JLabel lblCustomerName = new JLabel("Name");
        lblCustomerName.setBounds(10, 52, 42, 30);
        customerPane.add(lblCustomerName);

        JLabel lblCustomerContact = new JLabel("Contact");
        lblCustomerContact.setBounds(10, 90, 107, 29);
        customerPane.add(lblCustomerContact);

        JLabel lblCustomerAddress = new JLabel("Address");
        lblCustomerAddress.setBounds(10, 125, 107, 29);
        customerPane.add(lblCustomerAddress);

        return customerPane;
    }

    public JDesktopPane getCustomersPane() {

        JDesktopPane customersPane = new JDesktopPane();

        customersPane.setBorder(BorderFactory.createTitledBorder("Customers Table"));

        DefaultTableModel customers = (DefaultTableModel) Database.get("Customer");
        customers.setColumnIdentifiers(headers);
        if(customers != null) customersTable.setModel(customers);

        customersTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = customersTable.getSelectedRow();
                TableModel tableModel = customersTable.getModel();
                String data[] = new String[4];
                for (int i = 0; i < 4; i++) {
                    data[i] = tableModel.getValueAt(row, i).toString();
                }
                textField_id.setText(data[0]);
                textField_name.setText(data[1]);
                textField_contact.setText(data[2]);
                textField_address.setText(data[3]);
            }
        });

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 20, 440, 480);
        scrollPane.setViewportView(customersTable);
        customersPane.add(scrollPane);

        customersPane.setBounds(300, 100, 470, 500);

        return customersPane;
    }

    public void addSearchOption() {

        JLabel searchKeyLabel = new JLabel("Search Key");
        searchKeyLabel.setBounds(350, 10, 100, 30);
        add(searchKeyLabel);

        comboBox = new JComboBox<String>(new String[] {"c_id", "c_name", "c_contact", "c_address"});
        comboBox.setBounds(500, 10, 200, 30);
        add(comboBox);

        JLabel searchTextLabel = new JLabel("Search Text");
        searchTextLabel.setBounds(350, 50, 100, 30);
        add(searchTextLabel);

        txtSearchCustomers = new JTextField();
        txtSearchCustomers.setBounds(500, 50, 200, 30);
        txtSearchCustomers.setToolTipText("Search Customers");

        txtSearchCustomers.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent event) {
                String searchKey = (String) comboBox.getSelectedItem();
                String searchText = txtSearchCustomers.getText();

                if(searchText.isEmpty()) {
                    TableModel cusomers = Database.get("Customer");
                    if(cusomers != null) customersTable.setModel(cusomers);
                }
                else {
                    TableModel customers = Database.get("Customer", searchKey, searchText);
                    if(customers != null) customersTable.setModel(customers);
                }
            }
        });

        add(txtSearchCustomers);

    }

    public Customer getCustomer() {
        Customer customer = new Customer();

        customer.c_id = textField_id.getText();
        customer.c_name = textField_name.getText();
        customer.c_contact = textField_contact.getText();
        customer.c_address = textField_address.getText();

        return customer;
    }

}
