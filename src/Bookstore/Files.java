package Bookstore;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Ernestine Ho
 */
public class Files {

    public ArrayList<Book> getBooksFile() throws IOException { // Get contents of books.txt and add them to an Array
        Scanner in = new Scanner(new BufferedReader(new FileReader("books.txt")));
        ArrayList<Book> tempBookList = new ArrayList();
        String line;
        
        while (in.hasNextLine()) {
            line = in.nextLine();
            Scanner lineReader = new Scanner(line).useDelimiter(", ");
            while (lineReader.hasNext()) {
                String name = lineReader.next();
                Double price = Double.parseDouble(lineReader.next());
                tempBookList.add(new Book(name, price));
            }
        }
        in.close();
        return tempBookList;
    }
    
    public ArrayList<Customer> getCustomersFile() throws IOException { // Get contents of customers.txt and add them to an Array
        Scanner in = new Scanner(new BufferedReader(new FileReader("customers.txt")));
        ArrayList<Customer> tempCustomerList = new ArrayList();
        String line;
     
        while (in.hasNextLine()) {
               line = in.nextLine();
               Scanner lineReader = new Scanner(line).useDelimiter(", ");
               while (lineReader.hasNext()) {
                   String username = lineReader.next();
                   String password = lineReader.next();
                   int points = Integer.parseInt(lineReader.next());
                   tempCustomerList.add(new Customer(username, password, points));
               }
           }
        in.close();
        return tempCustomerList;
    }
    
    public void writeBooksFile(ArrayList<Book> books) throws IOException { // Write books to books.txt
        BufferedWriter writeBooks = new BufferedWriter(new FileWriter("books.txt", true));
        for(Book book : books) {
            String bookData = (book.getName() + ", " + book.getPrice() + "\n");
            writeBooks.write(bookData);
        }
        writeBooks.close();
    }
    
    public void writeCustomersFile(ArrayList<Customer> customers) throws IOException { // Write customers to file customers.txt
        BufferedWriter writeCustomers = new BufferedWriter(new FileWriter("customers.txt", true));
        writeCustomers.write(""); //writes empty string to clear everythign in the file
        for (Customer customer : customers) {
            String customerData = (customer.getUsername() + ", " + customer.getPassword() + ", " + customer.getPoints() + "\n");
            writeCustomers.write(customerData);
        }
        writeCustomers.close();
    }
    
    //resets the text files so they can be written to
    public void booksClear() throws IOException {
        PrintWriter booksClear = new PrintWriter("books.txt");
        booksClear.flush();
        booksClear.close();
    }

    public void customersClear() throws IOException {
        FileWriter customersClear = new FileWriter("customers.txt", false);
        customersClear.flush();
        customersClear.close();
    }
}
