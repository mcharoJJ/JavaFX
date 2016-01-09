import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Shubho on 20-11-15.
 * PopUpWindow class
 * responsible for all PopUp msg error signlas
 * log in
 * this class's method has been frequently used throughout the application
 * implements Layout and InItButtons Interface (in order to full fill the criteria )
 *
 */



public class PopUpWindow implements Layout,InItButtons{

    static String title = "";
    static boolean usrPassFlag = true;
    static ArrayList<String> usrDB = new ArrayList<String>();
    static ArrayList<String> passDB = new ArrayList<String>();
    static ArrayList<String> usrID = new ArrayList<String>();
    private static String usr = null, pass = null;
    private static String msg;
    static Button login_Button = new Button("Log in");
    static Button ok_Button = new Button("ok");
    static PopUpWindow popUpWindow = new PopUpWindow();

    /**
     * static method
     * its the head of this class
     * it checks condition and and produces windows we need
     */
    public static void popUp() {
        popUpWindow.initButtons();

        if (Main.error_popup_flag)
            error_popUp(msg);

        if (Main.donor_login_flag) {
            title = "Donor login";
            popUpWindow.LayoutConfiguration();
            Main.cout("here in donor");
        }
        if (Main.bloodBank_login_flag) {
            usr = "admin";
            pass = "password";
            title = "Blood Bank Login";
            popUpWindow.LayoutConfiguration();
        }

    }


    //Error Pop up
    /**
     * error window
     * @param msg
     */
    public static void error_popUp(String msg) {
        popUpWindow.initButtons();
        //Window of error
        Stage alert_window = new Stage();
        alert_window.setResizable(false);
        final Stage alert_window2 = alert_window;
        alert_window2.setTitle("System Message");
        alert_window2.initModality(Modality.APPLICATION_MODAL);
        alert_window2.centerOnScreen();
        //close

        //Lable and msg
        Label error_Msg_label = new Label(msg);
        error_Msg_label.setTextAlignment(TextAlignment.CENTER);
        error_Msg_label.autosize();
        //close


        //Onclick Listener
        ok_Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.error_popup_flag = false;
                alert_window2.close();
            }
        });
        //close

        // Layout
        VBox error_PopUp_Layout = new VBox(15);
        error_PopUp_Layout.setAlignment(Pos.CENTER);
        error_PopUp_Layout.setAlignment(Pos.TOP_CENTER);
        error_PopUp_Layout.getChildren().addAll(error_Msg_label, ok_Button);
        ///close

        //Scene/Frame
        Scene donor_Login_Scene = new Scene(error_PopUp_Layout, 300, 90);
        //close

        //setting Scene to window and showing
        alert_window2.setScene(donor_Login_Scene);
        alert_window2.show();
        //close
    }
    //close of error_popUP

    /**
     * initButtons, initialization of buttons and this fucntion is from Interface
     */
    public void initButtons(){
        login_Button.setStyle("-fx-base: #008000");
        login_Button.setEffect(ButtonEffect.reflection);

        //Button
        ok_Button.setStyle("-fx-base: #008000;");
        ok_Button.setEffect(ButtonEffect.reflection);
        //close


    }


    /**
     * Configuers Layout and shows window
     */
    public void LayoutConfiguration() {
        Stage alert_window = new Stage();
        alert_window.setResizable(false);
        final Stage alert_window2 = alert_window;
        alert_window.setTitle(title);
        alert_window.initModality(Modality.APPLICATION_MODAL);

        alert_window.setMinHeight(180);
        alert_window.setMinWidth(250);
        //alert_window.setMaxHeight(180);
        //alert_window.setMaxWidth(250);
        alert_window.initStyle(StageStyle.UTILITY);
        alert_window.setResizable(true);


        Label username_label = new Label("User Name");
        username_label.setWrapText(true);
        Label password_label = new Label("Password");
        // password_label.setWrapText(true);


        final TextField username_text_field = new TextField();
        username_text_field.setPromptText("Username");
        //username_text_field.setPrefWidth(20);


        final PasswordField password_text_field = new PasswordField();
        password_text_field.setPromptText("Password");

        login_Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Login Clicked");
                ConnectorModified c = new ConnectorModified();

                boolean flag = false;
                if (Main.bloodBank_login_flag) {
                    if (username_text_field.getText().toString().trim().toLowerCase().equals(usr)
                            && password_text_field.getText().toString().equals(pass)) {

                        Main.publicObject.cout("Logged In");
                        Main.donor_login_flag = false;
                        Main.bloodBank_login_flag = true;
                        BloodBankAdminPanel bloodBankAdminPanel = new BloodBankAdminPanel();
                        bloodBankAdminPanel.BankControlPanel();
                        Main.mainWindow.close();
                        alert_window2.close();
                    } else {
                        Main.donor_login_flag = false;
                        Main.bloodBank_login_flag = true;
                        msg = "Incorrect Username or Password";
                        error_popUp(msg);


                    }
                } else {

                    ResultSet rs = c.getTableData();

                    try {
                        if (usrPassFlag) {
                            usrPassFlag = false;
                            int i = 0;
                            while (rs.next()) {
                                if (Main.donor_login_flag) {
                                    usr = rs.getString("DUSER");
                                    pass = rs.getString("DPASS");
                                    usrID.add(rs.getString("DID"));
                                    usrDB.add(usr);
                                    passDB.add(pass);

                                }
                            }
                        }
                        for (int i = 0; i < usrDB.size(); i++) {

                            if (username_text_field.getText().toString().trim().toLowerCase().equals(usrDB.get(i))
                                    && password_text_field.getText().toString().equals(passDB.get(i))) {
                                DonorPanel.userName = usrDB.get(i);
                                Main.globalIndex = i;
                                DonorPanel.id = usrID.get(i);
                                flag = true;
                                break;
                            } else
                                flag = false;
                        }
                        if (flag) {

                            Main.publicObject.cout("Logged In");
                            Main.donor_login_flag = false;
                            Main.bloodBank_login_flag = false;
                            Main.donor_login_flag = true;
                            DonorPanel donorPanel = new DonorPanel();
                            donorPanel.donorControlPanel();
                            Main.mainWindow.close();
                            alert_window2.close();

                        } else {
                            Main.error_popup_flag = true;
                            Main.donor_login_flag = true;
                            Main.bloodBank_login_flag = false;
                            msg = "Incorrect Username or Password";
                            error_popUp(msg);
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        VBox login_Layout = new VBox(10);
        login_Layout.setAlignment(Pos.TOP_LEFT);
        login_Layout.getChildren().addAll(username_label, username_text_field, password_label, password_text_field, login_Button);
        login_Layout.setMargin(username_label, new Insets(0, 0, 0, 5));
        login_Layout.setMargin(username_text_field, new Insets(0, 5, 0, 5));
        login_Layout.setMargin(password_label, new Insets(0, 0, 0, 5));
        login_Layout.setMargin(password_text_field, new Insets(0, 5, 0, 5));
        login_Layout.setMargin(login_Button, new Insets(0, 0, 0, alert_window2.getMinWidth() - 20));

        BorderPane root = new BorderPane();

        Scene donor_Login_Scene = new Scene(login_Layout, 300, 180);
        alert_window.setScene(donor_Login_Scene);
        alert_window.show();

    }
}
//close of Login Layout config
