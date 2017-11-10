package views;

import controllers.ControlPanelController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel extends JFrame {

    private ControlPanel self = this;

    private ControlPanelController controlPanelController = new ControlPanelController();

    public ControlPanel() {
        super("Control Panel");
        setVisible(true);
        setBounds(new Rectangle(700, 700));

        init();
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

        JMenuItem products = getProductsMenuItem();
        menubar.add(products);

        JMenuItem purchases = getPurchasesMenuItem();
        menubar.add(purchases);

        //JMenu helpMenu = getHelpMenu();
        //menubar.add(helpMenu);

        return menubar;

    }

    public JMenuItem getProductsMenuItem() {

        JMenuItem products = new JMenuItem("Products");
        products.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                controlPanelController.clickListener(self, event);
            }
        });

        return products;

    }

    public JMenuItem getPurchasesMenuItem() {

        JMenuItem purchases = new JMenuItem("Purchases");
        purchases.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                controlPanelController.clickListener(self, event);
            }
        });

        return purchases;

    }

    public void init() {
        JMenuBar menubar = getMenubar();
        setJMenuBar(menubar);
    }

}
