package application;

import com.sun.javafx.scene.control.behavior.TextAreaBehavior;
import com.sun.javafx.scene.control.skin.TextAreaSkin;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
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

public class OilBaseMudController {
    
     @FXML
    private MenuItem addLocation;
     @FXML
    private MenuItem addWell;
    @FXML
    private MenuItem restoreWell;
    @FXML
    private MenuItem close;
    @FXML
    private MenuItem changeMud;
    @FXML
    private MenuItem about1;
    @FXML
    private ComboBox<String> chooseBoxLocat;
    @FXML
    private ComboBox<String> chooseBoxWell;
    @FXML
    private DatePicker date;
    @FXML
    private TextArea mudLost;
    @FXML
    private TextArea mudMade;
    @FXML
    private TextArea caCO3;
    @FXML
    private TextArea baseOil;
    @FXML
    private TextArea caCl2;
    @FXML
    private TextArea emulsifier;
    @FXML
    private TextArea versaTrol;
    @FXML
    private TextArea barite;
    @FXML
    private TextArea weightAgent;
    @FXML
    private TextArea clay;
    @FXML
    private TextArea lime;
    @FXML
    private Button addToDB;
    @FXML
    private TableView<RowTableOBM> Table;
    @FXML
    private TableColumn<RowTableOBM, String> dateTab;
    @FXML
    private TableColumn<RowTableOBM, String> mudLostTab;
    @FXML
    private TableColumn<RowTableOBM, String> mudMadeTab;
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
    private TableColumn<RowTableOBM, String> mudLeftTab;
    @FXML
    private TextArea caCO3Calc;
    @FXML
    private TextArea baseOilCalc;
    @FXML
    private TextArea caCl2Calc;
    @FXML
    private TextArea emulsifierCalc;
    @FXML
    private TextArea versaTrolCalc;
    @FXML
    private TextArea bariteCalc;
    @FXML
    private TextArea weightAgentCalc;
    @FXML
    private TextArea clayCalc;
    @FXML
    private TextArea limeCalc;
    @FXML
    private Button showAllCalc;
    @FXML
    private Button evaluateWellOBM;
    @FXML
    private TextArea caCO3Report;
    @FXML
    private TextArea baseOilReport;
    @FXML
    private TextArea caCl2Report;
    @FXML
    private TextArea emulsifierReport;
    @FXML
    private TextArea versaTrolReport;
    @FXML
    private TextArea bariteReport;
    @FXML
    private TextArea weightAgentReport;
    @FXML
    private TextArea clayReport;
    @FXML
    private TextArea limeReport;
    @FXML
    private Button addReportButt;
    @FXML
    private TextArea caCO3Diff;
    @FXML
    private TextArea  baseOilDiff;
    @FXML
    private TextArea caCl2Diff;
    @FXML
    private TextArea emulsifierDiff;
    @FXML
    private TextArea versaTrolDiff;
    @FXML
    private TextArea bariteDiff;
    @FXML
    private TextArea weightAgentDiff;
    @FXML
    private TextArea clayDiff;
    @FXML
    private TextArea limeDiff;
    
    public LoginModal loginModal = new LoginModal();  
    @FXML
    ObservableList<String> list = FXCollections.observableArrayList();
    ObservableList<String> listWell = FXCollections.observableArrayList();
    ObservableList<RowTableOBM> table = FXCollections.observableArrayList();
    ObservableList<String> dateList = FXCollections.observableArrayList();
    ObservableList<Integer> commentList = FXCollections.observableArrayList();
    String commentUpdate;
    
    @FXML

    public void initialize() {  
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
        baseOil.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if ((event.getCode() == KeyCode.TAB) || (event.getCode() == KeyCode.ENTER)) {
                    TextAreaSkin skin = (TextAreaSkin) baseOil.getSkin();
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
        caCl2.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if ((event.getCode() == KeyCode.TAB) || (event.getCode() == KeyCode.ENTER)) {
                    TextAreaSkin skin = (TextAreaSkin) caCl2.getSkin();
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
        emulsifier.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if ((event.getCode() == KeyCode.TAB) || (event.getCode() == KeyCode.ENTER)) {
                    TextAreaSkin skin = (TextAreaSkin) emulsifier.getSkin();
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
        versaTrol.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if ((event.getCode() == KeyCode.TAB) || (event.getCode() == KeyCode.ENTER)) {
                    TextAreaSkin skin = (TextAreaSkin) versaTrol.getSkin();
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
        weightAgent.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if ((event.getCode() == KeyCode.TAB) || (event.getCode() == KeyCode.ENTER)) {
                    TextAreaSkin skin = (TextAreaSkin) weightAgent.getSkin();
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
        clay.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if ((event.getCode() == KeyCode.TAB) || (event.getCode() == KeyCode.ENTER)) {
                    TextAreaSkin skin = (TextAreaSkin) clay.getSkin();
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
        lime.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if ((event.getCode() == KeyCode.TAB) || (event.getCode() == KeyCode.ENTER)) {
                    TextAreaSkin skin = (TextAreaSkin) lime.getSkin();
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
        baseOilReport.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if ((event.getCode() == KeyCode.TAB) || (event.getCode() == KeyCode.ENTER)) {
                    TextAreaSkin skin = (TextAreaSkin) baseOilReport.getSkin();
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
        caCl2Report.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if ((event.getCode() == KeyCode.TAB) || (event.getCode() == KeyCode.ENTER)) {
                    TextAreaSkin skin = (TextAreaSkin) caCl2Report.getSkin();
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
        emulsifierReport.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if ((event.getCode() == KeyCode.TAB) || (event.getCode() == KeyCode.ENTER)) {
                    TextAreaSkin skin = (TextAreaSkin) emulsifierReport.getSkin();
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
        versaTrolReport.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if ((event.getCode() == KeyCode.TAB) || (event.getCode() == KeyCode.ENTER)) {
                    TextAreaSkin skin = (TextAreaSkin) versaTrolReport.getSkin();
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
        weightAgentReport.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if ((event.getCode() == KeyCode.TAB) || (event.getCode() == KeyCode.ENTER)) {
                    TextAreaSkin skin = (TextAreaSkin) weightAgentReport.getSkin();
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
        clayReport.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if ((event.getCode() == KeyCode.TAB) || (event.getCode() == KeyCode.ENTER)) {
                    TextAreaSkin skin = (TextAreaSkin) clayReport.getSkin();
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
        limeReport.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if ((event.getCode() == KeyCode.TAB) || (event.getCode() == KeyCode.ENTER)) {
                    TextAreaSkin skin = (TextAreaSkin) limeReport.getSkin();
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
        
        OilBaseMudController app = new OilBaseMudController();
        try {
            list = app.allLocations();
            chooseBoxLocat.setItems(list);
        } catch (SQLException ex) {
            Logger.getLogger(OilBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Table.setRowFactory(new Callback<TableView<RowTableOBM>, TableRow<RowTableOBM>>() {
            @Override
            public TableRow<RowTableOBM> call(TableView<RowTableOBM> param) {
                TableRow<RowTableOBM> row = new TableRow<RowTableOBM>();
                MenuItem remove = new MenuItem("Remove Entry");
                MenuItem update = new MenuItem("Update Entry");
                MenuItem addComment = new MenuItem("Add/Show Comment");
                MenuItem transfer = new MenuItem("Transfer Mud");
                ContextMenu menu = new ContextMenu(update, addComment,transfer, remove);
                row.contextMenuProperty().setValue(menu);
                remove.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                         Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
                        alert.setHeaderText("Are you sure you want to delete entry?");
                        alert.setTitle("Delete Well");
                        alert.getDialogPane().setPrefSize(210, 70);
                        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                        stage.getIcons().add(new Image ("/misc/MudControl.png"));
                        alert.showAndWait();

                        if (alert.getResult() == ButtonType.YES) {
                        
                        String sql = "DELETE FROM MudControlProperties WHERE FieldLocation = ? AND Field_Pad_Well = ? AND Date_Time = ? AND (Mud_DayLost = ? OR Mud_DayLost IS NULL) AND (Mud_MadeDay = ? OR Mud_MadeDay IS NULL) AND (Mud_CaCO3 = ? OR Mud_CaCO3 IS NULL) AND (Mud_BaseOil = ? OR Mud_BaseOil IS NULL) AND (Mud_CaCl2 = ? OR Mud_CaCl2 IS NULL) AND (Mud_Emulsifier = ? OR Mud_Emulsifier IS NULL) AND (Mud_VersaTrol = ? OR Mud_VersaTrol IS NULL) AND (Mud_Barite = ? OR Mud_Barite IS NULL) AND (Mud_WeightingAgent = ? OR Mud_WeightingAgent IS NULL) AND (Mud_Clay = ? OR Mud_Clay IS NULL) AND (Mud_Lime = ? OR Mud_Lime IS NULL)";
                        Connection conn = loginModal.connectOBM();
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
                            if (table.get(Table.getSelectionModel().getSelectedIndex()).getBaseOil()== null) {
                                pstmt.setNull(7, 0);
                            } else {
                                pstmt.setDouble(7, Double.parseDouble(table.get(Table.getSelectionModel().getSelectedIndex()).getBaseOil()));
                            }
                            if (table.get(Table.getSelectionModel().getSelectedIndex()).getCaCl2()== null) {
                                pstmt.setNull(8, 0);
                            } else {
                                pstmt.setDouble(8, Double.parseDouble(table.get(Table.getSelectionModel().getSelectedIndex()).getCaCl2()));
                            }
                            if (table.get(Table.getSelectionModel().getSelectedIndex()).getEmilsifier()== null) {
                                pstmt.setNull(9, 0);
                            } else {
                                pstmt.setDouble(9, Double.parseDouble(table.get(Table.getSelectionModel().getSelectedIndex()).getEmilsifier()));
                            }
                            if (table.get(Table.getSelectionModel().getSelectedIndex()).getVersaTrol()== null) {
                                pstmt.setNull(10, 0);
                            } else {
                                pstmt.setDouble(10, Double.parseDouble(table.get(Table.getSelectionModel().getSelectedIndex()).getVersaTrol()));
                            }
                            if (table.get(Table.getSelectionModel().getSelectedIndex()).getBarite()== null) {
                                pstmt.setNull(11, 0);
                            } else {
                                pstmt.setDouble(11, Double.parseDouble(table.get(Table.getSelectionModel().getSelectedIndex()).getBarite()));
                            }
                            if (table.get(Table.getSelectionModel().getSelectedIndex()).getWeigthingAgent()== null) {
                                pstmt.setNull(12, 0);
                            } else {
                                pstmt.setDouble(12, Double.parseDouble(table.get(Table.getSelectionModel().getSelectedIndex()).getWeigthingAgent()));
                            }
                            if (table.get(Table.getSelectionModel().getSelectedIndex()).getClay()== null) {
                                pstmt.setNull(13, 0);
                            } else {
                                pstmt.setDouble(13, Double.parseDouble(table.get(Table.getSelectionModel().getSelectedIndex()).getClay()));
                            }
                            if (table.get(Table.getSelectionModel().getSelectedIndex()).getLime()== null) {
                                pstmt.setNull(14, 0);
                            } else {
                                pstmt.setDouble(14, Double.parseDouble(table.get(Table.getSelectionModel().getSelectedIndex()).getLime()));
                            }
                            pstmt.executeUpdate();
                        } catch (SQLException ex) {
                            Logger.getLogger(OilBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        try {
                            listWellsTable();
                        } catch (SQLException ex) {
                            Logger.getLogger(OilBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(OilBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
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
                        baseOil.setText(table.get(Table.getSelectionModel().getSelectedIndex()).getBaseOil());
                        caCl2.setText(table.get(Table.getSelectionModel().getSelectedIndex()).getCaCl2());
                        emulsifier.setText(table.get(Table.getSelectionModel().getSelectedIndex()).getEmilsifier());
                        versaTrol.setText(table.get(Table.getSelectionModel().getSelectedIndex()).getVersaTrol());
                        barite.setText(table.get(Table.getSelectionModel().getSelectedIndex()).getBarite());
                        weightAgent.setText(table.get(Table.getSelectionModel().getSelectedIndex()).getWeigthingAgent());
                        clay.setText(table.get(Table.getSelectionModel().getSelectedIndex()).getClay());
                        lime.setText(table.get(Table.getSelectionModel().getSelectedIndex()).getLime());
                        
                        CommentController appp = new CommentController();
                        try {
                            commentUpdate=appp.chooseCommentOBM(chooseBoxLocat.getValue(),chooseBoxWell.getValue(),table.get(Table.getSelectionModel().getSelectedIndex()).getDate());
                        } catch (SQLException ex) {
                            Logger.getLogger(OilBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        String sql = "DELETE FROM MudControlProperties WHERE FieldLocation = ? AND Field_Pad_Well = ? AND Date_Time = ? AND (Mud_DayLost = ? OR Mud_DayLost IS NULL) AND (Mud_MadeDay = ? OR Mud_MadeDay IS NULL) AND (Mud_CaCO3 = ? OR Mud_CaCO3 IS NULL) AND (Mud_BaseOil = ? OR Mud_BaseOil IS NULL) AND (Mud_CaCl2 = ? OR Mud_CaCl2 IS NULL) AND (Mud_Emulsifier = ? OR Mud_Emulsifier IS NULL) AND (Mud_VersaTrol = ? OR Mud_VersaTrol IS NULL) AND (Mud_Barite = ? OR Mud_Barite IS NULL) AND (Mud_WeightingAgent = ? OR Mud_WeightingAgent IS NULL) AND (Mud_Clay = ? OR Mud_Clay IS NULL) AND (Mud_Lime = ? OR Mud_Lime IS NULL)";
                        Connection conn = loginModal.connectOBM();
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
                            if (table.get(Table.getSelectionModel().getSelectedIndex()).getBaseOil()== null) {
                                pstmt.setNull(7, 0);
                            } else {
                                pstmt.setDouble(7, Double.parseDouble(table.get(Table.getSelectionModel().getSelectedIndex()).getBaseOil()));
                            }
                            if (table.get(Table.getSelectionModel().getSelectedIndex()).getCaCl2()== null) {
                                pstmt.setNull(8, 0);
                            } else {
                                pstmt.setDouble(8, Double.parseDouble(table.get(Table.getSelectionModel().getSelectedIndex()).getCaCl2()));
                            }
                            if (table.get(Table.getSelectionModel().getSelectedIndex()).getEmilsifier()== null) {
                                pstmt.setNull(9, 0);
                            } else {
                                pstmt.setDouble(9, Double.parseDouble(table.get(Table.getSelectionModel().getSelectedIndex()).getEmilsifier()));
                            }
                            if (table.get(Table.getSelectionModel().getSelectedIndex()).getVersaTrol()== null) {
                                pstmt.setNull(10, 0);
                            } else {
                                pstmt.setDouble(10, Double.parseDouble(table.get(Table.getSelectionModel().getSelectedIndex()).getVersaTrol()));
                            }
                            if (table.get(Table.getSelectionModel().getSelectedIndex()).getBarite()== null) {
                                pstmt.setNull(11, 0);
                            } else {
                                pstmt.setDouble(11, Double.parseDouble(table.get(Table.getSelectionModel().getSelectedIndex()).getBarite()));
                            }
                            if (table.get(Table.getSelectionModel().getSelectedIndex()).getWeigthingAgent()== null) {
                                pstmt.setNull(12, 0);
                            } else {
                                pstmt.setDouble(12, Double.parseDouble(table.get(Table.getSelectionModel().getSelectedIndex()).getWeigthingAgent()));
                            }
                            if (table.get(Table.getSelectionModel().getSelectedIndex()).getClay()== null) {
                                pstmt.setNull(13, 0);
                            } else {
                                pstmt.setDouble(13, Double.parseDouble(table.get(Table.getSelectionModel().getSelectedIndex()).getClay()));
                            }
                            if (table.get(Table.getSelectionModel().getSelectedIndex()).getLime()== null) {
                                pstmt.setNull(14, 0);
                            } else {
                                pstmt.setDouble(14, Double.parseDouble(table.get(Table.getSelectionModel().getSelectedIndex()).getLime()));
                            }
                            pstmt.executeUpdate();
                        } catch (SQLException ex) {
                            Logger.getLogger(OilBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        try {
                            listWellsTable();
                        } catch (SQLException ex) {
                            Logger.getLogger(OilBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(OilBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                addComment.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                       Stage primaryStage7 = new Stage();
                        try {
                            FXMLLoader loader = new FXMLLoader();
                            Parent root7 = loader.load(getClass().getResource("/application/CommentOBM.fxml").openStream());
                            CommentController comContr = (CommentController) loader.getController();
                            comContr.showCommentOBM(chooseBoxLocat.getValue(),chooseBoxWell.getValue(),table.get(Table.getSelectionModel().getSelectedIndex()).getDate());
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
                                        Logger.getLogger(OilBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (IOException ex) {
                                        Logger.getLogger(OilBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
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
                            Parent root8 = loader.load(getClass().getResource("/application/TransferMudOBM.fxml").openStream());
                            final TransferMudControllerOBM transContr = (TransferMudControllerOBM) loader.getController();
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
                                        
                                        OilBaseMudController app = new OilBaseMudController();
                                        String sql = "SELECT Comment " + "FROM MudControlProperties WHERE FieldLocation = ? AND Field_Pad_Well = ? AND Date_Time = ?";
                                        Connection conn = app.loginModal.connectOBM();
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
                                            
                                        Connection conn2 = app.loginModal.connectOBM();
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
                                        
                                        
                                        
                                        applyTransOBM(transLocation, transWell, transAmount, trans[0], trans[1], trans[2], trans[3], trans[4], trans[5], trans[6], trans[7],trans[8]);
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
                ContextMenu menu = new ContextMenu(archive, rename, delete);
                chooseBoxWell.contextMenuProperty().setValue(menu);
                delete.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
                        alert.setHeaderText("Are you sure you want to delete well?");
                        alert.setTitle("Delete Well");
                        alert.getDialogPane().setPrefSize(210, 70);
                        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                        stage.getIcons().add(new Image ("/misc/MudControl.png"));
                        alert.showAndWait();

                        if (alert.getResult() == ButtonType.YES) {
                            String sql = "DELETE FROM MudControlProperties WHERE FieldLocation = ? AND Field_Pad_Well = ?";
                            Connection conn = loginModal.connectOBM();
                            PreparedStatement pstmt;
                            try {
                                pstmt = conn.prepareStatement(sql);
                                pstmt.setString(1, chooseBoxLocat.getValue());
                                pstmt.setString(2, chooseBoxWell.getValue());
                                pstmt.executeUpdate();
                            } catch (SQLException ex) {
                                Logger.getLogger(OilBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            try {
                                OilBaseMudController app = new OilBaseMudController();
                                try {
                                    list = app.allLocations();
                                    chooseBoxLocat.setItems(list);
                                } catch (SQLException ex) {
                                    Logger.getLogger(OilBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                listWell.clear();
                                listWell = allWellsInLocation(chooseBoxLocat.getValue());
                                chooseBoxWell.setItems(listWell);
                                clearAll();
                            } catch (SQLException ex) {
                                Logger.getLogger(OilBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                });
                archive.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
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
                                Logger.getLogger(OilBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            try {
                                updateDb(3, chooseBoxLocat.getValue(), chooseBoxWell.getValue(), 1);
                            } catch (SQLException ex) {
                                Logger.getLogger(OilBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            try {
                                OilBaseMudController app = new OilBaseMudController();
                                try {
                                    list = app.allLocations();
                                    chooseBoxLocat.setItems(list);
                                } catch (SQLException ex) {
                                    Logger.getLogger(OilBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                listWell.clear();
                                listWell = allWellsInLocation(chooseBoxLocat.getValue());
                                chooseBoxWell.setItems(listWell);
                                clearAll();
                            } catch (SQLException ex) {
                                Logger.getLogger(OilBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                });
                rename.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
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
                                Connection conn = loginModal.connectOBM();
                                PreparedStatement pstmt;
                                try {
                                    pstmt = conn.prepareStatement(sql);
                                    pstmt.setString(1, result.get());
                                    pstmt.setString(2, chooseBoxLocat.getValue());
                                    pstmt.setString(3, chooseBoxWell.getValue());
                                    pstmt.executeUpdate();
                                } catch (SQLException ex) {
                                    Logger.getLogger(OilBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                try {
                                    OilBaseMudController app = new OilBaseMudController();
                                    try {
                                        list = app.allLocations();
                                        chooseBoxLocat.setItems(list);
                                    } catch (SQLException ex) {
                                        Logger.getLogger(OilBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    listWell.clear();
                                    listWell = allWellsInLocation(chooseBoxLocat.getValue());
                                    chooseBoxWell.setItems(listWell);
                                    clearAll();
                                } catch (SQLException ex) {
                                    Logger.getLogger(OilBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
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
                                Connection conn = loginModal.connectOBM();
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
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
                        alert.setHeaderText("Are you sure you want to delete location?");
                        alert.setTitle("Delete Well");
                        alert.getDialogPane().setPrefSize(210, 70);
                        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                        stage.getIcons().add(new Image ("/misc/MudControl.png"));
                        alert.showAndWait();

                        if (alert.getResult() == ButtonType.YES) {
                            String sql = "DELETE FROM MudControlProperties WHERE FieldLocation = ?";
                            Connection conn = loginModal.connectOBM();
                            PreparedStatement pstmt;
                            try {
                                pstmt = conn.prepareStatement(sql);
                                pstmt.setString(1, chooseBoxLocat.getValue());
                                pstmt.executeUpdate();
                            } catch (SQLException ex) {
                                Logger.getLogger(OilBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            try {
                                OilBaseMudController app = new OilBaseMudController();
                                try {
                                    list = app.allLocations();
                                    chooseBoxLocat.setItems(list);
                                } catch (SQLException ex) {
                                    Logger.getLogger(OilBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                listWell.clear();
                                listWell = allWellsInLocation(chooseBoxLocat.getValue());
                                chooseBoxWell.setItems(listWell);
                                clearAll();
                            } catch (SQLException ex) {
                                Logger.getLogger(OilBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                });
                }
        });
    }
    
    public void updateDb(int actNew, String locat, String field, int actOld) throws SQLException {
        String sql = "UPDATE MudControlProperties SET ActiveFlag = ? WHERE FieldLocation = ? AND Field_Pad_Well = ? AND ActiveFlag = ?";
        Connection conn = loginModal.connectOBM();
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
            Logger.getLogger(OilBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    void addWell(ActionEvent event) {
        Stage primaryStage = new Stage();
        try {
            Parent root3 = FXMLLoader.load(getClass().getResource("/application/AddWellOBM.fxml"));
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
            Parent root4 = FXMLLoader.load(getClass().getResource("/application/RestoreWellOBM.fxml"));
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
            Parent root4 = loader.load(getClass().getResource("/application/AddLocationOBM.fxml"));
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

    
    void refreshData() {
        OilBaseMudController app = new OilBaseMudController();
        try {
            list = app.allLocations();
            chooseBoxLocat.setItems(list);
        } catch (SQLException ex) {
            Logger.getLogger(OilBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void showAllCalc(ActionEvent event) throws InterruptedException {
        Stage primaryStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader();
            Pane root5 = loader.load(getClass().getResource("/application/AllCalculationOBM.fxml").openStream());
            AllCalculationOBMController allCalcContr = (AllCalculationOBMController) loader.getController();
            allCalcContr.showTable(chooseBoxLocat.getValue(), chooseBoxWell.getValue());
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
    
    @FXML
    void addReportData(ActionEvent event) throws SQLException, IOException {
        String sql = "DELETE FROM MudControlProperties WHERE FieldLocation = ? AND Field_Pad_Well = ? AND ActiveFlag = ?";
        Connection conn = loginModal.connectOBM();
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, chooseBoxLocat.getValue());
            pstmt.setString(2, chooseBoxWell.getValue());
            pstmt.setInt(3, 2);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OilBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
        }
        OilBaseMudController app = new OilBaseMudController();
        if (((NumberUtils.isCreatable(caCO3Report.getText().replace(',', '.'))) || (caCO3Report.getText().isEmpty()))
                && ((NumberUtils.isCreatable(baseOilReport.getText().replace(',', '.'))) || (baseOilReport.getText().isEmpty()))
                && ((NumberUtils.isCreatable(caCl2Report.getText().replace(',', '.'))) || (caCl2Report.getText().isEmpty()))
                && ((NumberUtils.isCreatable(emulsifierReport.getText().replace(',', '.'))) || (emulsifierReport.getText().isEmpty()))
                && ((NumberUtils.isCreatable(versaTrolReport.getText().replace(',', '.'))) || (versaTrolReport.getText().isEmpty()))
                && ((NumberUtils.isCreatable(bariteReport.getText().replace(',', '.'))) || (bariteReport.getText().isEmpty()))
                && ((NumberUtils.isCreatable(weightAgentReport.getText().replace(',', '.'))) || (weightAgentReport.getText().isEmpty()))
                && ((NumberUtils.isCreatable(clayReport.getText().replace(',', '.'))) || (clayReport.getText().isEmpty()))
                && ((NumberUtils.isCreatable(limeReport.getText().replace(',', '.'))) || (limeReport.getText().isEmpty()))) {
            String loc = chooseBoxLocat.getValue();
            String fiel = chooseBoxWell.getValue();
            Double[] arr = new Double[9];
            if ((caCO3Report.getText() != null) && (!caCO3Report.getText().isEmpty())) {
                arr[0] = Double.parseDouble(caCO3Report.getText().replace(',', '.'));
            } else {
                arr[0] = 0.0;
            }
            if ((baseOilReport.getText() != null) && (!baseOilReport.getText().isEmpty())) {
                arr[1] = Double.parseDouble(baseOilReport.getText().replace(',', '.'));
            } else {
                arr[1] = 0.0;
            }
            if ((caCl2Report.getText() != null) && (!caCl2Report.getText().isEmpty())) {
                arr[2] = Double.parseDouble(caCl2Report.getText().replace(',', '.'));
            } else {
                arr[2] = 0.0;
            }
            if ((emulsifierReport.getText() != null) && (!emulsifierReport.getText().isEmpty())) {
                arr[3] = Double.parseDouble(emulsifierReport.getText().replace(',', '.'));
            } else {
                arr[3] = 0.0;
            }
            if ((versaTrolReport.getText() != null) && (!versaTrolReport.getText().isEmpty())) {
                arr[4] = Double.parseDouble(versaTrolReport.getText().replace(',', '.'));
            } else {
                arr[4] = 0.0;
            }
            if ((bariteReport.getText() != null) && (!bariteReport.getText().isEmpty())) {
                arr[5] = Double.parseDouble(bariteReport.getText().replace(',', '.'));
            } else {
                arr[5] = 0.0;
            }
            if ((weightAgentReport.getText() != null) && (!weightAgentReport.getText().isEmpty())) {
                arr[6] = Double.parseDouble(weightAgentReport.getText().replace(',', '.'));
            } else {
                arr[6] = 0.0;
            }
            if ((clayReport.getText() != null) && (!clayReport.getText().isEmpty())) {
                arr[7] = Double.parseDouble(clayReport.getText().replace(',', '.'));
            } else {
                arr[7] = 0.0;
            }
             if ((limeReport.getText() != null) && (!limeReport.getText().isEmpty())) {
                arr[8] = Double.parseDouble(limeReport.getText().replace(',', '.'));
            } else {
                arr[8] = 0.0;
            }
            app.insertInDbOBM(loc, fiel, "", 0.0, 0.0, arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], 2,"");
            try {
                listWellsTable();
            } catch (SQLException ex) {
                Logger.getLogger(OilBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(OilBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
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
        OilBaseMudController app = new OilBaseMudController();
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
                    && ((NumberUtils.isCreatable(baseOil.getText().replace(',', '.'))) || (baseOil.getText().isEmpty()))
                    && ((NumberUtils.isCreatable(caCl2.getText().replace(',', '.'))) || (caCl2.getText().isEmpty()))
                    && ((NumberUtils.isCreatable(emulsifier.getText().replace(',', '.'))) || (emulsifier.getText().isEmpty()))
                    && ((NumberUtils.isCreatable(versaTrol.getText().replace(',', '.'))) || (versaTrol.getText().isEmpty()))
                    && ((NumberUtils.isCreatable(barite.getText().replace(',', '.'))) || (barite.getText().isEmpty()))
                    && ((NumberUtils.isCreatable(weightAgent.getText().replace(',', '.'))) || (weightAgent.getText().isEmpty()))
                    && ((NumberUtils.isCreatable(clay.getText().replace(',', '.'))) || (clay.getText().isEmpty()))
                    && ((NumberUtils.isCreatable(lime.getText().replace(',', '.'))) || (lime.getText().isEmpty()))) {
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
                if (!baseOil.getText().isEmpty()) {
                    arr[3] = Double.parseDouble(baseOil.getText().replace(',', '.'));
                } else {
                    arr[3] = Double.parseDouble("0");
                }
                if (!caCl2.getText().isEmpty()) {
                    arr[4] = Double.parseDouble(caCl2.getText().replace(',', '.'));
                } else {
                    arr[4] = Double.parseDouble("0");
                }
                if (!emulsifier.getText().isEmpty()) {
                    arr[5] = Double.parseDouble(emulsifier.getText().replace(',', '.'));
                } else {
                    arr[5] = Double.parseDouble("0");
                }
                if (!versaTrol.getText().isEmpty()) {
                    arr[6] = Double.parseDouble(versaTrol.getText().replace(',', '.'));
                } else {
                    arr[6] = Double.parseDouble("0");
                }
                if (!barite.getText().isEmpty()) {
                    arr[7] = Double.parseDouble(barite.getText().replace(',', '.'));
                } else {
                    arr[7] = Double.parseDouble("0");
                }
                if (!weightAgent.getText().isEmpty()) {
                    arr[8] = Double.parseDouble(weightAgent.getText().replace(',', '.'));
                } else {
                    arr[8] = Double.parseDouble("0");
                }
                if (!clay.getText().isEmpty()) {
                    arr[9] = Double.parseDouble(clay.getText().replace(',', '.'));
                } else {
                    arr[9] = Double.parseDouble("0");
                }
                if (!lime.getText().isEmpty()) {
                    arr[10] = Double.parseDouble(lime.getText().replace(',', '.'));
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
                    app.insertInDbOBM(loc, fiel, dat, arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], 1, commentUpdate);
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
                baseOil.clear();
                caCl2.clear();
                emulsifier.clear();
                versaTrol.clear();
                barite.clear();
                weightAgent.clear();
                clay.clear();
                lime.clear();
                try {
                    listWellsTable();
                } catch (SQLException ex) {
                    Logger.getLogger(OilBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(OilBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void applyTransOBM(String locat, String field,double mudTrans, double trcaCO3, double trLubric, double trkCl, double trkrahmal, double trbarite, double trnaCl, double tringib1, double tringib2, double trlime) throws SQLException, IOException
    {
        chooseBoxLocat.setValue(locat);
        chooseBoxWell.setValue(field);
        date.setValue(LocalDate.now().minusDays(1));
        mudMade.setText(String.format("%.0f",mudTrans).replace(",", "."));
        caCO3.setText(String.format("%.0f",trcaCO3).replace(",", "."));
        baseOil.setText(String.format("%.0f",trLubric).replace(",", "."));
        caCl2.setText(String.format("%.0f",trkCl).replace(",", "."));
        emulsifier.setText(String.format("%.0f",trkrahmal).replace(",", "."));
        versaTrol.setText(String.format("%.0f",trbarite).replace(",", "."));
        barite.setText(String.format("%.0f",trnaCl).replace(",", "."));
        weightAgent.setText(String.format("%.0f",tringib1).replace(",", "."));
        clay.setText(String.format("%.0f",tringib2).replace(",", "."));
        lime.setText(String.format("%.0f",trlime).replace(",", "."));
        listWellsTable();
    }
    
    public void insertInDbOBM(String locat, String field, String date, double mudLost, double mudMade, double caCO3, double baseOil, double caCl2, double emulsifier, double versaTrol, double barite, double weightAgent, double clay, double lime, int flag, String comment) throws SQLException {
        String sql = "INSERT INTO MudControlProperties(FieldLocation,Field_Pad_Well,Date_Time,Mud_DayLost,Mud_MadeDay,Mud_CaCO3,Mud_BaseOil,Mud_CaCl2,Mud_Emulsifier,Mud_VersaTrol,Mud_Barite,Mud_WeightingAgent,Mud_Clay,Mud_Lime,ActiveFlag,Comment) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Connection conn = loginModal.connectOBM();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, locat);
        pstmt.setString(2, field);
        pstmt.setString(3, date);
        pstmt.setDouble(4, mudLost);
        pstmt.setDouble(5, mudMade);
        pstmt.setDouble(6, caCO3);
        pstmt.setDouble(7, baseOil);
        pstmt.setDouble(8, caCl2);
        pstmt.setDouble(9, emulsifier);
        pstmt.setDouble(10, versaTrol);
        pstmt.setDouble(11, barite);
        pstmt.setDouble(12, weightAgent);
        pstmt.setDouble(13, clay);
        pstmt.setDouble(14, lime);
        pstmt.setInt(15, flag);
        pstmt.setString(16,comment);
        pstmt.executeUpdate();
        pstmt.close();
    }
    
    public ResultSet chooseWellsFromDb(String locat, String well) throws SQLException {
        ArrayList<String> fields = new ArrayList<String>();
        int flag = 0;
        String sql = "SELECT * " + "FROM MudControlProperties WHERE FieldLocation = ? AND Field_Pad_Well = ? ";
        Connection conn = loginModal.connectOBM();
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
        Connection conn = loginModal.connectOBM();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, location);
        ResultSet rs = pstmt.executeQuery();
        pstmt.close();
        return rs;
    }
    
    @FXML
    ObservableList<String> allLocations() throws SQLException {
        OilBaseMudController app = new OilBaseMudController();
        ArrayList<String> fields = app.chooseLocation(String.valueOf("1"));
        for (int i = 0; i < fields.size(); i++) {
            list.add(fields.get(i));
        }
        return list;
    } 
    
    ObservableList<String> allWellsInLocation(String a) throws SQLException {
        OilBaseMudController app = new OilBaseMudController();
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
        Connection conn = loginModal.connectOBM();
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
        Connection conn = loginModal.connectOBM();
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

    
     void closeApplication() {
        Platform.exit();
        System.exit(0);
    }
    
     @FXML
    void closeApp(ActionEvent event) {
         closeApplication();
    }

    @FXML
    void listAllWellsInLocation(ActionEvent event) throws SQLException, FileNotFoundException, IOException {
        OilBaseMudController app = new OilBaseMudController();
        try {
            list = app.allLocations();
            chooseBoxLocat.setItems(list);
        } catch (SQLException ex) {
            Logger.getLogger(OilBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
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

    Double calc_conc(ObservableList<RowTableOBM> table, int num_calc) throws SQLException, FileNotFoundException, IOException {
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
                    calcParam[i] = Double.parseDouble(table.get(i).baseOil);
                }
                ;
                ;
                break;
            case 3:
                for (int i = 0; i < table.size(); i++) {
                    calcParam[i] = Double.parseDouble(table.get(i).caCl2);
                }
                ;
                ;
                break;
            case 4:
                for (int i = 0; i < table.size(); i++) {
                    calcParam[i] = Double.parseDouble(table.get(i).emilsifier);
                }
                ;
                ;
                break;
            case 5:
                for (int i = 0; i < table.size(); i++) {
                    calcParam[i] = Double.parseDouble(table.get(i).versaTrol);
                }
                ;
                ;
                break;
            case 6:
                for (int i = 0; i < table.size(); i++) {
                    calcParam[i] = Double.parseDouble(table.get(i).barite);
                }
                ;
                ;
                break;
            case 7:
                for (int i = 0; i < table.size(); i++) {
                    calcParam[i] = Double.parseDouble(table.get(i).weigthingAgent);
                }
                ;
                ;
                break;
            case 8:
                for (int i = 0; i < table.size(); i++) {
                    calcParam[i] = Double.parseDouble(table.get(i).clay);
                }
                ;
                ;
                break;
            case 9:
                for (int i = 0; i < table.size(); i++) {
                    calcParam[i] = Double.parseDouble(table.get(i).lime);
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
        baseOilCalc.clear();
        caCl2Calc.clear();
        emulsifierCalc.clear();
        versaTrolCalc.clear();
        bariteCalc.clear();
        weightAgentCalc.clear();
        clayCalc.clear();
        limeCalc.clear();
        caCO3Report.clear();
        baseOilReport.clear();
        caCl2Report.clear();
        emulsifierReport.clear();
        versaTrolReport.clear();
        baseOilReport.clear();
        weightAgentReport.clear();
        clayReport.clear();
        limeReport.clear();
        caCO3Diff.clear();
        caCO3Diff.setBackground(new Background(new BackgroundFill(Color.rgb(190, 190, 190), CornerRadii.EMPTY, Insets.EMPTY)));
        baseOilDiff.clear();
        baseOilDiff.setBackground(new Background(new BackgroundFill(Color.rgb(190, 190, 190), CornerRadii.EMPTY, Insets.EMPTY)));
        caCl2.clear();
        caCl2.setBackground(new Background(new BackgroundFill(Color.rgb(190, 190, 190), CornerRadii.EMPTY, Insets.EMPTY)));
        emulsifierDiff.clear();
        emulsifierDiff.setBackground(new Background(new BackgroundFill(Color.rgb(190, 190, 190), CornerRadii.EMPTY, Insets.EMPTY)));
        versaTrolDiff.clear();
        versaTrolDiff.setBackground(new Background(new BackgroundFill(Color.rgb(190, 190, 190), CornerRadii.EMPTY, Insets.EMPTY)));
        bariteDiff.clear();
        bariteDiff.setBackground(new Background(new BackgroundFill(Color.rgb(190, 190, 190), CornerRadii.EMPTY, Insets.EMPTY)));
        weightAgentDiff.clear();
        weightAgentDiff.setBackground(new Background(new BackgroundFill(Color.rgb(190, 190, 190), CornerRadii.EMPTY, Insets.EMPTY)));
        clayDiff.clear();
        clayDiff.setBackground(new Background(new BackgroundFill(Color.rgb(190, 190, 190), CornerRadii.EMPTY, Insets.EMPTY)));
        limeDiff.clear();
        limeDiff.setBackground(new Background(new BackgroundFill(Color.rgb(190, 190, 190), CornerRadii.EMPTY, Insets.EMPTY)));
    }
    
    void listWellsTable() throws SQLException, FileNotFoundException, IOException {
        clearAll();
        ResultSet rs = chooseWellsFromDb(chooseBoxLocat.getValue(), chooseBoxWell.getValue());
        String a = new String();
        String[] repData = new String[9];
        table.clear();commentList.clear();
        while (rs.next()) {
            RowTableOBM azaz = new RowTableOBM(rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),
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
                    repData[1] = azaz.getBaseOil();
                    repData[2] = azaz.getCaCl2();
                    repData[3] = azaz.getEmilsifier();
                    repData[4] = azaz.getVersaTrol();
                    repData[5] = azaz.getBarite();
                    repData[6] = azaz.getWeigthingAgent();
                    repData[7] = azaz.getClay();
                    repData[8] = azaz.getLime();
                }
            }
        }
        int flag = 0;
        int flag2 = 0;
        RowTableOBM azaz;
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
        dateTab.setCellValueFactory(new PropertyValueFactory<RowTableOBM, String>("date"));
        dateTab.setCellFactory(new Callback<TableColumn<RowTableOBM, String>, TableCell<RowTableOBM, String>>() {
            @Override
            public TableCell<RowTableOBM, String> call(TableColumn<RowTableOBM, String> param) {
                return new TableCell<RowTableOBM, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
//                        CommentController commApp = new CommentController();
                        LocalDate[] localDate = new LocalDate[table.size()];
//                        int[] comments = new int[table.size()];
//                        for (int j=0;j<table.size(); j++){
//                            try {
//                                String s=commApp.chooseCommentOBM(chooseBoxLocat.getValue(), chooseBoxWell.getValue(),table.get(j).getDate());
//                                if ((s!=null))
//                                if (!s.isEmpty()) comments[j]=1;
//                            } catch (SQLException ex) {
//                                Logger.getLogger(OilBaseMudController.class.getName()).log(Level.SEVERE, null, ex);
//                            }
//                        }
                        int[] dateComp = new int[table.size()];
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
        mudLostTab.setCellValueFactory(new PropertyValueFactory<RowTableOBM, String>("mudLost"));
        mudLeftTab.setCellValueFactory(new PropertyValueFactory<RowTableOBM, String>("mudLeft"));
        mudMadeTab.setCellValueFactory(new PropertyValueFactory<RowTableOBM, String>("mudMade"));
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
        mudLostTab.setStyle("-fx-alignment: CENTER;");
        mudLeftTab.setStyle("-fx-alignment: CENTER;");
        mudMadeTab.setStyle("-fx-alignment: CENTER;");
        caCO3Tab.setStyle("-fx-alignment: CENTER;");
        baseOilTab.setStyle("-fx-alignment: CENTER;");
        caCl2Tab.setStyle("-fx-alignment: CENTER;");
        emulsifierTab.setStyle("-fx-alignment: CENTER;");
        versaTrolTab.setStyle("-fx-alignment: CENTER;");
        bariteTab.setStyle("-fx-alignment: CENTER;");
        weightAgentTab.setStyle("-fx-alignment: CENTER;");
        clayTab.setStyle("-fx-alignment: CENTER;");
        limeTab.setStyle("-fx-alignment: CENTER;");
        Table.itemsProperty().setValue(table);
        caCO3Calc.setText(String.format("%.1f", calc_conc(table, 1)));
        baseOilCalc.setText(String.format("%.1f", calc_conc(table, 2)));
        caCl2Calc.setText(String.format("%.1f", calc_conc(table, 3)));
        emulsifierCalc.setText(String.format("%.1f", calc_conc(table, 4)));
        versaTrolCalc.setText(String.format("%.1f", calc_conc(table, 5)));
        bariteCalc.setText(String.format("%.1f", calc_conc(table, 6)));
        weightAgentCalc.setText(String.format("%.1f", calc_conc(table, 7)));
        clayCalc.setText(String.format("%.1f", calc_conc(table, 8)));
        limeCalc.setText(String.format("%.1f", calc_conc(table, 9)));
        caCO3Report.setText(String.format("%.1f", Double.parseDouble(repData[0])));
        baseOilReport.setText(String.format("%.1f", Double.parseDouble(repData[1])));
        caCl2Report.setText(String.format("%.1f", Double.parseDouble(repData[2])));
        emulsifierReport.setText(String.format("%.1f", Double.parseDouble(repData[3])));
        versaTrolReport.setText(String.format("%.1f", Double.parseDouble(repData[4])));
        bariteReport.setText(String.format("%.1f", Double.parseDouble(repData[5])));
        weightAgentReport.setText(String.format("%.1f", Double.parseDouble(repData[6])));
        clayReport.setText(String.format("%.1f", Double.parseDouble(repData[7])));
        limeReport.setText(String.format("%.1f", Double.parseDouble(repData[8])));
        Double azik = ((Double.parseDouble(caCO3Calc.getText().replace(',', '.')) / Double.parseDouble(caCO3Report.getText().replace(',', '.'))) * 100 - 100);
        if ((!azik.isNaN()) && (!azik.isInfinite())) {
            caCO3Diff.setText(String.format("%.0f", azik));
            if (azik.compareTo(0.0) < 0) {
                caCO3Diff.setBackground(new Background(new BackgroundFill(Color.rgb(255, 0, 0), CornerRadii.EMPTY, Insets.EMPTY)));
            } else {
                caCO3Diff.setBackground(new Background(new BackgroundFill(Color.rgb(0, 210, 155), CornerRadii.EMPTY, Insets.EMPTY)));
            }
        }
        azik = ((Double.parseDouble(baseOilCalc.getText().replace(',', '.')) / Double.parseDouble(baseOilReport.getText().replace(',', '.'))) * 100 - 100);
        if ((!azik.isNaN()) && (!azik.isInfinite())) {
            baseOilDiff.setText(String.format("%.0f", azik));
            if (azik.compareTo(0.0) < 0) {
                baseOilDiff.setBackground(new Background(new BackgroundFill(Color.rgb(255, 0, 0), CornerRadii.EMPTY, Insets.EMPTY)));
            } else {
                baseOilDiff.setBackground(new Background(new BackgroundFill(Color.rgb(0, 210, 155), CornerRadii.EMPTY, Insets.EMPTY)));
            }
        }
        azik = ((Double.parseDouble(caCl2Calc.getText().replace(',', '.')) / Double.parseDouble(caCl2Report.getText().replace(',', '.'))) * 100 - 100);
        if ((!azik.isNaN()) && (!azik.isInfinite())) {
            caCl2Diff.setText(String.format("%.0f", azik));
            if (azik.compareTo(0.0) < 0) {
                caCl2Diff.setBackground(new Background(new BackgroundFill(Color.rgb(255, 0, 0), CornerRadii.EMPTY, Insets.EMPTY)));
            } else if (azik.compareTo(0.0) >= 0) {
                caCl2Diff.setBackground(new Background(new BackgroundFill(Color.rgb(0, 210, 155), CornerRadii.EMPTY, Insets.EMPTY)));
            }
        }
        azik = ((Double.parseDouble(emulsifierCalc.getText().replace(',', '.')) / Double.parseDouble(emulsifierReport.getText().replace(',', '.'))) * 100 - 100);
        if ((!azik.isNaN()) && (!azik.isInfinite())) {
            emulsifierDiff.setText(String.format("%.0f", azik));
            if (azik.compareTo(0.0) < 0) {
                emulsifierDiff.setBackground(new Background(new BackgroundFill(Color.rgb(255, 0, 0), CornerRadii.EMPTY, Insets.EMPTY)));
            } else if (azik.compareTo(0.0) >= 0) {
                emulsifierDiff.setBackground(new Background(new BackgroundFill(Color.rgb(0, 210, 155), CornerRadii.EMPTY, Insets.EMPTY)));
            }
        }
        azik = ((Double.parseDouble(versaTrolCalc.getText().replace(',', '.')) / Double.parseDouble(versaTrolReport.getText().replace(',', '.'))) * 100 - 100);
        if ((!azik.isNaN()) && (!azik.isInfinite())) {
            versaTrolDiff.setText(String.format("%.0f", azik));
            if (azik.compareTo(0.0) < 0) {
                versaTrolDiff.setBackground(new Background(new BackgroundFill(Color.rgb(255, 0, 0), CornerRadii.EMPTY, Insets.EMPTY)));
            } else if (azik.compareTo(0.0) >= 0) {
                versaTrolDiff.setBackground(new Background(new BackgroundFill(Color.rgb(0, 210, 155), CornerRadii.EMPTY, Insets.EMPTY)));
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
        azik = ((Double.parseDouble(weightAgentCalc.getText().replace(',', '.')) / Double.parseDouble(weightAgentReport.getText().replace(',', '.'))) * 100 - 100);
        if ((!azik.isNaN()) && (!azik.isInfinite())) {
            weightAgentDiff.setText(String.format("%.0f", azik));
            if (azik.compareTo(0.0) < 0) {
                weightAgentDiff.setBackground(new Background(new BackgroundFill(Color.rgb(255, 0, 0), CornerRadii.EMPTY, Insets.EMPTY)));
            } else if (azik.compareTo(0.0) >= 0) {
                weightAgentDiff.setBackground(new Background(new BackgroundFill(Color.rgb(0, 210, 155), CornerRadii.EMPTY, Insets.EMPTY)));
            }
        }
        azik = ((Double.parseDouble(clayCalc.getText().replace(',', '.')) / Double.parseDouble(clayReport.getText().replace(',', '.'))) * 100 - 100);
        if ((!azik.isNaN()) && (!azik.isInfinite())) {
            clayDiff.setText(String.format("%.0f", azik));
            if (azik.compareTo(0.0) < 0) {
                clayDiff.setBackground(new Background(new BackgroundFill(Color.rgb(255, 0, 0), CornerRadii.EMPTY, Insets.EMPTY)));
            } else if (azik.compareTo(0.0) >= 0) {
                clayDiff.setBackground(new Background(new BackgroundFill(Color.rgb(0, 210, 155), CornerRadii.EMPTY, Insets.EMPTY)));
            }
        }
        azik = ((Double.parseDouble(limeCalc.getText().replace(',', '.')) / Double.parseDouble(limeReport.getText().replace(',', '.'))) * 100 - 100);
        if ((!azik.isNaN()) && (!azik.isInfinite())) {
            limeDiff.setText(String.format("%.0f", azik));
            if (azik.compareTo(0.0) < 0) {
                limeDiff.setBackground(new Background(new BackgroundFill(Color.rgb(255, 0, 0), CornerRadii.EMPTY, Insets.EMPTY)));
            } else if (azik.compareTo(0.0) >= 0) {
                limeDiff.setBackground(new Background(new BackgroundFill(Color.rgb(0, 210, 155), CornerRadii.EMPTY, Insets.EMPTY)));
            }
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
    void changeMudType(ActionEvent event) {
        Stage stage = (Stage) addToDB.getScene().getWindow();
        stage.hide();
        Stage primaryStage = new Stage();
        Main main = new Main();
        main.start(primaryStage);
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
                evalContr.showEvalOBM(chooseBoxLocat.getValue()+"     1",chooseBoxWell.getValue());
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

}
