import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.event.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    //LibraryManager lm = new LibraryManager();
    @FXML
    private AnchorPane output_txt;
    @FXML
    private TextField login_code;
    @FXML
    private Button login_but;
    @FXML
    private Label output_text;

    public static String username;

    @FXML
    void login(ActionEvent event) throws IOException {
        username = Main.lm.login(login_code.getText());
        if (username == null) {
            output_text.setTextFill(Color.RED);
            output_text.setText("ERROR: wrong id. Please check your personal code and retry.");
        } else {
            /*output_text.setTextFill(Color.GREEN);
            output_text.setText("Login successful. Welcome " + username + ".");*/
            Main.changeScene(1);
        }
    }

    public static String getUsername() { return username; }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
