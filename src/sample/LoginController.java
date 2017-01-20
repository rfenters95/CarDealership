package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML public void doSomething(ActionEvent event) throws IOException {
        Parent loginPage = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene loginScene = new Scene(loginPage);
        Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        loginStage.hide();
        loginStage.setScene(loginScene);
        loginStage.show();
    }

}
