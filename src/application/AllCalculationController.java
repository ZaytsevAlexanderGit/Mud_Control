package application;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


public class AllCalculationController implements Initializable {

    @FXML
    private TextField Location;
    @FXML
    private TextField Well;
    @FXML
    private TableView<RowTableWBM> Table;
    @FXML
    private TableColumn<RowTableWBM, String> dateTab;
    @FXML
    private TableColumn<RowTableWBM, String> caCO3Tab;
    @FXML
    private TableColumn<RowTableWBM, String> lubricTab;
    @FXML
    private TableColumn<RowTableWBM, String> kClTab;
    @FXML
    private TableColumn<RowTableWBM, String> krakhmalTab;
    @FXML
    private TableColumn<RowTableWBM, String> bariteTab;
    @FXML
    private TableColumn<RowTableWBM, String> naClTab;
    @FXML
    private TableColumn<RowTableWBM, String> ingib1Tab;
    @FXML
    private TableColumn<RowTableWBM, String> ingib2Tab;
    @FXML
    private Button close;
    @FXML
    private Button excel;
     @FXML
    private Button plot;
     
     String inhibit1;String inhibit2;

    ObservableList<RowTableWBM> table = FXCollections.observableArrayList();
    ObservableList<RowTableWBM> tableCalc = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    
    
    public void showTable(String loc, String well, String inhibi1,String inhibi2){
        Location.setText(loc);Well.setText(well);ingib1Tab.setText(inhibi1);ingib2Tab.setText(inhibi2);
        inhibit1=inhibi1;inhibit2=inhibi2;
        WaterBaseMudController app = new WaterBaseMudController();
        ResultSet rs;
        try {
            rs = app.chooseWellsFromDb(loc, well);
            String a = new String();
            String[] repData = new String[8];
            table.clear();
            while (rs.next()) {
            RowTableWBM azaz = new RowTableWBM(rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),
                    rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12),
                    rs.getString(13), rs.getString(14), "");
             if ((!azaz.getDate().isEmpty()) && (!azaz.getDate().equals("0.0")) && (rs.getInt(15) == 1)) {
                table.add(azaz);}
           
        }
        int flag = 0;
        int flag2 = 0;
        RowTableWBM azaz;
        while (flag == 0) {
            flag2 = 0;
            for (int i = 0; i < table.size() - 1; i++) {
                if (table.get(i).date.compareTo(table.get(i + 1).date) > 0) {
                    flag2 = 1;
                    azaz = table.get(i);
                    table.set(i, table.get(i + 1));
                    table.set(i + 1, azaz);
                }
            }
            if (flag2 == 0) {
                flag = 1;
            }
        }
        Double[] totMud = new Double[table.size()];
        for (int i = 0; i < table.size(); i++) {
            if (i == 0) {
                totMud[0] = Double.parseDouble(table.get(i).mudMade) - Double.parseDouble(table.get(i).mudLost);
                if (totMud[0].compareTo(0.0) < 0) {
                    totMud[0] = 0.0;
                }
            } else {
                totMud[i] = totMud[i - 1] + Double.parseDouble(table.get(i).mudMade) - Double.parseDouble(table.get(i).mudLost);
                if (totMud[i].compareTo(0.0) < 0) {
                    totMud[i] = 0.0;
                }
            }
            table.get(i).setMudLeft(totMud[i].toString());
        }
            
        Double conc = null;
        Double conc_prev = null;
        Double[] calcCaCO3 = new Double[table.size()];Double[] calcLubric = new Double[table.size()];
        Double[] calcKCl = new Double[table.size()];Double[] calcKrahmal = new Double[table.size()];
        Double[] calcBarite = new Double[table.size()];Double[] calcNaCl = new Double[table.size()];
        Double[] calcIngib1 = new Double[table.size()];Double[] calcIngib2 = new Double[table.size()];
        for (int i = 0; i < table.size(); i++) {
            if (i == 0) {
                if ((Double.parseDouble(table.get(i).mudMade) - Double.parseDouble(table.get(i).mudLost)) == 0) {
                    calcCaCO3[i] = 0.0;
                } else {
                    calcCaCO3[i] = Double.parseDouble(table.get(i).caCO3) / (Double.parseDouble(table.get(i).mudMade) - Double.parseDouble(table.get(i).mudLost));
                }
                conc_prev = calcCaCO3[i];
            } else {
                if (totMud[i] == 0) {
                    calcCaCO3[i] = 0.0;
                } else {
                    calcCaCO3[i] = (totMud[i - 1] * conc_prev + Double.parseDouble(table.get(i).caCO3) - Double.parseDouble(table.get(i).mudLost) * conc_prev) / (totMud[i]);
                }
                conc_prev = calcCaCO3[i];
            }
        }
        conc = null;conc_prev = null;
        for (int i = 0; i < table.size(); i++) {
            if (i == 0) {
                if ((Double.parseDouble(table.get(i).mudMade) - Double.parseDouble(table.get(i).mudLost)) == 0) {
                    calcLubric[i] = 0.0;
                } else {
                    calcLubric[i] = Double.parseDouble(table.get(i).lubric) / (Double.parseDouble(table.get(i).mudMade) - Double.parseDouble(table.get(i).mudLost));
                }
                conc_prev = calcLubric[i];
            } else {
                if (totMud[i] == 0) {
                    calcLubric[i] = 0.0;
                } else {
                    calcLubric[i] = (totMud[i - 1] * conc_prev + Double.parseDouble(table.get(i).lubric) - Double.parseDouble(table.get(i).mudLost) * conc_prev) / (totMud[i]);
                }
                conc_prev = calcLubric[i];
            }
        }
        conc = null;conc_prev = null;
        for (int i = 0; i < table.size(); i++) {
            if (i == 0) {
                if ((Double.parseDouble(table.get(i).mudMade) - Double.parseDouble(table.get(i).mudLost)) == 0) {
                    calcKCl[i] = 0.0;
                } else {
                    calcKCl[i] = Double.parseDouble(table.get(i).kCl) / (Double.parseDouble(table.get(i).mudMade) - Double.parseDouble(table.get(i).mudLost));
                }
                conc_prev = calcKCl[i];
            } else {
                if (totMud[i] == 0) {
                    calcKCl[i] = 0.0;
                } else {
                    calcKCl[i] = (totMud[i - 1] * conc_prev + Double.parseDouble(table.get(i).kCl) - Double.parseDouble(table.get(i).mudLost) * conc_prev) / (totMud[i]);
                }
                conc_prev = calcKCl[i];
            }
        }
        conc = null;conc_prev = null;
        for (int i = 0; i < table.size(); i++) {
            if (i == 0) {
                if ((Double.parseDouble(table.get(i).mudMade) - Double.parseDouble(table.get(i).mudLost)) == 0) {
                    calcKrahmal[i] = 0.0;
                } else {
                    calcKrahmal[i] = Double.parseDouble(table.get(i).krakhmal) / (Double.parseDouble(table.get(i).mudMade) - Double.parseDouble(table.get(i).mudLost));
                }
                conc_prev = calcKrahmal[i];
            } else {
                if (totMud[i] == 0) {
                    calcKrahmal[i] = 0.0;
                } else {
                    calcKrahmal[i] = (totMud[i - 1] * conc_prev + Double.parseDouble(table.get(i).krakhmal) - Double.parseDouble(table.get(i).mudLost) * conc_prev) / (totMud[i]);
                }
                conc_prev = calcKrahmal[i];
            }
        }
        conc = null;conc_prev = null;
        for (int i = 0; i < table.size(); i++) {
            if (i == 0) {
                if ((Double.parseDouble(table.get(i).mudMade) - Double.parseDouble(table.get(i).mudLost)) == 0) {
                    calcBarite[i] = 0.0;
                } else {
                    calcBarite[i] = Double.parseDouble(table.get(i).barite) / (Double.parseDouble(table.get(i).mudMade) - Double.parseDouble(table.get(i).mudLost));
                }
                conc_prev = calcBarite[i];
            } else {
                if (totMud[i] == 0) {
                    calcBarite[i] = 0.0;
                } else {
                    calcBarite[i] = (totMud[i - 1] * conc_prev + Double.parseDouble(table.get(i).barite) - Double.parseDouble(table.get(i).mudLost) * conc_prev) / (totMud[i]);
                }
                conc_prev = calcBarite[i];
            }
        }
        conc = null;conc_prev = null;
        for (int i = 0; i < table.size(); i++) {
            if (i == 0) {
                if ((Double.parseDouble(table.get(i).mudMade) - Double.parseDouble(table.get(i).mudLost)) == 0) {
                    calcNaCl[i] = 0.0;
                } else {
                    calcNaCl[i] = Double.parseDouble(table.get(i).naCl) / (Double.parseDouble(table.get(i).mudMade) - Double.parseDouble(table.get(i).mudLost));
                }
                conc_prev = calcNaCl[i];
            } else {
                if (totMud[i] == 0) {
                    calcNaCl[i] = 0.0;
                } else {
                    calcNaCl[i] = (totMud[i - 1] * conc_prev + Double.parseDouble(table.get(i).naCl) - Double.parseDouble(table.get(i).mudLost) * conc_prev) / (totMud[i]);
                }
                conc_prev = calcNaCl[i];
            }
        }
        conc = null;conc_prev = null;
        for (int i = 0; i < table.size(); i++) {
            if (i == 0) {
                if ((Double.parseDouble(table.get(i).mudMade) - Double.parseDouble(table.get(i).mudLost)) == 0) {
                    calcIngib1[i] = 0.0;
                } else {
                    calcIngib1[i] = Double.parseDouble(table.get(i).ingib1) / (Double.parseDouble(table.get(i).mudMade) - Double.parseDouble(table.get(i).mudLost));
                }
                conc_prev = calcIngib1[i];
            } else {
                if (totMud[i] == 0) {
                    calcIngib1[i] = 0.0;
                } else {
                    calcIngib1[i] = (totMud[i - 1] * conc_prev + Double.parseDouble(table.get(i).ingib1) - Double.parseDouble(table.get(i).mudLost) * conc_prev) / (totMud[i]);
                }
                conc_prev = calcIngib1[i];
            }
        }
        conc = null;conc_prev = null;
        for (int i = 0; i < table.size(); i++) {
            if (i == 0) {
                if ((Double.parseDouble(table.get(i).mudMade) - Double.parseDouble(table.get(i).mudLost)) == 0) {
                    calcIngib2[i] = 0.0;
                } else {
                    calcIngib2[i] = Double.parseDouble(table.get(i).ingib2) / (Double.parseDouble(table.get(i).mudMade) - Double.parseDouble(table.get(i).mudLost));
                }
                conc_prev = calcIngib2[i];
            } else {
                if (totMud[i] == 0) {
                    calcIngib2[i] = 0.0;
                } else {
                    calcIngib2[i] = (totMud[i - 1] * conc_prev + Double.parseDouble(table.get(i).ingib2) - Double.parseDouble(table.get(i).mudLost) * conc_prev) / (totMud[i]);
                }
                conc_prev = calcIngib2[i];
            }
        }
        for (int i = 0; i < table.size(); i++) {
             RowTableWBM azaz2 = new RowTableWBM(table.get(i).date, "", "", String.format("%.1f", calcCaCO3[i]),
                    String.format("%.1f", calcLubric[i]), String.format("%.1f", calcKCl[i]), String.format("%.1f", calcKrahmal[i]),
                     String.format("%.1f", calcBarite[i]), String.format("%.1f", calcNaCl[i]), String.format("%.1f", calcIngib1[i]),
                    String.format("%.1f", calcIngib2[i]), "", "");
            tableCalc.add(azaz2);
         }
        dateTab.setCellValueFactory(new PropertyValueFactory<RowTableWBM, String>("date"));
        caCO3Tab.setCellValueFactory(new PropertyValueFactory<RowTableWBM, String>("caCO3"));
        lubricTab.setCellValueFactory(new PropertyValueFactory<RowTableWBM, String>("lubric"));
        kClTab.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<RowTableWBM, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<RowTableWBM, String> param) {
                return new SimpleStringProperty(param.getValue().getkCl());
            }
        });
        krakhmalTab.setCellValueFactory(new PropertyValueFactory<RowTableWBM, String>("krakhmal"));
        bariteTab.setCellValueFactory(new PropertyValueFactory<RowTableWBM, String>("barite"));
        naClTab.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<RowTableWBM, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<RowTableWBM, String> param) {
                return new SimpleStringProperty(param.getValue().getNaCl());
            }
        });
        ingib1Tab.setCellValueFactory(new PropertyValueFactory<RowTableWBM, String>("ingib1"));
        ingib2Tab.setCellValueFactory(new PropertyValueFactory<RowTableWBM, String>("ingib2"));
        dateTab.setStyle("-fx-alignment: CENTER;");
        caCO3Tab.setStyle("-fx-alignment: CENTER;");
        lubricTab.setStyle("-fx-alignment: CENTER;");
        kClTab.setStyle("-fx-alignment: CENTER;");
        krakhmalTab.setStyle("-fx-alignment: CENTER;");
        bariteTab.setStyle("-fx-alignment: CENTER;");
        naClTab.setStyle("-fx-alignment: CENTER;");
        ingib1Tab.setStyle("-fx-alignment: CENTER;");
        ingib2Tab.setStyle("-fx-alignment: CENTER;");
        Table.itemsProperty().setValue(tableCalc);
        
        } catch (SQLException ex) {
            Logger.getLogger(AllCalculationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
   @FXML
    void exportToExcel(ActionEvent event) throws FileNotFoundException, IOException, ParseException {
        Workbook wb=new HSSFWorkbook();
        
        Sheet sheet0 = wb.createSheet("Daily concentration's");
        Row row = sheet0.createRow(0);
        
        CellStyle header = row.getSheet().getWorkbook().createCellStyle();
        header.setBorderBottom(BorderStyle.MEDIUM);
        header.setBorderTop(BorderStyle.MEDIUM);
        header.setBorderRight(BorderStyle.MEDIUM);
        header.setBorderLeft(BorderStyle.MEDIUM);
        header.setAlignment(HorizontalAlignment.CENTER);
        
        CellStyle usualRow = row.getSheet().getWorkbook().createCellStyle();
        usualRow.setBorderBottom(BorderStyle.THIN);
        usualRow.setBorderTop(BorderStyle.THIN);
        usualRow.setBorderRight(BorderStyle.MEDIUM);
        usualRow.setBorderLeft(BorderStyle.MEDIUM);
        usualRow.setAlignment(HorizontalAlignment.CENTER);
        
        CellStyle lastRow = row.getSheet().getWorkbook().createCellStyle();
        lastRow.setBorderBottom(BorderStyle.MEDIUM);
        lastRow.setBorderTop(BorderStyle.THIN);
        lastRow.setBorderRight(BorderStyle.MEDIUM);
        lastRow.setBorderLeft(BorderStyle.MEDIUM);
        lastRow.setAlignment(HorizontalAlignment.CENTER);
        
        
        CellStyle cellStyleDate = row.getSheet().getWorkbook().createCellStyle();
        CreationHelper createHelper = wb.getCreationHelper();
        cellStyleDate.setDataFormat(createHelper.createDataFormat().getFormat("dd-mm-yyyy"));
        cellStyleDate.setAlignment(HorizontalAlignment.CENTER);
        cellStyleDate.setBorderBottom(BorderStyle.THIN);
        cellStyleDate.setBorderTop(BorderStyle.THIN);
        cellStyleDate.setBorderRight(BorderStyle.MEDIUM);
        cellStyleDate.setBorderLeft(BorderStyle.MEDIUM);
        
        CellStyle cellStyleDateLast = row.getSheet().getWorkbook().createCellStyle();
        CreationHelper createHelper2 = wb.getCreationHelper();
        cellStyleDateLast.setDataFormat(createHelper2.createDataFormat().getFormat("dd-mm-yyyy"));
        cellStyleDateLast.setAlignment(HorizontalAlignment.CENTER);
        cellStyleDateLast.setBorderBottom(BorderStyle.MEDIUM);
        cellStyleDateLast.setBorderTop(BorderStyle.THIN);
        cellStyleDateLast.setBorderRight(BorderStyle.MEDIUM);
        cellStyleDateLast.setBorderLeft(BorderStyle.MEDIUM);
        
        Cell cell = row.createCell(0); cell.setCellValue("Date");cell.setCellStyle(header);
        cell=row.createCell(1); cell.setCellValue("CaCO3");cell.setCellStyle(header);
        cell=row.createCell(2); cell.setCellValue("Lubricant");cell.setCellStyle(header);
        cell=row.createCell(3); cell.setCellValue("KCL");cell.setCellStyle(header);
        cell=row.createCell(4); cell.setCellValue("Starch");cell.setCellStyle(header);
        cell=row.createCell(5); cell.setCellValue("Barite");cell.setCellStyle(header);
        cell=row.createCell(6); cell.setCellValue("NaCL");cell.setCellStyle(header);
        cell=row.createCell(7); cell.setCellValue(inhibit1);cell.setCellStyle(header);
        cell=row.createCell(8); cell.setCellValue(inhibit2);cell.setCellStyle(header);
        for (int i=1;i<=tableCalc.size();i++)
        {
            if (i<tableCalc.size())
           {
           row = sheet0.createRow(i);
           DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
           Date date = format.parse(tableCalc.get(i-1).date);
           cell=row.createCell(0);cell.setCellValue(date);cell.setCellStyle(cellStyleDate);
           cell=row.createCell(1);cell.setCellValue(Double.parseDouble(tableCalc.get(i-1).caCO3.replace(',', '.')));cell.setCellStyle(usualRow);
           cell=row.createCell(2);cell.setCellValue(Double.parseDouble(tableCalc.get(i-1).lubric.replace(',', '.')));cell.setCellStyle(usualRow);
           cell=row.createCell(3);cell.setCellValue(Double.parseDouble(tableCalc.get(i-1).kCl.replace(',', '.')));cell.setCellStyle(usualRow);
           cell=row.createCell(4);cell.setCellValue(Double.parseDouble(tableCalc.get(i-1).krakhmal.replace(',', '.')));cell.setCellStyle(usualRow);
           cell=row.createCell(5);cell.setCellValue(Double.parseDouble(tableCalc.get(i-1).barite.replace(',', '.')));cell.setCellStyle(usualRow);
           cell=row.createCell(6);cell.setCellValue(Double.parseDouble(tableCalc.get(i-1).naCl.replace(',', '.')));cell.setCellStyle(usualRow);
           cell=row.createCell(7);cell.setCellValue(Double.parseDouble(tableCalc.get(i-1).ingib1.replace(',', '.')));cell.setCellStyle(usualRow);
           cell=row.createCell(8);cell.setCellValue(Double.parseDouble(tableCalc.get(i-1).ingib2.replace(',', '.')));cell.setCellStyle(usualRow);
           }
           else
           {
               row = sheet0.createRow(i);
           DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
           Date date = format.parse(tableCalc.get(i-1).date);
           cell=row.createCell(0);cell.setCellValue(date);cell.setCellStyle(cellStyleDateLast);
           cell=row.createCell(1);cell.setCellValue(Double.parseDouble(tableCalc.get(i-1).caCO3.replace(',', '.')));cell.setCellStyle(lastRow);
           cell=row.createCell(2);cell.setCellValue(Double.parseDouble(tableCalc.get(i-1).lubric.replace(',', '.')));cell.setCellStyle(lastRow);
           cell=row.createCell(3);cell.setCellValue(Double.parseDouble(tableCalc.get(i-1).kCl.replace(',', '.')));cell.setCellStyle(lastRow);
           cell=row.createCell(4);cell.setCellValue(Double.parseDouble(tableCalc.get(i-1).krakhmal.replace(',', '.')));cell.setCellStyle(lastRow);
           cell=row.createCell(5);cell.setCellValue(Double.parseDouble(tableCalc.get(i-1).barite.replace(',', '.')));cell.setCellStyle(lastRow);
           cell=row.createCell(6);cell.setCellValue(Double.parseDouble(tableCalc.get(i-1).naCl.replace(',', '.')));cell.setCellStyle(lastRow);
           cell=row.createCell(7);cell.setCellValue(Double.parseDouble(tableCalc.get(i-1).ingib1.replace(',', '.')));cell.setCellStyle(lastRow);
           cell=row.createCell(8);cell.setCellValue(Double.parseDouble(tableCalc.get(i-1).ingib2.replace(',', '.')));cell.setCellStyle(lastRow);
           }
        }
        for (int i=0;i<9;i++)
        sheet0.autoSizeColumn(i);
        File dir = new File("C:/MudControlData/Concentration's_Report's/");
        if (dir.exists()){}
        else {dir.mkdir();};
        String filename="C:/MudControlData/Concentration's_Report's/"+Well.getText()+".xls";
        FileOutputStream fos = new FileOutputStream(filename);
        wb.write(fos);
        fos.close();
        File file = new File(filename);
        Desktop.getDesktop().open(file);
    }

    
    @FXML
    void plotData(ActionEvent event) throws InterruptedException{
        Stage primaryStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader();
            Parent root2 = loader.load(getClass().getResource("/application/ConcentrationGraphsWBM.fxml").openStream());
            ConcentrationGraphsWBMController concGraphWBM = loader.getController();
            concGraphWBM.dataGather(tableCalc,inhibit1,inhibit2);
            Scene scene2 = new Scene(root2);
            scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene2);
            primaryStage.setResizable(true);
            primaryStage.setTitle("Concentrations");
            primaryStage.getIcons().add(new Image ("/misc/MudControl.png"));
            primaryStage.showAndWait();
            
            
                        
        } catch (IOException e) {
        }
    }

    
    @FXML
    void close(ActionEvent event) {
         Stage stage = (Stage) close.getScene().getWindow();
         stage.close();
    }
}
