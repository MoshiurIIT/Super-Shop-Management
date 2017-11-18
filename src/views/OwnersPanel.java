package views;

import models.Owner;
import services.Database;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;

public class OwnersPanel extends JPanel {

    private JTable ownersTable = new JTable();
    private JTextField textField_userName;
    private JPasswordField passwordField_password;

    private JComboBox comboBox;
    private JTextField txtSearchOwners;
    TableModel owners;

    public OwnersPanel() {
        setBackground(Color.WHITE);
        setBounds(0, 0, 1000, 700);
        setLayout(null);

        init();
    }

    public void loadOwners() {
        owners = Database.get("AdminLogin");
        if(owners != null) ownersTable.setModel(owners);
    }

    public void init() {

        JDesktopPane ownerPane = getOwnerPane();
        add(ownerPane);

        JDesktopPane ownersPen = getOwnersPane();
        add(ownersPen);

        addSearchOption();

        textField_userName = new JTextField();
        textField_userName.setBounds(120, 127, 120, 20);
        add(textField_userName);

        passwordField_password = new JPasswordField();
        passwordField_password.setBounds(120, 159, 120, 20);
        add(passwordField_password);

        JButton btnAddOwner = new JButton("Add");
        btnAddOwner.setBounds(5, 388, 88, 44);
        btnAddOwner.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Owner owner = getOwner();

                Boolean isAdded = Database.addOwner(owner);
                if(isAdded){
                    JOptionPane.showMessageDialog(null, "Data Added");
                    loadOwners();
                }
                else JOptionPane.showMessageDialog(null, "Error");
            }
        });
        add(btnAddOwner);

        JButton btnUpdateOwner = new JButton("Update");
        btnUpdateOwner.setBounds(100, 388, 88, 44);
        btnUpdateOwner.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Owner owner = getOwner();

                Boolean isUpdated = Database.updateOwner(owner);
                if(isUpdated){
                    JOptionPane.showMessageDialog(null, "Data Updated");
                    loadOwners();
                }
                else JOptionPane.showMessageDialog(null, "Error");
            }
        });
        add(btnUpdateOwner);

        JButton btnDeleteOwner = new JButton("Delete");
        btnDeleteOwner.setBounds(200, 388, 88, 44);
        btnDeleteOwner.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Owner owner = getOwner();

                Boolean isDeleted = Database.deleteOwner(owner);
                if(isDeleted){
                    JOptionPane.showMessageDialog(null, "Data Deleted");
                    loadOwners();
                }
                else JOptionPane.showMessageDialog(null, "Error");
            }
        });
        add(btnDeleteOwner);
    }

    public JDesktopPane getOwnerPane() {

        JDesktopPane ownerPane = new JDesktopPane();
        ownerPane.setBorder(BorderFactory.createTitledBorder("Owner Data"));
        ownerPane.setBounds(3, 97, 286, 280);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setBounds(10, 26, 80, 24);
        ownerPane.add(lblUsername);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setBounds(10, 52, 80, 30);
        ownerPane.add(lblPassword);

        return ownerPane;
    }

    public JDesktopPane getOwnersPane() {

        JDesktopPane ownersPane = new JDesktopPane();

        ownersPane.setBorder(BorderFactory.createTitledBorder("Owners Table"));

        loadOwners();

        ownersTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = ownersTable.getSelectedRow();
                TableModel tableModel = ownersTable.getModel();
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
        scrollPane.setViewportView(ownersTable);
        ownersPane.add(scrollPane);

        ownersPane.setBounds(300, 100, 670, 500);

        return ownersPane;
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

        txtSearchOwners = new JTextField();
        txtSearchOwners.setBounds(500, 50, 200, 30);
        txtSearchOwners.setToolTipText("Search Products");

        txtSearchOwners.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent event) {
                String searchKey = (String) comboBox.getSelectedItem();
                String searchText = txtSearchOwners.getText();

                if(searchText.isEmpty()) {
                    loadOwners();
                }
                else {
                    TableModel owners = Database.get("AdminLogin", searchKey, searchText);
                    if(owners != null) ownersTable.setModel(owners);
                }
            }
        });

        add(txtSearchOwners);

    }

    public Owner getOwner() {
        Owner owner = new Owner();

        owner.userName = textField_userName.getText();
        owner.Password = new String(passwordField_password.getPassword());

        return owner;
    }

}
