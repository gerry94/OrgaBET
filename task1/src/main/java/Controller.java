import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.event.*;

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
    void logout(ActionEvent event) throws IOException {
        Main.lm.logout();
        Main.changeScene(0);
    }

    @FXML
    void login(ActionEvent event) throws IOException {
    	List<String> result = new ArrayList<String>();
    	result = Main.lm.login(login_code.getText());
    	
        if (result == null) {
            output_text.setTextFill(Color.RED);
            output_text.setText("ERROR: wrong id. Please check your personal code and retry.");
        }
        
        username = result.get(0);
        privilege =  Integer.parseInt(result.get(1));
        
        if (privilege == 0){
            Main.changeScene(1);
        } else if (privilege == 1)
        	Main.changeScene(2);
    }

    public static String getUsername() { return username; }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
