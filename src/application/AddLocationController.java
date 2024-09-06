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
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class AddLocationController implements Initializable {

    ObservableList<String> list = FXCollections.observableArrayList();
    @FXML
    private TextArea locationName;
    @FXML
    private Button addLocation;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    void addLocation(ActionEvent event) throws SQLException {
        WaterBaseMudController app = new WaterBaseMudController();
        try {
            list = app.allLocations();
        } catch (SQLException ex) {
            Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (locationName.getText().equals("")) {
            Alert a1 = new Alert(Alert.AlertType.ERROR);
            a1.setHeaderText("Location fields must be filled");
            a1.setTitle("ERROR");
            a1.setResizable(true);
            a1.getDialogPane().setPrefSize(200, 70);
            a1.showAndWait();
        } else {
            int locFlag = 0;
            for (int i = 0; i < list.size(); i++) {
                if (locationName.getText().equals(list.get(i))) {
                    locFlag = 1;
                }
            }
            if (locFlag == 1) {
                Alert a1 = new Alert(Alert.AlertType.ERROR);
                a1.setHeaderText("Location with this Name already exist");
                a1.setTitle("ERROR");
                a1.setResizable(true);
                a1.getDialogPane().setPrefSize(200, 70);
                a1.showAndWait();
            } else {
                app.insertInDbWBM(locationName.getText(), "0.0", "0.0", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1,"");
                Stage stage = (Stage) addLocation.getScene().getWindow();
                stage.close();
//                list = app.allLocations();
//                return list;
            }
        }
//        list = app.allLocations();
//        return list;

    }
    
    ObservableList<String> giveList() throws SQLException{
        WaterBaseMudController app = new WaterBaseMudController();
        list = app.allLocations();
        return list;
    }
    
}
