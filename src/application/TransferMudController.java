package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
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
import org.apache.commons.lang3.math.NumberUtils;

public class TransferMudController implements Initializable {

    ObservableList<String> listWell = FXCollections.observableArrayList();
    ObservableList<String> list = FXCollections.observableArrayList();
    @FXML
    private ComboBox<String> chooseWell;
    @FXML
    private TextArea amountMud;
    @FXML
    private Button moveMud;
    Double[] con = new Double[8];
    String dateGlob ; String wellGlob; String locGlob;Double totMudAll;Double amountMudTrans;
    ObservableList<RowTableWBM> tableGlob = FXCollections.observableArrayList();

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }

    
    @FXML
    void moveMud(ActionEvent event) throws SQLException, IOException {
        WaterBaseMudController app = new WaterBaseMudController();
        int newFlag = 0;
        if ((chooseWell.getValue()==null)) {
            Alert a1 = new Alert(Alert.AlertType.ERROR);
            a1.setHeaderText("Please Choose Well for transfer");
            a1.setTitle("ERROR");
            a1.setResizable(true);
            a1.getDialogPane().setPrefSize(200, 70);
            a1.showAndWait();
            } else {
            
                 for (int i=0; i<tableGlob.size(); i++)
                {
                   if (LocalDate.parse(tableGlob.get(i).getDate()).isEqual(LocalDate.parse(dateGlob)))
                   {
                       int k=tableGlob.size()-1;
                       for (int j=k;j>i-1;j--)
                           tableGlob.remove(j);
                       break;
                   }                        
                }
            Double[] totMud = new Double[tableGlob.size()];
            for (int i = 0; i < tableGlob.size(); i++) {
            if (i == 0) {
                totMud[0] = Double.parseDouble(tableGlob.get(i).mudMade) - Double.parseDouble(tableGlob.get(i).mudLost);
                if (totMud[0].compareTo(0.0) < 0) {
                    totMud[0] = 0.0;
                }
            } else {
                totMud[i] = totMud[i - 1] + Double.parseDouble(tableGlob.get(i).mudMade) - Double.parseDouble(tableGlob.get(i).mudLost);
                if (totMud[i].compareTo(0.0) < 0) {
                    totMud[i] = 0.0;
                }
            }
        }
            totMudAll=totMud[tableGlob.size()-1];
            if (NumberUtils.isCreatable(amountMud.getText()))
            {
                if (Double.parseDouble(amountMud.getText())>totMudAll)
                {
                    Alert a3 = new Alert(Alert.AlertType.ERROR);
                    a3.setHeaderText("Amount of transfer mud more that Total mud avaliable");
                    a3.setTitle("ERROR");
                    a3.setResizable(true);
                    a3.getDialogPane().setPrefSize(200, 70);
                    a3.showAndWait();
                }
                else
                {
                    wellGlob=chooseWell.getValue();
                    amountMudTrans=Double.parseDouble(amountMud.getText());
                    con[0]=app.calc_conc(tableGlob, 1)*amountMudTrans;
                    con[1]=app.calc_conc(tableGlob, 2)*amountMudTrans;
                    con[2]=app.calc_conc(tableGlob, 3)*amountMudTrans;
                    con[3]=app.calc_conc(tableGlob, 4)*amountMudTrans;
                    con[4]=app.calc_conc(tableGlob, 5)*amountMudTrans;
                    con[5]=app.calc_conc(tableGlob, 6)*amountMudTrans;
                    con[6]=app.calc_conc(tableGlob, 7)*amountMudTrans;
                    con[7]=app.calc_conc(tableGlob, 8)*amountMudTrans;
                    close();
                }
            }
            else
            {
                Alert a2 = new Alert(Alert.AlertType.ERROR);
                a2.setHeaderText("Please input number in the amount of mud");
                a2.setTitle("ERROR");
                a2.setResizable(true);
                a2.getDialogPane().setPrefSize(200, 70);
                a2.showAndWait();
            }
        }
    }
    
    Double[] returningMud()
    {
        return con;
    }
    
    String returningLoc()
    {
        return locGlob;
    }
    
    String returningWell()
    {
        return wellGlob;
    }
    
    Double returningAmount()
    {
        return amountMudTrans;
    }
    
    
    public void loadLocation(String loc,String well,ObservableList<RowTableWBM> table2,String date){
        WaterBaseMudController app = new WaterBaseMudController();
        try {
            list = app.allWellsInLocation(loc);
            for (int i=0; i<list.size(); i++)
            {
                if (list.get(i).equals(well)) 
                {list.remove(i);
                break;}
            }
            chooseWell.setItems(list);
           
            tableGlob=table2;
            dateGlob=date;locGlob=loc;wellGlob=well;
        } catch (SQLException ex) {
            Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void close() {
         Stage stage = (Stage) chooseWell.getScene().getWindow();
         stage.close();
    }

}
