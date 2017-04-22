package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import util.Customer;
import util.DataHandler;
import util.Init;
import util.Session;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collections;

/*
*  SearchCustomerTabController
*  Author: Reed Fenters
*
*  Search db for customers, and select them for info modification (ViewDetails)
*  or for invoice generation (Select).
* */
public class SearchCustomerTabController implements Init {

    private AlphaController alphaController;
    private ResultSet resultSet;

    @FXML private TextField fNameTF;
    @FXML private TextField lNameTF;

    @FXML private ListView<Customer> listView;

    @FXML private TitledPane tPane;
    @FXML private TitledPane customerResultsTP;

    @FXML private Button viewDetailsButton;

    @FXML private Label selectedLabel;

    // Display results of search
    public void displayResultSet() {

        try {

            // Get search parameters
            final String fName = fNameTF.getText();
            final String lName = lNameTF.getText();

            // Update titledPane (search) with search parameters
            tPane.setText(String.format("Search Customers - {First Name = %s, Last Name = %s}",
                    !fName.isEmpty() ? fName : "Any",
                    !lName.isEmpty() ? lName : "Any"
            ));

            // Empty results check
            boolean hasResults = resultSet.next();
            if (hasResults) {

                // Clear old list items
                listView.getItems().clear();

                // Create customer objects from resultSet
                int numberOfResults = 0;
                while (hasResults) {
                    Customer customer = new Customer(resultSet);
                    listView.getItems().add(customer);
                    numberOfResults++;
                    hasResults = resultSet.next();
                }

                // Update titledPane (results) with result count
                customerResultsTP.setText(String.format("Results - %d", numberOfResults));
                Collections.sort(listView.getItems());

            } else {
                Session.getInstance().alert("No results!");
            }

        } catch (Exception e) {
            Session.getInstance().alert("Error: Contact Admin!");
            e.printStackTrace();
        }

    }

    // Show all db entries
    @FXML public void showAll(ActionEvent event) {

        fNameTF.clear();
        lNameTF.clear();
        updateResultSet();
        displayResultSet();

    }

    // Search db using search parameters, and update resultSet field
    @FXML public void search(ActionEvent event) {

        // Collapse search fields for better results viewing
        tPane.setExpanded(false);

        try {

            // Get search fields
            String sql;
            final String fName = fNameTF.getText();
            final String lName = lNameTF.getText();

            // Get db connection
            Connection connection = DataHandler.getConnection();
            Statement statement = connection.createStatement();

            // Search using fName & lName
            if (!fName.isEmpty() && !lName.isEmpty()) {

                sql = "SELECT * FROM CUSTOMERS WHERE FIRST_NAME=" + DataHandler.getWrappedValue(fName) + " AND LAST_NAME=" + DataHandler.getWrappedValue(lName);
                resultSet = statement.executeQuery(sql);
                displayResultSet();

            // Search using only fName
            } else if (!fName.isEmpty()) {

                sql = "SELECT * FROM CUSTOMERS WHERE FIRST_NAME=" + DataHandler.getWrappedValue(fName);
                resultSet = statement.executeQuery(sql);
                displayResultSet();

            // Search using only lName
            } else if (!lName.isEmpty()) {

                sql = "SELECT * FROM CUSTOMERS WHERE LAST_NAME=" + DataHandler.getWrappedValue(lName);
                resultSet = statement.executeQuery(sql);
                displayResultSet();

            // Empty fields validation
            } else {

                // Alert user
                Session.getInstance().alert("Error: Empty fields!");

                // Return all customers
                sql = "SELECT * FROM CUSTOMERS";
                resultSet = statement.executeQuery(sql);
                displayResultSet();

            }

        } catch (Exception e) {
            Session.getInstance().alert("Error: Contact Admin!");
        }
    }

    // Select customer
    @FXML public void select(ActionEvent event) {

        // Validate that a customer was selected
        if (listView.getSelectionModel().getSelectedItem() != null) {
            Session.getInstance().selectedCustomer = listView.getSelectionModel().getSelectedItem();
            viewDetailsButton.setDisable(false);
            selectedLabel.setText("Selected - " + Session.getInstance().selectedCustomer.toString());
        } else {
            Session.getInstance().alert("Error: No customer selected!");
        }

    }

    // Open window for more detailed info
    @FXML public void viewDetails(ActionEvent event) throws IOException {

        // Ensure customer been selected check
        if (Session.getInstance().selectedCustomer != null) {

            Stage newStage = new Stage();
            newStage.initModality(Modality.APPLICATION_MODAL);
            FXMLLoader fxmlLoader = new FXMLLoader();
            newStage.setTitle("View Customer");

            fxmlLoader.setLocation(getClass().getResource("../views/CustomerDetails.fxml"));
            Parent newResource = fxmlLoader.load();
            Scene newScene = new Scene(newResource);
            newStage.setScene(newScene);
            newStage.setResizable(false);
            newStage.showAndWait();

        }

    }

    @Override
    public void init(AlphaController alphaController) {

        // Get access to central controller for interController communication
        this.alphaController = alphaController;

        // Disable viewDetails until customer has been selected
        viewDetailsButton.setDisable(true);

        // Init selected customer label
        selectedLabel.setText("Selected - None");

        // Customize listView
        listView.setCellFactory(new Callback<ListView<Customer>, ListCell<Customer>>() {
            @Override
            public ListCell<Customer> call(ListView<Customer> param) {
                return new ListCell<Customer>() {
                    @Override
                    protected void updateItem(Customer item, boolean empty) {
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

        // Display all customers
        updateResultSet();
        displayResultSet();

    }

    /*
    * Update resultSet to contain all customers
    * typically used after CUSTOMER table modification
    * but also to init tables on start
    * */
    public void updateResultSet() {

        try {

            Connection connection = DataHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM CUSTOMERS");
            resultSet = preparedStatement.executeQuery();

        } catch (Exception e) {
            Session.getInstance().alert("Error: Connection failed!\nCustomer table");
        }

    }

}
