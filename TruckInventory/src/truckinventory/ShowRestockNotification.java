package truckinventory;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author vjsat
 */
public class ShowRestockNotification extends JFrame{
    
    // function to check if any of the inventory items are low on stock
    // if they are, show the user the restock notification
    public void checkStock(){
        ArrayList<Inventory> SupplyList = new ArrayList<>();
        SupplyList = ShowInventory.readData();
        Inventory tempInventory;
        
        for (int count =0; count < SupplyList.size();  count++) {
            tempInventory = SupplyList.get(count);
            if (tempInventory.getQuantity() < tempInventory.getRestockThreshold()){
                displayNotice(tempInventory);
            }	
    	}
    }
    
    // function to show the restock notification to the user
    public void displayNotice(Inventory item){
        JFrame frame;
        JLabel nameL, currentQuantityL, restockThresholdL;
        JButton acceptB, ignoreB;
        Container contents;
        
        frame = new JFrame("Restock Item Notification");
	// Create text labels and buttons for user to restock an item or not
	nameL = new JLabel("This item has a low stock: " + item.getItem());
        currentQuantityL = new JLabel("Quantity on hand: " + item.getQuantity());
        restockThresholdL = new JLabel("Restock Threshold: " + item.getRestockThreshold());
        acceptB = new JButton("Restock");
        ignoreB = new JButton("Ignore");
        
        // puts passes values on to the actionlistener
        acceptB.putClientProperty("name", item.getItem());
        acceptB.putClientProperty("quantity", item.getQuantity());
        acceptB.putClientProperty("restock", item.getRestockThreshold());
        
	contents = frame.getContentPane();
        
	// Set a grid layout for all the fields
	contents.setLayout(new GridLayout(4,2));
        
        // Create panel 1 for the labels
        JPanel p1 = new JPanel();
	p1.setLayout(new GridLayout(3,2));	 
	p1.add(nameL);
        p1.add(currentQuantityL);
        p1.add(restockThresholdL);
        
        // Create panel 2 for the buttons
        JPanel p2 = new JPanel();
	p2.setLayout(new GridLayout(2,0));	 
	p2.add(acceptB);
        p2.add(ignoreB);
        
	// Add both panels to Content Pane
        contents.add(p1, BorderLayout.CENTER);
        contents.add(p2, BorderLayout.CENTER);
        
        frame.setContentPane(contents);
        frame.pack();
        frame.setSize(400, 400);
        
        // Action listen for the Restock button
        // selecting this button will ask the user what value they'd like to restock the item to
        // once that is submitted, the item's quantity and last restock date/time are updated if it passed the following checks:
        // - User enters a value in the text field
        // - Value must be numeric
        acceptB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String itemName, message;
                int quantity, restockThreshold;
                
                // retrieves items from previous function
                itemName = ((String)((JButton)acceptB).getClientProperty( "name" ));
                quantity = ((Integer)((JButton)acceptB).getClientProperty( "quantity" ));
                restockThreshold = ((Integer)((JButton)acceptB).getClientProperty( "restock" ));
                
                try {
                    // ask the user for a restock value
                    String restockValue = (String)JOptionPane.showInputDialog(frame, "Please enter the quantity you would like " + itemName + " restocked to.\nThe default restock value is 10."
                            , "Restock value", JOptionPane.PLAIN_MESSAGE, null, null, 10);
                    // check if the user entered a value and verify the item is numeric
                    if(restockValue != null && restockValue.length() > 0){
                        int restockValueNumb = Integer.parseInt(restockValue);
                        String ogItemInfo = item.getItem() + "," + item.getQuantity() + "," + item.getRestockThreshold() + "," + item.getRestockDate();
                        RestockInventory updateItem = new RestockInventory();
                        updateItem.restock(item, ogItemInfo, restockValueNumb);
                        message = "Item '" + itemName + "' was successfully restocked to " + item.getQuantity();
                        JOptionPane.showMessageDialog(null, message);
                        frame.dispose();
                    }
                    else if(restockValue != null && restockValue.length() == 0){
                        message = "Please enter a quantity to restock the item to";
                        JOptionPane.showMessageDialog(frame, message, null, JOptionPane.ERROR_MESSAGE);
                    }
                }
                catch (Exception e){
                    System.out.println(e.toString());
                    if(e.toString().contains("java.lang.NumberFormatException")){
                        message = "Please enter a numeric value for the restock quantity";
                    }
                    else{
                        message = "Error occured during restock operation";
                    }
                    JOptionPane.showMessageDialog(frame, message, null, JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        // Action listener for the Ignore button
        // selecting this button will close the window and not update the item low in stock
        ignoreB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                    frame.dispose();
            }});
        
        // Show Frame
        frame.setVisible(true);
    }
}
