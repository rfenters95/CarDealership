package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AlphaController implements Initializable {

    @FXML private TextField customerFirstName;
    @FXML private TextField customerLastName;
    @FXML private TextField customerEmail;
    @FXML private TextField customerPhone;
    @FXML private TextField customerAddress;

    @FXML
    public void save(ActionEvent event) {
        System.out.println("Saved: " + customerLastName.getText() + ", " + customerFirstName.getText());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
