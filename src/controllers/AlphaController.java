package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import util.Session;

import java.net.URL;
import java.util.ResourceBundle;

public class AlphaController implements Initializable {

    @FXML private AddCustomerTabController addCustomerTabController;
    @FXML private AddVehicleTabController addVehicleTabController;

    @FXML private SearchCustomerTabController searchCustomerTabController;
    @FXML private SearchVehicleTabController searchVehicleTabController;
    @FXML private AddTradeInVehicleController addTradeInVehicleController;

    @FXML private AddEmployeeTabController addEmployeeTabController;
    @FXML private SearchEmployeeTabController searchEmployeeTabController;

    @FXML private SessionTabController sessionTabController;

    @FXML private Tab userSessionInfoTab;
    @FXML private Tab employeeTab;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        if (!Session.getInstance().sessionUser.isAdmin()) {
            employeeTab.setDisable(true);
        }

        userSessionInfoTab.setText(Session.getInstance().sessionUser.getFirstName() + " " + Session.getInstance().sessionUser.getLastName());

        addCustomerTabController.init(this);
        addEmployeeTabController.init(this);
        addVehicleTabController.init(this);
        searchCustomerTabController.init(this);
        searchVehicleTabController.init(this);
        searchEmployeeTabController.init(this);
        sessionTabController.init(this);

        Session.getInstance().alphaController = this;

    }

    public Tab getUserSessionInfoTab() {
        return userSessionInfoTab;
    }

    public AddCustomerTabController getAddCustomerTabController() {
        return addCustomerTabController;
    }

    public AddVehicleTabController getAddVehicleTabController() {
        return addVehicleTabController;
    }

    public SearchCustomerTabController getSearchCustomerTabController() {
        return searchCustomerTabController;
    }

    public SearchVehicleTabController getSearchVehicleTabController() {
        return searchVehicleTabController;
    }

    public AddEmployeeTabController getAddEmployeeTabController() {
        return addEmployeeTabController;
    }

    public SearchEmployeeTabController getSearchEmployeeTabController() {
        return searchEmployeeTabController;
    }

    public SessionTabController getSessionTabController() {
        return sessionTabController;
    }
}
