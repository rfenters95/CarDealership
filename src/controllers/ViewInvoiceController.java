package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import util.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ViewInvoiceController implements Initializable {

    @FXML private Label dateLabel;

    @FXML private Label eNameLabel;

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

            Invoice invoice = Session.selectedInvoice;
            Customer customer;
            Employee employee;
            Vehicle vehicle;

            String sql;
            ResultSet resultSet;
            Connection connection = DataHandler.getConnection();
            Statement statement = connection.createStatement();

            sql = String.format("SELECT * FROM CUSTOMERS WHERE ID=%s", DataHandler.getWrappedValue(invoice.getCustomerID()));
            resultSet = statement.executeQuery(sql);
            resultSet.next();

            customer = new Customer(resultSet);

            sql = String.format("SELECT * FROM EMPLOYEES WHERE ID=%s", DataHandler.getWrappedValue(invoice.getEmployeeID()));
            resultSet = statement.executeQuery(sql);
            resultSet.next();

            employee = new Employee(resultSet);

            sql = String.format("SELECT * FROM VEHICLES WHERE ID=%s", DataHandler.getWrappedValue(invoice.getVehicleID()));
            resultSet = statement.executeQuery(sql);
            resultSet.next();

            vehicle = new Vehicle(resultSet);

            // Get today's date
            dateLabel.setText(Session.selectedInvoice.getDate());

            // Display salesman info
            eNameLabel.setText(employee.getFirstName() + " " + employee.getLastName());

            // Display customer info
            cNameLabel.setText(customer.getFirstName() + " " + customer.getLastName());
            cPhoneLabel.setText(customer.getPhone());
            cAddressLabel.setText(customer.getAddress());
            cAddressLabel.setText(customer.getAddress());
            cCityLabel.setText(customer.getCity());

            // Display vehicle info
            vUsedLabel.setText(vehicle.getUsed());
            vMakeLabel.setText(vehicle.getMake());
            vModelLabel.setText(vehicle.getModel());
            vYearLabel.setText(vehicle.getYear());
            vColorLabel.setText(vehicle.getColor());
            vPriceLabel.setText(vehicle.getPrice());


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
