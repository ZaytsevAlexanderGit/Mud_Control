package application;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class AddWellController implements Initializable {

    ObservableList<String> listWell = FXCollections.observableArrayList();
    ObservableList<String> list = FXCollections.observableArrayList();
    @FXML
    private ComboBox<String> addWellChooseLocat;
    @FXML
    private TextArea wellName;
    @FXML
    private Button addWell;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        WaterBaseMudController app = new WaterBaseMudController();
        try {
            list = app.allLocations();
            addWellChooseLocat.setItems(list);
        } catch (SQLException ex) {
            Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void addWell(ActionEvent event) throws SQLException {
        WaterBaseMudController app = new WaterBaseMudController();
        int newFlag = 0;
        if ((wellName.getText().equals("") || (addWellChooseLocat.getValue() == null))) {
            Alert a1 = new Alert(Alert.AlertType.ERROR);
            a1.setHeaderText("Location and Well fields must be filled");
            a1.setTitle("ERROR");
            a1.setResizable(true);
            a1.getDialogPane().setPrefSize(200, 70);
            a1.showAndWait();
        } else {
            listWell = app.allWellsInLocation(addWellChooseLocat.getValue());
            for (int i = 0; i < listWell.size(); i++) {
                if (wellName.getText().equals(listWell.get(i))) {
                    newFlag = 1;
                }
            }
            if (newFlag == 0) {
                app.insertInDbWBM(addWellChooseLocat.getValue(), wellName.getText(), "0.0", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1,"");
                Stage stage = (Stage) addWell.getScene().getWindow();
                stage.close();
            } else {
                Alert a1 = new Alert(Alert.AlertType.ERROR);
                a1.setHeaderText("Well with this Name already exist");
                a1.setTitle("ERROR");
                a1.setResizable(true);
                a1.getDialogPane().setPrefSize(200, 70);
                a1.showAndWait();
            }

        }

    }

}
