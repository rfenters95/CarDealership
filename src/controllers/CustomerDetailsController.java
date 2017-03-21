package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import util.Customer;
import util.Session;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerDetailsController implements Initializable {

    @FXML private Label fNameLabel;
    @FXML private Label lNameLabel;
    @FXML private Label phoneLabel;
    @FXML private Label emailLabel;
    @FXML private Label addressLabel;
    @FXML private Label cityLabel;
    @FXML private Label DOBLabel;
    @FXML ComboBox<String> invoiceCB;

    public CustomerDetailsController() {
    }

    @FXML public void viewInvoice(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Customer customer = Session.selectedCustomer;
        fNameLabel.setText(customer.getFirstName());
        lNameLabel.setText(customer.getLastName());
        phoneLabel.setText(customer.getPhone());
        emailLabel.setText(customer.getEmail());
        addressLabel.setText(customer.getAddress());
        cityLabel.setText(customer.getCity());
        DOBLabel.setText(customer.getDateOfBirth());
    }
}
