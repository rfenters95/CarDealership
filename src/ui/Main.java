package ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Main extends Application {

    private static String alertMessage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("CarDealership");
        primaryStage.setScene(new Scene(root));
        primaryStage.getIcons().add(new Image(new File("res/icon.png").toURI().toString()));
        primaryStage.show();
    }

    public static String getAlertMessage() {
        return alertMessage;
    }

    public static void alertUser(ActionEvent event, Object controller, String alertMessage) throws IOException {
        Main.alertMessage = alertMessage;
        Parent register_page = FXMLLoader.load(controller.getClass().getResource("alert_box.fxml"));
        Scene register_scene = new Scene(register_page);
        Stage register_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        register_stage.hide();
        register_stage.setScene(register_scene);
        register_stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
