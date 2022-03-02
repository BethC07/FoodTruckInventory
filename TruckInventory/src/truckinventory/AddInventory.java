/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package truckinventory;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.*;
import java.io.*;
/**
 *
 * @author beth
 */
public class AddInventory extends JFrame{
    private JFrame frame;
    private JTextField name, currentQuantity, restockThreshold;
    private JLabel nameL, currentQuantityL, restockThresholdL;
    private JButton submit;
    private Container contents;
    private String message;
    private Scanner file;
    
    ArrayList<Inventory> SupplyList = new ArrayList<>( );
    
    
    public AddInventory(){
        frame = new JFrame("Add Inventory Item");
	// Create text fields, labels and submit button for user to enter the new item data
	name = new JTextField();
        currentQuantity = new JTextField();
        restockThreshold = new JTextField();
        nameL = new JLabel("Name: ");
        currentQuantityL = new JLabel("Quantity: ");
        restockThresholdL = new JLabel("Restock Threshold: ");
        submit = new JButton("Submit");
        
	contents = getContentPane();
        
	// Set a grid layout for all the fields
	contents.setLayout(new GridLayout(4,2));
        
        // Create panel 1 for the labels and text fields
        JPanel p1 = new JPanel();
	p1.setLayout(new GridLayout(3,2));	 
	p1.add(nameL);
        p1.add(name);
        p1.add(currentQuantityL);
        p1.add(currentQuantity);
        p1.add(restockThresholdL);
        p1.add(restockThreshold);
        
        // Create panel 2 for the submit button
        JPanel p2 = new JPanel();
	p2.setLayout(new GridLayout(2,0));	 
	p2.add(submit);
        
	// Add both panels to Content Pane
        contents.add(p1, BorderLayout.CENTER);
        contents.add(p2, BorderLayout.CENTER);
        
        frame.setContentPane(contents);
        frame.pack();
        frame.setSize(400, 400);

        // Show Frame
        frame.setVisible(true);
        
        // Action listener for the Submit button
        // Add the new item to the array as well as the text file if it passed the following checks:
        // - All fields must be populated
        // - Item must be new to the inventory
        // - Quantity and restock threshold must be integer values
         submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                // check if all the fields are populated
                if(name.getText() == null || name.getText().length() == 0 || 
                        currentQuantity.getText() == null || currentQuantity.getText().length() == 0 || 
                        restockThreshold.getText() == null || restockThreshold.getText().length() == 0) {
                    message = "Please fill out all the fields to submit a new item";
                    JOptionPane.showMessageDialog(frame, message, null, JOptionPane.ERROR_MESSAGE);
                }
                else{
                    // check if the item entered already exists in the inventory
                    SupplyList = ShowInventory.readData();
                    Inventory tempInventory = null;
                    String allItems = "";
                    for ( int ct = 0; ct < SupplyList.size(); ct++) {
                        tempInventory = SupplyList.get(ct);
                        allItems += tempInventory.getItem().toLowerCase() + ",";
                    }
                    if(allItems.contains(name.getText().toLowerCase()) && name.getText().length() != 0){
                        message = "The item '" + name.getText() + "' already exists in the inventory.\n"
                                + "Please go back to the main menu and select 'Modify Inventory' if you wish to update this item.";
                        JOptionPane.showMessageDialog(frame, message, null, JOptionPane.WARNING_MESSAGE);
                    }
                    // check if the quantity and restock threshold are numeric values
                    else if(name.getText().length() != 0){
                        try{
                            int currentQuantityNumb = Integer.parseInt(currentQuantity.getText());
                            int restockThresholdNumb = Integer.parseInt(restockThreshold.getText());

                            // add new item to the Inventory array list 
                            // and append new item to the inventory.txt file if all checks pass
                            FileWriter fw = null; 
                            BufferedWriter bw = null; 
                            PrintWriter pw = null; 
                            try {
                                // add new item to the Inventory array list
                                Inventory newItem = new Inventory(name.getText(), currentQuantityNumb, restockThresholdNumb, "");
                                SupplyList.add(newItem);

                                // append to file
                                fw = new FileWriter("Inventory.txt", true); 
                                bw = new BufferedWriter(fw); 
                                pw = new PrintWriter(bw);
                                pw.println(name.getText() + "," + currentQuantity.getText() + "," + restockThreshold.getText() + "," + "NA");
                                message = "New item '" + name.getText() + "' was successfully added to your truck's inventory";
                                JOptionPane.showMessageDialog(null, message);
                                pw.flush();
                                frame.dispose();
                            }
                            catch(IOException io){
                                message = "Unable to find Inventory.txt";
                                JOptionPane.showMessageDialog(frame, message, null, JOptionPane.ERROR_MESSAGE);
                            }
                            finally { 
                                try { 
                                    pw.close();
                                    bw.close();
                                    fw.close(); 
                                } 
                                catch (IOException io) {
                                    message = "Unable to find Inventory.txt";
                                    JOptionPane.showMessageDialog(frame, message, null, JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                        catch(NumberFormatException nfe){
                            message = "The Quantity and Restock Threshold values must be numeric to submit a new item";
                            JOptionPane.showMessageDialog(frame, message, null, JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
    }
}
