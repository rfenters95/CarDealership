package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import util.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class ViewInvoiceController implements Initializable {

    @FXML private Label dateLabel;

    @FXML private Label eNameLabel;

    @FXML private Label discountLabel; // derived from date + totalPrice
    @FXML private Label totalPriceLabel; // derived from vPrice + warranty
    @FXML private Label paymentMethodLabel; // stored in Invoice
    @FXML private Label tradeInValueLabel; // stored in Invoice
    @FXML private Label warrantyLabel; // stored in Invoice

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {

            Invoice invoice = Session.getInstance().selectedInvoice;

            Connection connection = DataHandler.getConnection();

            // Get Customer info from db
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM CUSTOMERS WHERE ID=?");
            preparedStatement.setString(1, invoice.getCustomerID());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            // Create Customer from resultSet
            Customer customer = new Customer(resultSet);

            // Get Employee info from db
            preparedStatement = connection.prepareStatement("SELECT * FROM EMPLOYEES WHERE ID=?");
            preparedStatement.setString(1, invoice.getEmployeeID());
            resultSet = preparedStatement.executeQuery();
            resultSet.next();

            // Create Employee from resultSet
            Employee employee = new Employee(resultSet);

            // Get Vehicle info from db
            preparedStatement = connection.prepareStatement("SELECT * FROM VEHICLES WHERE ID=?");
            preparedStatement.setString(1, invoice.getVehicleID());
            resultSet = preparedStatement.executeQuery();
            resultSet.next();

            // Create Vehicle from resultSet
            Vehicle vehicle = new Vehicle(resultSet);

            // Get today's date
            dateLabel.setText(Session.getInstance().selectedInvoice.getDate());

            // Display Employee info
            eNameLabel.setText(employee.getFirstName() + " " + employee.getLastName());

            // Display Customer info
            cNameLabel.setText(customer.getFirstName() + " " + customer.getLastName());
            cPhoneLabel.setText(Formatter.phoneFormatter(customer.getPhone()));
            cAddressLabel.setText(customer.getAddress());
            cAddressLabel.setText(customer.getAddress());
            cCityLabel.setText(customer.getCity());

            // Display Vehicle info
            vUsedLabel.setText(vehicle.getUsed());
            vMakeLabel.setText(vehicle.getMake());
            vModelLabel.setText(vehicle.getModel());
            vYearLabel.setText(vehicle.getYear());
            vColorLabel.setText(vehicle.getColor());
            vPriceLabel.setText(Formatter.USDFormatter(vehicle.getPrice()));

            paymentMethodLabel.setText(invoice.getPaymentMethod());
            tradeInValueLabel.setText(Formatter.USDFormatter(invoice.getTradeInValue()));
            warrantyLabel.setText(Formatter.USDFormatter(invoice.getWarrantyValue()));

            double totalPrice = Double.valueOf(vehicle.getPrice()) + Double.valueOf(invoice.getWarrantyValue());
            totalPriceLabel.setText(Formatter.USDFormatter(String.valueOf(totalPrice)));

            Date discountStartDate = Formatter.parseDate(invoice.getDate());
            long timePassed = new Date().getTime() - discountStartDate.getTime();
            if (TimeUnit.MILLISECONDS.toDays(timePassed) > 365) {
                discountLabel.setText("Discount expired!");
            } else {
                discountLabel.setText("One year of free cash washes");
            }

        } catch (Exception e) {
            Session.getInstance().alert("Error: Contact Admin!");
            e.printStackTrace();
        }

    }

}
