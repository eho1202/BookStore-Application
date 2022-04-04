package Bookstore;

/**
 *
 * @author Clenard
 */
public class Owner extends Account{
    
    public static Owner ownerInstance;
    //Bookstore myStore = Bookstore.getInstance();
    private Owner(String u, String p) {
        super(u, p);
    }
    
    public static Owner getInstance() {
        if (ownerInstance == null) {
            ownerInstance = new Owner("admin", "admin");
        }
        
        return ownerInstance;
    }
        
    /**
     * Returns the Owner's username
     * @return username
     */
    @Override
    public String getUsername() {
        return username;
    }
    
    /**
     * Returns the Owner's password
     * @return password
     */
    @Override
    public String getPassword() {
        return password;
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
     * Returns username and password of the Owner
     * @return Owner info
     */
    @Override
    public String toString() {
        return "Username: " + username + "\nPassword: " + password;
    }
}
