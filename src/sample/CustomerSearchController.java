package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.Customer;

import java.io.IOException;
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

    @FXML
    public void modify(ActionEvent event) {
        firstName.setText("");
        lastName.setText("");
        phoneNumber.setText("");
    }

    @FXML
    public void remove(ActionEvent event) {
        firstName.setText("");
        lastName.setText("");
        phoneNumber.setText("");
    }

    @FXML
    public void exit(ActionEvent event) throws IOException {
        Parent register_page = FXMLLoader.load(getClass().getResource("welcome.fxml"));
        Scene register_scene = new Scene(register_page);
        Stage register_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        register_stage.hide();
        register_stage.setScene(register_scene);
        register_stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //by default show all
        ObservableList<String> items = FXCollections.observableArrayList("Reed Fenters", "Krunal Patel", "Skyler James");
        listView.setItems(items);
    }
}
