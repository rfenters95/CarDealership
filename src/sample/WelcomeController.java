package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable {

    private final String CURRENT_FXML = "welcome.fxml";
    private final String CUSTOMER_REGISTRATION_FXML = "customer_registration.fxml";
    private final String CUSTOMER_SEARCH_FXML = "customer_registration.fxml";
    private final String LOGIN_FXML = "login.fxml";

    @FXML public void option1(ActionEvent event) throws IOException {
        Parent register_page = FXMLLoader.load(getClass().getResource(CUSTOMER_REGISTRATION_FXML));
        Scene register_scene = new Scene(register_page);
        Stage register_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        register_stage.hide();
        register_stage.setScene(register_scene);
        register_stage.show();
    }

    @FXML public void option2(ActionEvent event) throws IOException {
        Parent register_page = FXMLLoader.load(getClass().getResource(CUSTOMER_REGISTRATION_FXML));
        Scene register_scene = new Scene(register_page);
        Stage register_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        register_stage.hide();
        register_stage.setScene(register_scene);
        register_stage.show();
    }

    @FXML public void option3(ActionEvent event) throws IOException {
        Parent register_page = FXMLLoader.load(getClass().getResource(CUSTOMER_SEARCH_FXML));
        Scene register_scene = new Scene(register_page);
        Stage register_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        register_stage.hide();
        register_stage.setScene(register_scene);
        register_stage.show();
    }

    @FXML public void option4(ActionEvent event) throws IOException {
        Parent register_page = FXMLLoader.load(getClass().getResource(CUSTOMER_REGISTRATION_FXML));
        Scene register_scene = new Scene(register_page);
        Stage register_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        register_stage.hide();
        register_stage.setScene(register_scene);
        register_stage.show();
    }

    @FXML public void option5(ActionEvent event) throws IOException {

        //logout of database

        Parent register_page = FXMLLoader.load(getClass().getResource(LOGIN_FXML));
        Scene register_scene = new Scene(register_page);
        Stage register_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        register_stage.hide();
        register_stage.setScene(register_scene);
        register_stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.prevPage = CURRENT_FXML;
    }
}
