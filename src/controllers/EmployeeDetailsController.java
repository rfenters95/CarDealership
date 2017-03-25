package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import util.Employee;
import util.Formatter;
import util.Session;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeDetailsController implements Initializable {

    @FXML private TextField fNameTF;
    @FXML private TextField lNameTF;
    @FXML private TextField phoneTF;
    @FXML private TextField emailTF;
    @FXML private TextField addressTF;
    @FXML private TextField cityTF;
    @FXML private TextField dateOfBirthTF;
    @FXML private TextField jobTF;
    @FXML private TextField salaryTF;
    @FXML private TextField workStatusTF;
    @FXML private TextField totalSalesTF;
    @FXML private TextField commissionTF;
    @FXML private Label totalSalesLabel;
    @FXML private Label commissionLabel;

    /*
    @FXML public void viewInvoice(ActionEvent event) {
        
    }
    */

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Employee employee = Session.selectedEmployee;

        fNameTF.setText(employee.getFirstName());
        fNameTF.setDisable(true);

        lNameTF.setText(employee.getLastName());
        lNameTF.setDisable(true);

        phoneTF.setText(Formatter.phoneFormatter(employee.getPhone()));
        phoneTF.setDisable(true);

        emailTF.setText(employee.getEmail());
        emailTF.setDisable(true);

        addressTF.setText(employee.getAddress());
        addressTF.setDisable(true);

        cityTF.setText(employee.getCity());
        cityTF.setDisable(true);

        dateOfBirthTF.setText(employee.getDateOfBirth());
        dateOfBirthTF.setDisable(true);

        jobTF.setText(employee.getJobTitle());
        jobTF.setDisable(true);

        salaryTF.setText(Formatter.USDFormatter(employee.getSalary()));
        salaryTF.setDisable(true);

        workStatusTF.setText(employee.getWorkStatus().equals("1") ? "Active" : "Inactive");
        workStatusTF.setDisable(true);

        if (employee.getJobTitle().equals("Sales")) {

            totalSalesTF.setText(employee.getTotalSales());
            totalSalesTF.setDisable(true);

            double pCommission = Double.parseDouble(employee.getPercentCommission()) * 100;
            commissionLabel.setText("Commission (%" + pCommission + ")");
            commissionTF.setText(employee.getCommission());
            commissionTF.setDisable(true);
        } else {
            totalSalesLabel.setVisible(false);
            totalSalesTF.setVisible(false);
            commissionLabel.setVisible(false);
            commissionTF.setVisible(false);
        }
    }
}
