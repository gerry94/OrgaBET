import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserController extends Controller {
    @FXML
    private CheckBox available_check;
    @FXML
    private Button borrow_but;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        welcome_msg.setText("Welcome, " + Controller.getUsername());

        //blocking table column resize
        idCol.setResizable(false);
        titleCol.setResizable(false);
        authorCol.setResizable(false);
        availabilityCol.setResizable(false);

        //associating the table's column with the corresponding attributes of the book class
        idCol.setCellValueFactory(new PropertyValueFactory<Book, Long>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        authorCol.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        availabilityCol.setCellValueFactory(new PropertyValueFactory<Book, Integer>("numCopies"));

        //filling the table with the list returned by the query
        tableOffset = 0;
        list_table.setItems(Main.lm.browseBooks(tableOffset));

        previous_but.setDisable(true);
        totalPages = ((Main.lm.getNumBooks() + 9)/10);
        currentPage = 1;

        page_count.setText("Page " + currentPage + " of " + totalPages);
    }

    @FXML
    public void search(ActionEvent event) {
        output_field.clear();
        super.resetPageButtons();

        if(menuOption == null || menuOption.equals("Title")) updateTable(Main.lm.searchBooks(0, search_field.getText(), 0));
        else updateTable(Main.lm.searchBooks(1, search_field.getText(), 0));

        search_filter.setText("Title");
    }

    public void updateTable(ObservableList<Book> list)
    {
        list_table.setItems(list);
    }

    @FXML
    public void borrowSelected(ActionEvent ev) {
        Book selectedBook = list_table.getSelectionModel().getSelectedItem();

        if(selectedBook == null) {
            printErrorMessage("No book was selected. Please select a book a retry.");
        }
        else {
            output_field.clear();
            output_field.setText(Main.lm.borrowBook(selectedBook.getId()));
        }
    }

    @FXML
    public void nextPage(ActionEvent ev) {
        super.nextPage();
        updateTable(Main.lm.searchBooks(0, search_field.getText(), tableOffset));
    }

    @FXML
    public void previousPage(ActionEvent ev) {
        super.previousPage();
        updateTable(Main.lm.searchBooks(0, search_field.getText(), tableOffset));
    }
}
