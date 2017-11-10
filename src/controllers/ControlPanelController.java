package controllers;

import views.ControlPanel;
import views.ProductsPanel;
import views.PurchasesPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ControlPanelController {

    ProductsPanel productsPanel = new ProductsPanel();
    PurchasesPanel purchasesPanel = new PurchasesPanel();

    public ControlPanelController() {

    }

    public void clickListener(ControlPanel controlPanel, ActionEvent event) {

        if(event.getActionCommand() == "Purchases"){
            controlPanel.setContentPane(purchasesPanel);
        }
        else {
            controlPanel.setContentPane(productsPanel);
        }

    }

}
