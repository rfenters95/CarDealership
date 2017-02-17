package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import util.Customer;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AlphaController implements Initializable {

    @FXML private TextField customerFirstName;
    @FXML private TextField customerLastName;
    @FXML private TextField customerEmail;
    @FXML private TextField customerPhone;
    @FXML private TextField customerAddress;
    @FXML private DatePicker customerDOB; //getValue() format=yyyy-mm-dd
    @FXML private ComboBox<String> stateComboBox;

    @FXML private ListView<Customer> customerSearchListView;
    @FXML private ListView<Customer> vehicleSearchListView;

    @FXML private ComboBox<String> vehicleCondition;

    @FXML
    public void save(ActionEvent event) {
        Customer customer = new Customer(customerFirstName.getText(), customerLastName.getText(), customerPhone.getText(), customerEmail.getText(), customerAddress.getText(), null);
        System.out.println("Saved: " + customer.getLastName() + ", " + customer.getFirstName());
        System.out.println(customerDOB.getValue());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String[] states = {"AK","AL","AR","AZ","CA","CO","CT","DC","DE","FL","GA","GU","HI","IA","ID", "IL","IN","KS","KY","LA","MA","MD","ME","MH","MI","MN","MO","MS","MT","NC","ND","NE","NH","NJ","NM","NV","NY", "OH","OK","OR","PA","PR","PW","RI","SC","SD","TN","TX","UT","VA","VI","VT","WA","WI","WV","WY"};
        stateComboBox.getItems().addAll(states);

        customerDOB.setValue(LocalDate.now());

        String[] vehicleConditions = {"New", "Used"};
        vehicleCondition.getItems().addAll(vehicleConditions);

        //populate customer search list view with all customers
        //connect to db & get all customers
        customerSearchListView.setCellFactory(new Callback<ListView<Customer>, ListCell<Customer>>() {
            @Override
            public ListCell<Customer> call(ListView<Customer> param) {
                ListCell<Customer> cell = new ListCell<Customer>() {
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
                return cell;
            }
        });

        //populate vehicle search list view with all vehicle
        //connect to db & get all vehicles
        /*
        vehicleSearchListView.setCellFactory(new Callback<ListView<Vehicle>, ListCell<Vehicle>>() {
            @Override
            public ListCell<Vehicle> call(ListView<Vehicle> param) {
                ListCell<Customer> cell = new ListCell<Vehicle>() {
                    //private ImageView imageView = new ImageView(new File("res/jmGhosts.jpg").toURI().toString());
                    @Override
                    protected void updateItem(Vehicle item, boolean empty) {
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
                return cell;
            }
        });
        */
    }
}
