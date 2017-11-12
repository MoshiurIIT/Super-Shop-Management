package controllers;

import views.ControlPanel;
import views.CustomersPanel;
import views.PurchasesPanel;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class ControlPanelController implements MenuListener {

    private ControlPanel controlPanel;

    CustomersPanel productsPanel = new CustomersPanel();
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

    public void loadCutomersPanel() {
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
            default:
                break;
        }
    }

    @Override
    public void menuDeselected(MenuEvent e) {

    }

    @Override
    public void menuCanceled(MenuEvent e) {

    }
}
