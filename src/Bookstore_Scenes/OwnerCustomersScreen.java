package Bookstore_Scenes;

import Bookstore.BookStore;
import Bookstore.Customer;
import Bookstore.Main;
import Bookstore.Owner;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author Arjay Albisor
 * 501018768
 */
public class OwnerCustomersScreen implements Initializable{
    
    private final TableView<Customer> customerTableView = new TableView<>();
    private Group ownerCustomersScreen = new Group();
    BookStore bs = BookStore.getInstance();
    Owner owner = Owner.getInstance();
    
    ObservableList<Customer> customerList = FXCollections.observableArrayList();
    
    public ObservableList<Customer> addCustomers() throws IOException {
        customerList.addAll(bs.getCustomers());
        return customerList;
    }
    
    public Group OwnerCustomersScreen() throws IOException {
        BorderPane header = new BorderPane();
        header.setPadding(new Insets(5, 0, 5, 0)); // (top, right, bottom, left)

        
        //Table Columns
        TableColumn<Customer, String> username = new TableColumn<>("Username");
        username.setCellValueFactory(new PropertyValueFactory<>("Username"));
        username.setStyle("-fx-alignment: CENTER_LEFT");
        username.setMinWidth(200);
        
        TableColumn<Customer, String> password = new TableColumn<>("Password");
        password.setCellValueFactory(new PropertyValueFactory<>("Password"));
        password.setStyle("-fx-alignment: CENTER_LEFT");
        password.setMinWidth(200);
        
        TableColumn<Customer, Integer> points = new TableColumn<>("Points");
        points.setCellValueFactory(new PropertyValueFactory<>("Points"));
        points.setStyle("-fx-alignment: CENTER");
        points.setMinWidth(110);
        
        TableColumn<Customer, Boolean> checkBox = new TableColumn<>("Select");
        checkBox.setCellValueFactory(new PropertyValueFactory<>("checkBox"));
        checkBox.setStyle("-fx-alignment: CENTER");
        checkBox.setMinWidth(110);
        
        customerTableView.setItems(addCustomers());
        customerTableView.getColumns().addAll(username, password, points, checkBox);
        
        //Username Textbox
        final TextField addUsername = new TextField();
        addUsername.setPromptText("Username");
        addUsername.setMaxWidth(username.getPrefWidth());
        
        //Password Textbox
        final TextField addPassword = new TextField();
        addPassword.setMaxWidth(password.getPrefWidth());
        addPassword.setPromptText("Password");
        
        //Add button
        Button addBtn = new Button("Add");
        addBtn.setOnAction(e -> { //checks for existing customer with same parameters
            boolean duplicate = false;
            try {
                for(Customer c: bs.getCustomers()){
                    if((c.getUsername().equals(addUsername.getText()) && c.getPassword().equals(addPassword.getText())) || (addUsername.getText().equals(owner.getUsername()) && addPassword.getText().equals(owner.getPassword()))){
                        duplicate = true;
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(OwnerCustomersScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if(!(addUsername.getText().equals("") || addPassword.getText().equals("")) && !duplicate) {
                try {
                    bs.addCustomer(new Customer(addUsername.getText(), addPassword.getText(), 0)); //add new customer to arraylist
                } catch (IOException ex) {
                    Logger.getLogger(OwnerCustomersScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
                customerTableView.getItems().clear();
                try {
                    customerTableView.setItems(addCustomers());
                } catch (IOException ex) {
                    Logger.getLogger(OwnerCustomersScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
                addPassword.clear(); //clear text fields
                addUsername.clear();
            }
        });
        
        //delete button
        Button deleteBtn = new Button("Delete");
        deleteBtn.setOnAction(e -> {
            ArrayList<Customer> deletedCustomers = new ArrayList();
            for (Customer c: customerList){
                    if (c.getCheckBox().isSelected()){
                        deletedCustomers.add(c);
                    }
                }
            
            try {
                //deletes customers
                bs.getCustomers().removeAll(deletedCustomers);
                
                //refreshes table
                customerTableView.getItems().clear();
                customerTableView.setSelectionModel(null);
                customerTableView.setItems(addCustomers());
            } catch (IOException ex) {
                Logger.getLogger(OwnerCustomersScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        //Back button
        //Returns to OwnerStartScreen
        //TO DO
        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> {
            OwnerStartScreen ownerSS = new OwnerStartScreen();
            Scene ownerStartScene = new Scene(ownerSS.OwnerStartScreen(), 640, 640, Color.web("#ffefd4"));
            Main.getStage().setScene(ownerStartScene);
        });
        
        // TilePane to wrap and set bottom row buttons to the same size
        TilePane bottomRow = new TilePane(Orientation.HORIZONTAL);
        bottomRow.setAlignment(Pos.CENTER);
        bottomRow.setPadding(new Insets(10, 10, 0, 10)); // (top, right, bottom, left)
        bottomRow.setPrefRows(3);
        bottomRow.setHgap(10);
        bottomRow.getChildren().addAll(addUsername, addPassword, addBtn, deleteBtn, backBtn);
        
        // VBox to wrap header, book table, and button row together
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(60, 10, 0, 10)); // (top, right, bottom, left)
        vbox.getChildren().addAll(header, customerTableView, bottomRow);
        
        ownerCustomersScreen.getChildren().add(vbox);
        
        return ownerCustomersScreen;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}

//TO DO
// Implement back button properly