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
    //private ArrayList<Inventory> SupplyList = new ArrayList<>( );
    
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
    
    public void displayNotice(Inventory item){
        JFrame frame;
        JLabel nameL, currentQuantityL, restockThresholdL;
        JButton acceptB, ignoreB;
        Container contents;
        
    
        frame = new JFrame();
	// Create text fields, labels and submit button for user to enter the new item data
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
        
        // Create panel 1 for the labels and text fields
        JPanel p1 = new JPanel();
	p1.setLayout(new GridLayout(3,2));	 
	p1.add(nameL);
        p1.add(currentQuantityL);
        p1.add(restockThresholdL);
        
        // Create panel 2 for the submit button
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
        
        acceptB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                // check if all the fields are populated
                String itemName, message;
                int quantity, restockThreshold;
                
                // retrieves items from previous function
                itemName = ((String)((JButton)acceptB).getClientProperty( "name" ));
                quantity = ((Integer)((JButton)acceptB).getClientProperty( "quantity" ));
                restockThreshold = ((Integer)((JButton)acceptB).getClientProperty( "restock" ));
                try {
                    String ogItemInfo = item.getItem() + "," + item.getQuantity() + "," + item.getRestockThreshold() + "," + item.getRestockDate();
                    RestockInventory updateItem = new RestockInventory();
                    updateItem.restock(item, ogItemInfo);
                    message = "Item '" + itemName + "' was successfully restocked to " + item.getQuantity();
                    JOptionPane.showMessageDialog(null, message);
                    frame.dispose();
                }
                catch (Exception e){
                    message = "Error occured during restock operation";
                    JOptionPane.showMessageDialog( null, message);
                    frame.dispose();
                }
            }
        });
        
        ignoreB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                    frame.dispose();
            }});
        
        // Show Frame
        frame.setVisible(true);
    }
}
