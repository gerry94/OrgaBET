import javafx.application.Platform;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.text.Text;

import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class LibrarianUserController extends Controller {
    @FXML
    private TableView<User> user_table;
    @FXML
    private TableColumn<User, String> idCol;
    @FXML
    private TableColumn<User, String> nameCol;
    @FXML
    private TableColumn<User, String> surnameCol;

    @FXML
    private Button add_button;

    @FXML
    private TextField userid_field;
    @FXML
    private TextField surname_field;
    @FXML
    private TextField name_field;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        welcome_msg.setText("Welcome " + Controller.getUsername());

        //associating the table's column with the corresponding attributes of the user class
        idCol.setCellValueFactory(new PropertyValueFactory<User, String>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        surnameCol.setCellValueFactory(new PropertyValueFactory<User, String>("surname"));

        //filling the table with the list returned by the query
        user_table.setItems(Main.lm.browseUsers(0));
    }
}
