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

import java.io.IOException;

public class Controller {

    @FXML private TextField firstNameTextField;
    @FXML private TextField lastNameTextField;
    @FXML private TextField emailTextField;
    @FXML private TextField phoneTextField;
    @FXML private TextField addressTextField;
    @FXML private DatePicker dateOfBirthDatePicker;

    @FXML public void doSomething(ActionEvent event) throws IOException {
//        Customer customer = new Customer();
//        customer.setFirstName(firstNameTextField.getText());
//        customer.setLastName(lastNameTextField.getText());
//        customer.setEmail(emailTextField.getText());
//        customer.setPhone(phoneTextField.getText());
//        customer.setAddress(addressTextField.getText());
//        customer.setDateOfBirth(dateOfBirthDatePicker.getValue().toString());
//        System.out.println(customer.toString());
        Parent register_page = FXMLLoader.load(getClass().getResource("customer_registration.fxml"));
        Scene register_scene = new Scene(register_page);
        Stage register_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        register_stage.hide();
        register_stage.setScene(register_scene);
        register_stage.show();

    }

}
