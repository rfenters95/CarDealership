import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.DataHandler;
import util.Employee;
import util.Session;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML private TextField username;
    @FXML private TextField password;
    @FXML private Button login;
    @FXML private VBox root;

    @FXML
    public void login(ActionEvent event) {

        try {

            String sql;
            ResultSet resultSet;
            boolean hasResults;
            final String user = username.getText();
            final String pass = password.getText();

            Connection connection = DataHandler.getConnection();
            Statement statement = connection.createStatement();

            sql = "SELECT * FROM USERS WHERE USERNAME=" + DataHandler.getWrappedValue(user) + " AND PASSWORD=" + DataHandler.getWrappedValue(pass);
            resultSet = statement.executeQuery(sql);
            hasResults = resultSet.next();

            if (hasResults) {
                String EmployeeID = resultSet.getString(1);

                sql = "SELECT * FROM EMPLOYEES WHERE ID=" + DataHandler.getWrappedValue(EmployeeID);
                resultSet = statement.executeQuery(sql);
                hasResults = resultSet.next();

                if (hasResults) {

                    Session.employee = new Employee(resultSet);

                    //if Session.employee.isAdmin

                    loadResource(event, "views/Alpha.fxml");

                    //else

                } else {
                    System.out.println("Could not find employee matching that ID");
                }
            } else {
                System.out.println("Bad login combo!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadResource(ActionEvent event, String resourcePath) throws IOException {
        Parent register_page = FXMLLoader.load(getClass().getResource(resourcePath));
        Scene register_scene = new Scene(register_page);
        Stage register_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        register_stage.hide();
        register_stage.setScene(register_scene);
        register_stage.setResizable(true);
        register_stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        root.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case ENTER:
                    login.fire();
                    break;
            }
        });
    }
}
