import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class DonorPanel
 * provides registered donors an interface to
 * update their infos*
 *
 */
public class DonorPanel {

    public static String id = "";
    public static String userName = "";
    public static String userPass = "";
    //public TextField location = new TextField();
    //location from main
    public static boolean donorStringAdd = true;
    public TextField contact = new TextField();
    public TextField oldPasswordField = new PasswordField();
    public PasswordField passwordField1 = new PasswordField();
    public PasswordField passwordField2 = new PasswordField();
    public DatePicker lastDate = new DatePicker();
    public Button submit = new Button("Submit");
    Label donorInfo = new Label("Donor Info");
    ChoiceBox<String> userLocation = new ChoiceBox<>();

    //control panel
    public void donorControlPanel() {
        Stage donorPanel = new Stage();
        donorPanel.setTitle("Donor Control Panel");
        donorPanel.setResizable(true);
        donorPanel.centerOnScreen();
        donorPanel.setMinWidth(640);
        donorPanel.setMinHeight(480);


        donorPanel.setScene(getSceneBuild());
        Query.backButtonToMainWindow.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Main.mainWindow.show();
                donorPanel.close();
            }
        });

        donorPanel.show();

    }

    public void initComponents() {
        if (donorStringAdd) {
            Main.personList.getItems().add("Donor");
            donorStringAdd = false;
        }
        Main.personList.setValue("Donor");

        donorInfo.setWrapText(true);
        donorInfo.setTextFill(Color.web("#009987"));
        donorInfo.setStyle("-fx-font: 13 arial;");
        donorInfo.setEffect(ButtonEffect.reflection);

        userLocation.getItems().addAll(Main.location_array);
        userLocation.setStyle("-fx-font: 13 arial; -fx-base: #00bfff;");

        lastDate.setPromptText("MM/DD/YYYY");

        submit.setWrapText(true);
        submit.setStyle("-fx-font: 16 arial; -fx-base: #008888;");

    }

    /**
     *sets previous value of a donor
     */
    public void setDefaultValue() {

        String sql = "SELECT * from donor WHERE DID = '" + id + "';";
        ConnectorModified c = new ConnectorModified();

        ResultSet rs = c.getSingleRow(sql);
        try {
            while (rs.next()) {
                contact.setText(rs.getString("DCONTACT"));
                Main.locationList.setValue(rs.getString("DLOCATION"));
                userLocation.setValue(rs.getString("DLOCATION"));
                Main.readyList.setValue(rs.getString("DREADY"));
                Main.bloodGroupList.setValue(rs.getString("DBGROUP"));
                donorInfo.setText("Donor Profile:\nName:- " + rs.getString("DNAME"));
                userPass = rs.getString("DPASS");
                Main.cout(userPass);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    /**
     * to the method that handling the Stage
     * @return returns Scene object
     */
    public Scene getSceneBuild() {
        initComponents();
        Label contactLable = new Label("Contact");
        Label locationLable = new Label("Location");
        Label lastGivenDate = new Label("Last Given Date");
        Label active = new Label("Active");
        Label changePasswordLable = new Label("Change Password");
        Label oldPasswordLable = new Label("Old Password");
        Label newPasswordLable = new Label("New Password");
        Label repearNewPasswordLable = new Label("Type New Password Again");
        lastGivenDate.setTooltip(new Tooltip("MM/DD/YYYY"));


        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ConnectorModified c = new ConnectorModified();
                if (checkInputs()) {
                    String queryUpdate = "UPDATE donor SET DCONTACT = '" + contact.getText().trim() + "'," +
                            " DLOCATION = '" + userLocation.getValue() + "', DREADY = '" + Main.readyList.getValue() + "', DPASS = '" +
                            userPass + "' WHERE DID = '" + id + "'";
                    c.theQuery(queryUpdate);
                }
            }
        });


        //value to the filed
        setDefaultValue();
        //close

        VBox donorScene = new VBox(10);
        donorScene.setPadding(new Insets(10, 10, 0, 10));
        donorScene.setAlignment(Pos.TOP_LEFT);
        donorScene.getChildren().addAll(
                donorInfo, locationLable, userLocation, contactLable,
                contact, active, Main.readyList, changePasswordLable,
                oldPasswordLable, oldPasswordField, newPasswordLable,
                passwordField1, repearNewPasswordLable, passwordField2, submit);
        //close of Adding Components

        donorScene.setMargin(donorInfo, new Insets(0, 0, 10, 0));
        donorScene.setMargin(contact, new Insets(0, 0, 0, 0));
        donorScene.setMargin(userLocation, new Insets(0, 0, 0, 0));
        // donorScene.setMargin(lastDate, new Insets(0,0,0,0));
        donorScene.setMargin(changePasswordLable, new Insets(15, 0, 0, 0));
        donorScene.setMargin(oldPasswordField, new Insets(0, 0, 0, 0));
        donorScene.setMargin(passwordField1, new Insets(0, 0, 0, 0));
        donorScene.setMargin(passwordField2, new Insets(0, 0, 0, 0));
        donorScene.setMargin(submit, new Insets(10, 0, 0, 70));


        TableViewSample tableViewSample = new TableViewSample();
        VBox table = tableViewSample.getStart();
        table.setAlignment(Pos.TOP_RIGHT);
        HBox hBox = new HBox(10);
        hBox.setPadding(new Insets(10, 10, 0, 10));
        hBox.setAlignment(Pos.TOP_LEFT);
        hBox.getChildren().addAll(donorScene, Query.getQueryLayout());

        BorderPane root = new BorderPane();
        root.setCenter(hBox);

        Scene controlPanel = new Scene(root, 940, 580);
        return controlPanel;
    }

    //checks inputs and pops error if any
    public boolean checkInputs() {

        if (userLocation.getValue().toLowerCase().equals("all") ||
                userLocation.getValue().toLowerCase().equals("choose a location")) {
            PopUpWindow.error_popUp("Your location must need to be specific");
            return false;
        } else if (contact.getText().toLowerCase().trim().equals("")) {
            PopUpWindow.error_popUp("You can not keep your contact details empty");
            return false;
        } else if (oldPasswordField.getText().trim().equals("")) {
            return true;
        } else if (oldPasswordField.getText().equals(userPass)) {
            if (passwordField1.getText().equals(passwordField2.getText())) {
                userPass = passwordField1.getText();
                PopUpWindow.passDB.set(Main.globalIndex, userPass);
                return true;
            } else {

                PopUpWindow.error_popUp("New Password doesn't match");
                return false;
            }

        } else if (!oldPasswordField.getText().equals(userPass)) {

            PopUpWindow.error_popUp("Wrong old Password");
            return false;

        } else
            return true;
    }
    //close of check

}
//close of donor
