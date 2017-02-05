package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class VehicleSearchController {

    //TODO vehicle_search.fxml controller must be changed, so that it points here
    // in mark 2

    @FXML public void cancel(ActionEvent event) throws IOException {
        Parent register_page = FXMLLoader.load(getClass().getResource("welcome.fxml"));
        Scene register_scene = new Scene(register_page);
        Stage register_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        register_stage.hide();
        register_stage.setScene(register_scene);
        register_stage.show();
    }

}
