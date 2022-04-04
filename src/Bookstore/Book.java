package Bookstore;

import javafx.scene.control.CheckBox;

/**
 *
 * @author Clenard
 */
public class Book {
    double price;
    String name;
    CheckBox checkBox;
    
    public Book(String s, double d) {
        name = s;
        price = d;
        checkBox = new CheckBox();
    }
    
    /**
     * Returns the price of the book
     * @return price
     */
    public double getPrice() {
        return price;
    }
    
    /**
     * returns the name of the book
     * @return name
     */
    public String getName() {
        return name;
    }
    
    public CheckBox getCheckBox() {
        return checkBox;
    }
    
    /**
     * changes the price of the book
     * @param d
     */
    public void setPrice(double d) {
        price = d;
    }
    
    /**
     * changes the name of the book
     * @param s
     */
    public void setName(String s) {
        name = s;
    }
    
    public void setCheckBox(CheckBox cb) {
        checkBox = cb;
    }
    
    /**
     * Returns name and price of the book
     * @return Owner info
     */
    @Override
    public String toString() {
        return name + ", " + price;
    }
}
