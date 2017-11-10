package views;

import controllers.ControlPanelController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel extends JFrame {

    private ControlPanel self = this;

    private ControlPanelController controlPanelController = new ControlPanelController(self);

    public ControlPanel() {
        super("Control Panel");

        init();

        setBounds(new Rectangle(700, 700));
        setVisible(true);
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

        JMenu helpMenu = getHelpMenu();
        menubar.add(helpMenu);

        return menubar;

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
    }

}
