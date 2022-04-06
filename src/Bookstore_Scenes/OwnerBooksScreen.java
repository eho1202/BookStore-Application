package Bookstore_Scenes;

import Bookstore.Book;
import Bookstore.BookStore;
import Bookstore.Main;
import Bookstore.Owner;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author Clenard
 */
public class OwnerBooksScreen implements Initializable {
    private final TableView<Book> tableBooks = new TableView<>();
    private ObservableList<Book> bookList = FXCollections.observableArrayList();
    private Group ownerBooksScreen = new Group();
    Owner owner = Owner.getInstance();
    BookStore books = BookStore.getInstance();
    
    public ObservableList<Book> addBooks() throws IOException {
        bookList.addAll(books.getBooks());
        return bookList;
    }
    
    public Group OwnerBooksScreen() {
        //Creating a GridPane container
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(5);
        grid.setHgap(5);
        
        //Defining the bookName text field
        final TextField bookName = new TextField();
        bookName.setPrefColumnCount(15);
        bookName.setPromptText("Enter the name of the Book");
        GridPane.setConstraints(bookName, 0, 1);
        grid.getChildren().add(bookName);
        
        //Defining the bookPrice text field
        final TextField bookPrice = new TextField();
        bookPrice.setPrefColumnCount(15);
        bookPrice.setPromptText("Enter the price of the Book");
        GridPane.setConstraints(bookPrice, 0, 2);
        grid.getChildren().add(bookPrice);
        
        //Defining the add button
        Button add = new Button("Add");
        add.setPrefWidth(70);
        GridPane.setConstraints(add, 1, 1);
        grid.getChildren().add(add);
        
        HBox hbAdd = new HBox();
        hbAdd.getChildren().addAll(bookName, bookPrice, add);
        hbAdd.setSpacing(5);
        hbAdd.setAlignment(Pos.CENTER);
        grid.getChildren().add(hbAdd);
        GridPane.setConstraints(hbAdd, 0, 5);
        
        //defining the delete button 
        Button del = new Button("Delete");
        del.setPrefWidth(200);
        
         //defining the back button 
        Button back = new Button("Back");
        back.setPrefWidth(200);
        
        //groups delete and back button on the same row
        HBox hbDB = new HBox();
        hbDB.setSpacing(10);
        hbDB.setAlignment(Pos.CENTER);
        hbDB.getChildren().addAll(del, back);
        
        grid.getChildren().add(hbDB);
        GridPane.setConstraints(hbDB, 0, 10);
        
        //create Table that displays all books for sale at the bookstore
        final Label tableTitle = new Label("Books For Sale");
        tableTitle.setFont(new Font("Arial", 20));
 
        // Set Table Columns
        TableColumn<Book, String> bookNameCol = new TableColumn<>("Book Name");
        bookNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        bookNameCol.setStyle("-fx-alignment: CENTER_LEFT");
        bookNameCol.setMinWidth(400);
        
        TableColumn<Book, Double> bookPriceCol = new TableColumn<>("Book Price");
        bookPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        bookPriceCol.setStyle("-fx-alignment: CENTER");
        bookPriceCol.setMinWidth(110);
        
        TableColumn<Book, Boolean> checkBoxCol = new TableColumn<>("Select");
        checkBoxCol.setCellValueFactory(new PropertyValueFactory<>("checkBox"));
        checkBoxCol.setStyle("-fx-alignment: CENTER");
        checkBoxCol.setMinWidth(110);
        try {
            tableBooks.setItems(addBooks());
        } catch (IOException ex){
            System.out.println("Error");
            ex.printStackTrace();
        }
        tableBooks.setSelectionModel(null);
        tableBooks.getColumns().addAll(bookNameCol, bookPriceCol, checkBoxCol);
        
        add.setOnAction(e -> {
            try {
                if (!(bookName.getText().isEmpty())) {
                    try {
                        books.addBook(new Book(bookName.getText(), Double.parseDouble(bookPrice.getText())));
                        bookName.clear();
                        bookPrice.clear();
                        
                        tableBooks.getItems().clear();
                        tableBooks.setItems(addBooks());
                    }catch (IOException ex){
                        System.out.println("INPUT ERROR");
                        ex.printStackTrace();
                    }
                } else {
                    throw new NumberFormatException("Invalid Inputs");
                }
            
            } catch(NumberFormatException nf) {
                System.out.println("Invalid Inputs");
                nf.printStackTrace();
            }
        });
        
  
        del.setOnAction(e -> {
            try {
                ArrayList<Book> temp = new ArrayList<>();
                for (Book b : bookList) {
                    if (b.getCheckBox().isSelected()) {
                        temp.add(b);
                    }
                }
                
                books.getBooks().removeAll(temp);
                
                tableBooks.getItems().clear();
                tableBooks.setItems(addBooks());
            } catch(IOException ex) {
                System.out.print("Error");
                ex.printStackTrace();
            }
        });
        
        back.setOnAction(e -> {
            OwnerStartScreen ownerSS = new OwnerStartScreen();
            Scene ownerStartScene = new Scene(ownerSS.OwnerStartScreen(), 640, 640, Color.web("#ffefd4"));
            Main.getStage().setScene(ownerStartScene);
        });
    
 
        //groups the table and title of the table as one item in the grid
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.getChildren().addAll(tableTitle, tableBooks);
        vbox.setPadding(new Insets(60, 10, 0, 10)); // (top, right, bottom, left)
        vbox.setAlignment(Pos.CENTER);
        
        grid.getChildren().add(vbox);
        
        ownerBooksScreen.getChildren().add(grid);
        
        return ownerBooksScreen;
        
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
}
