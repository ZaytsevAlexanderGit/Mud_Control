package application;

import java.time.LocalDate;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import org.apache.commons.lang3.math.NumberUtils;

public class ConcentrationGraphsWBMController {

    @FXML
    private LineChart<String, Number> graphicChart;
    @FXML
    private CheckBox caCO3;
    @FXML
    private CheckBox lubricant;
    @FXML
    private CheckBox kCl;
    @FXML
    private CheckBox starch;
    @FXML
    private CheckBox barite;
    @FXML
    private CheckBox reference;
    @FXML
    private CheckBox naCl;
    @FXML
    private CheckBox ingibitor1;
    @FXML
    private CheckBox ingibitor2;
    @FXML
    private Button plot;
    @FXML
    private DatePicker dateFrom;
    @FXML
    private DatePicker dateTo;
    @FXML
    private TextField referenceLine;
    
    String inhibitor1;String inhibitor2;
    
    LocalDate dateStart;LocalDate dateEnd;LocalDate date;
    
    ObservableList<RowTableWBM> tablePlot;

    @FXML
    void plotData(ActionEvent event) {
        graphicChart.getData().clear();
        graphicChart.setAnimated(false);
       
        if (caCO3.isSelected())
        {
            XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
            for (int i=0;i<tablePlot.size();i++)
            {
                date=LocalDate.parse(tablePlot.get(i).date);
                if (!(date.isBefore(dateFrom.getValue())||date.isAfter(dateTo.getValue())))
                series.getData().add(new XYChart.Data<String, Number>(tablePlot.get(i).getDate(),Double.parseDouble(tablePlot.get(i).getCaCO3().replace(',', '.'))));                
            }
            series.setName("CaCO3");
            graphicChart.getData().add(series);
//            Node line = series.getNode().lookup(".chart-series-line");line.setStyle("-fx-stroke: blue");
//            Node symbol = series.getNode().lookup(".chart-symbol");
//            symbol.setStyle("-fx-background-color: blue");
            for (final XYChart.Data<String, Number> data : series.getData())
            {
                data.getNode().addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent event) 
                    {
                        Tooltip.install(data.getNode(), new Tooltip("Date :" + data.getXValue()+"\nCaCO3:"+data.getYValue().toString()));
                    }
                }
                );
            }
            
        }
        if (lubricant.isSelected())
        {
            XYChart.Series<String, Number> series2 = new XYChart.Series<String, Number>();
            for (int i=0;i<tablePlot.size();i++)
            {
                date=LocalDate.parse(tablePlot.get(i).date);
                if (!(date.isBefore(dateFrom.getValue())||date.isAfter(dateTo.getValue())))
                series2.getData().add(new XYChart.Data<String, Number>(tablePlot.get(i).getDate(),Double.parseDouble(tablePlot.get(i).getLubric().replace(',', '.'))));
            }
            series2.setName("Lubricant");
            graphicChart.getData().add(series2);
            for (final XYChart.Data<String, Number> data : series2.getData())
            {
                data.getNode().addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent event) 
                    {
                        Tooltip.install(data.getNode(), new Tooltip("Date :" + data.getXValue()+"\nLubricant:"+data.getYValue().toString()));
                    }
                }
                );
            }
        }
        if (kCl.isSelected())
        {
            XYChart.Series<String, Number> series3 = new XYChart.Series<String, Number>();
            for (int i=0;i<tablePlot.size();i++)
            {
                date=LocalDate.parse(tablePlot.get(i).date);
                if (!(date.isBefore(dateFrom.getValue())||date.isAfter(dateTo.getValue())))
                series3.getData().add(new XYChart.Data<String, Number>(tablePlot.get(i).getDate(),Double.parseDouble(tablePlot.get(i).getkCl().replace(',', '.'))));
            }
            series3.setName("KCL");
            graphicChart.getData().add(series3);
            for (final XYChart.Data<String, Number> data : series3.getData())
            {
                data.getNode().addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent event) 
                    {
                        Tooltip.install(data.getNode(), new Tooltip("Date :" + data.getXValue()+"\nKCL:"+data.getYValue().toString()));
                    }
                }
                );
            }
        }
        if (starch.isSelected())
        {
            XYChart.Series<String, Number> series4 = new XYChart.Series<String, Number>();
            for (int i=0;i<tablePlot.size();i++)
            {
                date=LocalDate.parse(tablePlot.get(i).date);
                if (!(date.isBefore(dateFrom.getValue())||date.isAfter(dateTo.getValue())))
                series4.getData().add(new XYChart.Data<String, Number>(tablePlot.get(i).getDate(),Double.parseDouble(tablePlot.get(i).getKrakhmal().replace(',', '.'))));
            }
            series4.setName("Starch");
            graphicChart.getData().add(series4);
            for (final XYChart.Data<String, Number> data : series4.getData())
            {
                data.getNode().addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent event) 
                    {
                        Tooltip.install(data.getNode(), new Tooltip("Date :" + data.getXValue()+"\nStarch:"+data.getYValue().toString()));
                    }
                }
                );
            }
        }
        if (barite.isSelected())
        {
            XYChart.Series<String, Number> series5 = new XYChart.Series<String, Number>();
            for (int i=0;i<tablePlot.size();i++)
            {
                date=LocalDate.parse(tablePlot.get(i).date);
                if (!(date.isBefore(dateFrom.getValue())||date.isAfter(dateTo.getValue())))
                series5.getData().add(new XYChart.Data<String, Number>(tablePlot.get(i).getDate(),Double.parseDouble(tablePlot.get(i).getBarite().replace(',', '.'))));
            }
            series5.setName("Barite");
            graphicChart.getData().add(series5);
            for (final XYChart.Data<String, Number> data : series5.getData())
            {
                data.getNode().addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent event) 
                    {
                        Tooltip.install(data.getNode(), new Tooltip("Date :" + data.getXValue()+"\nBarite:"+data.getYValue().toString()));
                    }
                }
                );
            }
        }
        if (naCl.isSelected())
        {
            XYChart.Series<String, Number> series6 = new XYChart.Series<String, Number>();
            for (int i=0;i<tablePlot.size();i++)
            {
                date=LocalDate.parse(tablePlot.get(i).date);
                if (!(date.isBefore(dateFrom.getValue())||date.isAfter(dateTo.getValue())))
                series6.getData().add(new XYChart.Data<String, Number>(tablePlot.get(i).getDate(),Double.parseDouble(tablePlot.get(i).getNaCl().replace(',', '.'))));
            }
            series6.setName("NaCL");
            graphicChart.getData().add(series6);
            for (final XYChart.Data<String, Number> data : series6.getData())
            {
                data.getNode().addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent event) 
                    {
                        Tooltip.install(data.getNode(), new Tooltip("Date :" + data.getXValue()+"\nNaCL:"+data.getYValue().toString()));
                    }
                }
                );
            }
        }
        if (ingibitor1.isSelected())
        {
            XYChart.Series<String, Number> series7 = new XYChart.Series<String, Number>();
            for (int i=0;i<tablePlot.size();i++)
            {
                date=LocalDate.parse(tablePlot.get(i).date);
                if (!(date.isBefore(dateFrom.getValue())||date.isAfter(dateTo.getValue())))
                series7.getData().add(new XYChart.Data<String, Number>(tablePlot.get(i).getDate(),Double.parseDouble(tablePlot.get(i).getIngib1().replace(',', '.'))));
            }
            series7.setName(inhibitor1);
            graphicChart.getData().add(series7);
            for (final XYChart.Data<String, Number> data : series7.getData())
            {
                data.getNode().addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent event) 
                    {
                        Tooltip.install(data.getNode(), new Tooltip("Date :" + data.getXValue()+"\n"+inhibitor1+":"+data.getYValue().toString()));
                    }
                }
                );
            }
        }
        if (ingibitor2.isSelected())
        {
            XYChart.Series<String, Number> series8 = new XYChart.Series<String, Number>();
            for (int i=0;i<tablePlot.size();i++)
            {
                date=LocalDate.parse(tablePlot.get(i).date);
                if (!(date.isBefore(dateFrom.getValue())||date.isAfter(dateTo.getValue())))
                series8.getData().add(new XYChart.Data<String, Number>(tablePlot.get(i).getDate(),Double.parseDouble(tablePlot.get(i).getIngib2().replace(',', '.'))));
            }
            series8.setName(inhibitor2);
            graphicChart.getData().add(series8);
            for (final XYChart.Data<String, Number> data : series8.getData())
            {
                data.getNode().addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent event) 
                    {
                        Tooltip.install(data.getNode(), new Tooltip("Date :" + data.getXValue()+"\n"+inhibitor2+":"+data.getYValue().toString()));
                    }
                }
                );
            }
        }
         if (reference.isSelected())
        {
            XYChart.Series<String, Number> series9 = new XYChart.Series<String, Number>();
            if (NumberUtils.isCreatable(referenceLine.getText()))
            {
                for (int i=0;i<tablePlot.size();i++)
                    {
                        date=LocalDate.parse(tablePlot.get(i).date);
                        if (!(date.isBefore(dateFrom.getValue())||date.isAfter(dateTo.getValue())))
                        series9.getData().add(new XYChart.Data<String, Number>(tablePlot.get(i).getDate(),Double.parseDouble(referenceLine.getText().replace(',', '.'))));
                    }
                series9.setName("Program");
                graphicChart.getData().add(series9);
            }
            else
                    {
                        Alert a1 = new Alert(Alert.AlertType.ERROR);
                       a1.setHeaderText("Please input number");
                       a1.setTitle("ERROR");
                       a1.setResizable(true);
                       a1.getDialogPane().setPrefSize(200, 70);
                       a1.showAndWait();
                    }
        }
    }

    public void dataGather(ObservableList<RowTableWBM> tableCalc, String inhib1,String inhib2){
        WaterBaseMudController app = new WaterBaseMudController();
        tablePlot=tableCalc;
        int i=0;inhibitor1=inhib1;inhibitor2=inhib2;
        ingibitor1.setText(inhib1);ingibitor2.setText(inhib2);
        LocalDate localDate1;LocalDate localDate2;
        while (i<tablePlot.size()-1)
        {
            localDate1=LocalDate.parse(tablePlot.get(i).date);
            localDate2=LocalDate.parse(tablePlot.get(i+1).date);
            if ((localDate1.until(localDate2).getYears() > 0) || (localDate1.until(localDate2).getMonths() > 0) || (localDate1.until(localDate2).getDays() > 1)) 
            {
                RowTableWBM azaz2 = new RowTableWBM(localDate1.plusDays(1).toString(), "", "", "0","0", "0", "0","0", "0", "0","0", "", "");
                tablePlot.add(i+1,azaz2);
            }
            i++;
        }
        dateStart=LocalDate.parse(tablePlot.get(0).date);
        dateEnd=LocalDate.parse(tablePlot.get(tablePlot.size()-1).date);
        dateFrom.setValue(dateStart);
        dateFrom.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    setDisable(date.isBefore(dateStart)||date.isAfter(dateEnd)||date.isAfter(dateTo.getValue()));            }
                    };
                }
            });
        dateTo.setValue(dateEnd);
        dateTo.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    setDisable(date.isBefore(dateStart)||date.isAfter(dateEnd)||date.isBefore(dateFrom.getValue()));            }
                    };
                }
            });
         referenceLine.setText("0");
    }
    
    
}