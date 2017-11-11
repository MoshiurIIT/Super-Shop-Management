package views;

import com.sun.org.apache.xpath.internal.operations.Bool;
import services.Authentication;
import sun.security.util.Password;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class LoginPanel extends JFrame {

    private LoginPanel self = this;

    private JComboBox userSelectionBox;
    private JTextField userNameField;
    private JPasswordField passwordField;

    public LoginPanel() {
        super("Log in");
        setBounds(new Rectangle(700, 700));

        init();

        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public JPanel getContentPanel() {

        JPanel contentPanel = new JPanel() {

            @Override
            protected void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);
                loadLoginImage(graphics);
            }
        };

        contentPanel.setBackground(Color.WHITE);

        JLabel userTypeLabel = new JLabel("User Type:");
        userTypeLabel.setBounds(150, 200, 100, 30);
        contentPanel.add(userTypeLabel);

        userSelectionBox = getUserSelectionBox();
        userSelectionBox.setBounds(250, 200, 200, 30);
        contentPanel.add(userSelectionBox);

        JLabel userNameLabel = new JLabel("Username:");
        userNameLabel.setBounds(150, 250, 100, 30);
        contentPanel.add(userNameLabel);

        userNameField = getUserNameField();
        userNameField.setBounds(250, 250, 200, 30);
        contentPanel.add(userNameField);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(150, 300, 100, 30);
        contentPanel.add(passwordLabel);

        passwordField = getPasswordField();
        passwordField.setBounds(250, 300, 200, 30);
        contentPanel.add(passwordField);

        JButton submitButton = getSubmitButton();
        contentPanel.add(submitButton);

        return contentPanel;

    }

    public JPasswordField getPasswordField() {

        JPasswordField passwordField = new JPasswordField();

        return passwordField;

    }

    public void loadLoginImage(Graphics graphics) {

        try{
            Image loginImage = ImageIO.read(new File("assets\\images\\login.jpg"));
            graphics.drawImage(loginImage, 250, 50, 200, 100, null);

        } catch(IOException exception){
            exception.printStackTrace();
        }

    }

    public JButton getSubmitButton() {

        JButton submitButton = new JButton("Log In");

        submitButton.setBounds(300, 400, 100, 30);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String userType = userSelectionBox.getSelectedItem().toString();
                String userName = userNameField.getText();
                String password = new String(passwordField.getPassword());

                Boolean authenticated = Authentication.authenticate(userType,userName, password);
                if(authenticated) JOptionPane.showMessageDialog(null,"Authentication Successful");
                else JOptionPane.showMessageDialog(null,"Try Again");
            }
        });

        return submitButton;

    }

    public JComboBox getUserSelectionBox() {

        String userTypes[] = {"Sales Staff", "Owner"};
        JComboBox userSelectionBox = new JComboBox(userTypes);

        return userSelectionBox;

    }

    public JTextField getUserNameField() {

        JTextField userNameField = new JTextField();

        return userNameField;

    }

    public void init() {

        JPanel contentPanel = getContentPanel();
        setContentPane(contentPanel);
    }

}
