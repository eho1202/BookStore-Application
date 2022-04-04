package Bookstore_Scenes;

import Bookstore.Book;
import Bookstore.BookStore;
import Bookstore.Customer;
import Bookstore.Main;
import Bookstore.Owner;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author Ernestine Ho
 */
public class CustomerStartScreen implements Initializable {
    private final TableView<Book> bookTableView = new TableView<>();
    private Group customerStartScreen = new Group();
    boolean isSelected = false;
    CustomerCostScreen customerCS = new CustomerCostScreen();
    BookStore books = BookStore.getInstance();
    Owner owner = Owner.getInstance();
    private String name, status;
    
    ObservableList<Book> booksList = FXCollections.observableArrayList();
    
    public ObservableList<Book> addBooks() throws IOException {
        booksList.addAll(books.getBooks());
        return booksList;
    }
    
    public Group CustomerStartScreen(Customer customer) throws IOException {
        Font fontSize = new Font(16);
        name = customer.getUsername();
        double points = customer.getPoints();
        status = customer.getStatus();
        
        Text customerInfoStatus = new Text(10, 50, "Welcome " + name + ", You have " + points + " points. Your status is ");
        Text customerStatus = new Text(10, 50, status);
        
        Alert errorAlert = new Alert(AlertType.ERROR);
        
        if (status.equalsIgnoreCase("silver")) {
            customerStatus.setFill(Color.SILVER);
        } else {
            customerStatus.setFill(Color.GOLD);
        }
        
        customerInfoStatus.setFont(fontSize);
        customerStatus.setFont(fontSize);
        customerStatus.setStyle("-fx-font-weight: bold");
        
        // HBox for displaying customer name, points, and status
        HBox customerInfo = new HBox();
        customerInfo.getChildren().addAll(customerInfoStatus, customerStatus);
        
        BorderPane header = new BorderPane();
        header.setPadding(new Insets(5, 0, 5, 0)); // (top, right, bottom, left)
        header.setTop(customerInfo);
        
        
        // Set Table Columns
        TableColumn<Book, String> bookName = new TableColumn<>("Book Name");
        bookName.setCellValueFactory(new PropertyValueFactory<>("name"));
        bookName.setStyle("-fx-alignment: CENTER_LEFT");
        bookName.setMaxWidth(1f * Integer.MAX_VALUE * 62.5);
        
        TableColumn<Book, Double> bookPrice = new TableColumn<>("Book Price");
        bookPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        bookPrice.setStyle("-fx-alignment: CENTER");
        bookPrice.setMaxWidth(1f * Integer.MAX_VALUE * 17.1875);
        
        TableColumn<Book, Boolean> checkBox = new TableColumn<>("Select");
        checkBox.setCellValueFactory(new PropertyValueFactory<>("checkBox"));
        checkBox.setStyle("-fx-alignment: CENTER");
        checkBox.setMaxWidth(1f * Integer.MAX_VALUE * 17.1875);
        
        // add items to TableView and get columns for book name, price, and checkbox
        bookTableView.setItems(addBooks());
        bookTableView.setSelectionModel(null);
        bookTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        bookTableView.getColumns().addAll(bookName, bookPrice, checkBox);
        
        // Buttom row buttons
        Button buy = new Button("Buy");
        Button redeemAndBuy = new Button("Redeem and Buy");
        Button logout = new Button("Logout");
        // Set the buttons to the same size
        buy.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        redeemAndBuy.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        logout.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        
        buy.setOnAction(e -> {
            try {
                for (Book b : books.getBooks()) { // check if any books are selected
                    if (b.getCheckBox().isSelected()) 
                        isSelected = true;
                }
                if (isSelected) {
                    Scene customerCostScene = new Scene(customerCS.CustomerCostScreen(false, customer), 640, 640);
                    Main.getStage().setScene(customerCostScene);
                } else {
                    errorAlert.setContentText("Please select a book before purchasing!");
                    errorAlert.showAndWait();
                }
            } catch (IOException ex) {
                Logger.getLogger(CustomerStartScreen.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            }
        });
        
        redeemAndBuy.setOnAction(e -> {
            if (customer.getPoints() != 0) {
                try {
                    for (Book b : books.getBooks()) { // check if any books are selected
                        if (b.getCheckBox().isSelected()) 
                            isSelected = true;
                    }
                    if (isSelected) {
                        Scene customerCostScene = new Scene(customerCS.CustomerCostScreen(true, customer), 640, 640);
                        Main.getStage().setScene(customerCostScene);
                    } else {
                        errorAlert.setContentText("Please select a book before purchasing!");
                        errorAlert.showAndWait();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(CustomerStartScreen.class.getName()).log(Level.SEVERE, null, ex);
                    ex.printStackTrace();
                }
            } else {
                errorAlert.setContentText("No points available to redeem!");
                errorAlert.showAndWait();
            }
            
        });
        
        logout.setOnAction(e -> {
            try {
                Parent loginRoot = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Scene loginScene = new Scene(loginRoot, 640, 640);
                Main.getStage().setScene(loginScene);
                
                for (Book b : books.getBooks()) { // reset checkbox when logging out
                    b.setCheckBox(new CheckBox());
                }
                
            } catch (IOException ex) {
                Logger.getLogger(OwnerStartScreen.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            }
        });
        
        // TilePane to wrap and set bottom row buttons to the same size
        TilePane buttonRow = new TilePane(Orientation.HORIZONTAL);
        buttonRow.setAlignment(Pos.CENTER);
        buttonRow.setPadding(new Insets(10, 10, 0, 10)); // (top, right, bottom, left)
        buttonRow.setPrefRows(3);
        buttonRow.setHgap(10);
        buttonRow.getChildren().addAll(buy, redeemAndBuy, logout);
        
        errorAlert.setHeaderText("Something seems wrong...");
        
        // VBox to wrap header, book table, and button row together
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(60, 10, 0, 25)); // (top, right, bottom, left)
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(header, bookTableView, buttonRow);
        
        customerStartScreen.getChildren().add(vbox);
        
        return customerStartScreen;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
}
