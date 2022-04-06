package Bookstore;

import Bookstore_Scenes.CustomerCostScreen;
import Bookstore_Scenes.CustomerStartScreen;
import Bookstore_Scenes.OwnerStartScreen;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Ernestine Ho
 */
public class Main extends Application implements Initializable {
    CustomerStartScreen customerSS = new CustomerStartScreen();
    CustomerCostScreen customerCS = new CustomerCostScreen();
    OwnerStartScreen ownerSS = new OwnerStartScreen();
    private static Stage primaryStage;
    Files files = new Files();
    private Parent root;
    
    public static Stage getStage() {
        return primaryStage;
    }
    
    public Parent getRoot() {
        return root;
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        primaryStage.setResizable(false);
        root = FXMLLoader.load(getClass().getResource("/Bookstore_Scenes/FXMLDocument.fxml"));
        
        Scene scene = new Scene(root, 640, 640);
        
        primaryStage.setTitle("Bookstore App");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        //Writes to files when closing program
        primaryStage.setOnCloseRequest(e -> {
            try {
                BookStore.getInstance().files.booksClear();
                BookStore.getInstance().files.customersClear();
                BookStore.getInstance().files.writeBooksFile(BookStore.getInstance().getBooks());
                BookStore.getInstance().files.writeCustomersFile(BookStore.getInstance().getCustomers());
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            }
        });
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        launch(args);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    public void changeScene(Scene customerStartScene) {
        primaryStage.setScene(customerStartScene);
    }

}
