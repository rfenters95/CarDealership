package controllers;

import components.NumberOnlyTextField;
import components.TextOnlyTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import util.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

/*
*  CustomerDetailsController
*  Author: Reed Fenters
*
*  View/Edit details of selected customer
*  View Invoices of selected customer
* */
public class CustomerDetailsController implements Initializable {

    private ResultSet resultSet;
    private AlphaController alphaController;

    @FXML private TextOnlyTextField fNameTF;
    @FXML private TextOnlyTextField lNameTF;
    @FXML private NumberOnlyTextField phoneTF;
    @FXML private TextField emailTF;
    @FXML private TextField addressTF;
    @FXML private TextOnlyTextField cityTF;
    @FXML private DatePicker dateOfBirthDP;
    @FXML private ComboBox<Invoice> invoiceCB;
    @FXML private Button viewInvoiceButton;

    private boolean inputDisabled = true;

    // View select invoice for selected customer
    @FXML public void viewInvoice(ActionEvent event) throws IOException {

        // Store invoice in Session for access across controllers
        Session.getInstance().selectedInvoice = invoiceCB.getSelectionModel().getSelectedItem();

        // Create popup stage & open
        Stage newStage = new Stage();
        newStage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader fxmlLoader = new FXMLLoader();
        newStage.setTitle("View Invoice");
        fxmlLoader.setLocation(getClass().getResource("../views/ViewInvoice.fxml"));
        Parent newResource = fxmlLoader.load();
        Scene newScene = new Scene(newResource);
        newStage.setScene(newScene);
        newStage.setResizable(false);
        newStage.showAndWait();

    }

    // Edit customer details
    @FXML public void edit(ActionEvent event) throws IOException {

        // Determines if fields are editable
        inputDisabled = !inputDisabled;
        Button button = (Button) event.getSource();

        // Flip text on edit btn
        if (inputDisabled) {
            button.setText("Edit");
        } else {
            button.setText("View");
        }

        // Determines if fields are editable
        fNameTF.setDisable(inputDisabled);
        lNameTF.setDisable(inputDisabled);
        phoneTF.setDisable(inputDisabled);
        emailTF.setDisable(inputDisabled);
        addressTF.setDisable(inputDisabled);
        cityTF.setDisable(inputDisabled);
        dateOfBirthDP.setDisable(inputDisabled);

    }

    // Save changes made to customer info
    @FXML public void save(ActionEvent event) throws IOException {

        try {

            // Update selectedCustomer object
            Session.getInstance().selectedCustomer.setFirstName(fNameTF.getText());
            Session.getInstance().selectedCustomer.setLastName(lNameTF.getText());
            Session.getInstance().selectedCustomer.setPhone(Formatter.parseNumber(phoneTF.getText()));
            Session.getInstance().selectedCustomer.setEmail(emailTF.getText());
            Session.getInstance().selectedCustomer.setAddress(addressTF.getText());
            Session.getInstance().selectedCustomer.setCity(cityTF.getText());
            Session.getInstance().selectedCustomer.setDateOfBirth(dateOfBirthDP.getValue().toString());

            // Save changed object to db
            Customer.updateEntry(Session.getInstance().selectedCustomer);

        } catch (Exception e) {
            Session.getInstance().alert("Error: Contact admin!");
            e.printStackTrace();
        }

        // Update search customers listView
        Session.getInstance().alphaController.getSearchCustomerTabController().updateResultSet();
        Session.getInstance().alphaController.getSearchCustomerTabController().displayResultSet();

        // Close stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // INIT fields to selectedCustomer info
        Customer customer = Session.getInstance().selectedCustomer;

        fNameTF.setText(customer.getFirstName());
        fNameTF.setDisable(true);

        lNameTF.setText(customer.getLastName());
        lNameTF.setDisable(true);

        phoneTF.setText(Formatter.phoneFormatter(customer.getPhone()));
        phoneTF.setDisable(true);

        emailTF.setText(customer.getEmail());
        emailTF.setDisable(true);

        addressTF.setText(customer.getAddress());
        addressTF.setDisable(true);

        cityTF.setText(customer.getCity());
        cityTF.setDisable(true);

        dateOfBirthDP.setValue(LocalDate.parse(customer.getDateOfBirth()));
        dateOfBirthDP.setDisable(true);
        // END INIT

        // Customize invoice comboBox
        invoiceCB.setCellFactory(new Callback<ListView<Invoice>, ListCell<Invoice>>() {
            @Override
            public ListCell<Invoice> call(ListView<Invoice> param) {
                return new ListCell<Invoice>() {
                    @Override
                    protected void updateItem(Invoice item, boolean empty) {
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

        // Get all invoices for selected customer
        try {

            Connection connection = DataHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `INVOICES` WHERE `CUSTOMER_ID`=?");

            preparedStatement.setString(1, Session.getInstance().selectedCustomer.getID());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Invoice invoice = new Invoice(resultSet);
                invoiceCB.getItems().add(invoice);
            }

            if (!invoiceCB.getItems().isEmpty()) {
                invoiceCB.getSelectionModel().select(0);
            } else {
                invoiceCB.setPromptText("None");
                invoiceCB.setDisable(true);
                viewInvoiceButton.setDisable(true);
            }

        } catch (Exception e) {
            Session.getInstance().alert("Error: Contact admin!");
            e.printStackTrace();
        }

    }
}
