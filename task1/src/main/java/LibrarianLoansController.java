import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.cell.*;
import javafx.scene.control.*;

public class LibrarianLoansController extends Controller {
    @FXML
    private Label currentUser;
    @FXML
    private TableView<Book> loan_table, return_table;
    @FXML
    private TableColumn loanTitleCol, loanBookIdCol;
    @FXML
    private TableColumn returnTitleCol, returnBookIdCol;
    @FXML
    private Button loan_but, return_but;

    private String userId = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        welcome_msg.setText("Welcome " + getUsername());
        currentUser.setText("Current User: < ? >");

        loanTitleCol.setResizable(false);
        loanBookIdCol.setResizable(false);
        returnTitleCol.setResizable(false);
        returnBookIdCol.setResizable(false);

        returnBookIdCol.setCellValueFactory(new PropertyValueFactory<Book, Long>("id"));
        loanBookIdCol.setCellValueFactory(new PropertyValueFactory<Book, Long>("id"));
        returnTitleCol.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        loanTitleCol.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
    }

    public void resetFields() {
        userId = "";
        currentUser.setText("Current User: < ? >");
        loan_table.getItems().clear();
        return_table.getItems().clear();
    }

    @FXML
    public void searchUser() {
        if(search_field.getText().isEmpty())
        {
            //error log
            printErrorMessage("empty User ID field. Please check the input field and retry.");
            resetFields();
        }
        else
        {
            output_field.clear();
            userId = search_field.getText();

            if(Main.lm.findUser(userId) == null)
            {
                printErrorMessage("specified User ID doesn't match any existing user.");
                resetFields();
            }
            else {
                currentUser.setText("Current User: <" + Main.lm.findUser(userId) + ">");
                loan_table.setItems(Main.lm.browseUserLoans(0, userId)); //0 = pending loan requests
                return_table.setItems(Main.lm.browseUserLoans(2, userId)); //2 = pending return requests)
            }
        }
    }

    @FXML
    public void confirmLoan(ActionEvent event) {
        output_field.clear();

        Book selectedBook = loan_table.getSelectionModel().getSelectedItem();
        Main.lm.validateBorrow(userId, selectedBook.getId());

        //refresh the table
        loan_table.setItems(Main.lm.browseUserLoans(0, userId)); //0 = pending loan requests
    }

    @FXML
    public void confirmReturn(ActionEvent event) {
        output_field.clear();

        Book selectedBook = return_table.getSelectionModel().getSelectedItem();
        Main.lm.validateReturn(userId, selectedBook.getId());

        //refresh the table
        return_table.setItems(Main.lm.browseUserLoans(2, userId)); //0 = pending loan requests
    }
}
