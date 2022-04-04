package Bookstore_Scenes;

import Bookstore.Book;
import Bookstore.BookStore;
import Bookstore.Customer;
import Bookstore.Main;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author Ernestine Ho
 */
public class CustomerCostScreen implements Initializable {

    private final TableView<Book> checkoutTableView = new TableView<>();
    private Group CustomerCostScreen = new Group();
    BookStore books = BookStore.getInstance();
    ArrayList<Book> purchaseBooksList = new ArrayList();
    ObservableList<Book> purchasedBooks;
    double subtotal = 0, total, discount;
    int earnedPoints, subtractPoints;

    public Group CustomerCostScreen(boolean redeemOrNot, Customer customer) throws IOException {

        for (Book b : books.getBooks()) {
            if (b.getCheckBox().isSelected()) {
                subtotal += b.getPrice();
                purchaseBooksList.add(b);
            }
        }

        /*
        if redeemOrNot = true, redeemAndBuy
        if redeemOrNot = false, buy
         */
        if (redeemOrNot) {
            System.out.println("Redeem and Buy");
            // If customer's points is greater than subtotal, set discount to subtotal and subtract points from available points
            // If customer's points is small than subtotal, set discount to available points and subtract points from available points
            if ((double)customer.getPoints()/100 >= subtotal) { 
                discount = subtotal;
                subtractPoints = -((int)subtotal*100);
                System.out.println(subtractPoints);
                customer.setPoints(subtractPoints);
            } else {
                discount = (double)customer.getPoints()/100;
                subtractPoints = -1 * customer.getPoints();
                customer.setPoints(subtractPoints);
            }
        } else {
            System.out.println("Buy");
            discount = 0;
        }

        total = subtotal - discount;
        // round total to 2 decimal places
        BigDecimal subtotalRounded = new BigDecimal(subtotal).setScale(2, RoundingMode.HALF_DOWN);
        BigDecimal totalRounded = new BigDecimal(total).setScale(2, RoundingMode.HALF_DOWN);
        earnedPoints = (int) total * 10;
        customer.setPoints(earnedPoints);
        String status = customer.getStatus();

        Font fontSize = new Font(16);
        Text buyStatus = new Text("SubTotal: $" + subtotalRounded + " \nDiscount: $" + discount + "\nTotal Cost: $" + totalRounded + "\nYou've earned " + earnedPoints + " points\n" + "Status: ");
        Text customerStatus = new Text("\n\n\n\n"+status);
        buyStatus.setFont(fontSize);
        customerStatus.setFont(fontSize);
        
        if (status.equalsIgnoreCase("silver")) {
            customerStatus.setFill(Color.SILVER);
        } else {
            customerStatus.setFill(Color.GOLD);
        }

        // HBox for displaying customer points and status
        VBox header = new VBox();
        header.getChildren().add(buyStatus);
        
        HBox headerWrapper = new HBox();
        headerWrapper.getChildren().addAll(buyStatus, customerStatus);

        // Set Table Columns
        TableColumn<Book, String> bookName = new TableColumn("Book Name");
        bookName.setCellValueFactory(new PropertyValueFactory<>("name"));
        bookName.setStyle("-fx-alignment: CENTER");
        bookName.setMinWidth(440);

        TableColumn<Book, Double> bookPrice = new TableColumn("Price");
        bookPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        bookPrice.setStyle("-fx-alignment: CENTER");
        bookPrice.setMinWidth(120);

        // Fetch ArrayList data [purchaseBooksList] from ObservableList [purchasedBooks]
        purchasedBooks = FXCollections.observableArrayList(purchaseBooksList);
        checkoutTableView.setItems(purchasedBooks);
        checkoutTableView.getColumns().addAll(bookName, bookPrice);
        
        VBox tableWrapper = new VBox();
        tableWrapper.setSpacing(5);
        for (Book b : purchaseBooksList) {
            Text nameBook = new Text(b.getName());
            Text priceBook = new Text(""+b.getPrice());
            BorderPane item = new BorderPane();
            item.setLeft(nameBook);
            item.setRight(priceBook);
            Line line = new Line(0, 150, 560, 150);
            tableWrapper.getChildren().addAll(item, line);
        }
        
        ScrollPane scroll = new ScrollPane(tableWrapper);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setFitToWidth(true);
        if(purchaseBooksList.size() <= 4) {
            scroll.setFitToHeight(true);
        } else {
            scroll.setPrefHeight(130);
        }
        
        Button logout = new Button("Logout");
        logout.setPrefSize(100, 20);

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

        TilePane buttonRow = new TilePane(Orientation.HORIZONTAL);
        buttonRow.setAlignment(Pos.CENTER);
        buttonRow.setPadding(new Insets(10, 10, 0, 10)); // (top, right, bottom, left)
        buttonRow.setPrefRows(3);
        buttonRow.setHgap(10);
        buttonRow.getChildren().add(logout);

        //VBox to wrap header, purchase books list, and button row together
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(200, 50, 0, 50)); // (top, right, bottom, left)
        vbox.getChildren().addAll(scroll, headerWrapper, buttonRow);
        
        CustomerCostScreen.getChildren().add(vbox);

        return CustomerCostScreen;

    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
