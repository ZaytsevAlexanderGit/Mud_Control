
package application;

import com.sun.javafx.scene.control.behavior.TextAreaBehavior;
import com.sun.javafx.scene.control.skin.TextAreaSkin;
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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class CommentController implements Initializable {
    
    @FXML   private TextField Well;
    @FXML   private TextField Date;
    @FXML   private Button Add;
    @FXML   private TextArea commentAreaWBM;
    @FXML   private TextArea commentAreaOBM;
    private String location;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        commentAreaWBM.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if ((event.getCode() == KeyCode.TAB) ) {
                    TextAreaSkin skin = (TextAreaSkin) commentAreaWBM.getSkin();
                    if (skin.getBehavior() instanceof TextAreaBehavior) {
                        TextAreaBehavior behavior = (TextAreaBehavior) skin.getBehavior();
                        if (event.isControlDown()) {
                            behavior.callAction("InsertTab");
                        } else {
                            behavior.callAction("TraverseNext");
                        }
                        event.consume();
                    }
                }
            }
        });
        commentAreaOBM.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if ((event.getCode() == KeyCode.TAB) ) {
                    TextAreaSkin skin = (TextAreaSkin) commentAreaOBM.getSkin();
                    if (skin.getBehavior() instanceof TextAreaBehavior) {
                        TextAreaBehavior behavior = (TextAreaBehavior) skin.getBehavior();
                        if (event.isControlDown()) {
                            behavior.callAction("InsertTab");
                        } else {
                            behavior.callAction("TraverseNext");
                        }
                        event.consume();
                    }
                }
            }
        });
}  
  
    void updateCommentWBM (){
        WaterBaseMudController app = new WaterBaseMudController();
        String sql = "UPDATE MudControlProperties SET Comment = ? WHERE FieldLocation = ? AND Field_Pad_Well = ? AND Date_Time = ?";
        Connection conn = app.loginModal.connectWBM();
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, commentAreaWBM.getText());
            pstmt.setString(2, location);
            pstmt.setString(3, Well.getText());
            pstmt.setString(4, Date.getText());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     void updateCommentOBM (){
        OilBaseMudController app = new OilBaseMudController();
        String sql = "UPDATE MudControlProperties SET Comment = ? WHERE FieldLocation = ? AND Field_Pad_Well = ? AND Date_Time = ?";
        Connection conn = app.loginModal.connectOBM();
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, commentAreaOBM.getText());
            pstmt.setString(2, location);
            pstmt.setString(3, Well.getText());
            pstmt.setString(4, Date.getText());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(OilBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @FXML
    void addCommentWBM(ActionEvent event) throws SQLException, IOException, InterruptedException {
        updateCommentWBM();
        Stage stage = (Stage) Add.getScene().getWindow();
        stage.close();  
    }
    
    @FXML
    void addCommentOBM(ActionEvent event) throws SQLException, IOException, InterruptedException {
        updateCommentOBM();
        Stage stage = (Stage) Add.getScene().getWindow();
        stage.close();  
    }
    
    public String chooseCommentWBM(String locat, String well, String date) throws SQLException {
        WaterBaseMudController app = new WaterBaseMudController();
        location=locat;
        ArrayList<String> fields = new ArrayList<String>();
        int flag = 0;
        String sql = "SELECT Comment " + "FROM MudControlProperties WHERE FieldLocation = ? AND Field_Pad_Well = ? AND Date_Time = ?";
        Connection conn = app.loginModal.connectWBM();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, locat);
        pstmt.setString(2, well);
        pstmt.setString(3, date);
        ResultSet rs = pstmt.executeQuery();
        String comm=rs.getString(1);
        conn.close();
        return comm;
    }
    
    public String chooseCommentOBM(String locat, String well, String date) throws SQLException {
        OilBaseMudController app = new OilBaseMudController();
        location=locat;
        ArrayList<String> fields = new ArrayList<String>();
        int flag = 0;
        String sql = "SELECT Comment " + "FROM MudControlProperties WHERE FieldLocation = ? AND Field_Pad_Well = ? AND Date_Time = ?";
        Connection conn = app.loginModal.connectOBM();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, locat);
        pstmt.setString(2, well);
        pstmt.setString(3, date);
        ResultSet rs = pstmt.executeQuery();
        String comm=rs.getString(1);
        conn.close();
        return comm;
    }
    
    public void showCommentWBM(String loc,String well, String date){
        Well.setText(well);Date.setText(date);
        WaterBaseMudController app = new WaterBaseMudController();
        try {    
            String comm=chooseCommentWBM(loc, well, date);
            commentAreaWBM.setText(comm);
        } catch (SQLException ex) {
            Logger.getLogger(CommentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void showCommentOBM(String loc,String well, String date){
        Well.setText(well);Date.setText(date);
        OilBaseMudController app = new OilBaseMudController();
        try {    
            String comm=chooseCommentOBM(loc, well, date);
            commentAreaOBM.setText(comm);
        } catch (SQLException ex) {
            Logger.getLogger(CommentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
