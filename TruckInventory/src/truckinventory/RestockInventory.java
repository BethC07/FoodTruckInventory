/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package truckinventory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author beth
 */
public class RestockInventory {
    
    String message;
    
    public void restock(Inventory item, String ogInfo, int restockValue){
        item.setQuantity(restockValue);
        updateTime(item, ogInfo);
    }
    
    public void updateTime(Inventory item, String ogInfo){
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String todatFormatted = today.format(dtf);
        item.setRestockDate(todatFormatted);
        appendInventoryFile(item, ogInfo);
    }
    
    public void appendInventoryFile(Inventory item, String ogInfo){
        // append to file
        try{
            String oldItemInfo = ogInfo, newItemInfo;
            FileWriter fw = null; 
            Scanner file = null;
            
            // gather all the inventory item data from the file into a string
            file = new Scanner( new File ("Inventory.txt") );   //insert file path   
            StringBuffer buffer = new StringBuffer();
	    while (file.hasNextLine()){
                buffer.append(file.nextLine() + System.lineSeparator());
            }
            
            String itemInfo = buffer.toString();
            file.close();

            newItemInfo = item.getItem() + "," + item.getQuantity() + "," + item.getRestockThreshold() + "," + item.getRestockDate();
            itemInfo = itemInfo.replaceAll(oldItemInfo, newItemInfo);
            
            // clear the inventory file
            fw = new FileWriter("Inventory.txt", false);
            fw.close();
            
            // write to the inventory file with the updated item info
            fw = new FileWriter("Inventory.txt", true);
            fw.append(itemInfo);
            fw.flush();
        }
        catch(IOException io){
            message = "Unable to find Inventory.txt";
            JOptionPane.showMessageDialog(null, message);
        }
    }
    
}
