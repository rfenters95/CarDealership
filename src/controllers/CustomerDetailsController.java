package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerDetailsController implements Initializable {

    public CustomerDetailsController() {
    }

    @FXML ComboBox<String> invoiceCB;
    @FXML public void fetch(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
