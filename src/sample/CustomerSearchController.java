package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import util.Customer;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerSearchController implements Initializable {

    @FXML
    ListView<String> listView;

    @FXML
    TextField firstName;

    @FXML
    TextField lastName;

    @FXML
    TextField phoneNumber;

    @FXML
    public void search(ActionEvent event) {
        Customer customer = new Customer(firstName.getText(), lastName.getText());
        System.out.println("Searching for: " + customer);
    }

    @FXML
    public void clear(ActionEvent event) {
        firstName.setText("");
        lastName.setText("");
        phoneNumber.setText("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //by default show all
        ObservableList<String> items = FXCollections.observableArrayList("Reed Fenters", "Krunal Patel", "Skyler James");
        listView.setItems(items);
    }
}
