package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Callback;
import util.Customer;
import util.IllegalEventMonitor;
import util.Vehicle;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AlphaController implements Initializable {

    public static Customer selectedCustomer;
    public static Vehicle selectedVehicle;

    @FXML private Label sessionEmployeeLabel;
    @FXML private Label logoutLabel;

    @FXML private TextField customerFirstName;
    @FXML private TextField customerLastName;
    @FXML private TextField customerEmail;
    @FXML private TextField customerPhone;
    @FXML private TextField customerAddress;
    @FXML private DatePicker customerDOB; //getValue() format=yyyy-mm-dd


    @FXML private TextField vehicleMake;
    @FXML private TextField vehicleModel;
    @FXML private TextField vehicleYear;
    @FXML private TextField vehicleColor;
    @FXML private TextField vehicleType;
    @FXML private TextField vehiclePrice;
    @FXML private ComboBox<String> vehicleCondition;

    @FXML ImageView imageView;

    @FXML private TabPane tabPane;
    @FXML private Tab customerTab;
    @FXML private Tab vehicleTab;
    @FXML private Tab invoiceTab;

    @FXML private ListView<Customer> customerSearchListView;
    @FXML private ListView<Vehicle> vehicleSearchListView;

    @FXML
    public void save(ActionEvent event) {
        try {
            Customer customer = new Customer(customerFirstName, customerLastName, customerPhone, customerEmail, customerAddress, customerDOB);
            System.out.println("Saved: " + customer.getLastName() + ", " + customer.getFirstName());
            customerSearchListView.getItems().add(customer);
        } catch (IllegalArgumentException e) {
            System.out.println("IllegalArgumentException!");
        }
    }

    @FXML
    public void customerSelected(ActionEvent event) {
        selectedCustomer = customerSearchListView.getSelectionModel().getSelectedItem();
        System.out.println(selectedCustomer.getFirstName() + " selected!");
    }

    @FXML
    public void searchCustomer(ActionEvent event) {
    }

    @FXML
    public void addVehicle(ActionEvent event) {
        try {
            Vehicle vehicle = new Vehicle(vehicleMake, vehicleModel, vehicleYear, vehicleColor, vehicleType, vehiclePrice, vehicleCondition);
            System.out.println("Added: " + vehicle.getMake() + " " + vehicle.getModel());
            vehicleSearchListView.getItems().add(vehicle);
        } catch (Exception e) {
            System.out.println("Incomplete Form Exception!");
        }
    }

    @FXML
    public void vehicleSelected(ActionEvent event) throws IOException {
        selectedVehicle = vehicleSearchListView.getSelectionModel().getSelectedItem();
        String message = selectedCustomer.getFirstName() + " " + selectedVehicle.getModel();
        //Main.alertUser(event, this, message, "alpha.fxml");
        tabPane.getSelectionModel().select(invoiceTab);
    }

    @FXML
    public void searchVehicle(ActionEvent event) throws IOException {
    }

    @FXML
    public void logoutHoverEnter(MouseEvent event) {
        logoutLabel.setTextFill(Paint.valueOf("Red"));
    }

    @FXML
    public void logoutHoverExit(MouseEvent event) {
        logoutLabel.setTextFill(Paint.valueOf("Blue"));
    }

    @FXML
    public void logout(MouseEvent event) throws IOException {
        Parent register_page = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene register_scene = new Scene(register_page);
        Stage register_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        register_stage.hide();
        register_stage.setScene(register_scene);
        register_stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //create custom text fields to auto implement this
        customerFirstName.textProperty().addListener(new IllegalEventMonitor(customerFirstName));
        customerLastName.textProperty().addListener(new IllegalEventMonitor(customerLastName));

        sessionEmployeeLabel.setText("Hello, " + Main.sessionEmployee);
        logoutLabel.setTextFill(Paint.valueOf("Blue"));

        customerDOB.setValue(LocalDate.now());

        String[] vehicleConditions = {"New", "Used"};
        vehicleCondition.getItems().addAll(vehicleConditions);

        //populate customer search list view with all customers
        //connect to db & get all customers
        customerSearchListView.setCellFactory(new Callback<ListView<Customer>, ListCell<Customer>>() {
            @Override
            public ListCell<Customer> call(ListView<Customer> param) {
                return new ListCell<Customer>() {
                    //private ImageView imageView = new ImageView(new File("res/jmGhosts.jpg").toURI().toString());
                    @Override
                    protected void updateItem(Customer item, boolean empty) {
                        super.updateItem(item, empty);
                        if(!empty && item != null) {
                            setText(item.getFirstName() + " " + item.getLastName());
                            //setGraphic(imageView);
                        } else {
                            setText("");
                            setGraphic(null);
                        }
                    }
                };
            }
        });

        //populate vehicle search list view with all vehicle
        //connect to db & get all vehicles
        vehicleSearchListView.setCellFactory(new Callback<ListView<Vehicle>, ListCell<Vehicle>>() {
            @Override
            public ListCell<Vehicle> call(ListView<Vehicle> param) {
                return new ListCell<Vehicle>() {
                    //private ImageView imageView = new ImageView(new File("res/jmGhosts.jpg").toURI().toString());
                    @Override
                    protected void updateItem(Vehicle item, boolean empty) {
                        super.updateItem(item, empty);
                        if(!empty && item != null) {
                            setText(item.getMake() + " " + item.getModel());
                            //setGraphic(imageView);
                        } else {
                            setText("");
                            setGraphic(null);
                        }
                    }
                };
            }
        });
    }
}
