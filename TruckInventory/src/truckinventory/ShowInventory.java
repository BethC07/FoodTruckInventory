/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package truckinventory;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
/**
 *
 * @author nuria
 */
public class ShowInventory extends JFrame {
    private JTextArea messageArea;
    private Container contents;
    private String item, restockDate, message;
    private int quantity, restockThreshold;
    private Scanner file;
    
    ArrayList<Inventory> SupplyList = new ArrayList<>( );
    
    public ShowInventory(){
        //
	// Create Message Area
	//
	messageArea = new JTextArea();
	messageArea.setFont(new Font("Serif", Font.ITALIC, 16));
	//messageArea.setLineWrap(true);
	//messageArea.setWrapStyleWord(true);
	//messageArea.setPreferredSize(new Dimension(400, 200));
	JScrollPane scrollPane = new JScrollPane(messageArea);    
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);	
        scrollPane.setPreferredSize(new Dimension(400,200));    
        //
	// Create get Content Pane holder
	//
	contents = getContentPane( );
	//
	// Set Layout
	//
	contents.setLayout(new BorderLayout());
        
        JPanel p1 = new JPanel();
	p1.setLayout(new FlowLayout());
	//p2.add(messageArea);	 
	p1.add(scrollPane);
        
        //
	// Add Panels to Content Pane
	//
        contents.add (p1, BorderLayout.CENTER);
        
        setSize(425, 400);
        //
        // Show Frame
        //
        setVisible(true);
        
        SupplyList = readData();
        
        message = "\n";
        
        Inventory rInventory = null;
        for ( int ct=0; ct<SupplyList.size();  ct++) {
            rInventory = SupplyList.get(ct);
    	    message = message + "\n"+rInventory.toString();
    	    		
    	}
        
        messageArea.setText(message);
        // make sure the text area is not editable
        messageArea.setEditable(false);
        
    }
    
   public static ArrayList readData(){
       ArrayList<Inventory> TempList = new ArrayList<>( );
       Scanner file = null;
       String item, restockDate, message;
       int quantity, restockThreshold;
       try{
	        
            file = new Scanner( new File ( "Inventory.txt" ) );   //insert file path       
	    while ( file.hasNext( ) ) // test for the end of the file
	        {
	            // read a line
	            String stringRead = file.nextLine( );
	            //
	            //Notice the StringTokenizer is using the comma as delimiter
	            //between data values
	            //
	
	             // process the line read
	            StringTokenizer st = new StringTokenizer( stringRead, "," );
	            item = st.nextToken( );
	            quantity = Integer.parseInt(st.nextToken( ));
                    restockThreshold = Integer.parseInt(st.nextToken( ));
                    restockDate = st.nextToken( );
	            try {

	                Inventory supply = new Inventory(item, quantity, restockThreshold, restockDate);
	                TempList.add(supply);
	            }//End Second Try{}
	            //
	            //Catch Numeric DataType Error
	            //
	             catch ( NumberFormatException nfe )
	           {
	            	 message = "Error in supply record: "
	                                              + stringRead
	                                              + "; record ignored" ;	
	            	 JOptionPane.showMessageDialog( null, message);
	           }
	          }//End While
    		}//End First Try{}
			catch ( FileNotFoundException fnfe )    	
	        {
	        	message = "Unable to find Inventory.txt";
	        	JOptionPane.showMessageDialog( null, message);
	        }
		 
			finally {
	       // Release resources associated with books.txt
	          file.close( ); 	      
	        }
       return TempList;
   }
            
}
