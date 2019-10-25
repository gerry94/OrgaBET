package sample;

import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.event.*;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private DBManager dbm = new DBManager(3306, "test", "password", "lsdb");


    @FXML
    private AnchorPane output_txt;
    @FXML
    private TextField login_code;
    @FXML
    private Button login_but;
    @FXML
    private Label output_text;

    @FXML
    void login(ActionEvent event) {
        String username = dbm.login(login_code.getText());
        if (username == "") {
            output_text.setTextFill(Color.RED);
            output_text.setText("ERROR: wrong id. Please check your personal code and retry.");
        } else {
            output_text.setTextFill(Color.GREEN);
            output_text.setText("Login successful. Welcome " + username + ".");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbm.start();
    }
}