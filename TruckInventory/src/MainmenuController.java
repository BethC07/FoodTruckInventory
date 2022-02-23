/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javax.swing.JFrame;
import truckinventory.ShowInventory;


/**
 * FXML Controller class
 *
 * @author vjsat
 */
public class MainmenuController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    //Show Inventory button handler
    @FXML private Button btnShow;
    public void handleBtnShow(ActionEvent event) {
        ShowInventory InventoryWindow = new ShowInventory();
    	InventoryWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //btnShow.setVisible(false); // delete this 
    }
    
    // Modify Inventory button handler
    @FXML private Button btnMod;
    public void handleBtnMod(ActionEvent event) {
        btnMod.setVisible(false); // delete this
    }
    
    // Add item button handler
    @FXML private Button btnAdd;
    public void handleBtnAdd(ActionEvent event) {
        btnAdd.setVisible(false); // delete this
    }
    
    // Remove Item Button handler
    @FXML private Button btnRemove;
    public void handleBtnRemove(ActionEvent event) {
        btnRemove.setVisible(false); // delete this
    }
    
    // Exit button handler
    @FXML private Button btnExit;
    public void handleBtnExit(ActionEvent event) {
        Platform.exit();
    }
    
}
