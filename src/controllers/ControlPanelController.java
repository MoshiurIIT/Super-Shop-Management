package controllers;

import views.ControlPanel;
import views.CustomersPanel;
import views.ProductsPanel;
import views.PurchasesPanel;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class ControlPanelController implements MenuListener {

    private ControlPanel controlPanel;

    ProductsPanel productsPanel = new ProductsPanel();
    PurchasesPanel purchasesPanel = new PurchasesPanel();
    CustomersPanel customersPanel = new CustomersPanel();

    public ControlPanelController(ControlPanel panel) {
        controlPanel = panel;
    }

    public void loadProductsPanel() {
        controlPanel.setContentPane(productsPanel);
        controlPanel.setVisible(true);
    }

    public void loadPurchasesPanel() {
        controlPanel.setContentPane(purchasesPanel);
        controlPanel.setVisible(true);
    }

    public void loadCustomersPanel() {
        controlPanel.setContentPane(customersPanel);
        controlPanel.setVisible(true);
    }

    @Override
    public void menuSelected(MenuEvent event) {
        String selectedMenu = ((JMenuItem)event.getSource()).getActionCommand();
        switch (selectedMenu){
            case "Products":
                loadProductsPanel();
                break;
            case "Purchases":
                loadPurchasesPanel();
                break;
            case "Customers":
                loadCustomersPanel();
                break;
            default:
                break;
        }
        controlPanel.setVisible(true);
    }

    @Override
    public void menuDeselected(MenuEvent e) {

    }

    @Override
    public void menuCanceled(MenuEvent e) {

    }
}
