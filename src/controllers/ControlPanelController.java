package controllers;

import views.ControlPanel;
import views.ProductsPanel;
import views.PurchasesPanel;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.event.ActionEvent;

public class ControlPanelController implements MenuListener {

    private ControlPanel controlPanel;

    ProductsPanel productsPanel = new ProductsPanel();
    PurchasesPanel purchasesPanel = new PurchasesPanel();

    public ControlPanelController(ControlPanel panel) {
        controlPanel = panel;
    }

    public void loadProductsPanel() {
        controlPanel.setContentPane(productsPanel);
    }

    public void loadPurchasesPanel() {
        controlPanel.setContentPane(purchasesPanel);
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
