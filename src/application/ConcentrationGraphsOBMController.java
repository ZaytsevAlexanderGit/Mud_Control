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

public class ConcentrationGraphsOBMController {

    @FXML
    private LineChart<String, Number> graphicChart;
    @FXML
    private CheckBox caCO3;
    @FXML
    private CheckBox baseOil;
    @FXML
    private CheckBox caCL2;
    @FXML
    private CheckBox emulsifier;
    @FXML
    private CheckBox versaTrol;
    @FXML
    private CheckBox barite;
    @FXML
    private CheckBox wettingAgent;
    @FXML
    private CheckBox lime;
    @FXML
    private CheckBox clay;
    @FXML
    private CheckBox reference;
    @FXML
    private Button plot;
    @FXML
    private DatePicker dateFrom;
    @FXML
    private DatePicker dateTo;
    @FXML
    private TextField referenceLine;
    
    LocalDate dateStart;LocalDate dateEnd;LocalDate date;
    
    ObservableList<RowTableOBM> tablePlot;

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
        if (baseOil.isSelected())
        {
            XYChart.Series<String, Number> series2 = new XYChart.Series<String, Number>();
            for (int i=0;i<tablePlot.size();i++)
            {
                date=LocalDate.parse(tablePlot.get(i).date);
                if (!(date.isBefore(dateFrom.getValue())||date.isAfter(dateTo.getValue())))
                series2.getData().add(new XYChart.Data<String, Number>(tablePlot.get(i).getDate(),Double.parseDouble(tablePlot.get(i).getBaseOil().replace(',', '.'))));
            }
            series2.setName("BaseOil");
            graphicChart.getData().add(series2);
            for (final XYChart.Data<String, Number> data : series2.getData())
            {
                data.getNode().addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent event) 
                    {
                        Tooltip.install(data.getNode(), new Tooltip("Date :" + data.getXValue()+"\nBaseOil:"+data.getYValue().toString()));
                    }
                }
                );
            }
        }
        if (caCL2.isSelected())
        {
            XYChart.Series<String, Number> series3 = new XYChart.Series<String, Number>();
            for (int i=0;i<tablePlot.size();i++)
            {
                date=LocalDate.parse(tablePlot.get(i).date);
                if (!(date.isBefore(dateFrom.getValue())||date.isAfter(dateTo.getValue())))
                series3.getData().add(new XYChart.Data<String, Number>(tablePlot.get(i).getDate(),Double.parseDouble(tablePlot.get(i).getCaCl2().replace(',', '.'))));
            }
            series3.setName("CaCL2");
            graphicChart.getData().add(series3);
            for (final XYChart.Data<String, Number> data : series3.getData())
            {
                data.getNode().addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent event) 
                    {
                        Tooltip.install(data.getNode(), new Tooltip("Date :" + data.getXValue()+"\nCaCL2:"+data.getYValue().toString()));
                    }
                }
                );
            }
        }
        if (emulsifier.isSelected())
        {
            XYChart.Series<String, Number> series4 = new XYChart.Series<String, Number>();
            for (int i=0;i<tablePlot.size();i++)
            {
                date=LocalDate.parse(tablePlot.get(i).date);
                if (!(date.isBefore(dateFrom.getValue())||date.isAfter(dateTo.getValue())))
                series4.getData().add(new XYChart.Data<String, Number>(tablePlot.get(i).getDate(),Double.parseDouble(tablePlot.get(i).getEmilsifier().replace(',', '.'))));
            }
            series4.setName("Emulsifier");
            graphicChart.getData().add(series4);
            for (final XYChart.Data<String, Number> data : series4.getData())
            {
                data.getNode().addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent event) 
                    {
                        Tooltip.install(data.getNode(), new Tooltip("Date :" + data.getXValue()+"\nEmulsifier:"+data.getYValue().toString()));
                    }
                }
                );
            }
        }
        if (versaTrol.isSelected())
        {
            XYChart.Series<String, Number> series5 = new XYChart.Series<String, Number>();
            for (int i=0;i<tablePlot.size();i++)
            {
                date=LocalDate.parse(tablePlot.get(i).date);
                if (!(date.isBefore(dateFrom.getValue())||date.isAfter(dateTo.getValue())))
                series5.getData().add(new XYChart.Data<String, Number>(tablePlot.get(i).getDate(),Double.parseDouble(tablePlot.get(i).getVersaTrol().replace(',', '.'))));
            }
            series5.setName("VersaTrol");
            graphicChart.getData().add(series5);
            for (final XYChart.Data<String, Number> data : series5.getData())
            {
                data.getNode().addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent event) 
                    {
                        Tooltip.install(data.getNode(), new Tooltip("Date :" + data.getXValue()+"\nVersaTrol:"+data.getYValue().toString()));
                    }
                }
                );
            }
        }
        if (barite.isSelected())
        {
            XYChart.Series<String, Number> series6 = new XYChart.Series<String, Number>();
            for (int i=0;i<tablePlot.size();i++)
            {
                date=LocalDate.parse(tablePlot.get(i).date);
                if (!(date.isBefore(dateFrom.getValue())||date.isAfter(dateTo.getValue())))
                series6.getData().add(new XYChart.Data<String, Number>(tablePlot.get(i).getDate(),Double.parseDouble(tablePlot.get(i).getBarite().replace(',', '.'))));
            }
            series6.setName("Barite");
            graphicChart.getData().add(series6);
            for (final XYChart.Data<String, Number> data : series6.getData())
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
        if (wettingAgent.isSelected())
        {
            XYChart.Series<String, Number> series7 = new XYChart.Series<String, Number>();
            for (int i=0;i<tablePlot.size();i++)
            {
                date=LocalDate.parse(tablePlot.get(i).date);
                if (!(date.isBefore(dateFrom.getValue())||date.isAfter(dateTo.getValue())))
                series7.getData().add(new XYChart.Data<String, Number>(tablePlot.get(i).getDate(),Double.parseDouble(tablePlot.get(i).getWeigthingAgent().replace(',', '.'))));
            }
            series7.setName("Weting Agent");
            graphicChart.getData().add(series7);
            for (final XYChart.Data<String, Number> data : series7.getData())
            {
                data.getNode().addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent event) 
                    {
                        Tooltip.install(data.getNode(), new Tooltip("Date :" + data.getXValue()+"\nWeting Agent:"+data.getYValue().toString()));
                    }
                }
                );
            }
        }
        if (clay.isSelected())
        {
            XYChart.Series<String, Number> series8 = new XYChart.Series<String, Number>();
            for (int i=0;i<tablePlot.size();i++)
            {
                date=LocalDate.parse(tablePlot.get(i).date);
                if (!(date.isBefore(dateFrom.getValue())||date.isAfter(dateTo.getValue())))
                series8.getData().add(new XYChart.Data<String, Number>(tablePlot.get(i).getDate(),Double.parseDouble(tablePlot.get(i).getClay().replace(',', '.'))));
            }
            series8.setName("Clay");
            graphicChart.getData().add(series8);
            for (final XYChart.Data<String, Number> data : series8.getData())
            {
                data.getNode().addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent event) 
                    {
                        Tooltip.install(data.getNode(), new Tooltip("Date :" + data.getXValue()+"\nClay:"+data.getYValue().toString()));
                    }
                }
                );
            }
        }
        if (lime.isSelected())
        {
            XYChart.Series<String, Number> series9 = new XYChart.Series<String, Number>();
            for (int i=0;i<tablePlot.size();i++)
            {
                date=LocalDate.parse(tablePlot.get(i).date);
                if (!(date.isBefore(dateFrom.getValue())||date.isAfter(dateTo.getValue())))
                series9.getData().add(new XYChart.Data<String, Number>(tablePlot.get(i).getDate(),Double.parseDouble(tablePlot.get(i).getLime().replace(',', '.'))));
            }
            series9.setName("Lime");
            graphicChart.getData().add(series9);
            for (final XYChart.Data<String, Number> data : series9.getData())
            {
                data.getNode().addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent event) 
                    {
                        Tooltip.install(data.getNode(), new Tooltip("Date :" + data.getXValue()+"\nLime:"+data.getYValue().toString()));
                    }
                }
                );
            }
        }
         if (reference.isSelected())
        {
            XYChart.Series<String, Number> series10 = new XYChart.Series<String, Number>();
            if (NumberUtils.isCreatable(referenceLine.getText()))
            {
                for (int i=0;i<tablePlot.size();i++)
                    {
                        date=LocalDate.parse(tablePlot.get(i).date);
                        if (!(date.isBefore(dateFrom.getValue())||date.isAfter(dateTo.getValue())))
                        series10.getData().add(new XYChart.Data<String, Number>(tablePlot.get(i).getDate(),Double.parseDouble(referenceLine.getText().replace(',', '.'))));
                    }
                series10.setName("Program");
                graphicChart.getData().add(series10);
//                Color color = Color.RED;
//                Node line = series9.getNode().lookup(".chart-series-line");
//
//                String rgb = String.format("%d, %d, %d",
//                    (int) (color.getRed() * 255),
//                    (int) (color.getGreen() * 255),
//                    (int) (color.getBlue() * 255));
//                line.setStyle("-fx-stroke: rgba(" + rgb + ", 1.0);");
//                final String color2 = String.format("rgba(%d, %d, %d, 1.0)",(int) (color.getRed() * 255), (int) (color.getGreen() * 255), (int) (color.getBlue() * 255));
//                final String symbolStyle = String.format("-fx-background-color: %s, whitesmoke;", color2);
//                for (XYChart.Data<String, Number> data: series9.getData())
//                data.getNode().lookup(".chart-line-symbol").setStyle(symbolStyle);
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

    public void dataGather(ObservableList<RowTableOBM> tableCalc){
        OilBaseMudController app = new OilBaseMudController();
        tablePlot=tableCalc;
        int i=0;
        LocalDate localDate1;LocalDate localDate2;
        while (i<tablePlot.size()-1)
        {
            localDate1=LocalDate.parse(tablePlot.get(i).date);
            localDate2=LocalDate.parse(tablePlot.get(i+1).date);
            if ((localDate1.until(localDate2).getYears() > 0) || (localDate1.until(localDate2).getMonths() > 0) || (localDate1.until(localDate2).getDays() > 1)) 
            {
                RowTableOBM azaz2 = new RowTableOBM(localDate1.plusDays(1).toString(), "", "", "0","0", "0", "0","0", "0", "0","0", "", "");
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