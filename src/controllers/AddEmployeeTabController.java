package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import util.Employee;
import util.Init;

import java.util.ArrayList;

public class AddEmployeeTabController implements Init {

    private AlphaController alphaController;

    @FXML private TextField fNameTF;
    @FXML private TextField lNameTF;
    @FXML private TextField phoneTF;
    @FXML private TextField emailTF;
    @FXML private TextField addressTF;
    @FXML private TextField cityTF;
    @FXML private DatePicker dateOfBirthDP;
    @FXML private ComboBox<String> jobTitleCB;

    @FXML public void save(ActionEvent event) {
        try {
            Employee employee = new Employee(fNameTF, lNameTF, phoneTF, emailTF, addressTF, cityTF, dateOfBirthDP, jobTitleCB);
            System.out.println(employee.getFirstName());
        } catch (Exception e) {
            //TODO fix exception replicate with empty fields
            System.out.println("Empty fields!");
        }
    }

    @Override
    public void init(AlphaController alphaController) {
        this.alphaController = alphaController;
        ArrayList<String> jobs = new ArrayList<>();
        jobs.add("Salesman");
        jobs.add("Accountant");
        jobs.add("Manager");
        jobTitleCB.getItems().addAll(jobs);
    }
}
