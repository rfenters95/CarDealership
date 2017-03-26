package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.Employee;
import util.Formatter;
import util.Session;

import java.io.IOException;
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

    private boolean inputDisabled = true;

    @FXML public void edit(ActionEvent event) throws IOException {

        inputDisabled = !inputDisabled;
        Button button = (Button) event.getSource();

        if (inputDisabled) {
            button.setText("Edit");
        } else {
            button.setText("View");
        }

        lNameTF.setDisable(inputDisabled);
        fNameTF.setDisable(inputDisabled);
        phoneTF.setDisable(inputDisabled);
        emailTF.setDisable(inputDisabled);
        addressTF.setDisable(inputDisabled);
        cityTF.setDisable(inputDisabled);
        dateOfBirthTF.setDisable(inputDisabled);
        jobTF.setDisable(inputDisabled);
        salaryTF.setDisable(inputDisabled);
        workStatusTF.setDisable(inputDisabled);
        totalSalesTF.setDisable(inputDisabled);
        commissionTF.setDisable(inputDisabled);

    }

    @FXML public void save(ActionEvent event) throws IOException {

        try {
            Session.selectedEmployee.setFirstName(fNameTF.getText());
            Session.selectedEmployee.setLastName(lNameTF.getText());
            Session.selectedEmployee.setPhone(Formatter.parsePhone(phoneTF.getText()));
            Session.selectedEmployee.setEmail(emailTF.getText());
            Session.selectedEmployee.setAddress(addressTF.getText());
            Session.selectedEmployee.setCity(cityTF.getText());
            Session.selectedEmployee.setDateOfBirth(dateOfBirthTF.getText());
            Employee.updateEntry(Session.selectedEmployee);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Session.alphaController.getSearchEmployeeTabController().updateResultSet();
        Session.alphaController.getSearchEmployeeTabController().displayResultSet();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

    }

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
