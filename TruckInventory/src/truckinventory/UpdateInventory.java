/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package truckinventory;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;

/**
 *
 * @author beth
 */
public class UpdateInventory extends JFrame{
    
    private JFrame frame;
    private JComboBox items;
    private String[] itemOptions;
    private JTextField currentName, currentQuantity, currentRestockThreshold, newName, newQuantity, newRestockThreshold;
    private JLabel instructions, currentNameL, currentQuantityL, currentRestockThresholdL, newNameL, newQuantityL, newRestockThresholdL;
    private JButton submit;
    private Container contents;
    private String currentOption, message;
    private Scanner file;
    
    ArrayList<Inventory> SupplyList = new ArrayList<>();
    
    public UpdateInventory(){
        frame = new JFrame("Modify Inventory Item");
        
        // Create a dropdown field to select from the existing items
        SupplyList = ShowInventory.readData();
        Inventory rInventory = null;
        itemOptions = new String[SupplyList.size()+1];
        itemOptions[0] = "";
        for (int ct = 0; ct < SupplyList.size();  ct++) {
            rInventory = SupplyList.get(ct);
    	    itemOptions[ct+1] = rInventory.getItem();
    	}
        currentOption = itemOptions[0];
        
        items = new JComboBox(itemOptions);
        items.setEditable(false);
        instructions = new JLabel("Enter an item or select one from the list");
        
	// Create text fields, labels and submit button for user to enter the new item data
        currentName = new JTextField();
        currentName.setEditable(false);
        currentQuantity = new JTextField();
        currentQuantity.setEditable(false);
        currentRestockThreshold = new JTextField();
        currentRestockThreshold.setEditable(false);
        currentNameL = new JLabel("Current Name: ");
        currentQuantityL = new JLabel("Current Quantity: ");
        currentRestockThresholdL = new JLabel("Current Restock Threshold: ");
        
	newName = new JTextField();
        newQuantity = new JTextField();
        newRestockThreshold = new JTextField();
        newNameL = new JLabel("Updated Name: ");
        newQuantityL = new JLabel("Updated Quantity: ");
        newRestockThresholdL = new JLabel("Updated Restock Threshold: ");
        submit = new JButton("Submit");
        
	contents = getContentPane();
        
	// Set a grid layout for all the fields
	contents.setLayout(new GridLayout(4,2));
        
        // Create panel 1 for the drop down menu of items
        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(2,0));
        p1.add(instructions);
	p1.add(items);
        
        // Create panel 2 for the labels and text fields
        JPanel p2 = new JPanel();
	p2.setLayout(new GridLayout(3,2));	 
	p2.add(currentNameL);
        p2.add(currentName);
        p2.add(currentQuantityL);
        p2.add(currentQuantity);
        p2.add(currentRestockThresholdL);
        p2.add(currentRestockThreshold);
        
        // Create panel 2 for the labels and text fields
        JPanel p3 = new JPanel();
	p3.setLayout(new GridLayout(3,2));	 
	p3.add(newNameL);
        p3.add(newName);
        p3.add(newQuantityL);
        p3.add(newQuantity);
        p3.add(newRestockThresholdL);
        p3.add(newRestockThreshold);
        
        // Create panel 3 for the submit button
        JPanel p4 = new JPanel();
	p4.setLayout(new GridLayout(2,0));	 
	p4.add(submit);
        
	// Add both panels to Content Pane
        contents.add(p1, BorderLayout.CENTER);
        contents.add(p2, BorderLayout.CENTER);
        contents.add(p3, BorderLayout.CENTER);
        contents.add(p4, BorderLayout.CENTER);
        
        frame.setContentPane(contents);
        frame.pack();
        frame.setSize(400, 400);

        // Show Frame
        frame.setVisible(true);
        
        items.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                String newSelection = (String)cb.getSelectedItem();
                currentOption = newSelection;
                
                
                ArrayList<Inventory> SupplyList = new ArrayList<>();
                SupplyList = ShowInventory.readData();
                Inventory tempInventory;

                for (int count = 0; count < SupplyList.size();  count++) {
                    tempInventory = SupplyList.get(count);
                    if(tempInventory.getItem().equals(currentOption)){
                        currentName.setText(tempInventory.getItem());
                        currentQuantity.setText(String.valueOf(tempInventory.getQuantity()));
                        currentRestockThreshold.setText(String.valueOf(tempInventory.getRestockThreshold()));
                     }	
                 }
            }
        });
        
        submit.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent ae) {
               // check if all the fields are populated
               if((newName.getText() == null || newName.getText().length() == 0) && 
                       (newQuantity.getText() == null || newQuantity.getText().length() == 0) && 
                       (newRestockThreshold.getText() == null || newRestockThreshold.getText().length() == 0)) {
                   message = "Please fill out at least one field to submit an update to an item";
                   JOptionPane.showMessageDialog(null, message);
               }
               // check if the quantity and restock threshold are numeric values
               else{
                   try{
                       if(newQuantity.getText().length() != 0){
                           Integer.parseInt(newQuantity.getText());
                       }
                       if(newRestockThreshold.getText().length() != 0){
                           Integer.parseInt(newRestockThreshold.getText());
                       }
                       
                       // update the item selected with the new values to the Inventory array list 
                       // and append updated item to the inventory.txt file if both checks pass
                       ArrayList<Inventory> SupplyList = new ArrayList<>();
                       SupplyList = ShowInventory.readData();
                       Inventory tempInventory;
                       String itemName = "";

                       for (int count = 0; count < SupplyList.size();  count++) {
                          tempInventory = SupplyList.get(count);
                          if(tempInventory.getItem().equals(currentOption)){
                              String ogItemInfo = tempInventory.getItem() + "," + tempInventory.getQuantity() + "," + tempInventory.getRestockThreshold() + "," + tempInventory.getRestockDate();
                              update(tempInventory, ogItemInfo);
                              itemName = tempInventory.getItem();
                           }	
                       }
                       
                       if(newName.getText().length() != 0){
                           itemName = newName.getText();
                       }
                       
                       message = "The item '" + itemName + "' was successfully updated.";
                       JOptionPane.showMessageDialog(null, message);
                       frame.dispose();
                   }
                   catch(NumberFormatException nfe){
                       message = "The Updated Quantity and Updated Restock Threshold values must be numeric to submit an update to an item";
                       JOptionPane.showMessageDialog(null, message);
                   }
               }
           }
       });
    }
    
    public void update(Inventory item, String ogInfo){
        if(newName.getText().length() != 0){
            item.setItem(newName.getText());
        }
        if(newQuantity.getText().length() != 0){
            item.setQuantity(Integer.parseInt(newQuantity.getText()));
        }
        if(newRestockThreshold.getText().length() != 0){
            item.setRestockThreshold(Integer.parseInt(newRestockThreshold.getText()));
        }
        RestockInventory appendItem = new RestockInventory();
        appendItem.appendInventoryFile(item, ogInfo);
    }   
}
