package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import util.DataHandler;
import util.Employee;
import util.Init;
import util.Session;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.TreeSet;

public class SearchEmployeeTabController implements Init {
    private AlphaController alphaController;
    private ResultSet resultSet;

    @FXML private TitledPane tPane;
    @FXML private TextField employeeID;
    @FXML private ListView<Employee> listView;
    @FXML private Button viewDetailsButton;

    public void displayResultSet() {

        try {

            boolean hasResults = resultSet.next();

            if (hasResults) {

                listView.getItems().clear();

                TreeSet<Employee> employees = new TreeSet<>();

                do {

                    Employee employee = new Employee(resultSet);
                    employees.add(employee);

                } while (resultSet.next());

                listView.getItems().addAll(employees);

            } else {
                System.out.println("DB empty!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML public void search(ActionEvent event) {
        tPane.setExpanded(false);

        try {

            String sql;
            final String EID = employeeID.getText();

            Connection connection = DataHandler.getConnection();
            Statement statement = connection.createStatement();

            if (!EID.isEmpty()) {

                sql = "SELECT * FROM EMPLOYEES WHERE ID=" + DataHandler.getWrappedValue(EID);
                resultSet = statement.executeQuery(sql);
                displayResultSet();

            } else {

                System.out.println("Empty search parameters!");

            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML public void select(ActionEvent event) {
        if (listView.getSelectionModel().getSelectedItem() != null) {
            Session.selectedEmployee = listView.getSelectionModel().getSelectedItem();
            viewDetailsButton.setDisable(false);
        }
    }

    @FXML public void viewDetails(ActionEvent event) {
        if (Session.selectedEmployee != null) {
            //popup records
        } else {
            //alert
        }
    }

    @Override
    public void init(AlphaController alphaController) {

        this.alphaController = alphaController;
        viewDetailsButton.setDisable(true);

        listView.setCellFactory(new Callback<ListView<Employee>, ListCell<Employee>>() {
            @Override
            public ListCell<Employee> call(ListView<Employee> param) {
                return new ListCell<Employee>() {
                    @Override
                    protected void updateItem(Employee item, boolean empty) {
                        super.updateItem(item, empty);
                        if(!empty && item != null) {
                            setText(item.getRow());
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

    public ResultSet updateResultSet() {
        try {
            String sql;

            Connection connection = DataHandler.getConnection();
            Statement statement = connection.createStatement();

            sql = "SELECT * FROM EMPLOYEES";
            resultSet = statement.executeQuery(sql);

        } catch (Exception e) {
            System.out.println(e);
        }

        return resultSet;
    }
}
