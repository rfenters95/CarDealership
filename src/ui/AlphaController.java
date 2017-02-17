package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
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
    }
}
