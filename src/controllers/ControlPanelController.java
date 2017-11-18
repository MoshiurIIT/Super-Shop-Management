package controllers;

import views.*;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanelController implements MenuListener, ActionListener {

    private ControlPanel controlPanel;

    ProductsPanel productsPanel = new ProductsPanel();
    PurchasesPanel purchasesPanel = new PurchasesPanel();
    CustomersPanel customersPanel = new CustomersPanel();
    OwnersPanel ownersPanel = new OwnersPanel();
    SalesStaffsPanel salesStaffsPanel = new SalesStaffsPanel();
    SalesPanel salesPanel = new SalesPanel();

    public ControlPanelController(ControlPanel panel) {
        controlPanel = panel;
    }

    public void loadProductsPanel() {
        productsPanel.loadProducts();
        controlPanel.setContentPane(productsPanel);
        controlPanel.setVisible(true);
    }

    public void loadPurchasesPanel() {
        purchasesPanel.loadProducts();
        controlPanel.setContentPane(purchasesPanel);
        controlPanel.setVisible(true);
    }

    public void loadCustomersPanel() {
        controlPanel.setContentPane(customersPanel);
        controlPanel.setVisible(true);
    }

    public void loadOwnersPanel() {
        ownersPanel.loadOwners();
        controlPanel.setContentPane(ownersPanel);
        controlPanel.setVisible(true);
    }

    public void loadSalesStaffPanel() {
        salesStaffsPanel.loadSalesStaffs();
        controlPanel.setContentPane(salesStaffsPanel);
        controlPanel.setVisible(true);
    }

    public void loadSalesPanel() {
        salesPanel.loadPurchases();
        controlPanel.setContentPane(salesPanel);
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
            case "Sales":
                loadSalesPanel();
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

    @Override
    public void actionPerformed(ActionEvent event) {
        String selectedItem = event.getActionCommand();

        switch (selectedItem) {
            case "Owner":
                loadOwnersPanel();
                break;
            case "Sales Staff":
                loadSalesStaffPanel();
                break;
            default:
                break;
        }
    }
}
