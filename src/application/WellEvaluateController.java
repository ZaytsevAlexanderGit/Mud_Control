package application;

import com.sun.javafx.scene.control.behavior.TextAreaBehavior;
import com.sun.javafx.scene.control.skin.TextAreaSkin;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class WellEvaluateController implements Initializable{
    
    @FXML
    private TextField Well;

    @FXML
    private RadioButton goodShape;

    @FXML
    private RadioButton badShape;

    @FXML
    private Button finishEval;

    @FXML
    private TextArea commentArea;

    @FXML
    private TextArea commentAreaOBM;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        commentArea.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if ((event.getCode() == KeyCode.TAB) ) {
                    TextAreaSkin skin = (TextAreaSkin) commentArea.getSkin();
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
    void chooseAct(ActionEvent event) {
        if (event.toString().contains("Good"))
        {badShape.setSelected(false);commentArea.setDisable(true);}
        else 
        {goodShape.setSelected(false);commentArea.setDisable(false);}
    }

    @FXML
    void finishEval(ActionEvent event) throws SQLException {
        String mudType=commentAreaOBM.getText().split("     ")[1];
        String locat=commentAreaOBM.getText().split("     ")[0];
        String commentNow;
        if (mudType.equals("0")) commentNow=showMark(locat, Well.getText());
        else commentNow=showMarkOBM(locat, Well.getText());
        String commentNew="";
        String[] commentNowPart=commentNow.split("     ");
        int commLeng=commentNow.split("     ").length;
        if (commLeng==1)
        {
            if (goodShape.isSelected()) commentNew="";
            else commentNew=commentArea.getText();
        }
        else if (commLeng==2)
        {
            if (goodShape.isSelected()) commentNew=commentNow;
            else commentNew=commentNow+"     "+commentArea.getText();
        }
        else 
        {
            if (goodShape.isSelected()) commentNew=commentNowPart[0]+"     "+commentNowPart[1];
            else commentNew=commentNowPart[0]+"     "+commentNowPart[1]+"     "+commentArea.getText();
        };
        if (mudType.equals("0"))
        {
            WaterBaseMudController app = new WaterBaseMudController();
            String sql = "UPDATE MudControlProperties SET Comment = ? WHERE FieldLocation = ? AND Field_Pad_Well = ? AND Date_Time = ?";
            Connection conn = app.loginModal.connectWBM();
            PreparedStatement pstmt;
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, commentNew);
                pstmt.setString(2, locat);
                pstmt.setString(3, Well.getText());
                pstmt.setString(4, "0.0");
                pstmt.executeUpdate();
                pstmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
            }

            Stage stage = (Stage) finishEval.getScene().getWindow();
                    stage.close();
        }
        else
        {
            OilBaseMudController app = new OilBaseMudController();
            String sql = "UPDATE MudControlProperties SET Comment = ? WHERE FieldLocation = ? AND Field_Pad_Well = ? AND Date_Time = ?";
            Connection conn = app.loginModal.connectOBM();
            PreparedStatement pstmt;
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, commentNew);
                pstmt.setString(2, locat);
                pstmt.setString(3, Well.getText());
                pstmt.setString(4, "0.0");
                pstmt.executeUpdate();
                pstmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
            }

            Stage stage = (Stage) finishEval.getScene().getWindow();
                    stage.close();            
        }

    }

    
    @FXML
    void showEval(String loc,String well) throws SQLException {
       commentAreaOBM.setText(loc);
       Well.setText(well);
       String locat=loc.split("     ")[0];
       String s=showMark(locat,well);
       String[] ss=s.split("     ");
       if (ss.length==1) 
       {
           if(ss[0].equals(""))
           {
               commentArea.setDisable(true);
               goodShape.setSelected(true);
               badShape.setSelected(false);
           }
           else
           {
               commentArea.setText(ss[0]);
               goodShape.setSelected(false);
               badShape.setSelected(true);
           }
       }
       else if (ss.length==2) 
       {
           commentArea.setDisable(true);
           goodShape.setSelected(true);
           badShape.setSelected(false);
       }
       else 
       {
           commentArea.setText(ss[2]);
           goodShape.setSelected(false);
           badShape.setSelected(true);           
       }
             
    }
    
    @FXML
    public String showMark (String loc,String well) throws SQLException
    {
        WaterBaseMudController app = new WaterBaseMudController();
        String sql = "SELECT Comment " + "FROM MudControlProperties WHERE FieldLocation = ? AND Field_Pad_Well = ? AND Date_Time = ?";
        Connection conn = app.loginModal.connectWBM();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, loc);
        pstmt.setString(2, well);
        pstmt.setString(3,"0.0");
        ResultSet rs = pstmt.executeQuery();
        String comment=new String();
        if (rs.getString(1)!=null)
        comment=rs.getString(1);
        conn.close();
        return comment; 
    }
    
    @FXML
    void showEvalOBM(String loc,String well) throws SQLException {
       commentAreaOBM.setText(loc);
       Well.setText(well);
       String locat=loc.split("     ")[0];
       String s=showMarkOBM(locat,well);
       if(s.equals(""))
           {
               commentArea.setDisable(true);
               goodShape.setSelected(true);
               badShape.setSelected(false);
           }
           else
           {
               commentArea.setText(s);
               goodShape.setSelected(false);
               badShape.setSelected(true);
           }
                   
    }
    
    @FXML
    public String showMarkOBM (String loc,String well) throws SQLException
    {
        OilBaseMudController app = new OilBaseMudController();
        String sql = "SELECT Comment " + "FROM MudControlProperties WHERE FieldLocation = ? AND Field_Pad_Well = ? AND Date_Time = ?";
        Connection conn = app.loginModal.connectOBM();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, loc);
        pstmt.setString(2, well);
        pstmt.setString(3,"0.0");
        ResultSet rs = pstmt.executeQuery();
        String comm=new String();
        String comment=rs.getString(1);
        conn.close();
        return comment; 
    }
    
}
