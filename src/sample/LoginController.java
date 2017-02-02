package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private int FAILED_ATTEMPTS = 0;
    private final int MAX_ATTEMPTS = 3;

    @FXML TextField userName;
    @FXML TextField passWord;

    @FXML public void cancel(ActionEvent event) throws IOException {
        Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        loginStage.close();
    }

    @FXML public void login(ActionEvent event) throws IOException {

        Parent loginPage = FXMLLoader.load(getClass().getResource("welcome.fxml"));
        Scene loginScene = new Scene(loginPage);
        Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        //get info from input fields

        //compare info with database

        //if valid grant access

        if (userName.getText().equals("Reed")) {
            loginStage.hide();
            loginStage.setScene(loginScene);
            loginStage.show();
        } else {
            FAILED_ATTEMPTS++;
            if (FAILED_ATTEMPTS == MAX_ATTEMPTS) {
                loginStage.close();
            } else {
                System.out.println("Login failed! Attempts remaining: " + (MAX_ATTEMPTS - FAILED_ATTEMPTS));
                userName.setText("");
                passWord.setText("");
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
