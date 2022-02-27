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
    private String item, restockDate;
    private int quantity, restockThreshold;
	
	public Inventory(String item, int quantity, int restockThreshold, String restockDate) {
	this.item = item;
	this.quantity = quantity;
        this.restockThreshold = restockThreshold;
        this.restockDate = restockDate;
	}
    //
    //Get Methods
    //
    public String getItem() {
        return item;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public int getRestockThreshold() {
        return restockThreshold;
    }
    
    public String getRestockDate() {
        return restockDate;
    }
    
    // Set methods
    public void setItem(String name){
        this.item = name;
    }
    
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    
    public void setRestockThreshold(int threshold){
        this.restockThreshold = threshold;
    }
    
    public void setRestockDate(String restockDate){
        this.restockDate = restockDate;
    }
    
    @Override
    public String toString() {
        String outStr = "Item: " + item+ 
                " || Quantity: "+ quantity+ 
                " || Restock Threshold: " + restockThreshold+
                " || Last Restock: " + restockDate; 
        return outStr;
    }
}