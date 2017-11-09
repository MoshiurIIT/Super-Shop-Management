package views;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JFrame {

    public ControlPanel() {
        super("Control Panel");
        setVisible(true);
        setBounds(new Rectangle(500, 500));

        init();
    }

    public void init() {
        JMenuBar menubar = getMenubar();
        setJMenuBar(menubar);
    }

    public JMenuBar getMenubar() {

        JMenuBar menubar = new JMenuBar();

        JMenuItem products = getProductsMenuItem();
        menubar.add(products);

        return menubar;

    }

    public JMenuItem getProductsMenuItem() {

        JMenuItem products = new JMenuItem("Products");

        return products;

    }

}
