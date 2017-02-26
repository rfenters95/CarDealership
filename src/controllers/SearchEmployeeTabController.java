package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.util.Callback;
import util.DataHandler;
import util.Employee;
import util.Init;
import util.Session;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeSet;

public class SearchEmployeeTabController implements Init {
    private AlphaController alphaController;

    @FXML private TitledPane tPane;
    @FXML private TextField employeeID;
    @FXML private ListView<Employee> listView;

    private void displayResultSet(ResultSet resultSet) throws SQLException {

        boolean hasResults = resultSet.next();

        if (hasResults) {

            listView.getItems().clear();

            TreeSet<Employee> Employees = new TreeSet<>();

            do {

                Employee Employee = new Employee(resultSet);
                Employees.add(Employee);

            } while (resultSet.next());

            listView.getItems().addAll(Employees);

        } else {
            System.out.println("DB empty!");
        }

    }

    @FXML public void search(ActionEvent event) {
        tPane.setExpanded(false);

        try {

            String sql;
            ResultSet resultSet;
            final String EID = employeeID.getText();

            Connection connection = DataHandler.getConnection();
            Statement statement = connection.createStatement();

            if (!EID.isEmpty()) {

                sql = "SELECT * FROM EMPLOYEES WHERE EMPLOYEE_ID=" + DataHandler.getWrappedValue(EID);
                resultSet = statement.executeQuery(sql);
                displayResultSet(resultSet);

            } else {

                System.out.println("Empty search parameters!");

            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML public void select(ActionEvent event) {
        Session.employee = listView.getSelectionModel().getSelectedItem();
    }

    @FXML public void viewDetails(ActionEvent event) {
        if (Session.employee != null) {
            //popup records
        } else {
            //alert
        }
    }

    @Override
    public void init(AlphaController alphaController) {

        this.alphaController = alphaController;

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

        try {

            String sql;
            ResultSet resultSet;

            Connection connection = DataHandler.getConnection();
            Statement statement = connection.createStatement();

            sql = "SELECT * FROM EMPLOYEES";
            resultSet = statement.executeQuery(sql);
            displayResultSet(resultSet);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
