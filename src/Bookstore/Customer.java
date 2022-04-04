package Bookstore;

import javafx.scene.control.CheckBox;

/**
 *
 * @author Ernestine Ho
 */
public final class Customer extends Account {
    private int points;
    private String status;
    private CheckBox checkBox;
    
    public Customer(String username, String password, int points) {
        super(username, password);
        this.points = points;
        setStatus(this.points);
        checkBox = new CheckBox();
    }
    
    /**
     * Returns the customer's username
     * @return username
     */
    @Override
    public String getUsername() {
        return username;
    }
    
    /**
     * Returns the customer's password
     * @return password
     */
    @Override
    public String getPassword() {
        return password;
    }
    
    /**
     * Returns the customer's total points
     * @return points
     */
    public int getPoints() {
        return points;
    }
    
    /**
     * Returns the customer's status
     * @return status
     */
    public String getStatus() {
        return status;
    }
    
    public CheckBox getCheckBox() {
        return checkBox;
    }
    
    /**
     * Changes the Owner's username
     * @param u
     */
    @Override
    public void setUsername(String u) {
        username = u;
    }
    
    /**
     * Changes the Owner's password
     * @param p
     */
    @Override
    public void setPassword(String p) {
        password = p;
    }
    
    /**
     * Changes the customer's status
     * @param points
     */
    public void setStatus(int points) {
        // If points > 1000, set status to gold, else set status to silver
         status = points > 1000 ? "Gold" : "Silver";
    }
    
    /**
     * Changes the customer's points
     * @param points
     */
    public void setPoints(int points) {
        this.points += points;
        setStatus(this.points);
    }
    
    public void setCheckBox(CheckBox cb) {
        checkBox = cb;
    }
    
    /**
     * Returns username and password of the Customer
     * @return Customer info
     */
    @Override
    public String toString() {
        return "Username: " + this.username + ", Password: " + this.password;
    }
    
}
