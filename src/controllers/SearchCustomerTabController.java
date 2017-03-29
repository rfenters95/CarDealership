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

    public void displayResultSet() {

        try {

            final String fName = fNameTF.getText();
            final String lName = lNameTF.getText();
            tPane.setText(String.format("Search Customers - {First Name = %s, Last Name = %s}",
                    !fName.isEmpty() ? fName : "Any",
                    !lName.isEmpty() ? lName : "Any"
            ));

            boolean hasResults = resultSet.next();
            if (hasResults) {

                listView.getItems().clear();

                int numberOfResults = 0;
                while (hasResults) {
                    Customer customer = new Customer(resultSet);
                    listView.getItems().add(customer);
                    numberOfResults++;
                    hasResults = resultSet.next();
                }

                customerResultsTP.setText(String.format("Results - %d", numberOfResults));
                Collections.sort(listView.getItems());

            } else {
                System.out.println("DB empty!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML public void showAll(ActionEvent event) {

        updateResultSet();
        displayResultSet();

    }

    @FXML public void search(ActionEvent event) {
        tPane.setExpanded(false);

        try {

            String sql;
            final String fName = fNameTF.getText();
            final String lName = lNameTF.getText();

            Connection connection = DataHandler.getConnection();
            Statement statement = connection.createStatement();

            if (!fName.isEmpty() && !lName.isEmpty()) {

                sql = "SELECT * FROM CUSTOMERS WHERE FIRST_NAME=" + DataHandler.getWrappedValue(fName) + " AND LAST_NAME=" + DataHandler.getWrappedValue(lName);
                resultSet = statement.executeQuery(sql);
                displayResultSet();

            } else if (!fName.isEmpty()) {

                sql = "SELECT * FROM CUSTOMERS WHERE FIRST_NAME=" + DataHandler.getWrappedValue(fName);
                resultSet = statement.executeQuery(sql);
                displayResultSet();

            } else if (!lName.isEmpty()) {

                sql = "SELECT * FROM CUSTOMERS WHERE LAST_NAME=" + DataHandler.getWrappedValue(lName);
                resultSet = statement.executeQuery(sql);
                displayResultSet();

            } else {

                System.out.println("Empty search parameters!");
                sql = "SELECT * FROM CUSTOMERS";
                resultSet = statement.executeQuery(sql);
                displayResultSet();

            }

        } catch (Exception e) {
            Session.getInstance().alert("Error: No customer selected!");
        }
    }

    @FXML public void select(ActionEvent event) {

        if (listView.getSelectionModel().getSelectedItem() != null) {
            Session.getInstance().selectedCustomer = listView.getSelectionModel().getSelectedItem();
            viewDetailsButton.setDisable(false);
            selectedLabel.setText("Selected - " + Session.getInstance().selectedCustomer.toString());
        } else {
            Session.getInstance().alert("Error: No customer selected!");
        }

    }

    @FXML public void viewDetails(ActionEvent event) throws IOException {

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

        this.alphaController = alphaController;
        viewDetailsButton.setDisable(true);

        selectedLabel.setText("Selected - None");

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

        updateResultSet();
        displayResultSet();
    }

    public void updateResultSet() {

        try {

            Connection connection = DataHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM CUSTOMERS");
            resultSet = preparedStatement.executeQuery();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}
