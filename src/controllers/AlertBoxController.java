package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import util.Session;

import java.net.URL;
import java.util.ResourceBundle;

public class AlertBoxController implements Initializable {

    @FXML private Label label;

    @FXML public void accept(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        label.setText(Session.getInstance().getAlertMessage());
    }
}
