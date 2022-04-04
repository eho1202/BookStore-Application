package Bookstore_Scenes;

import Bookstore.Main;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;

/**
 *
 * @author Ernestine Ho
 */
public class OwnerStartScreen implements Initializable {
    private Group ownerStartScreen = new Group();
    OwnerBooksScreen ownerBS = new OwnerBooksScreen();
    OwnerCustomersScreen ownerCS = new OwnerCustomersScreen();
    Main main;
    
    public Group OwnerStartScreen() {
        Button bookBtn = new Button("Books");
        Button customerBtn = new Button("Customers");
        Button logout = new Button("Logout");
        bookBtn.setPrefSize(140, 60);
        customerBtn.setPrefSize(140, 60);
        logout.setPrefSize(140, 60);
        
        bookBtn.setOnAction(e -> {
            Scene ownerBooksScene = new Scene(ownerBS.OwnerBooksScreen(), 640, 640);
            main.getStage().setScene(ownerBooksScene);
        });
        
        customerBtn.setOnAction(e -> {
            try {
                Scene ownerCustomersScene = new Scene(ownerCS.OwnerCustomersScreen(), 640, 640);
                main.getStage().setScene(ownerCustomersScene);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
        logout.setOnAction(e -> {
            try {
                Parent loginRoot = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Scene loginScene = new Scene(loginRoot, 640, 640);
                main.getStage().setScene(loginScene);
            } catch (IOException ex) {
                Logger.getLogger(OwnerStartScreen.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            }
        });
        
        TilePane buttons = new TilePane(Orientation.VERTICAL);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(10, 10, 0, 10)); // (top, right, bottom, left)
        buttons.setPrefRows(3);
        buttons.setVgap(30);
        buttons.setLayoutX(240);
        buttons.setLayoutY(180);
        buttons.getChildren().addAll(bookBtn, customerBtn, logout);
        
        ownerStartScreen.getChildren().add(buttons);
        
        return ownerStartScreen;
    }

    public void setMainController(Main screen) {
        this.main = screen;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
}
