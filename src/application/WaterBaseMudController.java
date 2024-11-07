package application;

import com.sun.javafx.scene.control.behavior.TextAreaBehavior;
import com.sun.javafx.scene.control.skin.TextAreaSkin;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class WaterBaseMudController implements Initializable {

    public LoginModal loginModal = new LoginModal();
    @FXML
    private Button addToDB;
    @FXML
    private Button clCalc;
    @FXML
    private Button addReportButt;
    @FXML
    private Button refresh;
    @FXML
    private Button evaluateWellWBM;
    @FXML
    private Button showAllCalc;
    @FXML
    private Button delete;
    @FXML
    private TextArea caCO3Diff;
    @FXML
    private TextArea lubricDiff;
    @FXML
    private TextArea kClDiff;
    @FXML
    private TextArea krakhmalDiff;
    @FXML
    private TextArea bariteDiff;
    @FXML
    private TextArea naClDiff;
    @FXML
    private TextArea ingib1Diff;
    @FXML
    private TextArea ingib2Diff;
    @FXML
    private TextArea caCO3Report;
    @FXML
    private TextArea lubricReport;
    @FXML
    private TextArea kClReport;
    @FXML
    private TextArea krakhmalReport;
    @FXML
    private TextArea bariteReport;
    @FXML
    private TextArea naClReport;
    @FXML
    private TextArea ingib1Report;
    @FXML
    private TextArea ingib2Report;
    @FXML
    private TextArea caCO3Calc;
    @FXML
    private TextArea lubricCalc;
    @FXML
    private TextArea kClCalc;
    @FXML
    private TextArea krakhmalCalc;
    @FXML
    private TextArea bariteCalc;
    @FXML
    private TextArea naClCalc;
    @FXML
    private TextArea ingib1Calc;
    @FXML
    private TextArea ingib2Calc;
    @FXML
    private DatePicker date;
    @FXML
    private TextArea mudLost;
    @FXML
    private TextArea mudMade;
    @FXML
    private TextArea caCO3;
    @FXML
    private TextArea lubric;
    @FXML
    private TextArea kCl;
    @FXML
    private TextArea krakhmal;
    @FXML
    private TextArea barite;
    @FXML
    private TextArea naCl;
    @FXML
    private TextArea ingib1;
    @FXML
    private TextArea ingib2;
    @FXML
    private TextArea centrifuge;
    @FXML
    private ComboBox<String> chooseBoxLocat;
    @FXML
    private ComboBox<String> chooseBoxWell;
    @FXML
    private TextArea Text;
    @FXML
    private TableView<RowTableWBM> Table;
    @FXML
    private TableColumn<RowTableWBM, String> dateTab;
    @FXML
    private TableColumn<RowTableWBM, String> mudLostTab;
    @FXML
    private TableColumn<RowTableWBM, String> mudLeftTab;
    @FXML
    private TableColumn<RowTableWBM, String> mudMadeTab;
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
    private TableColumn<RowTableWBM, String> centrifugeTab;
    @FXML
    private MenuItem addLocation;
    @FXML
    private MenuItem generateReport;
    @FXML
    private MenuItem addWell;
    @FXML
    private MenuItem restoreWell;
    @FXML
    private MenuItem close;
    @FXML
    private MenuItem about;
    @FXML
    private MenuItem changeMud;
    @FXML
    private Label inhib1daily;
    @FXML
    private Label inhib2daily;
    @FXML
    private Label inhib1report;
    @FXML
    private Label inhib2report;

    @FXML
    ObservableList<String> list = FXCollections.observableArrayList();
    ObservableList<String> listWell = FXCollections.observableArrayList();
    ObservableList<RowTableWBM> table = FXCollections.observableArrayList();
    ObservableList<String> dateList = FXCollections.observableArrayList();
    ObservableList<Integer> commentList = FXCollections.observableArrayList();
    String commentUpdate;
//    private String transLocation;
//    private String transWell;
//    private double[] trans=new double[9];





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mudLost.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if ((event.getCode() == KeyCode.TAB) || (event.getCode() == KeyCode.ENTER)) {
                    TextAreaSkin skin = (TextAreaSkin) mudLost.getSkin();
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
        mudMade.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if ((event.getCode() == KeyCode.TAB) || (event.getCode() == KeyCode.ENTER)) {
                    TextAreaSkin skin = (TextAreaSkin) mudMade.getSkin();
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
        caCO3.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if ((event.getCode() == KeyCode.TAB) || (event.getCode() == KeyCode.ENTER)) {
                    TextAreaSkin skin = (TextAreaSkin) caCO3.getSkin();
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
        lubric.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if ((event.getCode() == KeyCode.TAB) || (event.getCode() == KeyCode.ENTER)) {
                    TextAreaSkin skin = (TextAreaSkin) lubric.getSkin();
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
        kCl.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if ((event.getCode() == KeyCode.TAB) || (event.getCode() == KeyCode.ENTER)) {
                    TextAreaSkin skin = (TextAreaSkin) kCl.getSkin();
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
        krakhmal.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if ((event.getCode() == KeyCode.TAB) || (event.getCode() == KeyCode.ENTER)) {
                    TextAreaSkin skin = (TextAreaSkin) krakhmal.getSkin();
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
        barite.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if ((event.getCode() == KeyCode.TAB) || (event.getCode() == KeyCode.ENTER)) {
                    TextAreaSkin skin = (TextAreaSkin) barite.getSkin();
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
        naCl.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if ((event.getCode() == KeyCode.TAB) || (event.getCode() == KeyCode.ENTER)) {
                    TextAreaSkin skin = (TextAreaSkin) naCl.getSkin();
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
        ingib1.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if ((event.getCode() == KeyCode.TAB) || (event.getCode() == KeyCode.ENTER)) {
                    TextAreaSkin skin = (TextAreaSkin) ingib1.getSkin();
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
        ingib2.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if ((event.getCode() == KeyCode.TAB) || (event.getCode() == KeyCode.ENTER)) {
                    TextAreaSkin skin = (TextAreaSkin) ingib2.getSkin();
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
        centrifuge.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if ((event.getCode() == KeyCode.TAB) || (event.getCode() == KeyCode.ENTER)) {
                    TextAreaSkin skin = (TextAreaSkin) centrifuge.getSkin();
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
        caCO3Report.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if ((event.getCode() == KeyCode.TAB) || (event.getCode() == KeyCode.ENTER)) {
                    TextAreaSkin skin = (TextAreaSkin) caCO3Report.getSkin();
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
        lubricReport.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if ((event.getCode() == KeyCode.TAB) || (event.getCode() == KeyCode.ENTER)) {
                    TextAreaSkin skin = (TextAreaSkin) lubricReport.getSkin();
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
        kClReport.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if ((event.getCode() == KeyCode.TAB) || (event.getCode() == KeyCode.ENTER)) {
                    TextAreaSkin skin = (TextAreaSkin) kClReport.getSkin();
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
        krakhmalReport.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if ((event.getCode() == KeyCode.TAB) || (event.getCode() == KeyCode.ENTER)) {
                    TextAreaSkin skin = (TextAreaSkin) krakhmalReport.getSkin();
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
        bariteReport.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if ((event.getCode() == KeyCode.TAB) || (event.getCode() == KeyCode.ENTER)) {
                    TextAreaSkin skin = (TextAreaSkin) bariteReport.getSkin();
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
        naClReport.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if ((event.getCode() == KeyCode.TAB) || (event.getCode() == KeyCode.ENTER)) {
                    TextAreaSkin skin = (TextAreaSkin) naClReport.getSkin();
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
        ingib1Report.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if ((event.getCode() == KeyCode.TAB) || (event.getCode() == KeyCode.ENTER)) {
                    TextAreaSkin skin = (TextAreaSkin) ingib1Report.getSkin();
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
        ingib2Report.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if ((event.getCode() == KeyCode.TAB) || (event.getCode() == KeyCode.ENTER)) {
                    TextAreaSkin skin = (TextAreaSkin) ingib2Report.getSkin();
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
        WaterBaseMudController app = new WaterBaseMudController();
        try {
            list = app.allLocations();
            chooseBoxLocat.setItems(list);
        } catch (SQLException ex) {
            Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Table.setRowFactory(new Callback<TableView<RowTableWBM>, TableRow<RowTableWBM>>() {
            @Override
            public TableRow<RowTableWBM> call(TableView<RowTableWBM> param) {
                TableRow<RowTableWBM> row = new TableRow<RowTableWBM>();
                MenuItem remove = new MenuItem("Remove Entry");
                MenuItem update = new MenuItem("Update Entry");
                MenuItem addComment = new MenuItem("Add/Show Comment");
                MenuItem transfer = new MenuItem("Transfer Mud");
                ContextMenu menu = new ContextMenu(update, addComment, transfer, remove);
                row.contextMenuProperty().setValue(menu);
                remove.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                         Alert alert = new Alert(AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
                        alert.setHeaderText("Are you sure you want to delete entry?");
                        alert.setTitle("Delete Well");
                        alert.getDialogPane().setPrefSize(210, 70);
                        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                        stage.getIcons().add(new Image ("/misc/MudControl.png"));
                        alert.showAndWait();

                        if (alert.getResult() == ButtonType.YES) {

                        String sql = "DELETE FROM MudControlProperties WHERE FieldLocation = ? AND Field_Pad_Well = ? AND Date_Time = ? AND (Mud_DayLost = ? OR Mud_DayLost IS NULL) AND (Mud_MadeDay = ? OR Mud_MadeDay IS NULL) AND (Mud_CaCO3 = ? OR Mud_CaCO3 IS NULL) AND (Mud_Lubric = ? OR Mud_Lubric IS NULL) AND (Mud_KCL = ? OR Mud_KCL IS NULL) AND (Mud_Krahmal = ? OR Mud_Krahmal IS NULL) AND (Mud_Barite = ? OR Mud_Barite IS NULL) AND (Mud_NaCl = ? OR Mud_NaCl IS NULL) AND (Mud_Ingib1 = ? OR Mud_Ingib1 IS NULL) AND (Mud_Ingib2 = ? OR Mud_Ingib2 IS NULL) AND (Mud_CentriTime = ? OR Mud_CentriTime IS NULL)";
                        Connection conn = loginModal.connectWBM();
                        PreparedStatement pstmt;
                        try {
                            pstmt = conn.prepareStatement(sql);
                            pstmt.setString(1, chooseBoxLocat.getValue());
                            pstmt.setString(2, chooseBoxWell.getValue());
                            pstmt.setString(3, table.get(Table.getSelectionModel().getSelectedIndex()).getDate());
                            if (table.get(Table.getSelectionModel().getSelectedIndex()).getMudLost() == null) {
                                pstmt.setNull(4, 0);
                            } else {
                                pstmt.setDouble(4, Double.parseDouble(table.get(Table.getSelectionModel().getSelectedIndex()).getMudLost()));
                            }
                            if (table.get(Table.getSelectionModel().getSelectedIndex()).getMudMade() == null) {
                                pstmt.setNull(5, 0);
                            } else {
                                pstmt.setDouble(5, Double.parseDouble(table.get(Table.getSelectionModel().getSelectedIndex()).getMudMade()));
                            }
                            if (table.get(Table.getSelectionModel().getSelectedIndex()).getCaCO3() == null) {
                                pstmt.setNull(6, 0);
                            } else {
                                pstmt.setDouble(6, Double.parseDouble(table.get(Table.getSelectionModel().getSelectedIndex()).getCaCO3()));
                            }
                            if (table.get(Table.getSelectionModel().getSelectedIndex()).getLubric() == null) {
                                pstmt.setNull(7, 0);
                            } else {
                                pstmt.setDouble(7, Double.parseDouble(table.get(Table.getSelectionModel().getSelectedIndex()).getLubric()));
                            }
                            if (table.get(Table.getSelectionModel().getSelectedIndex()).getkCl() == null) {
                                pstmt.setNull(8, 0);
                            } else {
                                pstmt.setDouble(8, Double.parseDouble(table.get(Table.getSelectionModel().getSelectedIndex()).getkCl()));
                            }
                            if (table.get(Table.getSelectionModel().getSelectedIndex()).getKrakhmal() == null) {
                                pstmt.setNull(9, 0);
                            } else {
                                pstmt.setDouble(9, Double.parseDouble(table.get(Table.getSelectionModel().getSelectedIndex()).getKrakhmal()));
                            }
                            if (table.get(Table.getSelectionModel().getSelectedIndex()).getBarite() == null) {
                                pstmt.setNull(10, 0);
                            } else {
                                pstmt.setDouble(10, Double.parseDouble(table.get(Table.getSelectionModel().getSelectedIndex()).getBarite()));
                            }
                            if (table.get(Table.getSelectionModel().getSelectedIndex()).getNaCl() == null) {
                                pstmt.setNull(11, 0);
                            } else {
                                pstmt.setDouble(11, Double.parseDouble(table.get(Table.getSelectionModel().getSelectedIndex()).getNaCl()));
                            }
                            if (table.get(Table.getSelectionModel().getSelectedIndex()).getIngib1() == null) {
                                pstmt.setNull(12, 0);
                            } else {
                                pstmt.setDouble(12, Double.parseDouble(table.get(Table.getSelectionModel().getSelectedIndex()).getIngib1()));
                            }
                            if (table.get(Table.getSelectionModel().getSelectedIndex()).getIngib2() == null) {
                                pstmt.setNull(13, 0);
                            } else {
                                pstmt.setDouble(13, Double.parseDouble(table.get(Table.getSelectionModel().getSelectedIndex()).getIngib2()));
                            }
                            if (table.get(Table.getSelectionModel().getSelectedIndex()).getCentrifuge() == null) {
                                pstmt.setNull(14, 0);
                            } else {
                                pstmt.setDouble(14, Double.parseDouble(table.get(Table.getSelectionModel().getSelectedIndex()).getCentrifuge()));
                            }
                            pstmt.executeUpdate();
                        } catch (SQLException ex) {
                            Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        try {
                            listWellsTable();
                        } catch (SQLException ex) {
                            Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    }
                });
                update.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        date.setValue(LocalDate.parse(table.get(Table.getSelectionModel().getSelectedIndex()).getDate()));
                        mudLost.setText(table.get(Table.getSelectionModel().getSelectedIndex()).getMudLost());
                        mudMade.setText(table.get(Table.getSelectionModel().getSelectedIndex()).getMudMade());
                        caCO3.setText(table.get(Table.getSelectionModel().getSelectedIndex()).getCaCO3());
                        lubric.setText(table.get(Table.getSelectionModel().getSelectedIndex()).getLubric());
                        kCl.setText(table.get(Table.getSelectionModel().getSelectedIndex()).getkCl());
                        krakhmal.setText(table.get(Table.getSelectionModel().getSelectedIndex()).getKrakhmal());
                        barite.setText(table.get(Table.getSelectionModel().getSelectedIndex()).getBarite());
                        naCl.setText(table.get(Table.getSelectionModel().getSelectedIndex()).getNaCl());
                        ingib1.setText(table.get(Table.getSelectionModel().getSelectedIndex()).getIngib1());
                        ingib2.setText(table.get(Table.getSelectionModel().getSelectedIndex()).getIngib2());
                        centrifuge.setText(table.get(Table.getSelectionModel().getSelectedIndex()).getCentrifuge());

                        CommentController appp = new CommentController();
                        try {
                            commentUpdate=appp.chooseCommentWBM(chooseBoxLocat.getValue(),chooseBoxWell.getValue(),table.get(Table.getSelectionModel().getSelectedIndex()).getDate());
                        } catch (SQLException ex) {
                            Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        String sql = "DELETE FROM MudControlProperties WHERE FieldLocation = ? AND Field_Pad_Well = ? AND Date_Time = ? AND (Mud_DayLost = ? OR Mud_DayLost IS NULL) AND (Mud_MadeDay = ? OR Mud_MadeDay IS NULL) AND (Mud_CaCO3 = ? OR Mud_CaCO3 IS NULL) AND (Mud_Lubric = ? OR Mud_Lubric IS NULL) AND (Mud_KCL = ? OR Mud_KCL IS NULL) AND (Mud_Krahmal = ? OR Mud_Krahmal IS NULL) AND (Mud_Barite = ? OR Mud_Barite IS NULL) AND (Mud_NaCl = ? OR Mud_NaCl IS NULL) AND (Mud_Ingib1 = ? OR Mud_Ingib1 IS NULL) AND (Mud_Ingib2 = ? OR Mud_Ingib2 IS NULL) AND (Mud_CentriTime = ? OR Mud_CentriTime IS NULL)";
                        Connection conn = loginModal.connectWBM();
                        PreparedStatement pstmt;
                        try {
                            pstmt = conn.prepareStatement(sql);
                            pstmt.setString(1, chooseBoxLocat.getValue());
                            pstmt.setString(2, chooseBoxWell.getValue());
                            pstmt.setString(3, table.get(Table.getSelectionModel().getSelectedIndex()).getDate());
                            if (table.get(Table.getSelectionModel().getSelectedIndex()).getMudLost() == null) {
                                pstmt.setNull(4, 0);
                            } else {
                                pstmt.setDouble(4, Double.parseDouble(table.get(Table.getSelectionModel().getSelectedIndex()).getMudLost()));
                            }
                            if (table.get(Table.getSelectionModel().getSelectedIndex()).getMudMade() == null) {
                                pstmt.setNull(5, 0);
                            } else {
                                pstmt.setDouble(5, Double.parseDouble(table.get(Table.getSelectionModel().getSelectedIndex()).getMudMade()));
                            }
                            if (table.get(Table.getSelectionModel().getSelectedIndex()).getCaCO3() == null) {
                                pstmt.setNull(6, 0);
                            } else {
                                pstmt.setDouble(6, Double.parseDouble(table.get(Table.getSelectionModel().getSelectedIndex()).getCaCO3()));
                            }
                            if (table.get(Table.getSelectionModel().getSelectedIndex()).getLubric() == null) {
                                pstmt.setNull(7, 0);
                            } else {
                                pstmt.setDouble(7, Double.parseDouble(table.get(Table.getSelectionModel().getSelectedIndex()).getLubric()));
                            }
                            if (table.get(Table.getSelectionModel().getSelectedIndex()).getkCl() == null) {
                                pstmt.setNull(8, 0);
                            } else {
                                pstmt.setDouble(8, Double.parseDouble(table.get(Table.getSelectionModel().getSelectedIndex()).getkCl()));
                            }
                            if (table.get(Table.getSelectionModel().getSelectedIndex()).getKrakhmal() == null) {
                                pstmt.setNull(9, 0);
                            } else {
                                pstmt.setDouble(9, Double.parseDouble(table.get(Table.getSelectionModel().getSelectedIndex()).getKrakhmal()));
                            }
                            if (table.get(Table.getSelectionModel().getSelectedIndex()).getBarite() == null) {
                                pstmt.setNull(10, 0);
                            } else {
                                pstmt.setDouble(10, Double.parseDouble(table.get(Table.getSelectionModel().getSelectedIndex()).getBarite()));
                            }
                            if (table.get(Table.getSelectionModel().getSelectedIndex()).getNaCl() == null) {
                                pstmt.setNull(11, 0);
                            } else {
                                pstmt.setDouble(11, Double.parseDouble(table.get(Table.getSelectionModel().getSelectedIndex()).getNaCl()));
                            }
                            if (table.get(Table.getSelectionModel().getSelectedIndex()).getIngib1() == null) {
                                pstmt.setNull(12, 0);
                            } else {
                                pstmt.setDouble(12, Double.parseDouble(table.get(Table.getSelectionModel().getSelectedIndex()).getIngib1()));
                            }
                            if (table.get(Table.getSelectionModel().getSelectedIndex()).getIngib2() == null) {
                                pstmt.setNull(13, 0);
                            } else {
                                pstmt.setDouble(13, Double.parseDouble(table.get(Table.getSelectionModel().getSelectedIndex()).getIngib2()));
                            }
                            if (table.get(Table.getSelectionModel().getSelectedIndex()).getCentrifuge() == null) {
                                pstmt.setNull(14, 0);
                            } else {
                                pstmt.setDouble(14, Double.parseDouble(table.get(Table.getSelectionModel().getSelectedIndex()).getCentrifuge()));
                            }
                            pstmt.executeUpdate();
                        } catch (SQLException ex) {
                            Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        try {
                            listWellsTable();
                        } catch (SQLException ex) {
                            Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                addComment.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                       Stage primaryStage7 = new Stage();
                        try {
                            FXMLLoader loader = new FXMLLoader();
                            Parent root7 = loader.load(getClass().getResource("/application/CommentWBM.fxml").openStream());
                            CommentController comContr = (CommentController) loader.getController();
                            comContr.showCommentWBM(chooseBoxLocat.getValue(),chooseBoxWell.getValue(),table.get(Table.getSelectionModel().getSelectedIndex()).getDate());
                            Scene scene7 = new Scene(root7);
                            scene7.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
                            primaryStage7.setScene(scene7);
                            primaryStage7.setResizable(false);
                            primaryStage7.setTitle("Add/Show Comment");
                            primaryStage7.setOnHidden(new EventHandler<WindowEvent>() {
                                @Override
                                public void handle(WindowEvent event) {
                                    try {
                                        listWellsTable();
                                    } catch (SQLException ex) {
                                        Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (IOException ex) {
                                        Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            } );
                            primaryStage7.getIcons().add(new Image ("/misc/MudControl.png"));
                            primaryStage7.showAndWait();
                            } catch (IOException e) {
                        }
                    }
                });
                transfer.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Stage primaryStage8 = new Stage();
                        try {
                            FXMLLoader loader = new FXMLLoader();
                            Parent root8 = loader.load(getClass().getResource("/application/TransferMud.fxml").openStream());
                            final TransferMudController transContr = (TransferMudController) loader.getController();
                            transContr.loadLocation(chooseBoxLocat.getValue(),chooseBoxWell.getValue(),table, table.get(Table.getSelectionModel().getSelectedIndex()).getDate());
                            final String dateTrans=table.get(Table.getSelectionModel().getSelectedIndex()).getDate();
                            Scene scene8 = new Scene(root8);
                            scene8.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
                            primaryStage8.setScene(scene8);
                            primaryStage8.setResizable(false);
                            primaryStage8.setTitle("Transfer Mud");
                            primaryStage8.setOnHidden(new EventHandler<WindowEvent>() {
                                @Override
                                public void handle(WindowEvent event) {
                                    try {
                                        Double[] trans=transContr.returningMud();
                                        String transLocation=transContr.returningLoc();
                                        String transWell=transContr.returningWell();
                                        Double transAmount= transContr.returningAmount();

                                        WaterBaseMudController app = new WaterBaseMudController();
                                        String sql = "SELECT Comment " + "FROM MudControlProperties WHERE FieldLocation = ? AND Field_Pad_Well = ? AND Date_Time = ?";
                                        Connection conn = app.loginModal.connectWBM();
                                        PreparedStatement pstmt = conn.prepareStatement(sql);
                                        pstmt.setString(1, transLocation);
                                        pstmt.setString(2, chooseBoxWell.getValue());
                                        pstmt.setString(3, dateTrans);
                                        ResultSet rs = pstmt.executeQuery();
                                        String comm=rs.getString(1);
                                        conn.close();
                                        int flag=0;
                                        if ((comm==null)||(comm.equals(""))) flag=1;
//                                        WaterBaseMudController app = new WaterBaseMudController();
                                        String sql2 = "UPDATE MudControlProperties SET Comment = ? WHERE FieldLocation = ? AND Field_Pad_Well = ? AND Date_Time = ?";
                                        String comment;
                                        if (flag==1)
                                        {
                                            comment=transAmount.toString()+" m3 of mud was transfered to "+transWell;
                                        }
                                        else
                                        {
                                            comment = comm+"\n"+transAmount.toString()+" m3 of mud was transfered to "+transWell;
                                        }

                                        Connection conn2 = app.loginModal.connectWBM();
                                        PreparedStatement pstmt2;
                                        try {
                                            pstmt2 = conn2.prepareStatement(sql2);
                                            pstmt2.setString(1, comment);
                                            pstmt2.setString(2, transLocation);
                                            pstmt2.setString(3, chooseBoxWell.getValue());
                                            pstmt2.setString(4, dateTrans);
                                            pstmt2.executeUpdate();
                                            pstmt2.close();
                                        } catch (SQLException ex) {
                                            Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                                        }

                                        applyTrans(transLocation, transWell, transAmount, trans[0], trans[1], trans[2], trans[3], trans[4], trans[5], trans[6], trans[7]);
                                    } catch (SQLException ex) {
                                        Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (IOException ex) {
                                        Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            } );
                            primaryStage8.getIcons().add(new Image ("/misc/MudControl.png"));
                            primaryStage8.showAndWait();
                            } catch (IOException e) {
                        }

                    }
                });
                return row;
            }
        });
        chooseBoxWell.addEventFilter(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (event.getButton() == MouseButton.SECONDARY) {
                            event.consume();
                        }
                    }
                });
        chooseBoxWell.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                MenuItem delete = new MenuItem("Delete Well");
                MenuItem rename = new MenuItem("Rename Well");
                MenuItem archive = new MenuItem("Archive Well");
                MenuItem setInhib = new MenuItem("Set Inhibitors");
                ContextMenu menu = new ContextMenu(setInhib,archive, rename, delete);
                chooseBoxWell.contextMenuProperty().setValue(menu);
                delete.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Alert alert = new Alert(AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
                        alert.setHeaderText("Are you sure you want to delete well?");
                        alert.setTitle("Delete Well");
                        alert.getDialogPane().setPrefSize(210, 70);
                        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                        stage.getIcons().add(new Image ("/misc/MudControl.png"));
                        alert.showAndWait();

                        if (alert.getResult() == ButtonType.YES) {
                            String sql = "DELETE FROM MudControlProperties WHERE FieldLocation = ? AND Field_Pad_Well = ?";
                            Connection conn = loginModal.connectWBM();
                            PreparedStatement pstmt;
                            try {
                                pstmt = conn.prepareStatement(sql);
                                pstmt.setString(1, chooseBoxLocat.getValue());
                                pstmt.setString(2, chooseBoxWell.getValue());
                                pstmt.executeUpdate();
                            } catch (SQLException ex) {
                                Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            try {
                                WaterBaseMudController app = new WaterBaseMudController();
                                try {
                                    list = app.allLocations();
                                    chooseBoxLocat.setItems(list);
                                } catch (SQLException ex) {
                                    Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                listWell.clear();
                                listWell = allWellsInLocation(chooseBoxLocat.getValue());
                                chooseBoxWell.setItems(listWell);
                                clearAll();
                            } catch (SQLException ex) {
                                Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                });
                archive.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Alert alert = new Alert(AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
                        alert.setHeaderText("Are you sure you want to archive well?");
                        alert.setTitle("Archive Well");
                        alert.getDialogPane().setPrefSize(210, 70);
                        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                        stage.getIcons().add(new Image ("/misc/MudControl.png"));
                        alert.showAndWait();
                        if (alert.getResult() == ButtonType.YES) {
                            try {
                                updateDb(4, chooseBoxLocat.getValue(), chooseBoxWell.getValue(), 2);
                            } catch (SQLException ex) {
                                Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            try {
                                updateDb(3, chooseBoxLocat.getValue(), chooseBoxWell.getValue(), 1);
                            } catch (SQLException ex) {
                                Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            try {
                                WaterBaseMudController app = new WaterBaseMudController();
                                try {
                                    list = app.allLocations();
                                    chooseBoxLocat.setItems(list);
                                } catch (SQLException ex) {
                                    Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                listWell.clear();
                                listWell = allWellsInLocation(chooseBoxLocat.getValue());
                                chooseBoxWell.setItems(listWell);
                                clearAll();
                            } catch (SQLException ex) {
                                Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                });
                rename.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Alert alert = new Alert(AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
                        alert.setHeaderText("Are you sure you want to rename well?");
                        alert.setTitle("Rename Well");
                        alert.getDialogPane().setPrefSize(210, 70);
                        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                        stage.getIcons().add(new Image ("/misc/MudControl.png"));
                        alert.showAndWait();

                        if (alert.getResult() == ButtonType.YES) {
                            TextInputDialog dialog = new TextInputDialog(chooseBoxWell.getValue());
                            dialog.setTitle("Rename Well");
                            dialog.setHeaderText("");
                            dialog.setContentText("Please write new Well name:");
                            Stage stage2 = (Stage) dialog.getDialogPane().getScene().getWindow();
                            stage2.getIcons().add(new Image ("/misc/MudControl.png"));
                            Optional<String> result = dialog.showAndWait();
                            if (result.isPresent()){
                                String sql = "UPDATE MudControlProperties SET Field_Pad_Well = ? WHERE FieldLocation = ? AND Field_Pad_Well = ?";
                                Connection conn = loginModal.connectWBM();
                                PreparedStatement pstmt;
                                try {
                                    pstmt = conn.prepareStatement(sql);
                                    pstmt.setString(1, result.get());
                                    pstmt.setString(2, chooseBoxLocat.getValue());
                                    pstmt.setString(3, chooseBoxWell.getValue());
                                    pstmt.executeUpdate();
                                } catch (SQLException ex) {
                                    Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                try {
                                    WaterBaseMudController app = new WaterBaseMudController();
                                    try {
                                        list = app.allLocations();
                                        chooseBoxLocat.setItems(list);
                                    } catch (SQLException ex) {
                                        Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    listWell.clear();
                                    listWell = allWellsInLocation(chooseBoxLocat.getValue());
                                    chooseBoxWell.setItems(listWell);
                                    clearAll();
                                } catch (SQLException ex) {
                                    Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }
                });
                setInhib.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                       Stage primaryStage8 = new Stage();
                        try {
                            FXMLLoader loader = new FXMLLoader();
                            Parent root8 = loader.load(getClass().getResource("/application/SetInhibitorWBM.fxml").openStream());
                            SetInhibitorController inhibContr = (SetInhibitorController) loader.getController();
                            inhibContr.showInhibWMB(chooseBoxLocat.getValue(),chooseBoxWell.getValue());
                            Scene scene8 = new Scene(root8);
                            scene8.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
                            primaryStage8.setScene(scene8);
                            primaryStage8.setResizable(false);
                            primaryStage8.setTitle("Set Inhibitors");
                            primaryStage8.setOnHidden(new EventHandler<WindowEvent>() {
                                @Override
                                public void handle(WindowEvent event) {
                                    try {
                                        listWellsTable();
                                    } catch (SQLException ex) {
                                        Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (IOException ex) {
                                        Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            } );
                            primaryStage8.getIcons().add(new Image ("/misc/MudControl.png"));
                            primaryStage8.showAndWait();
                            } catch (IOException e) {
                        }
                    }
                });
            }
        });
        chooseBoxLocat.addEventFilter(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (event.getButton() == MouseButton.SECONDARY) {
                            event.consume();
                        }
                    }
                });
        chooseBoxLocat.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                MenuItem renameLoc = new MenuItem("Rename Location");
                MenuItem delete = new MenuItem("Delete Location");
                ContextMenu menu = new ContextMenu(renameLoc,delete);
                chooseBoxLocat.contextMenuProperty().setValue(menu);
                renameLoc.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Alert alert = new Alert(AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
                        alert.setHeaderText("Are you sure you want to rename location?");
                        alert.setTitle("Rename Location");
                        alert.getDialogPane().setPrefSize(210, 70);
                        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                        stage.getIcons().add(new Image ("/misc/MudControl.png"));
                        alert.showAndWait();

                        if (alert.getResult() == ButtonType.YES) {
                            TextInputDialog dialog = new TextInputDialog(chooseBoxLocat.getValue());
                            dialog.setTitle("Rename Location");
                            dialog.setHeaderText("");
                            dialog.setContentText("Please write new Location name:");
                            Stage stage2 = (Stage) dialog.getDialogPane().getScene().getWindow();
                            stage2.getIcons().add(new Image ("/misc/MudControl.png"));
                            Optional<String> result = dialog.showAndWait();
                            if (result.isPresent()){
                                String sql = "UPDATE MudControlProperties SET FieldLocation = ? WHERE FieldLocation = ?";
                                Connection conn = loginModal.connectWBM();
                                PreparedStatement pstmt;
                                try {
                                    pstmt = conn.prepareStatement(sql);
                                    pstmt.setString(1, result.get());
                                    pstmt.setString(2, chooseBoxLocat.getValue());
                                    pstmt.executeUpdate();
                                } catch (SQLException ex) {
                                    Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                WaterBaseMudController app = new WaterBaseMudController();
                                try {
                                    list = app.allLocations();
                                    chooseBoxLocat.setItems(list);
                                } catch (SQLException ex) {
                                    Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                listWell.clear();
                                clearAll();
                            }
                        }
                    }
                });
                delete.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Alert alert = new Alert(AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
                        alert.setHeaderText("Are you sure you want to delete location?");
                        alert.setTitle("Delete Location");
                        alert.getDialogPane().setPrefSize(210, 70);
                        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                        stage.getIcons().add(new Image ("/misc/MudControl.png"));
                        alert.showAndWait();

                        if (alert.getResult() == ButtonType.YES) {
                            String sql = "DELETE FROM MudControlProperties WHERE FieldLocation = ?";
                            Connection conn = loginModal.connectWBM();
                            PreparedStatement pstmt;
                            try {
                                pstmt = conn.prepareStatement(sql);
                                pstmt.setString(1, chooseBoxLocat.getValue());
                                pstmt.executeUpdate();
                            } catch (SQLException ex) {
                                Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            try {
                                WaterBaseMudController app = new WaterBaseMudController();
                                try {
                                    list = app.allLocations();
                                    chooseBoxLocat.setItems(list);
                                } catch (SQLException ex) {
                                    Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                listWell.clear();
                                listWell = allWellsInLocation(chooseBoxLocat.getValue());
                                chooseBoxWell.setItems(listWell);
                                clearAll();
                            } catch (SQLException ex) {
                                Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                });
                }
        });

    }

    @FXML
    void changeMudType(ActionEvent event) {
        Stage stage = (Stage) addToDB.getScene().getWindow();
        stage.close();
    }

    @FXML
    void showEvaluate(ActionEvent event) throws SQLException {

        if (chooseBoxWell.getValue()==null)
        {
            Alert a10 = new Alert(Alert.AlertType.ERROR);
                    a10.setHeaderText("Please choose well for Evaluation");
                    a10.setTitle("ERROR");
                    a10.setResizable(true);
                    a10.getDialogPane().setPrefSize(200, 70);
                    a10.showAndWait();
        }
        else{
        Stage primaryStage9 = new Stage();
            try {
                FXMLLoader loader = new FXMLLoader();
                Parent root9 = loader.load(getClass().getResource("/application/WellEvaluation.fxml").openStream());
                WellEvaluateController evalContr = (WellEvaluateController) loader.getController();
                evalContr.showEval(chooseBoxLocat.getValue()+"     0",chooseBoxWell.getValue());
                Scene scene9 = new Scene(root9);
                scene9.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
                primaryStage9.setScene(scene9);
                primaryStage9.setResizable(false);
                primaryStage9.setTitle("Evaluate Well");
                primaryStage9.setOnHidden(new EventHandler<WindowEvent>() {
                    @Override
                            public void handle(WindowEvent event) {
                                try {
                                    listWellsTable();
                                } catch (SQLException ex) {
                                    Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (IOException ex) {
                                    Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        } );
                    primaryStage9.getIcons().add(new Image ("/misc/MudControl.png"));
                    primaryStage9.showAndWait();
                } catch (IOException e) {
            }
        }
     }


    public void updateDb(int actNew, String locat, String field, int actOld) throws SQLException {
        String sql = "UPDATE MudControlProperties SET ActiveFlag = ? WHERE FieldLocation = ? AND Field_Pad_Well = ? AND ActiveFlag = ?";
        Connection conn = loginModal.connectWBM();
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, actNew);
            pstmt.setString(2, locat);
            pstmt.setString(3, field);
            pstmt.setInt(4, actOld);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    

    @FXML
    void about(ActionEvent event) {
        Stage primaryStage = new Stage();
        try {
            Parent root2 = FXMLLoader.load(getClass().getResource("/application/About.fxml"));
            Scene scene2 = new Scene(root2);
            scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene2);
            primaryStage.setResizable(false);
            primaryStage.setTitle("About");
            primaryStage.getIcons().add(new Image ("/misc/MudControl.png"));
            primaryStage.showAndWait();
        } catch (IOException e) {
        }
    }

    @FXML
    void addWell(ActionEvent event) {
        Stage primaryStage = new Stage();
        try {
            Parent root3 = FXMLLoader.load(getClass().getResource("/application/AddWell.fxml"));
            Scene scene3 = new Scene(root3);
            scene3.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene3);
            primaryStage.setResizable(false);
            primaryStage.setTitle("Add Well");
            primaryStage.setOnHidden(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    refreshData();
                    }
            });
            primaryStage.getIcons().add(new Image ("/misc/MudControl.png"));
            primaryStage.showAndWait();
        } catch (IOException e) {
        }
    }

    @FXML
    void restoreWell(ActionEvent event) {
        Stage primaryStage = new Stage();
        try {
            Parent root4 = FXMLLoader.load(getClass().getResource("/application/RestoreWell.fxml"));
            Scene scene4 = new Scene(root4);
            scene4.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene4);
            primaryStage.setResizable(false);
            primaryStage.setTitle("Restore Well");
            primaryStage.setOnHidden(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    refreshData();
                }
            });
            primaryStage.getIcons().add(new Image ("/misc/MudControl.png"));
            primaryStage.showAndWait();
        } catch (IOException e) {
        }
    }

    @FXML
    void addLocation(ActionEvent event) throws SQLException, IOException {
        Stage primaryStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader();
            Parent root4 = loader.load(getClass().getResource("/application/AddLocation.fxml"));
            Scene scene4 = new Scene(root4);
            scene4.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene4);
            primaryStage.setResizable(false);
            primaryStage.setTitle("Add Location");
            primaryStage.setOnHidden(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    refreshData();
                }
            });
            primaryStage.getIcons().add(new Image ("/misc/MudControl.png"));
            primaryStage.showAndWait();
        } catch (IOException e) {
        }
    }
    
    

    @FXML
    void generateReport(ActionEvent event) throws Exception{

//        Desktop desktop = Desktop.getDesktop();
//        String message = "mailto:dummy@domain.com?subject=First%20Email&body=";
//        URI uri = URI.create(message);
//        desktop.mail(uri);
//

    try{
        List<ArrayList<String>> repData = new ArrayList<ArrayList<String>>();
        int flag1=0; int flag2=0;
        WaterBaseMudController app = new WaterBaseMudController();
        String sql = "SELECT FieldLocation,Field_Pad_Well,Comment " + "FROM MudControlProperties WHERE Date_Time = ? AND ActiveFlag = ?";
        Connection conn = app.loginModal.connectWBM();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, "0.0");
        pstmt.setString(2, "1");
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            String[] azaz=new String[3];
            ArrayList<String> apap=new ArrayList<String>();
            azaz[0]=rs.getString(1);azaz[1]=rs.getString(2);azaz[2]=rs.getString(3);
            String commentNew=""; 
            if (azaz[1].equals("0.0")) flag1=1;
            if (azaz[2]==null) flag2=1;
            else{
                String[] commentNowPart=azaz[2].split("     ");
                int commLeng=azaz[2].split("     ").length;
                if (commLeng==1)
                {
                    if (commentNowPart[0].equals("")) flag2=1;
                    else commentNew=commentNowPart[0];
                }
                else if (commLeng==2)
                {
                    flag2=1;
                }
                else 
                {
                    if (commentNowPart[2].equals("")) flag2=1;
                    else commentNew=commentNowPart[2];
                };
                if ((flag1==0)&&(flag2==0)) 
                {
                    apap= new ArrayList<String>(Arrays.asList(azaz[0],azaz[1],commentNew));
                    repData.add(apap);
                }
                
            }
            flag1=0;flag2=0;
        }
        
        OilBaseMudController app2 = new OilBaseMudController();
        String sql2 = "SELECT FieldLocation,Field_Pad_Well,Comment " + "FROM MudControlProperties WHERE Date_Time = ? AND ActiveFlag = ?";
        Connection conn2 = app2.loginModal.connectOBM();
        PreparedStatement pstmt2 = conn2.prepareStatement(sql2);
        pstmt2.setString(1, "0.0");
        pstmt2.setString(2, "1");
        ResultSet rs2 = pstmt2.executeQuery();
        while (rs2.next()) {
            String[] azaz=new String[3];
            ArrayList<String> apap=new ArrayList<String>();
            azaz[0]=rs2.getString(1);azaz[1]=rs2.getString(2);azaz[2]=rs2.getString(3);
            String commentNew=""; 
            if (azaz[1].equals("0.0")) flag1=1;
            if ((azaz[2]==null)||(azaz[2].equals(""))) flag2=1;
            if ((flag1==0)&&(flag2==0)) 
            {
                apap= new ArrayList<String>(Arrays.asList(azaz[0],azaz[1],azaz[2]));
                repData.add(apap);                
            }
            flag1=0;flag2=0;
        }
        
        Collections.sort(repData, new Comparator<ArrayList<String>>() 
        {    
        @Override
            public int compare(ArrayList<String> o1, ArrayList<String> o2) 
            {
                return o1.get(0).compareTo(o2.get(0));
            }
        });
                
        XWPFDocument document = new XWPFDocument();
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run=paragraph.createRun();
        int flag=0;
        
//        run.setText("Добрый день всем,");run.addBreak();
//        run.setText("Для информации, после проверки рапортов по раствору (активные работы, рапорты по раствору за прошлые сутки):");
//        run.addBreak();
        
        String curLoc=new String();        
        
        for (int i=0;i<repData.size();i++)
        {
            if (!(repData.get(i).get(0).equals(curLoc)))
                    {
                        XWPFRun curRun=paragraph.createRun();
                        curRun.setBold(true);
                        curLoc=repData.get(i).get(0);
                        if (flag==1)curRun.addBreak(); else flag=1;
                        curRun.setText("Работы "+repData.get(i).get(0)+":");curRun.addBreak();
                        XWPFRun curRun2=paragraph.createRun(); 
                        curRun2.setText(repData.get(i).get(1)+" - "+repData.get(i).get(2));
                        curRun2.addBreak();
                    }
            else
            {
                XWPFRun curRun3=paragraph.createRun();
                curRun3.setText(repData.get(i).get(1)+" - "+repData.get(i).get(2));
                curRun3.addBreak();                
            }
         }

        File dir = new File("C:/MudControlData/Report's/");
        if (dir.exists()){}
        else {dir.mkdir();}

        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
        Date date = new Date();
        String date1=dateFormat.format(date).toString();

        String filename="C:/MudControlData/Report's/Рапорт_"+date1+".docx";
        FileOutputStream fos = new FileOutputStream(filename);

        document.write(fos);
        fos.close();
        File file = new File(filename);
        Desktop.getDesktop().open(file);
    }catch (Exception e){
        System.out.println(e);
    }

    }

    void refreshData() {
        WaterBaseMudController app = new WaterBaseMudController();
        try {
            list = app.allLocations();
            chooseBoxLocat.setItems(list);
        } catch (SQLException ex) {
            Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void showAllCalc(ActionEvent event) throws InterruptedException {
        Stage primaryStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader();
            Pane root5 = loader.load(getClass().getResource("/application/AllCalculation.fxml").openStream());
            AllCalculationController allCalcContr = (AllCalculationController) loader.getController();
            allCalcContr.showTable(chooseBoxLocat.getValue(), chooseBoxWell.getValue(),ingib1Tab.getText(),ingib2Tab.getText());
            Scene scene5 = new Scene(root5);
            scene5.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene5);
            primaryStage.setResizable(false);
            primaryStage.setTitle("Daily calculation's");
            primaryStage.getIcons().add(new Image ("/misc/MudControl.png"));
            primaryStage.showAndWait();
        } catch (IOException e) {
        }
    }

    void closeApplication() {
        Platform.exit();
        System.exit(0);
    }

     @FXML
    void closeApp(ActionEvent event) {
         closeApplication();
    }

      @FXML
    void clClac(ActionEvent event) {
         TextInputDialog dialog = new TextInputDialog("");
         dialog.setTitle("Cloride concentration");
         dialog.setHeaderText("");
         dialog.setContentText("Please input Chloride's (g/L):");
         Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
         stage.getIcons().add(new Image ("/misc/MudControl.png"));
         Optional<String> result = dialog.showAndWait();
         if (result.isPresent()){
             if (NumberUtils.isCreatable(result.get()))
             {
                 Double az=Double.parseDouble(result.get())*2.0792+3.9074;
                 kClReport.setText(String.format("%.0f", az));
             }
             else
             {
                 Alert a1 = new Alert(Alert.AlertType.ERROR);
                a1.setHeaderText("Please input number");
                a1.setTitle("ERROR");
                a1.setResizable(true);
                a1.getDialogPane().setPrefSize(200, 70);
                Stage stage2 = (Stage) a1.getDialogPane().getScene().getWindow();
                stage2.getIcons().add(new Image ("/misc/MudControl.png"));
                a1.showAndWait();
             }
         }
    }


    @FXML
    void addReportData(ActionEvent event) throws SQLException, IOException {
        String sql = "DELETE FROM MudControlProperties WHERE FieldLocation = ? AND Field_Pad_Well = ? AND ActiveFlag = ?";
        Connection conn = loginModal.connectWBM();
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, chooseBoxLocat.getValue());
            pstmt.setString(2, chooseBoxWell.getValue());
            pstmt.setInt(3, 2);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
        }
        WaterBaseMudController app = new WaterBaseMudController();
        if (((NumberUtils.isCreatable(caCO3Report.getText().replace(',', '.'))) || (caCO3Report.getText().isEmpty()))
                && ((NumberUtils.isCreatable(lubricReport.getText().replace(',', '.'))) || (lubricReport.getText().isEmpty()))
                && ((NumberUtils.isCreatable(kClReport.getText().replace(',', '.'))) || (kClReport.getText().isEmpty()))
                && ((NumberUtils.isCreatable(krakhmalReport.getText().replace(',', '.'))) || (krakhmalReport.getText().isEmpty()))
                && ((NumberUtils.isCreatable(bariteReport.getText().replace(',', '.'))) || (bariteReport.getText().isEmpty()))
                && ((NumberUtils.isCreatable(naClReport.getText().replace(',', '.'))) || (naClReport.getText().isEmpty()))
                && ((NumberUtils.isCreatable(ingib1Report.getText().replace(',', '.'))) || (ingib1Report.getText().isEmpty()))
                && ((NumberUtils.isCreatable(ingib2Report.getText().replace(',', '.'))) || (ingib2Report.getText().isEmpty()))) {
            String loc = chooseBoxLocat.getValue();
            String fiel = chooseBoxWell.getValue();
            Double[] arr = new Double[8];
            if ((caCO3Report.getText() != null) && (!caCO3Report.getText().isEmpty())) {
                arr[0] = Double.parseDouble(caCO3Report.getText().replace(',', '.'));
            } else {
                arr[0] = 0.0;
            }
            if ((lubricReport.getText() != null) && (!lubricReport.getText().isEmpty())) {
                arr[1] = Double.parseDouble(lubricReport.getText().replace(',', '.'));
            } else {
                arr[1] = 0.0;
            }
            if ((kClReport.getText() != null) && (!kClReport.getText().isEmpty())) {
                arr[2] = Double.parseDouble(kClReport.getText().replace(',', '.'));
            } else {
                arr[2] = 0.0;
            }
            if ((krakhmalReport.getText() != null) && (!krakhmalReport.getText().isEmpty())) {
                arr[3] = Double.parseDouble(krakhmalReport.getText().replace(',', '.'));
            } else {
                arr[3] = 0.0;
            }
            if ((bariteReport.getText() != null) && (!bariteReport.getText().isEmpty())) {
                arr[4] = Double.parseDouble(bariteReport.getText().replace(',', '.'));
            } else {
                arr[4] = 0.0;
            }
            if ((naClReport.getText() != null) && (!naClReport.getText().isEmpty())) {
                arr[5] = Double.parseDouble(naClReport.getText().replace(',', '.'));
            } else {
                arr[5] = 0.0;
            }
            if ((ingib1Report.getText() != null) && (!ingib1Report.getText().isEmpty())) {
                arr[6] = Double.parseDouble(ingib1Report.getText().replace(',', '.'));
            } else {
                arr[6] = 0.0;
            }
            if ((ingib2Report.getText() != null) && (!ingib2Report.getText().isEmpty())) {
                arr[7] = Double.parseDouble(ingib2Report.getText().replace(',', '.'));
            } else {
                arr[7] = 0.0;
            }
            app.insertInDbWBM(loc, fiel, "", 0.0, 0.0, arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], 0.0, 2,"");
            try {
                listWellsTable();
            } catch (SQLException ex) {
                Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Alert a1 = new Alert(Alert.AlertType.ERROR);
            a1.setHeaderText("Please insert numbers in Report parameters");
            a1.setTitle("ERROR");
            a1.setResizable(true);
            a1.getDialogPane().setPrefSize(200, 70);
            Stage stage = (Stage) a1.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image ("/misc/MudControl.png"));
            a1.showAndWait();
        }
    }

    @FXML
    void addToDB(ActionEvent event) throws SQLException, IOException {
        WaterBaseMudController app = new WaterBaseMudController();
        if (date.getValue() == null) {
            Alert a1 = new Alert(Alert.AlertType.ERROR);
            a1.setHeaderText("Date fields must be filled");
            a1.setTitle("ERROR");
            a1.setResizable(true);
            a1.getDialogPane().setPrefSize(200, 70);
            Stage stage = (Stage) a1.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image ("/misc/MudControl.png"));
            a1.showAndWait();
        } else {
            if (((NumberUtils.isCreatable(mudLost.getText().replace(',', '.'))) || (mudLost.getText().isEmpty()))
                    && ((NumberUtils.isCreatable(mudMade.getText().replace(',', '.'))) || (mudMade.getText().isEmpty()))
                    && ((NumberUtils.isCreatable(caCO3.getText().replace(',', '.'))) || (caCO3.getText().isEmpty()))
                    && ((NumberUtils.isCreatable(lubric.getText().replace(',', '.'))) || (lubric.getText().isEmpty()))
                    && ((NumberUtils.isCreatable(kCl.getText().replace(',', '.'))) || (kCl.getText().isEmpty()))
                    && ((NumberUtils.isCreatable(centrifuge.getText().replace(',', '.'))) || (centrifuge.getText().isEmpty()))
                    && ((NumberUtils.isCreatable(krakhmal.getText().replace(',', '.'))) || (krakhmal.getText().isEmpty()))
                    && ((NumberUtils.isCreatable(barite.getText().replace(',', '.'))) || (barite.getText().isEmpty()))
                    && ((NumberUtils.isCreatable(naCl.getText().replace(',', '.'))) || (naCl.getText().isEmpty()))
                    && ((NumberUtils.isCreatable(ingib1.getText().replace(',', '.'))) || (ingib1.getText().isEmpty()))
                    && ((NumberUtils.isCreatable(ingib2.getText().replace(',', '.'))) || (ingib2.getText().isEmpty()))) {
                String loc = chooseBoxLocat.getValue();
                String fiel = chooseBoxWell.getValue();
                String dat = date.getValue().toString();
                Double[] arr = new Double[11];
                if (!mudLost.getText().isEmpty()) {
                    arr[0] = Double.parseDouble(mudLost.getText().replace(',', '.'));
                } else {
                    arr[0] = Double.parseDouble("0");
                }
                if (!mudMade.getText().isEmpty()) {
                    arr[1] = Double.parseDouble(mudMade.getText().replace(',', '.'));
                } else {
                    arr[1] = Double.parseDouble("0");
                }
                if (!caCO3.getText().isEmpty()) {
                    arr[2] = Double.parseDouble(caCO3.getText().replace(',', '.'));
                } else {
                    arr[2] = Double.parseDouble("0");
                }
                if (!lubric.getText().isEmpty()) {
                    arr[3] = Double.parseDouble(lubric.getText().replace(',', '.'));
                } else {
                    arr[3] = Double.parseDouble("0");
                }
                if (!kCl.getText().isEmpty()) {
                    arr[4] = Double.parseDouble(kCl.getText().replace(',', '.'));
                } else {
                    arr[4] = Double.parseDouble("0");
                }
                if (!krakhmal.getText().isEmpty()) {
                    arr[5] = Double.parseDouble(krakhmal.getText().replace(',', '.'));
                } else {
                    arr[5] = Double.parseDouble("0");
                }
                if (!barite.getText().isEmpty()) {
                    arr[6] = Double.parseDouble(barite.getText().replace(',', '.'));
                } else {
                    arr[6] = Double.parseDouble("0");
                }
                if (!naCl.getText().isEmpty()) {
                    arr[7] = Double.parseDouble(naCl.getText().replace(',', '.'));
                } else {
                    arr[7] = Double.parseDouble("0");
                }
                if (!ingib1.getText().isEmpty()) {
                    arr[8] = Double.parseDouble(ingib1.getText().replace(',', '.'));
                } else {
                    arr[8] = Double.parseDouble("0");
                }
                if (!ingib2.getText().isEmpty()) {
                    arr[9] = Double.parseDouble(ingib2.getText().replace(',', '.'));
                } else {
                    arr[9] = Double.parseDouble("0");
                }
                if (!centrifuge.getText().isEmpty()) {
                    arr[10] = Double.parseDouble(centrifuge.getText().replace(',', '.'));
                } else {
                    arr[10] = Double.parseDouble("0");
                }
                ResultSet rs = chooseWellsFromDb(chooseBoxLocat.getValue(), chooseBoxWell.getValue());
                int dateFlag = 0;
                dateList.clear();
                String a;
                while (rs.next()) {
                    a = rs.getString(3);
                    if (!a.equals(null)) {
                        dateList.add(a);
                    }
                }
                for (int i = 0; i < dateList.size(); i++) {
                    if (dat.equals(dateList.get(i))) {
                        dateFlag = 1;
                    }
                }
                if (dateFlag == 0) {
                    app.insertInDbWBM(loc, fiel, dat, arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], 1, commentUpdate);
                    commentUpdate="";
                } else {
                    Alert a1 = new Alert(Alert.AlertType.ERROR);
                    a1.setHeaderText("Input with this Date already exist");
                    a1.setTitle("ERROR");
                    a1.setResizable(true);
                    a1.getDialogPane().setPrefSize(200, 70);
                    Stage stage = (Stage) a1.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image ("/misc/MudControl.png"));
                    a1.showAndWait();
                }
                date.setValue(null);
                mudLost.clear();
                mudMade.clear();
                caCO3.clear();
                lubric.clear();
                kCl.clear();
                krakhmal.clear();
                barite.clear();
                naCl.clear();
                ingib1.clear();
                ingib2.clear();
                centrifuge.clear();
                try {
                    listWellsTable();
                } catch (SQLException ex) {
                    Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Alert a1 = new Alert(Alert.AlertType.ERROR);
                a1.setHeaderText("Please insert numbers in parameters");
                a1.setTitle("ERROR");
                a1.setResizable(true);
                a1.getDialogPane().setPrefSize(200, 70);
                Stage stage = (Stage) a1.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image ("/misc/MudControl.png"));
                a1.showAndWait();
            }
        }
    }

    public void applyTrans(String locat, String field,double mudTrans, double trcaCO3, double trLubric, double trkCl, double trkrahmal, double trbarite, double trnaCl, double tringib1, double tringib2 ) throws SQLException, IOException
    {
        chooseBoxLocat.setValue(locat);
        chooseBoxWell.setValue(field);
        date.setValue(LocalDate.now().minusDays(1));
        mudMade.setText(String.format("%.0f",mudTrans).replace(",", "."));
        caCO3.setText(String.format("%.0f",trcaCO3).replace(",", "."));
        lubric.setText(String.format("%.0f",trLubric).replace(",", "."));
        kCl.setText(String.format("%.0f",trkCl).replace(",", "."));
        krakhmal.setText(String.format("%.0f",trkrahmal).replace(",", "."));
        barite.setText(String.format("%.0f",trbarite).replace(",", "."));
        naCl.setText(String.format("%.0f",trnaCl).replace(",", "."));
        ingib1.setText(String.format("%.0f",tringib1).replace(",", "."));
        ingib2.setText(String.format("%.0f",tringib2).replace(",", "."));
        listWellsTable();
    }

    public void insertInDbWBM(String locat, String field, String date, double mudLost, double mudMade, double caCO3, double lubric, double kCl, double krahmal, double barite, double naCl, double ingib1, double ingib2, double centrif, int flag, String comment) throws SQLException {
        String sql = "INSERT INTO MudControlProperties(FieldLocation,Field_Pad_Well,Date_Time,Mud_DayLost,Mud_MadeDay,Mud_CaCO3,Mud_Lubric,Mud_KCL,Mud_Krahmal,Mud_Barite,Mud_NaCl,Mud_Ingib1,Mud_Ingib2,Mud_CentriTime,ActiveFlag,Comment) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Connection conn = loginModal.connectWBM();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, locat);
        pstmt.setString(2, field);
        pstmt.setString(3, date);
        pstmt.setDouble(4, mudLost);
        pstmt.setDouble(5, mudMade);
        pstmt.setDouble(6, caCO3);
        pstmt.setDouble(7, lubric);
        pstmt.setDouble(8, kCl);
        pstmt.setDouble(9, krahmal);
        pstmt.setDouble(10, barite);
        pstmt.setDouble(11, naCl);
        pstmt.setDouble(12, ingib1);
        pstmt.setDouble(13, ingib2);
        pstmt.setDouble(14, centrif);
        pstmt.setInt(15, flag);
        pstmt.setString(16,comment);
        pstmt.executeUpdate();
        pstmt.close();
    }
    
    public void clearAllDb(String locat) throws SQLException {
        String sql = "DELETE FROM MudControlProperties WHERE FieldLocation = ?";
        Connection conn = loginModal.connectWBM();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, locat);
        pstmt.executeUpdate();
        pstmt.close();
    }

    public ResultSet chooseWellsFromDb(String locat, String well) throws SQLException {
        ArrayList<String> fields = new ArrayList<String>();
        int flag = 0;
        String sql = "SELECT * " + "FROM MudControlProperties WHERE FieldLocation = ? AND Field_Pad_Well = ? ";
        Connection conn = loginModal.connectWBM();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, locat);
        pstmt.setString(2, well);
        ResultSet rs = pstmt.executeQuery();
        return rs;
    }

    public ResultSet chooseLocationFromDb(String location) throws SQLException {
        ArrayList<String> fields = new ArrayList<String>();
        int flag = 0;
        String sql = "SELECT * FROM MudControlProperties WHERE FieldLocation = ?";
        Connection conn = loginModal.connectWBM();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, location);
        ResultSet rs = pstmt.executeQuery();
        pstmt.close();
        return rs;
    }

    @FXML
    ObservableList<String> allLocations() throws SQLException {
        WaterBaseMudController app = new WaterBaseMudController();
        ArrayList<String> fields = app.chooseLocation(String.valueOf("1"));
        for (int i = 0; i < fields.size(); i++) {
            list.add(fields.get(i));
        }
        return list;
    }

    ObservableList<String> allWellsInLocation(String a) throws SQLException {
        WaterBaseMudController app = new WaterBaseMudController();
        ArrayList<String> fields = app.chooseWells(a, "1");
        for (int i = 0; i < fields.size(); i++) {
            if (!fields.get(i).equals("0.0")) {
                listWell.add(fields.get(i));
            }
        }
        listWell.sort(null);
        return listWell;
    }

    public ArrayList<String> chooseLocation(String actFlag) throws SQLException {
        ArrayList<String> fields = new ArrayList<String>();
        int flag = 0;
        String sql = "SELECT FieldLocation FROM MudControlProperties WHERE ActiveFlag = ?";
        Connection conn = loginModal.connectWBM();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, actFlag);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            if (fields.isEmpty()) {
                fields.add(rs.getString("FieldLocation"));
            } else {
                for (int i = 0; i < fields.size(); i++) {
                    if (rs.getString("FieldLocation").equals(fields.get(i))) {
                        flag = 1;
                    }
                }
                if (flag == 0) {
                    fields.add(rs.getString("FieldLocation"));
                } else {
                    flag = 0;
                }
            }
        }
        pstmt.close();
        return fields;
    }

    public ArrayList<String> chooseWells(String location, String actFlag) throws SQLException {
        ArrayList<String> fields = new ArrayList<String>();
        int flag = 0;
        String sql = "SELECT Field_Pad_Well " + "FROM MudControlProperties WHERE FieldLocation = ? AND ActiveFlag = ?";
        Connection conn = loginModal.connectWBM();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, location);
        pstmt.setString(2, actFlag);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            if (fields.isEmpty()) {
                fields.add(rs.getString("Field_Pad_Well"));
            } else {
                for (int i = 0; i < fields.size(); i++) {
                    if (rs.getString("Field_Pad_Well").equals(fields.get(i))) {
                        flag = 1;
                    }
                }
                if (flag == 0) {
                    fields.add(rs.getString("Field_Pad_Well"));
                } else {
                    flag = 0;
                }
            }
        }
        pstmt.close();
        return fields;
    }

    @FXML
    void listAllWellsInLocation(ActionEvent event) throws SQLException, FileNotFoundException, IOException {
        WaterBaseMudController app = new WaterBaseMudController();
        try {
            list = app.allLocations();
            chooseBoxLocat.setItems(list);
        } catch (SQLException ex) {
            Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
        }
        listWell.clear();
        listWell = allWellsInLocation(chooseBoxLocat.getValue());
        chooseBoxWell.setItems(listWell);


        clearAll();
    }

    @FXML
    void listWellsTab(ActionEvent event) throws SQLException, FileNotFoundException, IOException {
        listWellsTable();
    }

    Double calc_conc(ObservableList<RowTableWBM> table, int num_calc) throws SQLException, FileNotFoundException, IOException {
        Double conc = null;
        Double conc_prev = null;
        Double[] totMud = new Double[table.size()];
        Double[] calcParam = new Double[table.size()];
        switch (num_calc) {
            case 1:
                for (int i = 0; i < table.size(); i++) {
                    calcParam[i] = Double.parseDouble(table.get(i).caCO3);
                }
                ;
                break;
            case 2:
                for (int i = 0; i < table.size(); i++) {
                    calcParam[i] = Double.parseDouble(table.get(i).lubric);
                }
                ;
                ;
                break;
            case 3:
                for (int i = 0; i < table.size(); i++) {
                    calcParam[i] = Double.parseDouble(table.get(i).kCl);
                }
                ;
                ;
                break;
            case 4:
                for (int i = 0; i < table.size(); i++) {
                    calcParam[i] = Double.parseDouble(table.get(i).krakhmal);
                }
                ;
                ;
                break;
            case 5:
                for (int i = 0; i < table.size(); i++) {
                    calcParam[i] = Double.parseDouble(table.get(i).barite);
                }
                ;
                ;
                break;
            case 6:
                for (int i = 0; i < table.size(); i++) {
                    calcParam[i] = Double.parseDouble(table.get(i).naCl);
                }
                ;
                ;
                break;
            case 7:
                for (int i = 0; i < table.size(); i++) {
                    calcParam[i] = Double.parseDouble(table.get(i).ingib1);
                }
                ;
                ;
                break;
            case 8:
                for (int i = 0; i < table.size(); i++) {
                    calcParam[i] = Double.parseDouble(table.get(i).ingib2);
                }
                ;
                ;
                break;
        }
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
        }
        for (int i = 0; i < table.size(); i++) {
            if (i == 0) {
                if ((Double.parseDouble(table.get(i).mudMade) - Double.parseDouble(table.get(i).mudLost)) == 0) {
                    conc = 0.0;
                } else {
                    conc = calcParam[i] / (Double.parseDouble(table.get(i).mudMade) - Double.parseDouble(table.get(i).mudLost));
                }
                conc_prev = conc;
            } else {
                if (totMud[i] == 0) {
                    conc = 0.0;
                } else {
                    conc = (totMud[i - 1] * conc_prev + calcParam[i] - Double.parseDouble(table.get(i).mudLost) * conc_prev) / (totMud[i]);
                }
                conc_prev = conc;
            }
        }
        return conc;
    }

    void clearAll() {
        caCO3Calc.clear();
        lubricCalc.clear();
        kClCalc.clear();
        krakhmalCalc.clear();
        bariteCalc.clear();
        naClCalc.clear();
        ingib1Calc.clear();
        ingib2Calc.clear();
        caCO3Report.clear();
        lubricReport.clear();
        kClReport.clear();
        krakhmalReport.clear();
        bariteReport.clear();
        naClReport.clear();
        ingib1Report.clear();
        ingib2Report.clear();
        caCO3Diff.clear();
        caCO3Diff.setBackground(new Background(new BackgroundFill(Color.rgb(190, 190, 190), CornerRadii.EMPTY, Insets.EMPTY)));
        lubricDiff.clear();
        lubricDiff.setBackground(new Background(new BackgroundFill(Color.rgb(190, 190, 190), CornerRadii.EMPTY, Insets.EMPTY)));
        kClDiff.clear();
        kClDiff.setBackground(new Background(new BackgroundFill(Color.rgb(190, 190, 190), CornerRadii.EMPTY, Insets.EMPTY)));
        krakhmalDiff.clear();
        krakhmalDiff.setBackground(new Background(new BackgroundFill(Color.rgb(190, 190, 190), CornerRadii.EMPTY, Insets.EMPTY)));
        bariteDiff.clear();
        bariteDiff.setBackground(new Background(new BackgroundFill(Color.rgb(190, 190, 190), CornerRadii.EMPTY, Insets.EMPTY)));
        naClDiff.clear();
        naClDiff.setBackground(new Background(new BackgroundFill(Color.rgb(190, 190, 190), CornerRadii.EMPTY, Insets.EMPTY)));
        ingib1Diff.clear();
        ingib1Diff.setBackground(new Background(new BackgroundFill(Color.rgb(190, 190, 190), CornerRadii.EMPTY, Insets.EMPTY)));
        ingib2Diff.clear();
        ingib2Diff.setBackground(new Background(new BackgroundFill(Color.rgb(190, 190, 190), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    void listWellsTable() throws SQLException, FileNotFoundException, IOException {
        clearAll();
        ResultSet rs = chooseWellsFromDb(chooseBoxLocat.getValue(), chooseBoxWell.getValue());
        String a = new String();
        String[] repData = new String[8];
        table.clear();commentList.clear();
        while (rs.next()) {
            RowTableWBM azaz = new RowTableWBM(rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),
                    rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12),
                    rs.getString(13), rs.getString(14), "");
            if ((!azaz.getDate().isEmpty()) && (!azaz.getDate().equals("0.0")) && (rs.getInt(15) == 1))
            {
                if (((rs.getString(16)==null)||(rs.getString(16).equals(""))))
                    commentList.add(0);
                else commentList.add(1);
                table.add(azaz);
            } else {
                if (rs.getInt(15) == 2) {
                    repData[0] = azaz.getCaCO3();
                    repData[1] = azaz.getLubric();
                    repData[2] = azaz.getkCl();
                    repData[3] = azaz.getKrakhmal();
                    repData[4] = azaz.getBarite();
                    repData[5] = azaz.getNaCl();
                    repData[6] = azaz.getIngib1();
                    repData[7] = azaz.getIngib2();
                }
                if (azaz.getDate().equals("0.0"))
                {
                    if ((rs.getString(16)==null)||(rs.getString(16).equals(""))||(rs.getString(16).split("    ").length==1))
                    {
                        inhib1daily.setText("Inhibitor 1, kg");
                        inhib2daily.setText("Inhibitor 2, kg");
                        ingib1Tab.setText("Inhibitor 1");
                        ingib2Tab.setText("Inhibitor 2");
                        inhib1report.setText("Inhibitor 1");
                        inhib2report.setText("Inhibitor 2");
                    }
                    else
                    {
                        String[] ingib=new String[2];
                        ingib=rs.getString(16).split("     ");
                        inhib1daily.setText(ingib[0]+ ", kg");
                        inhib2daily.setText(ingib[1]+ ", kg");
                        ingib1Tab.setText(ingib[0]);
                        ingib2Tab.setText(ingib[1]);
                        inhib1report.setText(ingib[0]);
                        inhib2report.setText(ingib[1]);
                    }
                }
            }
        }

        int flag = 0;
        int flag2 = 0;
        RowTableWBM azaz;
        Integer azaza;
        while (flag == 0) {
            flag2 = 0;
            for (int i = 0; i < table.size() - 1; i++) {
                if (table.get(i).date.compareTo(table.get(i + 1).date) > 0) {
                    flag2 = 1;
                    azaz = table.get(i);
                    table.set(i, table.get(i + 1));
                    table.set(i + 1, azaz);
                    azaza = commentList.get(i);
                    commentList.set(i,commentList.get(i+1));
                    commentList.set(i+1, azaza);
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
            table.get(i).setMudLeft(String.format("%.1f", totMud[i]));//totMud[i].toString());
        }
        dateTab.setCellValueFactory(new PropertyValueFactory<RowTableWBM, String>("date"));
        dateTab.setCellFactory(new Callback<TableColumn<RowTableWBM, String>, TableCell<RowTableWBM, String>>() {
            @Override
            public TableCell<RowTableWBM, String> call(TableColumn<RowTableWBM, String> param) {
                return new TableCell<RowTableWBM, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
//                        CommentController commApp = new CommentController();
                        LocalDate[] localDate = new LocalDate[table.size()];
//                        int[] comments = new int[table.size()];
//                        for (int j=0;j<table.size(); j++){
//                            try {
//                                String s=commApp.chooseCommentWBM(chooseBoxLocat.getValue(), chooseBoxWell.getValue(),table.get(j).getDate());
//                                if ((s!=null))
//                                if (!s.isEmpty()) comments[j]=1;
//                            } catch (SQLException ex) {
//                                Logger.getLogger(WaterBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
//                            }
//                        }
                        int[] dateComp = new int[table.size()];
//                        LocalDate start = LocalDate.parse("1970-01-01");
                        for (int j = 0; j < table.size(); j++) {
                            localDate[j] = LocalDate.parse(table.get(j).date);
                        }
                        for (int j = 0; j < table.size() - 1; j++) {
                            if (((localDate[j].until(localDate[j + 1])).getYears() > 0) || ((localDate[j].until(localDate[j + 1])).getMonths() > 0) || ((localDate[j].until(localDate[j + 1])).getDays() > 1)) {
                                dateComp[j] = 1;
                            }
                        }
                        super.updateItem(item, empty);
                        if (!isEmpty()) {
                            setText(item);
                            if (dateComp[super.getIndex()] == 1) {
                                this.setTextFill(Color.RED);
                            }
                            else{
                                this.setTextFill(Color.rgb(65, 65, 65));
                            }
                            if (commentList.get(super.getIndex()) == 1){
                                this.setUnderline(true);
                            }
                            else{this.setUnderline(false);}
                        }
                    }
                };
            }
        });
        mudLostTab.setCellValueFactory(new PropertyValueFactory<RowTableWBM, String>("mudLost"));
        mudLeftTab.setCellValueFactory(new PropertyValueFactory<RowTableWBM, String>("mudLeft"));
        mudMadeTab.setCellValueFactory(new PropertyValueFactory<RowTableWBM, String>("mudMade"));
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
        centrifugeTab.setCellValueFactory(new PropertyValueFactory<RowTableWBM, String>("centrifuge"));
        dateTab.setStyle("-fx-alignment: CENTER;");
        mudLostTab.setStyle("-fx-alignment: CENTER;");
        mudLeftTab.setStyle("-fx-alignment: CENTER;");
        mudMadeTab.setStyle("-fx-alignment: CENTER;");
        caCO3Tab.setStyle("-fx-alignment: CENTER;");
        lubricTab.setStyle("-fx-alignment: CENTER;");
        kClTab.setStyle("-fx-alignment: CENTER;");
        centrifugeTab.setStyle("-fx-alignment: CENTER;");
        krakhmalTab.setStyle("-fx-alignment: CENTER;");
        bariteTab.setStyle("-fx-alignment: CENTER;");
        naClTab.setStyle("-fx-alignment: CENTER;");
        ingib1Tab.setStyle("-fx-alignment: CENTER;");
        ingib2Tab.setStyle("-fx-alignment: CENTER;");
        Table.itemsProperty().setValue(table);
        caCO3Calc.setText(String.format("%.1f", calc_conc(table, 1)));
        lubricCalc.setText(String.format("%.1f", calc_conc(table, 2)));
        kClCalc.setText(String.format("%.1f", calc_conc(table, 3)));
        krakhmalCalc.setText(String.format("%.1f", calc_conc(table, 4)));
        bariteCalc.setText(String.format("%.1f", calc_conc(table, 5)));
        naClCalc.setText(String.format("%.1f", calc_conc(table, 6)));
        ingib1Calc.setText(String.format("%.1f", calc_conc(table, 7)));
        ingib2Calc.setText(String.format("%.1f", calc_conc(table, 8)));
        caCO3Report.setText(String.format("%.1f", Double.parseDouble(repData[0])));
        lubricReport.setText(String.format("%.1f", Double.parseDouble(repData[1])));
        kClReport.setText(String.format("%.1f", Double.parseDouble(repData[2])));
        krakhmalReport.setText(String.format("%.1f", Double.parseDouble(repData[3])));
        bariteReport.setText(String.format("%.1f", Double.parseDouble(repData[4])));
        naClReport.setText(String.format("%.1f", Double.parseDouble(repData[5])));
        ingib1Report.setText(String.format("%.1f", Double.parseDouble(repData[6])));
        ingib2Report.setText(String.format("%.1f", Double.parseDouble(repData[7])));
        Double azik = ((Double.parseDouble(caCO3Calc.getText().replace(',', '.')) / Double.parseDouble(caCO3Report.getText().replace(',', '.'))) * 100 - 100);
        if ((!azik.isNaN()) && (!azik.isInfinite())) {
            caCO3Diff.setText(String.format("%.0f", azik));
            if (azik.compareTo(0.0) < 0) {
                caCO3Diff.setBackground(new Background(new BackgroundFill(Color.rgb(255, 0, 0), CornerRadii.EMPTY, Insets.EMPTY)));
            } else {
                caCO3Diff.setBackground(new Background(new BackgroundFill(Color.rgb(0, 210, 155), CornerRadii.EMPTY, Insets.EMPTY)));
            }
        }
        azik = ((Double.parseDouble(lubricCalc.getText().replace(',', '.')) / Double.parseDouble(lubricReport.getText().replace(',', '.'))) * 100 - 100);
        if ((!azik.isNaN()) && (!azik.isInfinite())) {
            lubricDiff.setText(String.format("%.0f", azik));
            if (azik.compareTo(0.0) < 0) {
                lubricDiff.setBackground(new Background(new BackgroundFill(Color.rgb(255, 0, 0), CornerRadii.EMPTY, Insets.EMPTY)));
            } else {
                lubricDiff.setBackground(new Background(new BackgroundFill(Color.rgb(0, 210, 155), CornerRadii.EMPTY, Insets.EMPTY)));
            }
        }
        azik = ((Double.parseDouble(kClCalc.getText().replace(',', '.')) / Double.parseDouble(kClReport.getText().replace(',', '.'))) * 100 - 100);
        if ((!azik.isNaN()) && (!azik.isInfinite())) {
            kClDiff.setText(String.format("%.0f", azik));
            if (azik.compareTo(0.0) < 0) {
                kClDiff.setBackground(new Background(new BackgroundFill(Color.rgb(255, 0, 0), CornerRadii.EMPTY, Insets.EMPTY)));
            } else if (azik.compareTo(0.0) >= 0) {
                kClDiff.setBackground(new Background(new BackgroundFill(Color.rgb(0, 210, 155), CornerRadii.EMPTY, Insets.EMPTY)));
            }
        }
        azik = ((Double.parseDouble(krakhmalCalc.getText().replace(',', '.')) / Double.parseDouble(krakhmalReport.getText().replace(',', '.'))) * 100 - 100);
        if ((!azik.isNaN()) && (!azik.isInfinite())) {
            krakhmalDiff.setText(String.format("%.0f", azik));
            if (azik.compareTo(0.0) < 0) {
                krakhmalDiff.setBackground(new Background(new BackgroundFill(Color.rgb(255, 0, 0), CornerRadii.EMPTY, Insets.EMPTY)));
            } else if (azik.compareTo(0.0) >= 0) {
                krakhmalDiff.setBackground(new Background(new BackgroundFill(Color.rgb(0, 210, 155), CornerRadii.EMPTY, Insets.EMPTY)));
            }
        }
        azik = ((Double.parseDouble(bariteCalc.getText().replace(',', '.')) / Double.parseDouble(bariteReport.getText().replace(',', '.'))) * 100 - 100);
        if ((!azik.isNaN()) && (!azik.isInfinite())) {
            bariteDiff.setText(String.format("%.0f", azik));
            if (azik.compareTo(0.0) < 0) {
                bariteDiff.setBackground(new Background(new BackgroundFill(Color.rgb(255, 0, 0), CornerRadii.EMPTY, Insets.EMPTY)));
            } else if (azik.compareTo(0.0) >= 0) {
                bariteDiff.setBackground(new Background(new BackgroundFill(Color.rgb(0, 210, 155), CornerRadii.EMPTY, Insets.EMPTY)));
            }
        }
        azik = ((Double.parseDouble(naClCalc.getText().replace(',', '.')) / Double.parseDouble(naClReport.getText().replace(',', '.'))) * 100 - 100);
        if ((!azik.isNaN()) && (!azik.isInfinite())) {
            naClDiff.setText(String.format("%.0f", azik));
            if (azik.compareTo(0.0) < 0) {
                naClDiff.setBackground(new Background(new BackgroundFill(Color.rgb(255, 0, 0), CornerRadii.EMPTY, Insets.EMPTY)));
            } else if (azik.compareTo(0.0) >= 0) {
                naClDiff.setBackground(new Background(new BackgroundFill(Color.rgb(0, 210, 155), CornerRadii.EMPTY, Insets.EMPTY)));
            }
        }
        azik = ((Double.parseDouble(ingib1Calc.getText().replace(',', '.')) / Double.parseDouble(ingib1Report.getText().replace(',', '.'))) * 100 - 100);
        if ((!azik.isNaN()) && (!azik.isInfinite())) {
            ingib1Diff.setText(String.format("%.0f", azik));
            if (azik.compareTo(0.0) < 0) {
                ingib1Diff.setBackground(new Background(new BackgroundFill(Color.rgb(255, 0, 0), CornerRadii.EMPTY, Insets.EMPTY)));
            } else if (azik.compareTo(0.0) >= 0) {
                ingib1Diff.setBackground(new Background(new BackgroundFill(Color.rgb(0, 210, 155), CornerRadii.EMPTY, Insets.EMPTY)));
            }
        }
        azik = ((Double.parseDouble(ingib2Calc.getText().replace(',', '.')) / Double.parseDouble(ingib2Report.getText().replace(',', '.'))) * 100 - 100);
        if ((!azik.isNaN()) && (!azik.isInfinite())) {
            ingib2Diff.setText(String.format("%.0f", azik));
            if (azik.compareTo(0.0) < 0) {
                ingib2Diff.setBackground(new Background(new BackgroundFill(Color.rgb(255, 0, 0), CornerRadii.EMPTY, Insets.EMPTY)));
            } else if (azik.compareTo(0.0) >= 0) {
                ingib2Diff.setBackground(new Background(new BackgroundFill(Color.rgb(0, 210, 155), CornerRadii.EMPTY, Insets.EMPTY)));
            }
        }
    }

}