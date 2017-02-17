package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AlertBoxController implements Initializable {

    @FXML Label messageLabel;

    @FXML
    public void accept(ActionEvent event) throws IOException {
        Parent register_page = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene register_scene = new Scene(register_page);
        Stage register_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        register_stage.hide();
        register_stage.setScene(register_scene);
        register_stage.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        messageLabel.setText(Main.getAlertMessage());
    }
}
