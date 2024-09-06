package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.io.FileUtils;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class Main_controller {

	
	String azaza="";
	List<String[]> replace = new ArrayList<String[]>();
	Collection<File> filesList;
	
	@FXML
    private MenuBar menu;
    @FXML
    private MenuItem fold_choose;
    @FXML
    private MenuItem file_choose;
    @FXML
    private MenuItem rule_choose;
    @FXML
    private MenuItem exit;
    @FXML
    private MenuItem about;
    @FXML
    private Label folder;
    @FXML
    private Label file;
    @FXML
    private Button translit;
    @FXML
    private Button clearFol;
    @FXML
    private Button clearFile;

    @FXML
    void clearFileBut(ActionEvent event) {
    	file.setText("");
    }
    @FXML
    void clearFolBut(ActionEvent event) {
    	folder.setText("");
    }  
    @FXML
    void aBout(ActionEvent event) {
    	Stage primaryStage = new Stage();
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/About.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("About");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    @FXML
    void appClose(ActionEvent event) {
    	Platform.exit();
    	System.exit(0);
    }
    @FXML
    void ruleChoose(ActionEvent event) throws IOException {
    	FileChooser fc = new FileChooser();
//    	fc.setInitialDirectory(new File("D:\\JavaPrimers"));
    	fc.getExtensionFilters().addAll(new ExtensionFilter("Rule's file","*.txt"));
    	File selectedFile = fc.showOpenDialog(null);
	    BufferedReader reader;
		reader = new BufferedReader(new FileReader(selectedFile));
		while (reader.ready())
		{
			String tempo=reader.readLine();
			String[] parts = tempo.split(" ");
			replace.add(parts);
		}
		reader.close();

    }
    
    @FXML
    void fileChoose(ActionEvent event) {
    	FileChooser fc = new FileChooser();
//    	fc.setInitialDirectory(new File("D:\\JavaPrimers"));
    	fc.getExtensionFilters().addAll(new ExtensionFilter("Las files","*.las"));
    	File selectedFile = fc.showOpenDialog(null);
    	file.setText(selectedFile.getPath());
    }

    @FXML
    void folderChoose(ActionEvent event) {
    	DirectoryChooser dc = new DirectoryChooser();
    	File selectedDirectory=dc.showDialog(null);
    	folder.setText(selectedDirectory.getPath());
    	String[] az={"las","Las","lAs","laS","LAs","LaS","lAS","LAS",};
		filesList = FileUtils.listFiles(selectedDirectory, az, true);
    }

    @FXML
    void translitAll(ActionEvent event) throws IOException {
    	int flag=0;
    	if (replace.isEmpty()) flag=-3;
    	else
    	{
    		if ((file.getText().isEmpty())&&(folder.getText().isEmpty())) flag=-2;
    		else
    		{
    			if ((!file.getText().isEmpty())&&(!folder.getText().isEmpty())) flag=-1;
    			else
    			{
    				if ((file.getText().isEmpty())&&(!folder.getText().isEmpty())) flag=0;
    				else
    				{
    					if ((!file.getText().isEmpty())&&(folder.getText().isEmpty())) flag=1;
    				}
    			}
    		}
    	}
    	switch (flag){
    		case -3:{
    			Alert a1=new Alert(Alert.AlertType.ERROR);
    			a1.setHeaderText("Please choose rule's file");
    			a1.setTitle("ERROR");
    			a1.setResizable(true);
    			a1.getDialogPane().setPrefSize(200, 70);
    			a1.showAndWait();
    			break;
    		}
    		case -2:{
    			Alert a2=new Alert(Alert.AlertType.ERROR);
    			a2.setHeaderText("Please choose either file or folder");
				a2.setTitle("ERROR");
				a2.setResizable(true);
				a2.getDialogPane().setPrefSize(200, 70);
				a2.showAndWait();
				break;
    		}
    		case -1:{
    			Alert a3=new Alert(Alert.AlertType.ERROR);
    			a3.setHeaderText("Please choose only file or folder(not both)");
				a3.setTitle("ERROR");
				a3.setResizable(true);
				a3.getDialogPane().setPrefSize(200, 70);
				a3.showAndWait();
				break;
    		}
    		case  0:{
    			for (int k=0;k<filesList.toArray().length;k++)
    			{    			
	    			BufferedReader reader;
		    		reader = new BufferedReader(new FileReader(filesList.toArray()[k].toString()));
		    		List<String> fileData = new ArrayList<String>();
		    				
		    		while (reader.ready())
		    		fileData.add(reader.readLine());
		    		reader.close();
		    		
//		    		PrintWriter writer = new PrintWriter(filesList.toArray()[k].toString().replace(".", "_Translit."));//Create new File
		    		
		    		PrintWriter writer = new PrintWriter(filesList.toArray()[k].toString());//Replace existing File
		    		int flag2=0;
		    		int flag3=0;
		    		for (int i=0;i<fileData.size();i++)
		    		{
		    			azaza=fileData.get(i);
		    			if (azaza.contains("~A ")||azaza.contains("~ASCII")) {flag3=-1;}
		    			if (flag2==0)
		    			{
		    				for (int j=0;j<replace.size();j++)	azaza=azaza.replace(replace.get(j)[0], replace.get(j)[1]);
		    			}
		    			if (flag3==-1) flag2=-1;
		    			fileData.set(i, azaza);
		    			writer.println(fileData.get(i));
		    		}
		    	    writer.close();
    			}
    			
    			
    			
    			Alert a4=new Alert(Alert.AlertType.INFORMATION);
	    		a4.setHeaderText("Translitiration Complete");
	    		a4.setTitle("Progress");
	    		a4.setResizable(true);
	    		a4.getDialogPane().setPrefSize(200, 70);
	    		a4.showAndWait();
				break;
    		}
    		case  1:{
    			BufferedReader reader;
	    		reader = new BufferedReader(new FileReader(file.getText()));
	    		List<String> fileData = new ArrayList<String>();
	    				
	    		while (reader.ready())
	    		fileData.add(reader.readLine());
	    		reader.close();
	    		
//	    		PrintWriter writer = new PrintWriter(file.getText().replace(".", "_Translit."));//Create new File
	    		
	    		PrintWriter writer = new PrintWriter(file.getText());//Replace existing File
	    		int flag2=0;
	    		int flag3=0;
	    		for (int i=0;i<fileData.size();i++)
	    		{
	    			azaza=fileData.get(i);
	    			if (azaza.contains("~A ")||azaza.contains("~ASCII")) {flag3=-1;}
	    			if (flag2==0)
	    			{
	    				for (int j=0;j<replace.size();j++)	azaza=azaza.replace(replace.get(j)[0], replace.get(j)[1]);
	    			}
	    			if (flag3==-1) flag2=-1;
	    			fileData.set(i, azaza);
	    			writer.println(fileData.get(i));
	    		}
	    	    writer.close();
    			
	    		Alert a5=new Alert(Alert.AlertType.INFORMATION);
	    		a5.setHeaderText("Translitiration Complete");
	    		a5.setTitle("Progress");
	    		a5.setResizable(true);
	    		a5.getDialogPane().setPrefSize(200, 70);
	    		a5.showAndWait();
				break;
		    	}
    	}
    }
}
