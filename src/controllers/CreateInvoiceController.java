package controllers;

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
import java.util.ResourceBundle;

public class CreateInvoiceController implements Initializable {

    @FXML private Label dateLabel;

    @FXML private Label eNameLabel;

    @FXML private Label discountLabel;
    @FXML private Label totalPriceLabel;
    @FXML private ComboBox<String> paymentMethodCB;
    @FXML private ComboBox<USD> warrantyCB;
    @FXML private ComboBox<String> tradeInCB;
    @FXML private TextField tradeInValueTF;

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

    private Customer customer;
    private Employee employee;
    private Vehicle vehicle;
    private String date;
    private String paymentMethod;
    private String warrantyValue;

    @FXML public void saveInvoice(ActionEvent event) {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        try {

            Invoice invoice = new Invoice(
                    customer.getID(),
                    employee.getID(),
                    vehicle.getID(),
                    date,
                    paymentMethod,
                    (!tradeInValueTF.getText().isEmpty()) ? tradeInValueTF.getText() : "0",
                    Formatter.parseNumber(warrantyValue)
            );

            // Save Invoice to db
            Invoice.saveInvoice(invoice);

            // Delete vehicle from db
            Vehicle.removeVehicle();
            Session.getInstance().reloadVehicles();

            // Update employee totalSales
            double currentSales = Double.valueOf(employee.getTotalSales());
            double currentSale = Double.valueOf(Formatter.parseNumber(totalPriceLabel.getText()));
            employee.setTotalSales(String.valueOf(currentSales + currentSale));
            Employee.updateEntry(employee);

            Session.getInstance().alert("Invoice Created!");
            stage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Get today's date
        date = Formatter.getFormattedDate();
        dateLabel.setText(date);

        // Display salesman info
        employee = Session.getInstance().sessionUser;
        eNameLabel.setText(employee.getFirstName() + " " + employee.getLastName());

        paymentMethodCB.getItems().add("Cash");
        paymentMethodCB.getItems().add("Credit");
        paymentMethodCB.getItems().add("Finance");

        tradeInValueTF.setDisable(true);

        paymentMethodCB.setOnAction(e -> paymentMethod = paymentMethodCB.getSelectionModel().getSelectedItem());

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

                    Session.getInstance().alphaController.getSearchVehicleTabController().updateResultSet();
                    Session.getInstance().alphaController.getSearchVehicleTabController().displayResultSet();

                    tradeInValueTF.setDisable(false);

                } else {
                    tradeInValueTF.setDisable(true);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        // Display Customer info
        customer = Session.getInstance().selectedCustomer;
        cNameLabel.setText(customer.getFirstName() + " " + customer.getLastName());
        cPhoneLabel.setText(Formatter.phoneFormatter(customer.getPhone()));
        cAddressLabel.setText(customer.getAddress());
        cAddressLabel.setText(customer.getAddress());
        cCityLabel.setText(customer.getCity());

        // Display Vehicle info
        vehicle = Session.getInstance().selectedVehicle;
        vUsedLabel.setText(vehicle.getUsed());
        vMakeLabel.setText(vehicle.getMake());
        vModelLabel.setText(vehicle.getModel());
        vYearLabel.setText(vehicle.getYear());
        vColorLabel.setText(vehicle.getColor());
        vPriceLabel.setText(Formatter.USDFormatter(Double.parseDouble(vehicle.getPrice())));

        warrantyCB.setOnAction(e -> {

            double vehiclePrice = Double.parseDouble(vehicle.getPrice());
            double warrantyPrice = warrantyCB.getSelectionModel().getSelectedItem().getDoubleValue();

            double totalPrice;
            if (!tradeInValueTF.isDisabled()) {
                double tradeInPrice = Double.valueOf(tradeInValueTF.getText());
                totalPrice = vehiclePrice + warrantyPrice - tradeInPrice;
            } else {
                totalPrice = vehiclePrice + warrantyPrice;
            }
            warrantyValue = String.valueOf(warrantyPrice);
            totalPriceLabel.setText(Formatter.USDFormatter(totalPrice));

            if (totalPrice > 50000) {
                discountLabel.setText("One year of free car washes");
            } else {
                discountLabel.setText("None");
            }

        });

    }

}