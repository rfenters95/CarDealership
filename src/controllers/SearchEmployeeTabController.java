package controllers;

import components.NumberOnlyTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import util.DataHandler;
import util.Employee;
import util.Init;
import util.Session;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collections;

public class SearchEmployeeTabController implements Init {

    private AlphaController alphaController;
    private ResultSet resultSet;

    @FXML private TitledPane tPane;
    @FXML private TitledPane employeeResultsTP;

    @FXML private NumberOnlyTextField employeeID;

    @FXML private ListView<Employee> listView;

    @FXML private Button viewDetailsButton;

    @FXML private Label selectedLabel;

    public void displayResultSet() {

        tPane.setText(String.format(
                "Search Employees - {EID = %s}",
                !employeeID.getText().isEmpty() ? employeeID.getText() : "Any"
        ));

        try {

            boolean hasResults = resultSet.next();
            if (hasResults) {

                listView.getItems().clear();

                int numberOfResults = 0;
                while (hasResults) {
                    Employee employee = new Employee(resultSet);
                    listView.getItems().add(employee);
                    numberOfResults++;
                    hasResults = resultSet.next();
                }

                employeeResultsTP.setText(String.format("Results - %d", numberOfResults));
                Collections.sort(listView.getItems());

            } else {
                Session.getInstance().alert("No results!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML public void showAll(ActionEvent event) {

        employeeID.clear();
        updateResultSet();
        displayResultSet();

    }

    @FXML public void search(ActionEvent event) {
        tPane.setExpanded(false);

        try {

            final String EID = employeeID.getText();

            Connection connection = DataHandler.getConnection();

            if (!EID.isEmpty()) {

                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `EMPLOYEES` WHERE ID=?");
                preparedStatement.setString(1, EID);
                resultSet = preparedStatement.executeQuery();
                displayResultSet();

            } else {

                System.out.println("Empty search parameters!");
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `EMPLOYEES`");
                resultSet = preparedStatement.executeQuery();
                displayResultSet();

            }

        } catch (Exception e) {
            Session.getInstance().alert("Error: Contact Admin!");
            e.printStackTrace();
        }
    }

    @FXML public void select(ActionEvent event) {
        if (listView.getSelectionModel().getSelectedItem() != null) {
            Session.getInstance().selectedEmployee = listView.getSelectionModel().getSelectedItem();
            viewDetailsButton.setDisable(false);
            selectedLabel.setText("Selected - " + Session.getInstance().selectedEmployee.toString());
        }
    }

    @FXML public void viewDetails(ActionEvent event) throws IOException {

        if (Session.getInstance().selectedEmployee != null) {

            Stage newStage = new Stage();
            newStage.initModality(Modality.APPLICATION_MODAL);
            FXMLLoader fxmlLoader = new FXMLLoader();
            newStage.setTitle("View Employee");

            fxmlLoader.setLocation(getClass().getResource("../views/EmployeeDetails.fxml"));
            Parent newResource = fxmlLoader.load();
            Scene newScene = new Scene(newResource);
            newStage.setScene(newScene);
            newStage.setResizable(false);
            newStage.showAndWait();

        } else {
            System.out.println("Error: No Employee selected!");
        }

    }

    @Override
    public void init(AlphaController alphaController) {

        this.alphaController = alphaController;
        viewDetailsButton.setDisable(true);

        selectedLabel.setText("Selected - None");

        listView.setCellFactory(new Callback<ListView<Employee>, ListCell<Employee>>() {
            @Override
            public ListCell<Employee> call(ListView<Employee> param) {
                return new ListCell<Employee>() {
                    @Override
                    protected void updateItem(Employee item, boolean empty) {
                        super.updateItem(item, empty);
                        if(!empty && item != null) {
                            setText(item.toString());
                        } else {
                            setText("");
                            setGraphic(null);
                        }
                    }
                };
            }
        });

        updateResultSet();
        displayResultSet();

    }

    public void updateResultSet() {
        try {

            Connection connection = DataHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM EMPLOYEES");
            resultSet = preparedStatement.executeQuery();
            employeeID.clear();

        } catch (Exception e) {
            Session.getInstance().alert("Error: Contact Admin!");
            e.printStackTrace();
        }
    }
}
