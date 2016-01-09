import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Shubho on 21-11-15.
 * Class Query Provides Query interface
 *
 */
public class Query {

    //member variables
    final static Stage queryWindow = new Stage();
    
    public static Button backButtonToMainWindow = null, searchButton = null;
    static String lableMsg = "", sql = "";

//Start of queryWindow

    /**
     * Query Window
     * opens a Query Window
     */
    public void queryWindow() {

        queryWindow.getIcons().add(new Image(Query.class.getResourceAsStream("/iconjava.png")));//Icon 
        queryWindow.centerOnScreen();
        queryWindow.setResizable(false);
        queryWindow.setTitle("Query");
        queryWindow.setMinWidth(640);
        queryWindow.setMinHeight(480);
        queryWindow.setMaxWidth(640);
        queryWindow.setMaxHeight(480);


        Scene queryScene = new Scene(getQueryLayout(), 640, 480);

        queryWindow.setScene(queryScene);
        queryWindow.show();
    }
    //end of queryWindow

    
    //start of getQueryLayout() returns Layout as a BorderPane object 

    /**
     * builds the layout
     * @return BorderPane Object
     */
    public static BorderPane getQueryLayout() {
        //initialize back button
        initBackButtonToMainWindow();

        Text queryInstructionText = new Text("Select from these Lists below:");

        //Button
        searchButton = new Button("Search");
        searchButton.setWrapText(true);
        searchButton.setStyle("-fx-font: 15 arial; -fx-base: #FF0000;");
        buttonEffect();
        //Button  close

        VBox bannerImageLayout = new VBox(15);
        bannerImageLayout.getChildren().add(Main.image_banner_query);
        Main.image_banner_query.fitWidthProperty().bind(queryWindow.widthProperty());

        bannerImageLayout.setAlignment(Pos.TOP_LEFT);
        bannerImageLayout.getChildren().addAll(backButtonToMainWindow, queryInstructionText);
        bannerImageLayout.setMargin(queryInstructionText, new Insets(0, 0, 0, 20));
        bannerImageLayout.setMargin(backButtonToMainWindow, new Insets(0, 0, 0, 20));


        HBox choiceBoxLayout = new HBox(80);
        choiceBoxLayout.setAlignment(Pos.CENTER);
        choiceBoxLayout.getChildren().addAll(Main.personList, Main.bloodGroupList, Main.locationList);

        VBox searchButtonLayout = new VBox(50);
        searchButtonLayout.setAlignment(Pos.TOP_RIGHT);
        searchButtonLayout.setMargin(searchButton, new Insets(20, 20, 0, 0));
        searchButtonLayout.getChildren().add(searchButton);

        Text lableText = new Text("Query Result:");
        lableText.setTextAlignment(TextAlignment.LEFT);


        Label resultLable = new Label(lableMsg);
        resultLable.setPrefSize(640, 250);
        resultLable.setStyle("-fx-border-color: gray;");
        resultLable.setCursor(Cursor.TEXT);
        resultLable.setAlignment(Pos.TOP_LEFT);


        VBox queryLayoutConfigure = new VBox(10);

        queryLayoutConfigure.getChildren().addAll(bannerImageLayout, choiceBoxLayout,
                searchButtonLayout,/* sql_text, sql_lable,*/ lableText, resultLable);
        queryLayoutConfigure.setMargin(lableText, new Insets(0, 0, 0, 20));
        queryLayoutConfigure.setMargin(resultLable, new Insets(0, 20, 20, 20));

        final Label sqlResultFinal = resultLable;


        //Button setOnAction
        backButtonToMainWindow.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                backButtonToMainWindow.setCursor(Cursor.DEFAULT);
                Main.patient_login_flag = Main.donor_login_flag = Main.bloodBank_login_flag = false;
                queryWindow.close();
                Main.mainWindow.show();
            }
        });
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                searchButton.setCursor(Cursor.DEFAULT);
                Main.cout("Search Button Clicked");
                ConnectorModified c = new ConnectorModified();
                sql = getSqlQurey();
                ResultSet rs = c.getTableData();
                TableView table = new TableView();

                if (Main.patient_login_flag || Main.donor_login_flag) {
                    String result = "ID\tName\t\tBlood Group\t\tContact\t\t\t\tActive\t\t\tLocation\n";
                    try {
                        while (rs.next()) {
                            String col1 = rs.getString("DID");
                            String col2 = rs.getString("DNAME");
                            String col3 = rs.getString("DBGROUP");
                            String col4 = rs.getString("DCONTACT");
                            String col5 = rs.getString("DLASTDATE");
                            String col6 = rs.getString("DREADY");
                            String col7 = rs.getString("DLOCATION");
                            String col8 = rs.getString("DUSER");
                            String col9 = rs.getString("DPASS");
                            if (col3.toLowerCase().equals(Main.bloodGroupList.getValue().toString().toLowerCase())
                                    && (col7.toLowerCase().equals(Main.locationList.getValue().toString().toLowerCase()) ||
                                    Main.locationList.getValue().toString().toLowerCase().equals("all"))) {

                                result += col1 + "\t" + col2 + "\t\t\t" + col3 + "\t\t\t" + col4 + "\t\t\t" + col6 + "\t\t" + "\t\t" + col7 + "\n";
                                resultLable.setText(result);
                            }

                        }
                        rs.close();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }

            }
        });
        //set on action close

        BorderPane root = new BorderPane();
        root.setCenter(queryLayoutConfigure);

        return root;
    }

    /**
     * initBackButtonToMainWindow()
     * static method
     *
     */
    public static void initBackButtonToMainWindow() {
        backButtonToMainWindow = new Button("Back to Home");
        backButtonToMainWindow.setWrapText(true);
        backButtonToMainWindow.setStyle("-fx-font: 14 arial; -fx-base: #008000;");
        backButtonToMainWindow.setAlignment(Pos.TOP_RIGHT);
    }

    /**
     *
     * @return String containing sql for mysql databes
     */
    public static String getSqlQurey() {
        String sql;
        if (Main.personList.getValue().equals("Choose A Person")) {
            PopUpWindow.error_popUp("Select A Person");
            return "Select A Person";

        }
        if (Main.bloodGroupList.getValue().equals("Choose A Blood Group")) {
            PopUpWindow.error_popUp("Select A Blood Group");
            return "Select A Blood Group";
        }
        if (Main.locationList.getValue().equals(Main.location_array[0])) {
            PopUpWindow.error_popUp("Select A location");
            return "Select A Location";
        }

        if (Main.patient_login_flag) {
            sql = Main.personList.getValue().toUpperCase();
            sql += " WHERE DBGROUP = '" + Main.bloodGroupList.getValue().toUpperCase() + "'";
            sql += " AND DLOCATION = '" + Main.locationList.getValue().toUpperCase() + "';";
            return "SELECT DID, DNAME, DBGROUP, DCONTACT FROM " + sql;
        } else if (Main.donor_login_flag) {

            sql = Main.personList.getValue().toUpperCase();
            sql += " WHERE PBGROUP = '" + Main.bloodGroupList.getValue().toUpperCase() + "'";
            sql += " AND PLOCATION = '" + Main.locationList.getValue().toUpperCase() + "';";
            return "SELECT PID, PNAME, PBGROUP, PCONTACT FROM " + sql;

        } else if (Main.bloodBank_login_flag) {
            return "working on it";
        }
        return null;
    }
//close of generating Sql Query


    //effects and handler
    private static void buttonEffect() {
        searchButton.setEffect(ButtonEffect.shadow);
        backButtonToMainWindow.setEffect(ButtonEffect.shadow);

        backButtonToMainWindow.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        backButtonToMainWindow.setEffect(ButtonEffect.reflection);

                    }
                });

        backButtonToMainWindow.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        backButtonToMainWindow.setEffect(ButtonEffect.shadow);

                    }
                });


        //search button
        searchButton.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        searchButton.setEffect(ButtonEffect.reflection);

                    }
                });

        searchButton.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        searchButton.setEffect(ButtonEffect.shadow);

                    }
                });
    }
    //close of effects and handler


}
