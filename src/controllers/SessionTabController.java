package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.DataHandler;
import util.Init;
import util.Session;

public class SessionTabController implements Init {

    private AlphaController alphaController;

    @FXML private Label employeeLabel;
    @FXML private Label customerLabel;
    @FXML private Label vehicleLabel;
    @FXML private Button logoutButton;
    @FXML private Button createInvoiceButton;


    private void updateSessionInfo() {

        boolean allSet = true;

        if (Session.getInstance().sessionUser != null) {
            employeeLabel.setText(Session.getInstance().sessionUser.toString());
        } else {
            allSet = false;
        }

        if (Session.getInstance().selectedCustomer != null) {
            customerLabel.setText(Session.getInstance().selectedCustomer.toString());
        } else {
            allSet = false;
        }

        if (Session.getInstance().selectedVehicle != null) {
            vehicleLabel.setText(Session.getInstance().selectedVehicle.toString());
        } else {
            allSet = false;
        }

        if (allSet) {
            createInvoiceButton.setDisable(false);
        } else {
            createInvoiceButton.setDisable(true);
        }

    }

    @FXML public void createInvoice(ActionEvent event) throws Exception {

        Stage newStage = new Stage();
        newStage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader fxmlLoader = new FXMLLoader();
        newStage.setTitle("Create Invoice");

        fxmlLoader.setLocation(getClass().getResource("../views/CreateInvoice.fxml"));
        Parent newResource = fxmlLoader.load();
        Scene newScene = new Scene(newResource);
        newStage.setScene(newScene);
        newStage.setResizable(false);
        newStage.showAndWait();

    }

    @FXML public void hoverIn(MouseEvent event) {
        logoutButton.setStyle("-fx-background-color:red;-fx-text-fill:white");
    }
    @FXML public void hoverOut(MouseEvent event) {
        logoutButton.setStyle("-fx-background-color:crimson;-fx-text-fill:white");
    }
    @FXML public void logout(ActionEvent event) {
        try {
            DataHandler.killConnection();
            Session.getInstance().clearSession();
            Parent register_page = FXMLLoader.load(getClass().getResource("../views/Login.fxml"));
            Scene register_scene = new Scene(register_page);
            Stage register_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            register_stage.hide();
            register_stage.setScene(register_scene);
            register_stage.show();
        } catch (Exception e) {
            Session.getInstance().alert("Error: Contact Admin!");
            e.printStackTrace();
        }
    }

    @Override
    public void init(AlphaController alphaController) {

        this.alphaController = alphaController;
        updateSessionInfo();

        Tab tab = alphaController.getUserSessionInfoTab();
        tab.setOnSelectionChanged(event -> {
            updateSessionInfo();
        });

        if (!Session.getInstance().sessionUser.getJobTitle().equals("Sales")) {
            createInvoiceButton.setVisible(false);
        }

    }
}
