import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    @FXML
    private Label welcome_msg;
    @FXML
    private Button logout_but;
    @FXML
    private TextField search_field;
    @FXML
    private CheckBox available_check;
    @FXML
    private Button search_but;
    @FXML
    private Button borrow_but;
    @FXML
    private TextArea output_field;
    @FXML
    private TableView<Book> list_table;
    @FXML
    private TableColumn idCol;
    @FXML
    private TableColumn titleCol;
    @FXML
    private TableColumn authorCol;
    //@FXML
    //private TableColumn categoryCol;
    @FXML
    private TableColumn availabilityCol;
    @FXML
    private MenuButton search_filter;

    @FXML
    void logout(ActionEvent event) throws IOException {

        Main.lm.logout();
        Main.changeScene(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        welcome_msg.setText("Welcome " + Controller.getUsername());

        //associating the table's column with the corresponding attributes of the book class
        idCol.setCellValueFactory(new PropertyValueFactory<Book, Long>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        authorCol.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        availabilityCol.setCellValueFactory(new PropertyValueFactory<Book, Integer>("numCopies"));

        //filling the table with the list returned by the query
        list_table.setItems(Main.lm.browseBooks(0));
    }

}
