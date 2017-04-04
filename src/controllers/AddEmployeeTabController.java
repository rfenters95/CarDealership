package controllers;

import components.NumberOnlyTextField;
import components.TextOnlyTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import util.Employee;
import util.Init;
import util.Session;

public class AddEmployeeTabController implements Init {

    private AlphaController alphaController;

    @FXML private TextOnlyTextField fNameTF;
    @FXML private TextOnlyTextField lNameTF;
    @FXML private NumberOnlyTextField phoneTF;
    @FXML private TextField emailTF;
    @FXML private TextField addressTF;
    @FXML private TextOnlyTextField cityTF;
    @FXML private NumberOnlyTextField salaryTF;
    @FXML private DatePicker dateOfBirthDP;
    @FXML private ComboBox<String> jobTitleCB;

    @FXML public void save(ActionEvent event) {
        try {

            Employee employee = new Employee(fNameTF, lNameTF, phoneTF, emailTF, addressTF, cityTF, dateOfBirthDP, jobTitleCB, salaryTF);
            Employee.insertEntry(employee);
            Session.getInstance().reloadEmployees();
            Session.getInstance().alert("Employee Added!");

        } catch (Exception e) {
            Session.getInstance().alert(e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void init(AlphaController alphaController) {

        this.alphaController = alphaController;
        jobTitleCB.getItems().addAll(Employee.getJobList());

    }

}
