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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
public class CustomerCostScreen implements Initializable {

    private final TableView<Book> checkoutTableView = new TableView<>();
    private Group CustomerCostScreen = new Group();
    BookStore books = BookStore.getInstance();
    ArrayList<Book> purchaseBooksList = new ArrayList();
    ObservableList<Book> purchasedBooks;
    double subtotal = 0, total, discount;
    int earnedPoints, subtractPoints;

    public Group CustomerCostScreen(boolean redeemOrNot, Customer customer) throws IOException {

        // Get books with checkbox selected and add them to an arraylist
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

        // Text for subtotal, discount, total, points earned, and status
        Font fontSize = new Font(16);
        Text buyStatus = new Text("SubTotal: $" + subtotalRounded + " \nDiscount: $" + discount + "\nTotal Cost: $" + totalRounded + "\nYou've earned " + earnedPoints + " points");
        Text customerStatus1 = new Text("Your new status: ");
        Text customerStatus2 = new Text(customer.getStatus());
        buyStatus.setFont(fontSize);
        customerStatus1.setFont(fontSize);
        customerStatus2.setFont(fontSize);
        customerStatus2.setStyle("-fx-font-weight: bold");
        
        
        // Change color of customer status
        if (customer.getStatus().equalsIgnoreCase("silver")) {
            customerStatus2.setFill(Color.SILVER);
        } else {
            customerStatus2.setFill(Color.GOLD);
        }
        
        // HBox for wrapper customer status texts
        HBox customerStatusWrapper = new HBox();
        customerStatusWrapper.getChildren().addAll(customerStatus1, customerStatus2);
        customerStatusWrapper.setPadding(Insets.EMPTY);
        
        // VBox for displaying customer points and status
        VBox header = new VBox();
        header.setPadding(Insets.EMPTY);
        header.getChildren().addAll(buyStatus, customerStatusWrapper);

        // Set Table Columns
        TableColumn<Book, String> bookName = new TableColumn("Book Name");
        bookName.setCellValueFactory(new PropertyValueFactory<>("name"));
        bookName.setStyle("-fx-alignment: CENTER_LEFT");
        bookName.setMinWidth(440);

        TableColumn<Book, Double> bookPrice = new TableColumn("Price");
        bookPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        bookPrice.setStyle("-fx-alignment: CENTER");
        bookPrice.setMinWidth(120);

        // Fetch ArrayList data [purchaseBooksList] from ObservableList [purchasedBooks]
        purchasedBooks = FXCollections.observableArrayList(purchaseBooksList);
        checkoutTableView.setItems(purchasedBooks);
        checkoutTableView.getColumns().addAll(bookName, bookPrice);
        
        Button logout = new Button("Logout");
        logout.setPrefSize(100, 20);

        logout.setOnAction(e -> {
            try {
                Parent loginRoot = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Scene loginScene = new Scene(loginRoot, 640, 640, Color.web("#e3bf74"));
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
        VBox checkoutWrapper = new VBox();
        checkoutWrapper.setPadding(new Insets(50, 50, 0, 50)); // (top, right, bottom, left)
        checkoutWrapper.getChildren().addAll(checkoutTableView, header, buttonRow);

        CustomerCostScreen.getChildren().add(checkoutWrapper);

        return CustomerCostScreen;

    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
