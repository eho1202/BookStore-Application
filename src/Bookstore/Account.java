/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bookstore;

/**
 *
 * @author Clenard
 */
public abstract class Account {
    
    String username, password;
    public Account(String user, String pass) {
        username = user;
        password = pass;
    }
    
    /**
     * Returns the Account's username
     * @return username
     */
    public abstract String getUsername();
    
    /**
     * Returns the Account's password
     * @return password
     */
    public abstract String getPassword();
    
    /**
     * Changes the Account's username
     * @param u
     */
    public abstract void setUsername(String u);
    
    /**
     * Changes the Account's password
     * @param p
     */
    public abstract void setPassword(String p);
    
    /**
     * Returns username and password of the Account
     * @return Account info
     */
    @Override
    public abstract String toString();
}
