package views;

import models.Owner;
import models.SalesStaff;
import services.Database;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;

public class SalesStaffsPanel extends JPanel {

    private JTable salesStaffsTable = new JTable();
    private JTextField textField_userName;
    private JPasswordField passwordField_password;

    private JComboBox comboBox;
    private JTextField txtSearchSalesStaffs;
    TableModel salesStaffs;

    public SalesStaffsPanel() {
        setBackground(Color.WHITE);
        setBounds(0, 0, 1000, 700);
        setLayout(null);

        init();
    }

    public void loadSalesStaffs() {
        salesStaffs = Database.get("Login");
        if(salesStaffs != null) salesStaffsTable.setModel(salesStaffs);
    }

    public void init() {

        JDesktopPane salesStaffPane = getSalesStaffPane();
        add(salesStaffPane);

        JDesktopPane salesStaffsPane = getSalesStaffsPane();
        add(salesStaffsPane);

        addSearchOption();

        textField_userName = new JTextField();
        textField_userName.setBounds(120, 127, 120, 20);
        add(textField_userName);

        passwordField_password = new JPasswordField();
        passwordField_password.setBounds(120, 159, 120, 20);
        add(passwordField_password);

        JButton btnAddSalesStaff = new JButton("Add");
        btnAddSalesStaff.setBounds(5, 388, 88, 44);
        btnAddSalesStaff.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                SalesStaff owner = getSalesStaff();

                Boolean isAdded = Database.addSalesStaff(owner);
                if(isAdded){
                    JOptionPane.showMessageDialog(null, "Data Added");
                    loadSalesStaffs();
                }
                else JOptionPane.showMessageDialog(null, "Error");
            }
        });
        add(btnAddSalesStaff);

        JButton btnUpdateSalesStaff = new JButton("Update");
        btnUpdateSalesStaff.setBounds(100, 388, 88, 44);
        btnUpdateSalesStaff.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                SalesStaff salesStaff = getSalesStaff();

                Boolean isUpdated = Database.updateSalesStaff(salesStaff);
                if(isUpdated){
                    JOptionPane.showMessageDialog(null, "Data Updated");
                    loadSalesStaffs();
                }
                else JOptionPane.showMessageDialog(null, "Error");
            }
        });
        add(btnUpdateSalesStaff);

        JButton btnDeleteSalesStaff = new JButton("Delete");
        btnDeleteSalesStaff.setBounds(200, 388, 88, 44);
        btnDeleteSalesStaff.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                SalesStaff salesStaff = getSalesStaff();

                Boolean isDeleted = Database.deleteSalesStaff(salesStaff);
                if(isDeleted){
                    JOptionPane.showMessageDialog(null, "Data Deleted");
                    loadSalesStaffs();
                }
                else JOptionPane.showMessageDialog(null, "Error");
            }
        });
        add(btnDeleteSalesStaff);
    }

    public JDesktopPane getSalesStaffPane() {

        JDesktopPane salesStaffPane = new JDesktopPane();
        salesStaffPane.setBorder(BorderFactory.createTitledBorder("Sales Staff Data"));
        salesStaffPane.setBounds(3, 97, 286, 280);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setBounds(10, 26, 80, 24);
        salesStaffPane.add(lblUsername);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setBounds(10, 52, 80, 30);
        salesStaffPane.add(lblPassword);

        return salesStaffPane;
    }

    public JDesktopPane getSalesStaffsPane() {

        JDesktopPane salesStaffs = new JDesktopPane();

        salesStaffs.setBorder(BorderFactory.createTitledBorder("Sales Staffs Table"));

        loadSalesStaffs();

        salesStaffsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = salesStaffsTable.getSelectedRow();
                TableModel tableModel = salesStaffsTable.getModel();
                String data[] = new String[2];
                for (int i = 0; i < 2; i++) {
                    data[i] = tableModel.getValueAt(row, i).toString();
                }
                textField_userName.setText(data[0]);
                passwordField_password.setText(data[1]);
            }
        });

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 20, 640, 480);
        scrollPane.setViewportView(salesStaffsTable);
        salesStaffs.add(scrollPane);

        salesStaffs.setBounds(300, 100, 670, 500);

        return salesStaffs;
    }

    public void addSearchOption() {

        JLabel searchKeyLabel = new JLabel("Search Key");
        searchKeyLabel.setBounds(350, 10, 100, 30);
        add(searchKeyLabel);

        comboBox = new JComboBox<String>(new String[] {"Username", "Password"});
        comboBox.setBounds(500, 10, 200, 30);
        add(comboBox);

        JLabel searchTextLabel = new JLabel("Search Text");
        searchTextLabel.setBounds(350, 50, 100, 30);
        add(searchTextLabel);

        txtSearchSalesStaffs = new JTextField();
        txtSearchSalesStaffs.setBounds(500, 50, 200, 30);
        txtSearchSalesStaffs.setToolTipText("Search Products");

        txtSearchSalesStaffs.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent event) {
                String searchKey = (String) comboBox.getSelectedItem();
                String searchText = txtSearchSalesStaffs.getText();

                if(searchText.isEmpty()) {
                    loadSalesStaffs();
                }
                else {
                    TableModel salesStaffs = Database.get("Login", searchKey, searchText);
                    if(salesStaffs != null) salesStaffsTable.setModel(salesStaffs);
                }
            }
        });

        add(txtSearchSalesStaffs);

    }

    public SalesStaff getSalesStaff() {
        SalesStaff salesStaff = new SalesStaff();

        salesStaff.userName = textField_userName.getText();
        salesStaff.Password = new String(passwordField_password.getPassword());

        return salesStaff;
    }

}
