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
    @FXML
    private TableColumn availabilityCol;
    @FXML
    private MenuButton search_filter;
    @FXML
    private MenuItem m1, m2;

    public String menuOption;

    @FXML
    void logout(ActionEvent event) throws IOException {
        Main.lm.logout();
        Main.changeScene(0);
    }

    @FXML
    void search(ActionEvent event) {
        if(menuOption.equals("Author"))
            updateTable(Main.lm.searchBooksByAuthor(search_field.getText(), 0));
        else updateTable(Main.lm.searchBooksByTitle(search_field.getText(), 0));

        search_filter.setText("Search by...");
    }

    @FXML
    void setMenuOption(ActionEvent event) {
        menuOption = ((MenuItem) event.getSource()).getText();
        search_filter.setText(menuOption);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        welcome_msg.setText("Welcome, " + Controller.getUsername());

        //associating the table's column with the corresponding attributes of the book class
        idCol.setCellValueFactory(new PropertyValueFactory<Book, Long>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        authorCol.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        availabilityCol.setCellValueFactory(new PropertyValueFactory<Book, Integer>("numCopies"));

        //filling the table with the list returned by the query
        list_table.setItems(Main.lm.browseBooks(0));
    }

    public void updateTable(ObservableList<Book> list)
    {
        list_table.setItems(list);
    }

}
