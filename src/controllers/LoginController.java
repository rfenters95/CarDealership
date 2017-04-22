package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.DataHandler;
import util.Employee;
import util.Session;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

/*
*  LoginController
*  Author: Reed Fenters
*
*  Controls access to system via employee authorization
* */
public class LoginController implements Initializable {

    @FXML private TextField username;
    @FXML private TextField password;
    @FXML private Button login;
    @FXML private VBox root;


    // Use input fields info to attempt to login
    @FXML public void login(ActionEvent event) {

        try {

            // Empty field validation
            if (!username.getText().isEmpty() && !password.getText().isEmpty()) {

                // Search user table for employee with matching username & password
                Connection connection = DataHandler.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `USERS` WHERE USERNAME = ? AND PASSWORD = ?");
                preparedStatement.setString(1, username.getText());
                preparedStatement.setString(2, password.getText());
                ResultSet resultSet = preparedStatement.executeQuery();
                boolean hasResults = resultSet.next();

                // Empty results check (USER table)
                if (hasResults) {

                    // Get employee's id from user table
                    String employeeID = resultSet.getString(1);

                    // Use employee's id to find employee in (EMPLOYEE table)
                    preparedStatement = connection.prepareStatement("SELECT * FROM EMPLOYEES WHERE ID = ?");
                    preparedStatement.setString(1, employeeID);
                    resultSet = preparedStatement.executeQuery();
                    hasResults = resultSet.next();

                    // Empty results check (EMPLOYEE table)
                    if (hasResults) {

                        // Once employee is found open main stage
                        Session.getInstance().sessionUser = new Employee(resultSet);
                        loadResource(event, "../views/Alpha.fxml");

                    } else {
                        Session.getInstance().alert("Invalid login combo!");
                    }

                } else {
                    Session.getInstance().alert("Invalid login combo!");
                }

            } else {
                Session.getInstance().alert("Error: Empty fields!");
            }

        } catch (Exception e) {
            Session.getInstance().alert("Error: Contact admin!");
            e.printStackTrace();
        }

    }

    // Load stage created from path to fxml file
    private void loadResource(ActionEvent event, String resourcePath) throws IOException {
        Parent register_page = FXMLLoader.load(getClass().getResource(resourcePath));
        Scene register_scene = new Scene(register_page);
        Stage register_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        register_stage.hide();
        register_stage.setScene(register_scene);
        register_stage.setResizable(true);
        register_stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Allow enter key to trigger login button
        root.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case ENTER:
                    login.fire();
                    break;
            }
        });

    }
}
