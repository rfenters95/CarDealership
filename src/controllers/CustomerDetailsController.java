package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import java.util.ResourceBundle;

public class CustomerDetailsController implements Initializable {

    private ResultSet resultSet;
    private AlphaController alphaController;

    @FXML private TextField fNameTF;
    @FXML private TextField lNameTF;
    @FXML private TextField phoneTF;
    @FXML private TextField emailTF;
    @FXML private TextField addressTF;
    @FXML private TextField cityTF;
    @FXML private TextField dateOfBirthTF;
    @FXML private ComboBox<Invoice> invoiceCB;
    @FXML private Button viewInvoiceButton;

    @FXML public void viewInvoice(ActionEvent event) throws IOException {
        Session.selectedInvoice = invoiceCB.getSelectionModel().getSelectedItem();

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Customer customer = Session.selectedCustomer;

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

        dateOfBirthTF.setText(customer.getDateOfBirth());
        dateOfBirthTF.setDisable(true);

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

        try {

            Connection connection = DataHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `INVOICES` WHERE `CUSTOMER_ID`=?");

            preparedStatement.setString(1, Session.selectedCustomer.getID());
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

            e.printStackTrace();

        }

    }
}
