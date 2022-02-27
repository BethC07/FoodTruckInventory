/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package truckinventory;

import javafx.application.Application;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

/**
 *
 * @author vjsat
 */
public class TruckInventory extends Application {
    
    DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").withZone(ZoneId.of("UTC"));
    Instant timeStamp;
    String logName = "log.txt";
    
    @Override
    public void start(Stage primaryStage) {
        // sets up panel, labels and buttons for main login screen
        ShowRestockNotification checkRestock = new ShowRestockNotification();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        Text scenetitle = new Text("Food Truck Inventory. Login to continue.");
        grid.add(scenetitle, 0, 0, 2, 1);
        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);
        TextField inputUsername = new TextField();
        grid.add(inputUsername, 1, 1);
        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);
        PasswordField inputPass = new PasswordField();
        grid.add(inputPass, 1, 2);
        Button btn = new Button("Login");
        grid.add(btn, 0, 3);
        Button btn2 = new Button("Clear Form");
        grid.add(btn2, 1, 3);
        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);
        //set up alert for failed login attempts
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        
        btn.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent e) {
                // Authenticate the user
                boolean isValid = authenticate(inputUsername.getText(), inputPass.getText());
                // code added to stop login after so many attempts
                if (isValid) { // if password is valid then clear the pane and go to next screen
                    // records success to audit log
                    timeStamp = Instant.now();
                    String logText = ("Successful login. ||" + dtf.format(timeStamp) +"|| User: " + inputUsername.getText());
                    logData(logText);
                    // starts main menu
                    InputClass main = new InputClass();
                    try {
                        checkRestock.checkStock();
                        primaryStage.setScene(main.createMainMenu(primaryStage));
                        primaryStage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(TruckInventory.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {                    
                    //final Text actiontarget = new Text();
                    alert.setHeaderText("Failed Login Attempt");
                    timeStamp = Instant.now();
                    String logText = ("Failed login attempt ||" + dtf.format(timeStamp) +"|| User: " + inputUsername.getText());
                    logData(logText);
                    alert.showAndWait();
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("Please try again.");
                }
                
            }
        });
        
        btn2.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                inputUsername.clear();
                inputPass.clear();
            }
        });
                
        Scene scene = new Scene(grid, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login Form");
        primaryStage.sizeToScene(); 
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
   
        // verifies input username and password
    public boolean authenticate(String user, String pword) {
        boolean isValid = false;
        if (user.equalsIgnoreCase("admin")
                && pword.equals("1234")) {
            isValid = true;
        }
        return isValid;
    }
    
    public void logData(String logText){
        File log = new File(logName);
        try{
            FileWriter fw = new FileWriter(log, true);
            PrintWriter auditLog = new PrintWriter(fw);
            auditLog.println(logText);
            auditLog.close();
        }catch(IOException io) {
            System.out.println("File IO Exception" + io.getMessage());
        }
        
    }
}