package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.util.Callback;
import util.Customer;
import util.DataHandler;
import util.Init;
import util.Session;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeSet;

public class SearchCustomerTabController implements Init {

    private AlphaController alphaController;

    @FXML private TextField fNameTF;
    @FXML private TextField lNameTF;
    @FXML private ListView<Customer> listView;
    @FXML private TitledPane tPane;

    private void displayResultSet(ResultSet resultSet) throws SQLException {

        boolean hasResults = resultSet.next();

        if (hasResults) {

            listView.getItems().clear();

            TreeSet<Customer> customers = new TreeSet<>();

            do {

                Customer customer = new Customer(resultSet);
                customers.add(customer);

            } while (resultSet.next());

            listView.getItems().addAll(customers);

        } else {
            System.out.println("DB empty!");
        }

    }

    @FXML public void search(ActionEvent event) {
        tPane.setExpanded(false);

        try {

            String sql;
            ResultSet resultSet;
            final String fName = fNameTF.getText();
            final String lName = lNameTF.getText();

            Connection connection = DataHandler.getConnection();
            Statement statement = connection.createStatement();

            if (!fName.isEmpty() && !lName.isEmpty()) {

                sql = "SELECT * FROM CUSTOMERS WHERE FNAME=" + DataHandler.getWrappedValue(fName) + " AND LNAME=" + DataHandler.getWrappedValue(lName);
                resultSet = statement.executeQuery(sql);
                displayResultSet(resultSet);

            } else if (!fName.isEmpty()) {

                sql = "SELECT * FROM CUSTOMERS WHERE FNAME=" + DataHandler.getWrappedValue(fName);
                resultSet = statement.executeQuery(sql);
                displayResultSet(resultSet);

            } else if (!lName.isEmpty()) {

                sql = "SELECT * FROM CUSTOMERS WHERE LNAME=" + DataHandler.getWrappedValue(lName);
                resultSet = statement.executeQuery(sql);
                displayResultSet(resultSet);

            } else {

                System.out.println("Empty search parameters!");

            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML public void selectCustomer(ActionEvent event) {
        Session.customer = listView.getSelectionModel().getSelectedItem();
        System.out.println(Session.customer.getRow());
    }

    @FXML public void viewRecords(ActionEvent event) {
        if (Session.customer != null) {
            //popup records
        } else {
            //alert
        }
    }

    @Override
    public void init(AlphaController alphaController) {

        this.alphaController = alphaController;

        listView.setCellFactory(new Callback<ListView<Customer>, ListCell<Customer>>() {
            @Override
            public ListCell<Customer> call(ListView<Customer> param) {
                return new ListCell<Customer>() {
                    @Override
                    protected void updateItem(Customer item, boolean empty) {
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
            boolean hasResults;

            Connection connection = DataHandler.getConnection();
            Statement statement = connection.createStatement();

            sql = "SELECT * FROM CUSTOMERS";
            resultSet = statement.executeQuery(sql);
            displayResultSet(resultSet);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
