package application;

import java.io.File;
import java.sql.*;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class SqLiteConnection {
    
    public static Connection ConnectorWBM() {
        try {
            Class.forName("org.sqlite.JDBC");
            File f = new File("C:\\MudControlData\\MudParametersWBM.db");
            if(f.exists() && !f.isDirectory()) { 
            Connection conn=DriverManager.getConnection("jdbc:sqlite:C:\\MudControlData\\MudParametersWBM.db");
            return conn;}
            else{
                Alert a1 = new Alert(Alert.AlertType.ERROR);
        a1.setHeaderText("MudParametersWBM.db is missing on C:\\MudControlData");
        a1.setTitle("ERROR");
        a1.setResizable(true);
        a1.getDialogPane().setPrefSize(320, 70);
        Stage stage = (Stage) a1.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image ("/misc/MudControl.png"));
        a1.showAndWait();
        return null;
            }
        }  catch (ClassNotFoundException e) {
            return null;
        } catch (SQLException e) {
        return null;
        }
    }
    
     public static Connection ConnectorOBM() {
        try {
            Class.forName("org.sqlite.JDBC");
             File f = new File("C:\\MudControlData\\MudParametersOBM.db");
            if(f.exists() && !f.isDirectory()) { 
              Connection conn=DriverManager.getConnection("jdbc:sqlite:C:\\MudControlData\\MudParametersOBM.db");
              return conn;}
            else {
                Alert a1 = new Alert(Alert.AlertType.ERROR);
        a1.setHeaderText("MudParametersOBM.db is missing on C:\\MudControlData");
        a1.setTitle("ERROR");
        a1.setResizable(true);
        a1.getDialogPane().setPrefSize(320, 70);
        Stage stage = (Stage) a1.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image ("/misc/MudControl.png"));
        a1.showAndWait();
        return null;
            }
        }  catch (ClassNotFoundException e) {
            return null;
        } catch (SQLException e) {
            return null;
        }
    }
}
