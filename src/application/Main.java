package application;
	
import java.io.IOException;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.WindowEvent;


public class Main extends Application {
	@Override
	public void start(final Stage primaryStage) {
//            if ((System.getProperty("user.name").equals("AZaytsev2"))||(System.getProperty("user.name").equals("DMilyakov"))||(System.getProperty("user.name").equals("SOsaulko"))||(System.getProperty("user.name").equals("ASioutch"))||(System.getProperty("user.name").equals("Mumrichello")))
            if (1==1)
            {
            try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/WaterBaseMud.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Water Base Mud Control");
                        primaryStage.setResizable(false);
                        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                            @Override
                            public void handle(WindowEvent event) {
                                WaterBaseMudController wbmc = new WaterBaseMudController();
                                wbmc.closeApplication();
                                }
                        });
                        primaryStage.show();                        
                        primaryStage.getIcons().add(new Image ("/misc/MudControl.png"));
                        primaryStage.setOnHidden(new EventHandler<WindowEvent>() {
                                @Override
                                public void handle(WindowEvent event) {
                                    Stage primaryStage2 = new Stage();
                                    try {
                                        FXMLLoader loader = new FXMLLoader();
                                        Parent root2 = loader.load(getClass().getResource("/application/OilBaseMud.fxml"));
                                        Scene scene2 = new Scene(root2);
                                        scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
                                        primaryStage2.setScene(scene2);
                                        primaryStage2.setResizable(false);
                                        primaryStage2.setTitle("Oil Base Mud Control");
                                        primaryStage2.getIcons().add(new Image ("/misc/MudControl.png"));
                                        primaryStage2.showAndWait();
                                    } catch (IOException e) {
                                    }
                                }
                            } ); 
		} catch(IOException e) {
		}}
            else
            {
                Alert a1 = new Alert(Alert.AlertType.ERROR);
                Label label = new Label("Sorry you not able to Run Application.\nContact AZaytsev03@gmail.com\nGood Luck!");
                label.setWrapText(true);
                a1.setTitle("ERROR");
                a1.setResizable(false);
                a1.getDialogPane().setContent(label);
                a1.showAndWait();
            }
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
