package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import util.Employee;
import util.Session;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeDetailsController implements Initializable {

    @FXML private Label fNameLabel;
    @FXML private Label lNameLabel;
    @FXML private Label phoneLabel;
    @FXML private Label emailLabel;
    @FXML private Label addressLabel;
    @FXML private Label cityLabel;
    @FXML private Label DOBLabel;
    @FXML private Label commissionLabel;

    /*
    @FXML public void viewInvoice(ActionEvent event) {
        
    }
    */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Employee employee = Session.selectedEmployee;
        fNameLabel.setText(employee.getFirstName());
        lNameLabel.setText(employee.getLastName());
        phoneLabel.setText(employee.getPhone());
        emailLabel.setText(employee.getEmail());
        addressLabel.setText(employee.getAddress());
        cityLabel.setText(employee.getCity());
        DOBLabel.setText(employee.getDateOfBirth());
    }
}
