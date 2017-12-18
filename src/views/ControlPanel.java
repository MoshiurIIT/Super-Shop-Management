package views;

import controllers.ControlPanelController;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JFrame {

    private ControlPanel self = this;
    private String userRole;

    private ControlPanelController controlPanelController = new ControlPanelController(self);

    public ControlPanel(String _role) {
        super("Super Shop");
        userRole = _role;

        init();

        setBounds(new Rectangle(1000, 700));
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public JMenu getAccountMenu() {

        JMenu accountMenu = new JMenu("Account");

        JMenuItem changePasswordItem = new JMenuItem("Change Password");
        changePasswordItem.addActionListener(controlPanelController);
        accountMenu.add(changePasswordItem);

        return accountMenu;

    }

    public JMenu getCustomersMenu() {

        JMenu customers = new JMenu("Customers");
        customers.addMenuListener(controlPanelController);

        return customers;

    }

    public JMenu getHelpMenu() {

        JMenu helpMenu = new JMenu("Help");

        JMenuItem manualHelpMenuItem = getManualHelpMenuItem();
        helpMenu.add(manualHelpMenuItem);

        return helpMenu;

    }

    public JMenuItem getManualHelpMenuItem() {

        JMenuItem manualHelpMenuItem = new JMenuItem("Manual");

        return manualHelpMenuItem;

    }

    public JMenuBar getMenubar() {

        JMenuBar menubar = new JMenuBar();

        JMenu products = getProductsMenu();
        menubar.add(products);

        JMenu purchases = getPurchasesMenu();
        menubar.add(purchases);

        JMenu customers = getCustomersMenu();
        menubar.add(customers);

        JMenu accountMenu = getAccountMenu();
        menubar.add(accountMenu);

        if(userRole == "Owner") {
            JMenu usersMenu = getUsersMenu();
            menubar.add(usersMenu);

            JMenu salesMenu = getSalesMenu();
            menubar.add(salesMenu);
        }

        JMenu helpMenu = getHelpMenu();
        menubar.add(helpMenu);

        return menubar;

    }

    public JMenu getSalesMenu() {
        JMenu salesMenu = new JMenu("Sales");
        salesMenu.addMenuListener(controlPanelController);

        return salesMenu;
    }

    public JMenu getUsersMenu() {
        JMenu usersMenu = new JMenu("Users");

        JMenuItem adminMenuItem = new JMenuItem("Owner");
        adminMenuItem.addActionListener(controlPanelController);
        usersMenu.add(adminMenuItem);

        JMenuItem salesStaffMenuItem = new JMenuItem("Sales Staff");
        salesStaffMenuItem.addActionListener(controlPanelController);
        usersMenu.add(salesStaffMenuItem);

        return usersMenu;
    }

    public JMenu getProductsMenu() {

        JMenu products = new JMenu("Products");
        products.addMenuListener(controlPanelController);

        return products;

    }

    public JMenu getPurchasesMenu() {

        JMenu purchases = new JMenu("Purchases");
        purchases.addMenuListener(controlPanelController);

        return purchases;

    }

    public void init() {
        JMenuBar menubar = getMenubar();
        setJMenuBar(menubar);

        controlPanelController.loadPurchasesPanel();
    }

}
