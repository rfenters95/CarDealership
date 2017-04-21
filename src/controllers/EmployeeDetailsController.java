package controllers;

import components.NumberOnlyTextField;
import components.TextOnlyTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.Employee;
import util.Formatter;
import util.Session;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EmployeeDetailsController implements Initializable {

    @FXML private TextOnlyTextField fNameTF;
    @FXML private TextOnlyTextField lNameTF;
    @FXML private NumberOnlyTextField phoneTF;
    @FXML private TextField emailTF;
    @FXML private TextField addressTF;
    @FXML private TextOnlyTextField cityTF;
    @FXML private DatePicker dateOfBirthDP;
    @FXML private TextOnlyTextField jobTF;
    @FXML private NumberOnlyTextField salaryTF;
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
        dateOfBirthDP.setDisable(inputDisabled);
        jobTF.setDisable(inputDisabled);
        salaryTF.setDisable(inputDisabled);

    }

    @FXML public void save(ActionEvent event) throws IOException {

        try {
            Session.getInstance().selectedEmployee.setFirstName(fNameTF.getText());
            Session.getInstance().selectedEmployee.setLastName(lNameTF.getText());
            Session.getInstance().selectedEmployee.setPhone(Formatter.parseNumber(phoneTF.getText()));
            Session.getInstance().selectedEmployee.setEmail(emailTF.getText());
            Session.getInstance().selectedEmployee.setAddress(addressTF.getText());
            Session.getInstance().selectedEmployee.setCity(cityTF.getText());
            Session.getInstance().selectedEmployee.setDateOfBirth(dateOfBirthDP.getValue().toString());
            Session.getInstance().selectedEmployee.setJobTitle(jobTF.getText());
            Session.getInstance().selectedEmployee.setSalary(Formatter.parseNumber(salaryTF.getText()));
            Employee.updateEntry(Session.getInstance().selectedEmployee);
        } catch (Exception e) {
            Session.getInstance().alert("Error: Contact admin!");
            e.printStackTrace();
        }

        Session.getInstance().reloadEmployees();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Employee employee = Session.getInstance().selectedEmployee;

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

        dateOfBirthDP.setValue(LocalDate.parse(employee.getDateOfBirth()));
        dateOfBirthDP.setDisable(true);

        jobTF.setText(employee.getJobTitle());
        jobTF.setDisable(true);

        salaryTF.setText(Formatter.USDFormatter(employee.getSalary()));
        salaryTF.setDisable(true);

        workStatusTF.setText(employee.getWorkStatus().equals("1") ? "Active" : "Inactive");
        workStatusTF.setDisable(true);

        if (employee.getJobTitle().equals("Sales")) {

            totalSalesTF.setText(Formatter.USDFormatter(employee.getTotalSales()));
            totalSalesTF.setDisable(true);

            double pCommission = Double.parseDouble(employee.getPercentCommission()) * 100;
            commissionLabel.setText("Commission (%" + pCommission + ")");
            commissionTF.setText(Formatter.USDFormatter(employee.getCommission()));
            commissionTF.setDisable(true);

        } else {
            totalSalesLabel.setVisible(false);
            totalSalesTF.setVisible(false);
            commissionLabel.setVisible(false);
            commissionTF.setVisible(false);
        }
    }
}
