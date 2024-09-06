
package application;

import com.sun.javafx.scene.control.behavior.TextAreaBehavior;
import com.sun.javafx.scene.control.skin.TextAreaSkin;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static java.sql.Types.NULL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class SetInhibitorController implements Initializable {
    
@FXML
    private TextField Location;
    @FXML
    private TextField Well;
    @FXML
    private TextArea  firstInhib;
    @FXML
    private TextArea  secondInhib;
    @FXML
    private Button set;
    ObservableList<String> list = FXCollections.observableArrayList();

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        firstInhib.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if ((event.getCode() == KeyCode.TAB) ) {
                    TextAreaSkin skin = (TextAreaSkin) firstInhib.getSkin();
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
        secondInhib.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if ((event.getCode() == KeyCode.TAB) ) {
                    TextAreaSkin skin = (TextAreaSkin) secondInhib.getSkin();
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

    @FXML
    void setInhibWBM(ActionEvent event) throws SQLException {
        if (firstInhib.getText().equals("")||secondInhib.getText().equals(""))
        {
            Alert a1 = new Alert(Alert.AlertType.ERROR);
                    a1.setHeaderText("Inhibitors can't be empty");
                    a1.setTitle("ERROR");
                    a1.setResizable(true);
                    a1.getDialogPane().setPrefSize(200, 70);
                    a1.showAndWait();
        }
        else
            {
                setInhibWBMAction();
                Stage stage = (Stage) set.getScene().getWindow();
                stage.close();
            }
    }
    
    void setInhibWBMAction() throws SQLException{
        WaterBaseMudController app = new WaterBaseMudController();
        String comment=chooseInhibitors(Location.getText(),Well.getText());
        String commentNew="";
        int comLeng=comment.split("     ").length;
        String[] comParts=comment.split("     ");
        if (comLeng==1)
        {
            if (comment.equals(""))
            commentNew=firstInhib.getText()+"     "+secondInhib.getText();
            else commentNew=firstInhib.getText()+"     "+secondInhib.getText()+"     "+comment;
        }
        else if (comLeng==2) 
        {
            commentNew=firstInhib.getText()+"     "+secondInhib.getText();
        }
        else
        {
            commentNew=firstInhib.getText()+"     "+secondInhib.getText()+"     "+comParts[2];
        }
        String sql = "UPDATE MudControlProperties SET Comment = ? WHERE FieldLocation = ? AND Field_Pad_Well = ? AND Date_Time = ?";
        Connection conn = app.loginModal.connectWBM();
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, commentNew);
            pstmt.setString(2, Location.getText());
            pstmt.setString(3, Well.getText());
            pstmt.setString(4, "0.0");
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    
    public void showInhibWMB(String loc,String well){
        Location.setText(loc);Well.setText(well);
        WaterBaseMudController app = new WaterBaseMudController();
        try {    
            String comment=chooseInhibitors(loc, well);
            String[] ingib=new String[2];
            if ((comment!=null)&&(!comment.equals(""))&&(comment.split("     ").length!=1))  ingib=comment.split("     ");
        else {
            ingib[0]="Inhibitor 1"; 
            ingib[1]="Inhibitor 2";
        }
            firstInhib.setText(ingib[0]);secondInhib.setText(ingib[1]);
        } catch (SQLException ex) {
            Logger.getLogger(CommentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        public String chooseInhibitors(String locat, String well) throws SQLException {
        WaterBaseMudController app = new WaterBaseMudController();
        String sql = "SELECT Comment " + "FROM MudControlProperties WHERE FieldLocation = ? AND Field_Pad_Well = ? AND Date_Time = ?";
        Connection conn = app.loginModal.connectWBM();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, locat);
        pstmt.setString(2, well);
        pstmt.setString(3,"0.0");
        ResultSet rs = pstmt.executeQuery();
        String comment=rs.getString(1);
        conn.close();
//        if (comment.equals(NULL))
//        {
//            comment="";
//        }
        return comment;
    }
}
