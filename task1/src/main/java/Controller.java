import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.event.*;
import javafx.scene.input.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
    public static int privilege;

    @FXML
    public void logout(ActionEvent event) throws IOException {
        Main.lm.logout();
        Main.changeScene(0);
    }

    @FXML
    public void loginEvent(ActionEvent event) throws IOException { login(); }

    private void login() throws IOException
    {
        List<String> result = new ArrayList<String>();
        result = Main.lm.login(login_code.getText());

        if (result == null) {
            output_text.setTextFill(Color.RED);
            output_text.setText("ERROR: wrong id. Please check your personal code and retry.");
            Main.lm.logout();
        }
        else {
            username = result.get(0);
            privilege = Integer.parseInt(result.get(1));

            System.out.println(username +", " + privilege);

            if (privilege == 0) {
                Main.changeScene(1);
            } else if (privilege == 1)
                Main.changeScene(2);
        }
    }

    public static String getUsername() { return username; }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login_code.setOnKeyPressed((event) -> {
            if(event.getCode() == KeyCode.ENTER)
                try { login(); } catch (IOException e) { e.printStackTrace(); }
        });
    }
}
