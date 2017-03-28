package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import util.Customer;
import util.Init;
import util.Session;

public class AddCustomerTabController implements Init {

    private AlphaController alphaController;

    @FXML private TextField fNameTF;
    @FXML private TextField lNameTF;
    @FXML private TextField phoneTF;
    @FXML private TextField emailTF;
    @FXML private TextField addressTF;
    @FXML private TextField cityTF;
    @FXML private DatePicker dateOfBirthDP;

    @FXML public void save(ActionEvent event) {

        try {

            Customer customer = new Customer(fNameTF, lNameTF, phoneTF, emailTF, addressTF, cityTF, dateOfBirthDP);
            Customer.insertEntry(customer);
            alphaController.getSearchCustomerTabController().updateResultSet();
            alphaController.getSearchCustomerTabController().displayResultSet();
            Session.alert("Added Customer!");

        } catch (Exception e) {
            Session.alert(e.getMessage());
        }

    }

    @Override
    public void init(AlphaController alphaController) {
        this.alphaController = alphaController;
    }
}
