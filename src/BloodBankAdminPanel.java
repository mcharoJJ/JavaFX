import db.table.DbTable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Admin Panel
 * has the all sort of access to the database to manipulate
 */

public class BloodBankAdminPanel {
    String lastDateFromDB = "";
    Stage adminPanel = new Stage();
    Text idText = new Text("ID");
    Text nameText = new Text("Name");
    Text bGroupText = new Text("Blood Group");
    Text contactText = new Text("Contact");
    Text lastGivenDateText = new Text("Blood Given Date");
    Text readyText = new Text("Active");
    Text locationText = new Text("Location");
    Text hospitalText = new Text("Hospital");
    Text usernameText = new Text("User Name");
    Text passwordText = new Text("Password");
    DonorPanel donorPanel = new DonorPanel();


    ChoiceBox<String> person = new ChoiceBox<String>();

    TextField id = new TextField();
    TextField name = new TextField();

    //Blood Group From Main class

    TextField contact = new TextField();
    //TextField location = new TextField("Location");

    TextField user = new TextField();
    TextField password = new TextField();
    DatePicker lastDate = new DatePicker();

    Button add = new Button("Add");
    Button edit = new Button("Edit");
    Button delete = new Button("Delete");
    Button getDetails = new Button("Get Details");

    //intializang buttons
    public void initButtons() {
        ConnectorModified c = new ConnectorModified();

        getDetails.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getValues(id.getText(), person.getValue().toUpperCase());
            }
        });

        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String sql = "";
                if (person.getValue().toLowerCase().equals("donor")) {
                    sql = "INSERT INTO donor VALUES( '" +
                            id.getText() + "', '" + name.getText() + "' ,'" + Main.bloodGroupList.getValue() + "', '" +
                            contact.getText() + "', '" + lastDate.getValue() + "', '" + Main.readyList.getValue() +
                            "', '" + Main.locationList.getValue() + "', '" + user.getText() + "', '" + password.getText() + "');";
                    c.theQuery(sql);

                } else if (person.getValue().toLowerCase().equals("patient")) {
                    sql = "INSERT INTO patient VALUES('" +
                            id.getText() + "', '" + name.getText() + "', '" + Main.bloodGroupList.getValue() + "', '" +
                            contact.getText() + "' );";
                    c.theQuery(sql);
                } else {
                    PopUpWindow.error_popUp("Something Went Wrong");
                }

            }
        });

        edit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String queryUpdate = "";
                if (person.getValue().toString().toLowerCase().equals("donor")) {

                    queryUpdate = "UPDATE donor SET DNAME = '" + name.getText().toString().trim() + "', DCONTACT = '" + contact.getText().toString().trim() + "'," +
                            " DLOCATION = '" + Main.locationList.getValue().toString() + "', DREADY = '" + Main.readyList.getValue().toString() + "', DPASS = '" +
                            password.getText() + "', DBGROUP = '" + Main.bloodGroupList.getValue() + "', DLASTDATE = '" + lastDateFromDB +
                            "', DUSER = '" + user.getText().toString().toLowerCase() + "' WHERE DID = '" + id.getText() + "'";
                    c.theQuery(queryUpdate);

                } else if (person.getValue().toString().toLowerCase().equals("patient")) {

                    queryUpdate = "UPDATE patient SET PNAME = '" + name.getText().toString().trim() + "', PCONTACT = '" +
                            contact.getText().toString().trim() + "', PBGROUP = '" + Main.bloodGroupList.getValue() + "' WHERE PID = '" + id.getText() + "'";

                    c.theQuery(queryUpdate);
                } else {
                    PopUpWindow.error_popUp("Something Went Wrong");
                }

            }
        });

        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String deleteSql = "DELETE FROM " + person.getValue() + " WHERE " + person.getValue().charAt(0) + "ID = '" + id.getText() + "';";
                c.theQuery(deleteSql);
            }
        });


    }

    /**
     *
     * @param getId
     * @param person
     */
    public void getValues(String getId, String person) {
        Main.cout("SELECT * FROM " + person + " WHERE " + person.charAt(0) + "ID = '" + id.getText() + "';");
        ConnectorModified c = new ConnectorModified();
        ResultSet rs = c.getSingleRow("SELECT * FROM " + person + " WHERE " + person.charAt(0) + "ID = '" + id.getText() + "';");
        try {
            while (rs.next()) {
                if (person.charAt(0) == 'D') {
                    name.setText(rs.getString("DNAME"));
                    Main.bloodGroupList.setValue(rs.getString("DBGROUP"));
                    contact.setText(rs.getString("DCONTACT"));
                    lastDateFromDB = rs.getString("DLASTDATE");
                    Main.readyList.setValue(rs.getString("DREADY"));
                    Main.locationList.setValue(rs.getString("DLOCATION"));
                    user.setText(rs.getString("DUSER"));
                    password.setText(rs.getString("DPASS"));
                } else if (person.charAt(0) == 'P') {
                    name.setText(rs.getString("PNAME"));
                    Main.bloodGroupList.setValue(rs.getString("PBGROUP"));
                    contact.setText(rs.getString("PCONTACT"));
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public Scene getSceneBuild() {
        Button backToMainWindow = new Button("Open Home");
        backToMainWindow.setWrapText(true);
        backToMainWindow.setStyle("-fx-font: 14 arial; -fx-base: #008000;");
        backToMainWindow.setEffect(ButtonEffect.reflection);

        person.getItems().addAll("Donor", "Patient");
        person.setValue("Donor");


        backToMainWindow.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                backToMainWindow.setCursor(Cursor.DEFAULT);
                Main.patient_login_flag = Main.donor_login_flag = Main.bloodBank_login_flag = false;
                //  adminPanel.close();
                Main.mainWindow.show();
            }
        });

        Button showTable = new Button("Show Table");
        showTable.setWrapText(true);
        showTable.setStyle("-fx-font: 15 arial; -fx-base: #FF0000;");
        showTable.setEffect(ButtonEffect.reflection);

        showTable.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (person.getValue().toLowerCase().equals("donor"))
                    DbTable.donorFlag = true;
                else
                    DbTable.donorFlag = false;

                DbTable.main("hello");

            }
        });


        Button logOut = new Button("Log out");
        logOut.setWrapText(true);
        logOut.setStyle("-fx-font: 15 arial; -fx-base: #FFFF00;");
        logOut.setEffect(ButtonEffect.reflection);

        logOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Main.patient_login_flag = Main.donor_login_flag = Main.bloodBank_login_flag = false;
                adminPanel.close();
                Main.mainWindow.show();

            }
        });


        HBox homeAndBack = new HBox(10);
        homeAndBack.getChildren().addAll(backToMainWindow, showTable, logOut);
        homeAndBack.setAlignment(Pos.TOP_RIGHT);

        ConnectorModified c = new ConnectorModified();

        HBox hBox = new HBox(10);
        hBox.setPadding(new Insets(0, 10, 0, 10));
        hBox.setAlignment(Pos.TOP_LEFT);
        hBox.getChildren().addAll(getAdminPanelLayout());
        hBox.setAlignment(Pos.TOP_LEFT);

        VBox vBox = new VBox(0);
        vBox.setPadding(new Insets(10, 10, 0, 10));
        vBox.getChildren().addAll(homeAndBack, hBox);


        BorderPane root = new BorderPane(vBox);
        root.setCenter(vBox);

        Scene controlPanel = new Scene(root, 200, 400);
        return controlPanel;
    }

    /**
     *
     * @return vbox object to be set on scene/frame
     */
    public VBox getAdminPanelLayout() {

        person.setStyle("-fx-font: 13 arial; -fx-base: #00bfff;");
        person.setEffect(ButtonEffect.reflection);
        person.setCursor(Cursor.CLOSED_HAND);

        delete.setStyle("-fx-font: 18 arial; -fx-base: #ff0000;");
        delete.setEffect(ButtonEffect.reflection);

        edit.setStyle("-fx-font: 18 arial; -fx-base: #ffff00;");
        edit.setEffect(ButtonEffect.reflection);

        add.setStyle("-fx-font: 18 arial; -fx-base: #00ff00;");
        add.setEffect(ButtonEffect.reflection);

        getDetails.setStyle("-fx-font: 14 arial; -fx-base: #708090;");
        getDetails.setEffect(ButtonEffect.reflection);

        initButtons();
        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(delete, edit, add);

        HBox idAndGet = new HBox(10);
        idAndGet.getChildren().addAll(id, getDetails);

        VBox pLayout = new VBox(10);
        pLayout.setPadding(new Insets(10, 10, 10, 10));
        pLayout.getChildren().addAll(person, idText, idAndGet, nameText, name, bGroupText, Main.bloodGroupList,
                contactText, contact, lastGivenDateText, lastDate, readyText, Main.readyList, locationText,
                Main.locationList, usernameText, user, passwordText, password, hbox);

        pLayout.setMargin(person, new Insets(10, 0, 0, 0));


        return pLayout;
    }

    /**
     * panel Window
     */

    public void BankControlPanel() {

        adminPanel.setTitle("Admin Control Panel");
        adminPanel.setResizable(true);
        adminPanel.centerOnScreen();
        adminPanel.setMinWidth(640);
        adminPanel.setMinHeight(480);

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        adminPanel.setX(bounds.getMinX());
        adminPanel.setY(bounds.getMinY());
        adminPanel.setWidth(bounds.getWidth());
        adminPanel.setHeight(bounds.getHeight());

        adminPanel.setScene(getSceneBuild());
        adminPanel.show();
    }


}
//close of donor
