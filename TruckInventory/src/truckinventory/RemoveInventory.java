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
 * @author nuria
 */
public class RemoveInventory extends JFrame{
    private JFrame frame;
    private JComboBox items;
    private String[] itemOptions;
    private JLabel nameL;
    private JButton submit;
    private Container contents;
    private String currentOption, message, newSelection;
    private Scanner file;
    
    ArrayList<Inventory> SupplyList = new ArrayList<>( );
    
    public RemoveInventory(){
        frame = new JFrame("Remove Inventory Item");
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
        
        // Create label and submit button for user to remove an item
        nameL = new JLabel("Item to remove: ");
        submit = new JButton("Submit");
        
	contents = getContentPane();
        
	// Set a grid layout for all the fields
	contents.setLayout(new GridLayout(4,2));
        
        // Create panel 1 for the label and dropdown fields
        JPanel p1 = new JPanel();
	p1.setLayout(new GridLayout(3,2));	 
	p1.add(nameL);
        p1.add(items);
        
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
        
        // Action listener for the items dropdown
        // Sets a string value to the selected item
        items.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                newSelection = (String)cb.getSelectedItem();
                currentOption = newSelection;
            }
        });
        
        // Action listener for the Submit button
        // Removes an item from the array as well as the text file if it passed the following checks:
        // - An item is selected from the drop down
        // - User confirms they want to remove the item
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                // check if an item was selected
                if(currentOption == "") {
                    message = "Please select an item to remove from the dropdown to submit";
                    JOptionPane.showMessageDialog(frame, message, null, JOptionPane.ERROR_MESSAGE);
                }
                else{
                    // verify the user wants to remove the item with a confirmation window
                    int verify = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove the item '" + newSelection + "'?", "Confirmation - Remove Inventory Item", JOptionPane.YES_NO_OPTION);
                    if(verify == JOptionPane.YES_OPTION){
                        String rName = newSelection;
                        Inventory tempInventory = null;
                        FileWriter fw = null; 
                        BufferedWriter bw = null; 
                        PrintWriter pw = null; 

                        for ( int ct = 0; ct < SupplyList.size(); ct++) {
                            tempInventory = SupplyList.get(ct);
                            if(tempInventory.getItem().equalsIgnoreCase(rName)){
                                SupplyList.remove(ct);
                                message = "The item '" + rName + "' was successfully removed from the inventory.";

                                try{
                                    fw = new FileWriter("Inventory.txt", false); 
                                    bw = new BufferedWriter(fw); 
                                    pw = new PrintWriter(bw);
                                    Inventory wSupply = null;
                                    String wItem, wRestockDate;
                                    int wQuantity, wRestockThreshold;
                                    for ( int cnt=0; cnt<SupplyList.size();  cnt++) {
                                        wSupply = SupplyList.get(cnt);
                                        wItem = wSupply.getItem();
                                        wQuantity = wSupply.getQuantity();
                                        wRestockThreshold = wSupply.getRestockThreshold();
                                        wRestockDate = wSupply.getRestockDate();                                     
                                        pw.print(wItem + "," + wQuantity + "," + wRestockThreshold + "," + wRestockDate + "\n");
                                    }  
                                    pw.close();
                                    JOptionPane.showMessageDialog( null, message);
                                    frame.dispose();
                                }catch(IOException io){
                                    message = "Unable to find Inventory.txt";
                                    JOptionPane.showMessageDialog(frame, message, null, JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    }
                }
            }
        });    
    }
}
