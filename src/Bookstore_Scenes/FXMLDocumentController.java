package Bookstore_Scenes;

import Bookstore.BookStore;
import Bookstore.Customer;
import Bookstore.Main;
import Bookstore.Owner;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import java.io.IOException;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
/**
 *
 * @author Ethan Moo-Young
 */
//Log-in Screen Set-up
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label wrongLogin;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button button;
    
    public void userLogin(ActionEvent event) throws IOException {
        Main m = new Main();
        Owner o = Owner.getInstance();
        BookStore bookstore = BookStore.getInstance();
        OwnerStartScreen ownerSS = new OwnerStartScreen();
        CustomerStartScreen customerSS = new CustomerStartScreen();
        Customer customer;
        if(username.getText().toString().equals(o.getUsername()) && password.getText().toString().equals(o.getPassword())) {
            wrongLogin.setText("Success");
            Scene ownerStartScene = new Scene(ownerSS.OwnerStartScreen(), 640, 640, Color.web("#ffefd4"));
            m.changeScene(ownerStartScene);
        } else if (username.getText().isEmpty() || password.getText().isEmpty()){
            wrongLogin.setText("Empty Username or Password");
        } else {
            for (Customer c : bookstore.getCustomers()) {
                if (username.getText().toString().equals(c.getUsername()) && password.getText().toString().equals(c.getPassword())) {
                    wrongLogin.setText("Success");
                    customer = c;
                    try {
                        Scene customerStartScene = new Scene(customerSS.CustomerStartScreen(customer), 640, 640, Color.web("#ffefd4"));
                        m.changeScene(customerStartScene);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                wrongLogin.setText("Username or Password is incorrect");
                }
            }
        }
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
