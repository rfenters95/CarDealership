package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import util.Customer;
import util.DataHandler;
import util.Invoice;
import util.Session;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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

        phoneTF.setText(customer.getPhone());
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

            String sql;
            Connection connection = DataHandler.getConnection();
            Statement statement = connection.createStatement();

            sql = String.format("SELECT * FROM INVOICES WHERE CUSTOMER_ID=%s", Session.selectedCustomer.getID());
            resultSet = statement.executeQuery(sql);

            // for every invoice in resultSet add date to comboBox
            while (resultSet.next()) {
                Invoice invoice = new Invoice(resultSet);
                invoiceCB.getItems().add(invoice);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
