package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import util.Customer;

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
}
