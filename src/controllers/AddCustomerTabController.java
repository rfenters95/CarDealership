package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import util.Customer;

public class AddCustomerTabController {
    @FXML private TextField fNameTF;
    @FXML private TextField lNameTF;
    @FXML private TextField phoneTF;
    @FXML private TextField emailTF;
    @FXML private TextField addressTF;
    @FXML private TextField cityTF;
    @FXML private DatePicker dateOfBirthDP;

    @FXML public void save(ActionEvent event) {
        try {
            Customer customer = new Customer(fNameTF, lNameTF, phoneTF, emailTF, addressTF, cityTF, dateOfBirthDP);
            System.out.println(customer.getFirstName());
        } catch (Exception e) {
            //TODO fix exception replicate with empty fields
            System.out.println("Empty fields!");
        }
    }
}
