package controllers;

import components.NumberOnlyTextField;
import components.TextOnlyTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import util.Customer;
import util.Formatter;
import util.Init;
import util.Session;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class AddCustomerTabController implements Init {

    private AlphaController alphaController;

    @FXML private TextOnlyTextField fNameTF;
    @FXML private TextOnlyTextField lNameTF;
    @FXML private NumberOnlyTextField phoneTF;
    @FXML private TextField emailTF;
    @FXML private TextField addressTF;
    @FXML private TextOnlyTextField cityTF;
    @FXML private DatePicker dateOfBirthDP;

    @FXML public void save(ActionEvent event) {

        try {

            Customer customer = new Customer(fNameTF, lNameTF, phoneTF, emailTF, addressTF, cityTF, dateOfBirthDP);

            Date birthDate = Formatter.parseDate(customer.getDateOfBirth());
            long timePassed = new Date().getTime() - birthDate.getTime();
            if (TimeUnit.MILLISECONDS.toDays(timePassed) >= 365 * 18) {
                Customer.insertEntry(customer);
                Session.getInstance().reloadCustomers();
                Session.getInstance().alert("Added Customer!");
            } else {
                Session.getInstance().alert("Customer must be at least 18!");
            }

        } catch (Exception e) {
            Session.getInstance().alert(e.getMessage());
        }

    }

    @Override
    public void init(AlphaController alphaController) {
        this.alphaController = alphaController;
    }
}
