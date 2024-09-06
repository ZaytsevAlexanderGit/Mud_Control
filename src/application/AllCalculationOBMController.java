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


public class AllCalculationOBMController implements Initializable {

    @FXML
    private TextField Location;
    @FXML
    private TextField Well;
    @FXML
    private TableView<RowTableOBM> Table;
    @FXML
    private TableColumn<RowTableOBM, String> dateTab;
    @FXML
    private TableColumn<RowTableOBM, String> caCO3Tab;
    @FXML
    private TableColumn<RowTableOBM, String> baseOilTab;
    @FXML
    private TableColumn<RowTableOBM, String> caCl2Tab;
    @FXML
    private TableColumn<RowTableOBM, String> emulsifierTab;
    @FXML
    private TableColumn<RowTableOBM, String> versaTrolTab;
    @FXML
    private TableColumn<RowTableOBM, String> bariteTab;
    @FXML
    private TableColumn<RowTableOBM, String> weightAgentTab;
    @FXML
    private TableColumn<RowTableOBM, String> clayTab;
    @FXML
    private TableColumn<RowTableOBM, String> limeTab;
    @FXML
    private Button close;
    @FXML
    private Button excel;
    @FXML
    private Button plot;


    ObservableList<RowTableOBM> table = FXCollections.observableArrayList();
    ObservableList<RowTableOBM> tableCalc = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    
    
    public void showTable(String loc, String well){
        Location.setText(loc);Well.setText(well);
        OilBaseMudController app = new OilBaseMudController();
        ResultSet rs;
        try {
            rs = app.chooseWellsFromDb(loc, well);
            String a = new String();
        String[] repData = new String[8];
        table.clear();
        while (rs.next()) {
            RowTableOBM azaz = new RowTableOBM(rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),
                    rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12),
                    rs.getString(13), rs.getString(14), "");
             if ((!azaz.getDate().isEmpty()) && (!azaz.getDate().equals("0.0")) && (rs.getInt(15) == 1)) {
                table.add(azaz);}
           
        }
        int flag = 0;
        int flag2 = 0;
        RowTableOBM azaz;
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
//        Double nul=0.0;
        Double[] calcCaCO3 = new Double[table.size()];Double[] calcBaseOil = new Double[table.size()];
        Double[] calcCaCl2 = new Double[table.size()];Double[] calcEmulsifier = new Double[table.size()];
        Double[] calcVersaTrol = new Double[table.size()];Double[] calcBarite = new Double[table.size()];
        Double[] calcWeightAgent = new Double[table.size()];Double[] calcClay = new Double[table.size()];
        Double[] calcLime = new Double[table.size()];
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
                    calcBaseOil[i] = 0.0;
                } else {
                    calcBaseOil[i] = Double.parseDouble(table.get(i).baseOil) / (Double.parseDouble(table.get(i).mudMade) - Double.parseDouble(table.get(i).mudLost));
                }
                conc_prev = calcBaseOil[i];
            } else {
                if (totMud[i] == 0) {
                    calcBaseOil[i] = 0.0;
                } else {
                    calcBaseOil[i] = (totMud[i - 1] * conc_prev + Double.parseDouble(table.get(i).baseOil) - Double.parseDouble(table.get(i).mudLost) * conc_prev) / (totMud[i]);
                }
                conc_prev = calcBaseOil[i];
            }
        }
        conc = null;conc_prev = null;
        for (int i = 0; i < table.size(); i++) {
            if (i == 0) {
                if ((Double.parseDouble(table.get(i).mudMade) - Double.parseDouble(table.get(i).mudLost)) == 0) {
                    calcCaCl2[i] = 0.0;
                } else {
                    calcCaCl2[i] = Double.parseDouble(table.get(i).caCl2) / (Double.parseDouble(table.get(i).mudMade) - Double.parseDouble(table.get(i).mudLost));
                }
                conc_prev = calcCaCl2[i];
            } else {
                if (totMud[i] == 0) {
                    calcCaCl2[i] = 0.0;
                } else {
                    calcCaCl2[i] = (totMud[i - 1] * conc_prev + Double.parseDouble(table.get(i).caCl2) - Double.parseDouble(table.get(i).mudLost) * conc_prev) / (totMud[i]);
                }
                conc_prev = calcCaCl2[i];
            }
        }
        conc = null;conc_prev = null;
        for (int i = 0; i < table.size(); i++) {
            if (i == 0) {
                if ((Double.parseDouble(table.get(i).mudMade) - Double.parseDouble(table.get(i).mudLost)) == 0) {
                    calcEmulsifier[i] = 0.0;
                } else {
                    calcEmulsifier[i] = Double.parseDouble(table.get(i).emilsifier) / (Double.parseDouble(table.get(i).mudMade) - Double.parseDouble(table.get(i).mudLost));
                }
                conc_prev = calcEmulsifier[i];
            } else {
                if (totMud[i] == 0) {
                    calcEmulsifier[i] = 0.0;
                } else {
                    calcEmulsifier[i] = (totMud[i - 1] * conc_prev + Double.parseDouble(table.get(i).emilsifier) - Double.parseDouble(table.get(i).mudLost) * conc_prev) / (totMud[i]);
                }
                conc_prev = calcEmulsifier[i];
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
                    calcVersaTrol[i] = 0.0;
                } else {
                    calcVersaTrol[i] = Double.parseDouble(table.get(i).versaTrol) / (Double.parseDouble(table.get(i).mudMade) - Double.parseDouble(table.get(i).mudLost));
                }
                conc_prev = calcVersaTrol[i];
            } else {
                if (totMud[i] == 0) {
                    calcVersaTrol[i] = 0.0;
                } else {
                    calcVersaTrol[i] = (totMud[i - 1] * conc_prev + Double.parseDouble(table.get(i).versaTrol) - Double.parseDouble(table.get(i).mudLost) * conc_prev) / (totMud[i]);
                }
                conc_prev = calcVersaTrol[i];
            }
        }
        conc = null;conc_prev = null;
        for (int i = 0; i < table.size(); i++) {
            if (i == 0) {
                if ((Double.parseDouble(table.get(i).mudMade) - Double.parseDouble(table.get(i).mudLost)) == 0) {
                    calcWeightAgent[i] = 0.0;
                } else {
                    calcWeightAgent[i] = Double.parseDouble(table.get(i).weigthingAgent) / (Double.parseDouble(table.get(i).mudMade) - Double.parseDouble(table.get(i).mudLost));
                }
                conc_prev = calcWeightAgent[i];
            } else {
                if (totMud[i] == 0) {
                    calcWeightAgent[i] = 0.0;
                } else {
                    calcWeightAgent[i] = (totMud[i - 1] * conc_prev + Double.parseDouble(table.get(i).weigthingAgent) - Double.parseDouble(table.get(i).mudLost) * conc_prev) / (totMud[i]);
                }
                conc_prev = calcWeightAgent[i];
            }
        }
        conc = null;conc_prev = null;
        for (int i = 0; i < table.size(); i++) {
            if (i == 0) {
                if ((Double.parseDouble(table.get(i).mudMade) - Double.parseDouble(table.get(i).mudLost)) == 0) {
                    calcClay[i] = 0.0;
                } else {
                    calcClay[i] = Double.parseDouble(table.get(i).clay) / (Double.parseDouble(table.get(i).mudMade) - Double.parseDouble(table.get(i).mudLost));
                }
                conc_prev = calcClay[i];
            } else {
                if (totMud[i] == 0) {
                    calcClay[i] = 0.0;
                } else {
                    calcClay[i] = (totMud[i - 1] * conc_prev + Double.parseDouble(table.get(i).clay) - Double.parseDouble(table.get(i).mudLost) * conc_prev) / (totMud[i]);
                }
                conc_prev = calcClay[i];
            }
        }
        conc = null;conc_prev = null;
        for (int i = 0; i < table.size(); i++) {
            if (i == 0) {
                if ((Double.parseDouble(table.get(i).mudMade) - Double.parseDouble(table.get(i).mudLost)) == 0) {
                    calcLime[i] = 0.0;
                } else {
                    calcLime[i] = Double.parseDouble(table.get(i).lime) / (Double.parseDouble(table.get(i).mudMade) - Double.parseDouble(table.get(i).mudLost));
                }
                conc_prev = calcLime[i];
            } else {
                if (totMud[i] == 0) {
                    calcLime[i] = 0.0;
                } else {
                    calcLime[i] = (totMud[i - 1] * conc_prev + Double.parseDouble(table.get(i).lime) - Double.parseDouble(table.get(i).mudLost) * conc_prev) / (totMud[i]);
                }
                conc_prev = calcLime[i];
            }
        }
        for (int i = 0; i < table.size(); i++) {
             RowTableOBM azaz2 = new RowTableOBM(table.get(i).date, "", "", String.format("%.1f", calcCaCO3[i]),
                    String.format("%.1f", calcBaseOil[i]), String.format("%.1f", calcCaCl2[i]), String.format("%.1f", calcEmulsifier[i]),
                     String.format("%.1f", calcVersaTrol[i]), String.format("%.1f", calcBarite[i]), String.format("%.1f", calcWeightAgent[i]),
                    String.format("%.1f", calcClay[i]), String.format("%.1f", calcLime[i]), "");
            tableCalc.add(azaz2);
         }
        dateTab.setCellValueFactory(new PropertyValueFactory<RowTableOBM, String>("date"));
        caCO3Tab.setCellValueFactory(new PropertyValueFactory<RowTableOBM, String>("caCO3"));
        baseOilTab.setCellValueFactory(new PropertyValueFactory<RowTableOBM, String>("baseOil"));
        caCl2Tab.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<RowTableOBM, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<RowTableOBM, String> param) {
                return new SimpleStringProperty(param.getValue().getCaCl2());
            }
        });
        emulsifierTab.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<RowTableOBM, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<RowTableOBM, String> param) {
                return new SimpleStringProperty(param.getValue().getEmilsifier());
            }
        });
        versaTrolTab.setCellValueFactory(new PropertyValueFactory<RowTableOBM, String>("versaTrol"));
        bariteTab.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<RowTableOBM, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<RowTableOBM, String> param) {
                return new SimpleStringProperty(param.getValue().getBarite());
            }
        });
        weightAgentTab.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<RowTableOBM, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<RowTableOBM, String> param) {
                return new SimpleStringProperty(param.getValue().getWeigthingAgent());
            }
        });
        clayTab.setCellValueFactory(new PropertyValueFactory<RowTableOBM, String>("clay"));
        limeTab.setCellValueFactory(new PropertyValueFactory<RowTableOBM, String>("lime"));
        dateTab.setStyle("-fx-alignment: CENTER;");
        caCO3Tab.setStyle("-fx-alignment: CENTER;");
        baseOilTab.setStyle("-fx-alignment: CENTER;");
        caCl2Tab.setStyle("-fx-alignment: CENTER;");
        emulsifierTab.setStyle("-fx-alignment: CENTER;");
        versaTrolTab.setStyle("-fx-alignment: CENTER;");
        bariteTab.setStyle("-fx-alignment: CENTER;");
        weightAgentTab.setStyle("-fx-alignment: CENTER;");
        clayTab.setStyle("-fx-alignment: CENTER;");
        limeTab.setStyle("-fx-alignment: CENTER;");
        Table.itemsProperty().setValue(tableCalc);
        
        } catch (SQLException ex) {
            Logger.getLogger(AllCalculationOBMController.class.getName()).log(Level.SEVERE, null, ex);
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
        cell=row.createCell(2); cell.setCellValue("BaseOil");cell.setCellStyle(header);
        cell=row.createCell(3); cell.setCellValue("CaCL2");cell.setCellStyle(header);
        cell=row.createCell(4); cell.setCellValue("Emulsifier");cell.setCellStyle(header);
        cell=row.createCell(5); cell.setCellValue("VersaTrol");cell.setCellStyle(header);
        cell=row.createCell(6); cell.setCellValue("Barite");cell.setCellStyle(header);
        cell=row.createCell(7); cell.setCellValue("Wetting Agent");cell.setCellStyle(header);
        cell=row.createCell(8); cell.setCellValue("Clay");cell.setCellStyle(header);
        cell=row.createCell(9); cell.setCellValue("Lime");cell.setCellStyle(header);
        for (int i=1;i<=tableCalc.size();i++)
        {
           if (i<tableCalc.size())
           {
           row = sheet0.createRow(i);
           DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
           Date date = format.parse(tableCalc.get(i-1).date);
           cell=row.createCell(0);cell.setCellValue(date);cell.setCellStyle(cellStyleDate);
           cell=row.createCell(1);cell.setCellValue(Double.parseDouble(tableCalc.get(i-1).caCO3.replace(',', '.')));cell.setCellStyle(usualRow);
           cell=row.createCell(2);cell.setCellValue(Double.parseDouble(tableCalc.get(i-1).baseOil.replace(',', '.')));cell.setCellStyle(usualRow);
           cell=row.createCell(3);cell.setCellValue(Double.parseDouble(tableCalc.get(i-1).caCl2.replace(',', '.')));cell.setCellStyle(usualRow);
           cell=row.createCell(4);cell.setCellValue(Double.parseDouble(tableCalc.get(i-1).emilsifier.replace(',', '.')));cell.setCellStyle(usualRow);
           cell=row.createCell(5);cell.setCellValue(Double.parseDouble(tableCalc.get(i-1).versaTrol.replace(',', '.')));cell.setCellStyle(usualRow);
           cell=row.createCell(6);cell.setCellValue(Double.parseDouble(tableCalc.get(i-1).barite.replace(',', '.')));cell.setCellStyle(usualRow);
           cell=row.createCell(7);cell.setCellValue(Double.parseDouble(tableCalc.get(i-1).weigthingAgent.replace(',', '.')));cell.setCellStyle(usualRow);
           cell=row.createCell(8);cell.setCellValue(Double.parseDouble(tableCalc.get(i-1).clay.replace(',', '.')));cell.setCellStyle(usualRow);
           cell=row.createCell(9);cell.setCellValue(Double.parseDouble(tableCalc.get(i-1).lime.replace(',', '.')));cell.setCellStyle(usualRow);}
           else
           {
               row = sheet0.createRow(i);
           DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
           Date date = format.parse(tableCalc.get(i-1).date);
           cell=row.createCell(0);cell.setCellValue(date);cell.setCellStyle(cellStyleDateLast);
           cell=row.createCell(1);cell.setCellValue(Double.parseDouble(tableCalc.get(i-1).caCO3.replace(',', '.')));cell.setCellStyle(lastRow);
           cell=row.createCell(2);cell.setCellValue(Double.parseDouble(tableCalc.get(i-1).baseOil.replace(',', '.')));cell.setCellStyle(lastRow);
           cell=row.createCell(3);cell.setCellValue(Double.parseDouble(tableCalc.get(i-1).caCl2.replace(',', '.')));cell.setCellStyle(lastRow);
           cell=row.createCell(4);cell.setCellValue(Double.parseDouble(tableCalc.get(i-1).emilsifier.replace(',', '.')));cell.setCellStyle(lastRow);
           cell=row.createCell(5);cell.setCellValue(Double.parseDouble(tableCalc.get(i-1).versaTrol.replace(',', '.')));cell.setCellStyle(lastRow);
           cell=row.createCell(6);cell.setCellValue(Double.parseDouble(tableCalc.get(i-1).barite.replace(',', '.')));cell.setCellStyle(lastRow);
           cell=row.createCell(7);cell.setCellValue(Double.parseDouble(tableCalc.get(i-1).weigthingAgent.replace(',', '.')));cell.setCellStyle(lastRow);
           cell=row.createCell(8);cell.setCellValue(Double.parseDouble(tableCalc.get(i-1).clay.replace(',', '.')));cell.setCellStyle(lastRow);
           cell=row.createCell(9);cell.setCellValue(Double.parseDouble(tableCalc.get(i-1).lime.replace(',', '.')));cell.setCellStyle(lastRow);
           }
        }
        for (int i=0;i<10;i++)
        sheet0.autoSizeColumn(i);
        File dir = new File("D:/MudControlData/Concentration's_Report's/");
        if (dir.exists()){}
        else {dir.mkdir();};
        String filename="D:/MudControlData/Concentration's_Report's/"+Well.getText()+".xls";
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
            Parent root2 = loader.load(getClass().getResource("/application/ConcentrationGraphsOBM.fxml").openStream());
            ConcentrationGraphsOBMController concGraphOBM = loader.getController();
            concGraphOBM.dataGather(tableCalc);
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
