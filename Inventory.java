/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package truckinventory;

/**
 *
 * @author nuria
 */
public class Inventory {
    private String item, quantity;
	
	
	
	public Inventory(String item, String quantity) {
	this.item = item;
	this.quantity = quantity;
	}
        
	//
    //Get Methods
    //
    public String getItem() {
        return item;
    }
    
    public String getQuantity() {
        return quantity;
    }
    
    @Override
    public String toString() {
        String outStr = "Item: " + item + "       Quantity: "+ quantity;
        return outStr;
    }
}

