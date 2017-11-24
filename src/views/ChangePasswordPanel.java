package views;

import services.Authentication;
import services.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangePasswordPanel extends JPanel {

    public ChangePasswordPanel() {
        setBackground(Color.WHITE);
        setBounds(0, 0, 1000, 700);
        setLayout(null);

        init();
    }

    public void init() {

        JDesktopPane changePasswordPane = getChangePasswordPane();
        add(changePasswordPane);

    }

    private JDesktopPane getChangePasswordPane() {

        JDesktopPane changePasswordPane = new JDesktopPane();

        changePasswordPane.setBorder(BorderFactory.createTitledBorder("Change Password"));
        changePasswordPane.setBounds(300, 150, 400, 300);

        JLabel usernamelbl = new JLabel("Username");
        usernamelbl.setBounds(20, 20, 200, 30);
        changePasswordPane.add(usernamelbl);

        JLabel usernameText = new JLabel(Authentication.getUsername());
        usernameText.setBounds(230, 20, 150, 30);
        changePasswordPane.add(usernameText);

        JLabel passwordlbl = new JLabel("Current Password");
        passwordlbl.setBounds(20, 80, 200, 30);
        changePasswordPane.add(passwordlbl);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(230, 80, 150, 30);
        changePasswordPane.add(passwordField);

        JLabel newPasswordlbl = new JLabel("New Password");
        newPasswordlbl.setBounds(20, 140, 200, 30);
        changePasswordPane.add(newPasswordlbl);

        JPasswordField newPasswordField = new JPasswordField();
        newPasswordField.setBounds(230, 140, 150, 30);
        changePasswordPane.add(newPasswordField);

        JLabel confirmPasswordlbl = new JLabel("Confirm Password");
        confirmPasswordlbl.setBounds(20, 200, 200, 30);
        changePasswordPane.add(confirmPasswordlbl);

        JPasswordField confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(230, 200, 150, 30);
        changePasswordPane.add(confirmPasswordField);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(280, 260, 100, 30);
        submitButton.addActionListener(event -> {
            String curPassword = new String(passwordField.getPassword());
            String newPassword = new String(newPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            if(!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(null, "New password does not match with confirm password");
                return;
            }
            String msg = Database.updatePassword(curPassword, newPassword);
            JOptionPane.showMessageDialog(null, msg);
        });
        changePasswordPane.add(submitButton);

        return changePasswordPane;
    }

}
