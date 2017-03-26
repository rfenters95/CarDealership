package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import util.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

            // Get Customer, Employee, and Vehicle info from db matching those from Invoice
            Invoice invoice = Session.selectedInvoice;
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
            dateLabel.setText(Session.selectedInvoice.getDate());

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


        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}
