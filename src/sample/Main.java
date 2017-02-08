package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    public static String prevPage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Car Dealership");
        //primaryStage.getIcons().add(new Image("sample/icon.png"));
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setResizable(false);
        ///primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setScene(new Scene(root));

        // delete
        primaryStage.setResizable(false);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
