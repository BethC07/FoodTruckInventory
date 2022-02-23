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
        frame = new JFrame();
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
        
         submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                // check if all the fields are populated
                if(name.getText() == null || name.getText().length() == 0 || 
                        currentQuantity.getText() == null || currentQuantity.getText().length() == 0 || 
                        restockThreshold.getText() == null || restockThreshold.getText().length() == 0) {
                    message = "Please fill out all the fields to submit a new item";
                    JOptionPane.showMessageDialog(null, message);
                }
                // check if the quantity and restock threshold are numeric values
                else{
                    try{
                        int currentQuantityNumb = Integer.parseInt(currentQuantity.getText());
                        int restockThresholdNumb = Integer.parseInt(restockThreshold.getText());
                        
                        // append new item to the inventory.txt file if both checks pass
                        FileWriter fw = null; 
                        BufferedWriter bw = null; 
                        PrintWriter pw = null; 
                        try { 
                            fw = new FileWriter("Inventory.txt", true); 
                            bw = new BufferedWriter(fw); 
                            pw = new PrintWriter(bw);
                            pw.println(name.getText() + "," + currentQuantity.getText() + "," + restockThreshold.getText());
                            message = "New item '" + name.getText() + "' was successfully added to your truck's inventory";
                            JOptionPane.showMessageDialog(null, message);
                            pw.flush();
                            frame.dispose();
                        }
                        catch(IOException io){
                            message = "Unable to find Inventory.txt";
                            JOptionPane.showMessageDialog( null, message);
                        }
                        finally { 
                            try { 
                                pw.close();
                                bw.close();
                                fw.close(); 
                            } 
                            catch (IOException io) {
                                message = "Unable to find Inventory.txt";
                                JOptionPane.showMessageDialog( null, message);
                            }
                        }
                    }
                    catch(NumberFormatException nfe){
                        message = "The Quantity and Restock Threshold values must be numeric to submit a new item";
                        JOptionPane.showMessageDialog(null, message);
                    }
                }
            }
        });
    }
}
