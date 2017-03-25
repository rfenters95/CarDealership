package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import util.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class CreateInvoiceController implements Initializable {

    @FXML private Label dateLabel;
    private String dateString;

    @FXML private Label eNameLabel;

    @FXML private Label discountLabel;
    @FXML private Label totalPriceLabel;
    @FXML private ComboBox<String> paymentMethodCB;
    @FXML private ComboBox<String> warrantyCB;

    @FXML private Label cNameLabel;
    @FXML private Label cPhoneLabel;
    @FXML private Label cAddressLabel;
    @FXML private Label cCityLabel;

    @FXML private Label vUsedLabel;
    @FXML private Label vMakeLabel;
    @FXML private Label vModelLabel;
    @FXML private Label vYearLabel;
    @FXML private Label vColorLabel;
    @FXML private Label vPriceLabel;

    @FXML public void saveInvoice(ActionEvent event) {

        try {

            String sql;

            Connection connection = DataHandler.getConnection();
            Statement statement = connection.createStatement();

            sql = String.format("INSERT INTO `INVOICES` (`CUSTOMER_ID`, `EMPLOYEE_ID`, `VEHICLE_ID`, `DATE`) VALUES (%s, %s, %s, %s)",
                    DataHandler.getWrappedValue(Session.selectedCustomer.getID()),
                    DataHandler.getWrappedValue(Session.sessionUser.getID()),
                    DataHandler.getWrappedValue(Session.selectedVehicle.getID()),
                    DataHandler.getWrappedValue(dateString));
            statement.executeUpdate(sql);

        } catch (Exception e) {

            System.out.println("Empty fields!");

        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Get today's date
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        dateString = dateFormat.format(date);
        dateLabel.setText(dateString);

        // Display salesman info
        Employee employee = Session.sessionUser;
        eNameLabel.setText(employee.getFirstName() + " " + employee.getLastName());

        paymentMethodCB.getItems().add("Cash");
        paymentMethodCB.getItems().add("Credit");
        paymentMethodCB.getItems().add("Finance");

        warrantyCB.getItems().add("$0");
        warrantyCB.getItems().add("$3000");
        warrantyCB.getItems().add("$5000");

        // Display customer info
        Customer customer = Session.selectedCustomer;
        cNameLabel.setText(customer.getFirstName() + " " + customer.getLastName());
        cPhoneLabel.setText(customer.getPhone());
        cAddressLabel.setText(customer.getAddress());
        cAddressLabel.setText(customer.getAddress());
        cCityLabel.setText(customer.getCity());

        // Display vehicle info
        Vehicle vehicle = Session.selectedVehicle;
        vUsedLabel.setText(vehicle.getUsed());
        vMakeLabel.setText(vehicle.getMake());
        vModelLabel.setText(vehicle.getModel());
        vYearLabel.setText(vehicle.getYear());
        vColorLabel.setText(vehicle.getColor());
        vPriceLabel.setText("$" + vehicle.getPrice());

        warrantyCB.setOnAction(e -> {

            double vehiclePrice = Double.parseDouble(vehicle.getPrice());
            double warrantyPrice = Double.parseDouble(warrantyCB.getSelectionModel().getSelectedItem().substring(1));
            double totalPrice = vehiclePrice + warrantyPrice;

            totalPriceLabel.setText("$" + String.valueOf(totalPrice));

            if (totalPrice > 50000) {
                discountLabel.setText("One year of free car washes");
            } else {
                discountLabel.setText("No discounts");
            }

        });

    }

}
