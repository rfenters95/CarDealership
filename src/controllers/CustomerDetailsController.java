package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import util.Customer;
import util.DataHandler;
import util.Invoice;
import util.Session;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class CustomerDetailsController implements Initializable {

    private ResultSet resultSet;
    private AlphaController alphaController;

    @FXML private Label fNameLabel;
    @FXML private Label lNameLabel;
    @FXML private Label phoneLabel;
    @FXML private Label emailLabel;
    @FXML private Label addressLabel;
    @FXML private Label cityLabel;
    @FXML private Label DOBLabel;
    @FXML ComboBox<Invoice> invoiceCB;

    @FXML public void viewInvoice(ActionEvent event) {
        // on selection of date
        Invoice invoice = invoiceCB.getSelectionModel().getSelectedItem();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Customer customer = Session.selectedCustomer;
        fNameLabel.setText(customer.getFirstName());
        lNameLabel.setText(customer.getLastName());
        phoneLabel.setText(customer.getPhone());
        emailLabel.setText(customer.getEmail());
        addressLabel.setText(customer.getAddress());
        cityLabel.setText(customer.getCity());
        DOBLabel.setText(customer.getDateOfBirth());

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
