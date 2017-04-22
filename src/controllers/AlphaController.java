package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import util.Session;

import java.net.URL;
import java.util.ResourceBundle;

/*
*  AlphaController
*  Author: Reed Fenters
*
*  Central control object enables interController communication
* */
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


        // Determines privilege of user, if admin allow editing of employees
        if (!Session.getInstance().sessionUser.isAdmin()) {
            employeeTab.setDisable(true);
        }

        // Personalize session tab to user
        userSessionInfoTab.setText(Session.getInstance().sessionUser.getFirstName() + " " + Session.getInstance().sessionUser.getLastName());

        // Add sub-controllers to AlphaController
        addCustomerTabController.init(this);
        addEmployeeTabController.init(this);
        addVehicleTabController.init(this);
        searchCustomerTabController.init(this);
        searchVehicleTabController.init(this);
        searchEmployeeTabController.init(this);
        sessionTabController.init(this);

        // Give access to AlphaController to Session
        Session.getInstance().alphaController = this;

    }

    // Getter methods
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
