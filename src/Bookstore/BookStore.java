package Bookstore;

import java.io.IOException;
import java.util.ArrayList;

public class BookStore {
 
    private static BookStore instance;
    private ArrayList<Book> arrBooks = new ArrayList<Book>();
    private ArrayList<Customer> arrCustomers = new ArrayList<Customer>();
    Files files = new Files();
    boolean initializeBooks = true; //used to prevent the .txt files from adding to the arraylist several times
    boolean initializeCustomers = true;
    
    private BookStore(){}
    //singleton
    public static BookStore getInstance(){
        if (instance == null)
            instance = new BookStore();
        return instance;
    }
    
    public ArrayList<Book> getBooks() throws IOException {
        if (initializeBooks){
            ArrayList<Book> tempBooksList = files.getBooksFile();
            arrBooks.addAll(tempBooksList);
            initializeBooks = false;
        }
        return arrBooks;
    }
    
    public void addBook(Book b) throws IOException {
        getBooks().add(b);
    }

    public void delBook(Book b) throws IOException {
        getBooks().remove(b);
    }
    
    public ArrayList<Customer> getCustomers() throws IOException {
        if (initializeCustomers){
            ArrayList<Customer> tempCustomerList = files.getCustomersFile();
            arrCustomers.addAll(tempCustomerList);
            initializeCustomers = false;
        }
        return arrCustomers;
    }
    
    public void addCustomer(Customer c) throws IOException{
        getCustomers().add(c);
    }
    
    public void delCustomer(Customer c) throws IOException {
        getCustomers().remove(c);
    }
    
    //prints book arraylist
    public void displayBooks(){
        for (Book b: arrBooks){
            System.out.println("Name: " + b.getName() + " Price: " + b.getPrice());
        }
    }
    
    //prints customer arraylist
    public void displayCustomers(){
        for (Customer c: arrCustomers){
            System.out.println("Username: " + c.getUsername() + " Password: " + c.getPassword());
        }
    }
}