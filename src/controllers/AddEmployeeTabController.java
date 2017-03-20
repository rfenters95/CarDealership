package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import util.DataHandler;
import util.Employee;
import util.Init;

import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddEmployeeTabController implements Init, Initializable {

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

            String sql;

            Employee employee = new Employee(fNameTF, lNameTF, phoneTF, emailTF, addressTF, cityTF, dateOfBirthDP, jobTitleCB);
            Connection connection = DataHandler.getConnection();
            Statement statement = connection.createStatement();

            sql = "INSERT INTO `EMPLOYEES` (`ID`, `FIRST_NAME`, `LAST_NAME`, `PHONE`, `EMAIL`, `ADDRESS`, `CITY`, `DATE_OF_BIRTH`, `JOB`, `SALARY`) VALUES (NULL, " + employee.getInsertSQL() + ", '$50,000');";
            statement.executeUpdate(sql);

        } catch (Exception e) {
            System.out.println("Empty fields!");
        }

        try {
            alphaController.getSearchEmployeeTabController().updateResultSet();
            alphaController.getSearchEmployeeTabController().displayResultSet();
        } catch (Exception e) {
            e.printStackTrace();
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //this works
    }
}
