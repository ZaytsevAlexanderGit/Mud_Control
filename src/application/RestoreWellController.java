
package application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;


public class RestoreWellController implements Initializable {

    ObservableList<String> listWell = FXCollections.observableArrayList();
    ObservableList<String> list = FXCollections.observableArrayList();
    
    @FXML
    private ComboBox<String> addWellChooseLocat;

    @FXML
    private ComboBox<String> addWellChooseWell;

    @FXML
    private Button restoreWell;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
             try {
            list = allLocations2();
            addWellChooseLocat.setItems(list);
        } catch (SQLException ex) {
            Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }    
    
    @FXML
    void restoreWell(ActionEvent event) {
        WaterBaseMudController app = new WaterBaseMudController();
        int newFlag = 0;
        try {
            app.updateDb(2, addWellChooseLocat.getValue(), addWellChooseWell.getValue(), 4);
            } catch (SQLException ex) {
            Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                app.updateDb(1, addWellChooseLocat.getValue(), addWellChooseWell.getValue(), 3);
                } catch (SQLException ex) {
                Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Stage stage = (Stage) restoreWell.getScene().getWindow();
            stage.close();
            }
    
    @FXML
    ObservableList<String> allLocations2() throws SQLException {
        WaterBaseMudController app = new WaterBaseMudController();
        ArrayList<String> fields = chooseLocation2("3");
        for (int i = 0; i < fields.size(); i++) {
            list.add(fields.get(i));
        }
        return list;
    }
    
    public ArrayList<String> chooseLocation2(String actFlag) throws SQLException {
        WaterBaseMudController app = new WaterBaseMudController();
        ArrayList<String> fields = new ArrayList<String>();
        int flag = 0;
        String sql = "SELECT FieldLocation FROM MudControlProperties WHERE ActiveFlag = ?";
        Connection conn = app.loginModal.connectWBM();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, actFlag);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            if (fields.isEmpty()) {
                fields.add(rs.getString("FieldLocation"));
            } else {
                for (int i = 0; i < fields.size(); i++) {
                    if (rs.getString("FieldLocation").equals(fields.get(i))) {
                        flag = 1;
                    }
                }
                if (flag == 0) {
                    fields.add(rs.getString("FieldLocation"));
                } else {
                    flag = 0;
                }
            }
        }
        pstmt.close();
        return fields;
    }
    
    @FXML
    void listAllWellsInLocation2(ActionEvent event) throws SQLException, FileNotFoundException, IOException {
        listWell.clear();
        listWell = allWellsInLocation2(addWellChooseLocat.getValue());
        addWellChooseWell.setItems(listWell);
        /*New kusok delete...*/
        addWellChooseWell.setEditable(true);
        TextFields.bindAutoCompletion(addWellChooseWell.getEditor(), addWellChooseWell.getItems());
       //    
     }
    
    ObservableList<String> allWellsInLocation2(String a) throws SQLException {
        ArrayList<String> fields = chooseWells2(a, "3");
        for (int i = 0; i < fields.size(); i++) {
            if (!fields.get(i).equals("0.0")) listWell.add(fields.get(i));
        }
        listWell.sort(null);
        return listWell;
    }
    
    public ArrayList<String> chooseWells2(String location, String actFlag) throws SQLException {
        WaterBaseMudController app = new WaterBaseMudController();
        ArrayList<String> fields = new ArrayList<String>();
        int flag = 0;
        String sql = "SELECT Field_Pad_Well " + "FROM MudControlProperties WHERE FieldLocation = ? AND ActiveFlag = ?";
        Connection conn = app.loginModal.connectWBM();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, location);
        pstmt.setString(2, actFlag);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            if (fields.isEmpty()) {
                fields.add(rs.getString("Field_Pad_Well"));
            } else {
                for (int i = 0; i < fields.size(); i++) {
                    if (rs.getString("Field_Pad_Well").equals(fields.get(i))) {
                        flag = 1;
                    }
                }
                if (flag == 0) {
                    fields.add(rs.getString("Field_Pad_Well"));
                } else {
                    flag = 0;
                }
            }
        }
        pstmt.close();
        return fields;
    }

}