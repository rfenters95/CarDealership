package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.Customer;

import java.io.IOException;

public class CustomerRegistrationController {
    @FXML TextField firstName;
    @FXML TextField lastName;
    @FXML TextField phone;
    @FXML TextField email;
    @FXML DatePicker dateOfBirth;
    @FXML TextField address;

    @FXML public void save(ActionEvent event) {
        Customer customer = new Customer(firstName.getText(), lastName.getText());
        System.out.println("Saved new customer: " + customer);
    }

    @FXML public void cancel(ActionEvent event) throws IOException {
        Parent register_page = FXMLLoader.load(getClass().getResource("welcome.fxml"));
        Scene register_scene = new Scene(register_page);
        Stage register_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        register_stage.hide();
        register_stage.setScene(register_scene);
        register_stage.show();
    }
}
