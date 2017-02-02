package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerSearchController implements Initializable {

    @FXML
    ListView<String> listView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //by default show all
        ObservableList<String> items = FXCollections.observableArrayList("Reed Fenters", "Krunal Patel", "Skyler James");
        listView.setItems(items);
    }
}
