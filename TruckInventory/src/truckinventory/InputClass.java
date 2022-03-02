/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package truckinventory;

import java.io.IOException;
import java.net.URL;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
/**
 *
 * @author vjsat
 */
public class InputClass {
  
    public Scene createMainMenu(Stage stage) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/mainmenu.fxml");
        loader.setLocation(xmlUrl);
        Scene root = loader.load();
                
        return root;
    }
}
