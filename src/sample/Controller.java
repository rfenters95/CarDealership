package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import util.Customer;

public class Controller {

    @FXML private TextField firstNameTextField;
    @FXML private TextField lastNameTextField;
    @FXML private TextField emailTextField;
    @FXML private TextField phoneTextField;
    @FXML private TextField addressTextField;
    @FXML private DatePicker dateOfBirthDatePicker;

    @FXML public void doSomething(ActionEvent event) {
        Customer customer = new Customer();
        customer.setFirstName(firstNameTextField.getText());
        customer.setLastName(lastNameTextField.getText());
        customer.setEmail(emailTextField.getText());
        customer.setPhone(phoneTextField.getText());
        customer.setAddress(addressTextField.getText());
        customer.setDateOfBirth(dateOfBirthDatePicker.getValue().toString());
        System.out.println(customer.toString());
    }
}
