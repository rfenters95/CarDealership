package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import util.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

public class CreateInvoiceController implements Initializable {

    @FXML private Label dateLabel;
    private String dateString;

    @FXML private Label eNameLabel;

    @FXML private Label discountLabel;
    @FXML private Label totalPriceLabel;
    @FXML private ComboBox<String> paymentMethodCB;
    @FXML private ComboBox<USD> warrantyCB;
    @FXML private ComboBox<String> tradeInCB;

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

            Connection connection = DataHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `INVOICES` (`CUSTOMER_ID`, `EMPLOYEE_ID`, `VEHICLE_ID`, `DATE`) VALUES (?, ?, ?, ?)");

            preparedStatement.setString(1, Session.selectedCustomer.getID());
            preparedStatement.setString(2, Session.sessionUser.getID());
            preparedStatement.setString(3, Session.selectedVehicle.getID());
            preparedStatement.setString(4, dateString);

            preparedStatement.executeUpdate();

        } catch (Exception e) {

            System.out.println("Empty fields!");

        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Get today's date
        dateString = Formatter.getFormattedDate();
        dateLabel.setText(dateString);

        // Display salesman info
        Employee employee = Session.sessionUser;
        eNameLabel.setText(employee.getFirstName() + " " + employee.getLastName());

        paymentMethodCB.getItems().add("Cash");
        paymentMethodCB.getItems().add("Credit");
        paymentMethodCB.getItems().add("Finance");

        warrantyCB.getItems().add(new USD(0).setStringValue("$0"));
        warrantyCB.getItems().add(new USD(3000));
        warrantyCB.getItems().add(new USD(5000));

        warrantyCB.setCellFactory(new Callback<ListView<USD>, ListCell<USD>>() {
            @Override
            public ListCell<USD> call(ListView<USD> param) {
                return new ListCell<USD>() {
                    @Override
                    protected void updateItem(USD item, boolean empty) {
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

        tradeInCB.getItems().add("No");
        tradeInCB.getItems().add("Yes");

        tradeInCB.setOnAction(event -> {

            try {

                if (tradeInCB.getSelectionModel().getSelectedItem().equals("Yes")) {

                    Stage newStage = new Stage();
                    newStage.initModality(Modality.APPLICATION_MODAL);
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    newStage.setTitle("Add Trade-In Vehicle");

                    fxmlLoader.setLocation(getClass().getResource("../views/AddTradeInVehicle.fxml"));
                    Parent newResource = fxmlLoader.load();
                    Scene newScene = new Scene(newResource);
                    newStage.setScene(newScene);
                    newStage.setResizable(false);
                    newStage.showAndWait();

                    Session.alphaController.getSearchVehicleTabController().updateResultSet();
                    Session.alphaController.getSearchVehicleTabController().displayResultSet();

                }

            } catch (IOException e) {

                e.printStackTrace();

            }

        });

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
        vPriceLabel.setText(Formatter.USDFormatter(Double.parseDouble(vehicle.getPrice())));

        warrantyCB.setOnAction(e -> {

            double vehiclePrice = Double.parseDouble(vehicle.getPrice());
            double warrantyPrice = warrantyCB.getSelectionModel().getSelectedItem().getDoubleValue();
            double totalPrice = vehiclePrice + warrantyPrice;

            totalPriceLabel.setText(Formatter.USDFormatter(totalPrice));

            if (totalPrice > 50000) {
                discountLabel.setText("One year of free car washes");
            } else {
                discountLabel.setText("None");
            }

        });

    }

}