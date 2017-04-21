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

            boolean allNonEmpty;
            allNonEmpty = !fNameTF.isEmpty();
            allNonEmpty = allNonEmpty && !lNameTF.isEmpty();
            allNonEmpty = allNonEmpty && !phoneTF.isEmpty();
            allNonEmpty = allNonEmpty && !emailTF.getText().isEmpty();
            allNonEmpty = allNonEmpty && !addressTF.getText().isEmpty();
            allNonEmpty = allNonEmpty && !cityTF.isEmpty();
            allNonEmpty = allNonEmpty && dateOfBirthDP.getValue() != null;

            if (allNonEmpty) {
                if (phoneTF.getText().length() >= 7 && phoneTF.getText().length() <= 10) {
                    if (emailTF.getText().matches("[A-Za-z0-9]{2,}@[a-z]{3,}\\.[a-z]{3}")) {

                        // Create customer from input fields
                        Customer customer = new Customer(fNameTF, lNameTF, phoneTF, emailTF, addressTF, cityTF, dateOfBirthDP);

                        // If age >= 18, add customer to db
                        Date birthDate = Formatter.parseDate(customer.getDateOfBirth());
                        long timePassed = new Date().getTime() - birthDate.getTime();
                        if (TimeUnit.MILLISECONDS.toDays(timePassed) >= 365 * 18) {
                            Customer.insertEntry(customer);
                            Session.getInstance().reloadCustomers();
                            Session.getInstance().alert("Added Customer!");
                        } else {
                            Session.getInstance().alert("Customer must be at least 18!");
                        }

                    } else {
                        Session.getInstance().alert("Invalid Email!\nFormat: sometext@host.com");
                    }
                } else {
                    Session.getInstance().alert("Invalid Phone!\nMust be between 7 - 10 digits!");
                }
            } else {
                Session.getInstance().alert("Empty fields!");
            }

        } catch (Exception e) {
            Session.getInstance().alert("Error: Contact Admin!");
            e.printStackTrace();
        }

    }

    @Override
    public void init(AlphaController alphaController) {
        this.alphaController = alphaController;
    }
}
