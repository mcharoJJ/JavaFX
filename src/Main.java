import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * @author Shubho, Seemanta Ahmed, Mahbub Islam, Hasnat, Md, Waliul
 * @version 1.0
 * 
 * This is the main Class extends Application Class

 */

public class Main extends Application {
    //Member Varivables and Methods
    public static Main publicObject = new Main();
    public static int globalIndex = 0;
    //boolean flags
    public static boolean patient_login_flag = false, donor_login_flag = false, bloodBank_login_flag = true, error_popup_flag = false;
    //close

    //ImageView
    public static ImageView image_banner_query;
    //close
    public static Stage mainWindow;
    //close Strings
    //choice box
    public static ChoiceBox<String> personList, bloodGroupList, locationList, readyList;
    //close Buttons

    //close
    //Image Button/view
    static ImageView mainWindow_image_1 = null;
    //Strings
    public String mainWindowTitle = "Save A Life, Give Blood. Blood Management System.";
    //Buttons
    public Button patient_Button, donor_Button, blood_Bank_Button;
    ImageView patientImage = new ImageView(new Image(getClass().getResourceAsStream("/patient.png")));


    //close
    ImageView donorImage = new ImageView(new Image(getClass().getResourceAsStream("/donor.png")));
    //close map
    ImageView bankImage = new ImageView(new Image(getClass().getResourceAsStream("/bank.png")));

    
    /**
     *This is the Main  function 
     * @param args used
     * 
     */
    
    public static void main(String[] args) {
        launch(args);
    }


    /**
     * @method configureChoicebox()
     * @param not used
     * static method 
     * @purpose to configure the choice boxes  
     */
    private static void configureChoicebox() {

        personList = new ChoiceBox<>();
        bloodGroupList = new ChoiceBox<>();
        locationList = new ChoiceBox<>();
        readyList = new ChoiceBox<>();

        personList.setStyle("-fx-font: 13 arial; -fx-base: #00bfff;");
        bloodGroupList.setStyle("-fx-font: 13 arial; -fx-base: #00bfff;");
        locationList.setStyle("-fx-font: 13 arial; -fx-base: #00bfff;");
        readyList.setStyle("-fx-font: 13 arial; -fx-base: #00bfff;");

        personList.setEffect(ButtonEffect.reflection);
        personList.setCursor(Cursor.CLOSED_HAND);

        bloodGroupList.setEffect(ButtonEffect.reflection);
        bloodGroupList.setCursor(Cursor.CLOSED_HAND);

        locationList.setEffect(ButtonEffect.reflection);
        locationList.setCursor(Cursor.CLOSED_HAND);


        readyList.setEffect(ButtonEffect.reflection);
        readyList.setCursor(Cursor.CLOSED_HAND);


        bloodGroupList.getItems().addAll("Choose A Blood Group", "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");
        bloodGroupList.setValue("Choose A Blood Group");
        bloodGroupList.setTooltip(new Tooltip("What is The Blood Group you are looking for?"));


        //for(int i=0; i<location_array.length;i++)
        locationList.getItems().addAll(location_array);
        locationList.setValue(location_array[0]);
        locationList.setTooltip(new Tooltip("Need a Specific area? if no, Select All"));


        readyList.getItems().addAll("YES", "NO");
        readyList.setValue("NO");
        readyList.setTooltip(new Tooltip("Ready to Donate?"));


    }

    /**
     * methid cout
     * prints in console
     */
    
    public static void cout(Object obj) {
        System.out.println(obj);
    }


    /**
     * @purpose Starts the Window
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        //main window config
        mainWindow = stage;

        mainWindow.getIcons().add(new Image(
                Main.class.getResourceAsStream("/iconjava.png")
        ));

        mainWindow.setTitle(mainWindowTitle);
        mainWindow.setResizable(false);
        mainWindow.centerOnScreen();
        mainWindow.setMinWidth(640);
        mainWindow.setMinHeight(480);
        mainWindow.setMaxWidth(640);
        mainWindow.setMaxHeight(480);
        //close

        configure_Buttons(); //Initializing Buttons
        configureChoicebox();

        //Image for the mainWindow up Banner and for the Query Page Banner
        mainWindow_image_1 = new ImageView(new Image(getClass().getResourceAsStream("/slideshow/3.png")));
        mainWindow_image_1.setFitWidth(640);
        mainWindow_image_1.setFitHeight(300);
        mainWindow_image_1.setSmooth(true);
        mainWindow_image_1.setEffect(ButtonEffect.reflection);
        //close
        //query Page
        image_banner_query = new ImageView(new Image(getClass().getResourceAsStream("/query_banner.jpg")));
        image_banner_query.fitWidthProperty().bind(mainWindow.widthProperty());
        image_banner_query.setSmooth(true);
        image_banner_query.setFitHeight(50);

        //close


        //main Window Layout configuration
        VBox image_Vertical_Layout = new VBox(20);
        image_Vertical_Layout.getChildren().add(mainWindow_image_1);
        image_Vertical_Layout.setAlignment(Pos.TOP_CENTER);
        HBox button_Horizontal_Layout = new HBox(20);
        button_Horizontal_Layout.setAlignment(Pos.TOP_CENTER);
        button_Horizontal_Layout.getChildren().add(patientImage);
        button_Horizontal_Layout.getChildren().add(donorImage);
        button_Horizontal_Layout.getChildren().add(bankImage);

        image_Vertical_Layout.getChildren().add(button_Horizontal_Layout);
        //close

        Scene mainWindow_scene = new Scene(image_Vertical_Layout, 640, 480);
        mainWindow.setScene(mainWindow_scene);
        mainWindow.show();

    }


    /**
     *
     * configures buttons
     */
    public void configure_Buttons() {
        patient_Button = new Button("Patient");
        donor_Button = new Button("Donor Login");
        blood_Bank_Button = new Button("Blood Bank Login");

        patient_Button.setStyle("-fx-font: 18 arial; -fx-base: #6960EC;");
        donor_Button.setStyle("-fx-font: 18 arial; -fx-base: #6960EC;");
        blood_Bank_Button.setStyle("-fx-font: 18 arial; -fx-base: #6960EC;");

        patient_Button.setEffect(ButtonEffect.reflection);
        donor_Button.setEffect(ButtonEffect.reflection);
        blood_Bank_Button.setEffect(ButtonEffect.reflection);

        patientImage.setEffect(ButtonEffect.shadow);
        donorImage.setEffect(ButtonEffect.shadow);
        bankImage.setEffect(ButtonEffect.shadow);

        patientImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                cout("Patient Button Clicked");
                patient_login_flag = true;
                donor_login_flag = false;
                bloodBank_login_flag = false;
                configureChoicebox();
                personList.getItems().add("Donor");
                personList.setValue("Donor");
                personList.setTooltip(new Tooltip("You can only Search for Donor"));

                Query query = new Query();
                query.queryWindow();

                mainWindow.close();

            }
        });


        donorImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                cout("Donor Login Clicked");
                donor_login_flag = true;
                bloodBank_login_flag = false;
                patient_login_flag = false;


                /* configureChoicebox();
                personList.getItems().addAll("Choose A Person","Donor","Patient");
                personList.setValue("Choose A Person");
                personList.setTooltip(new Tooltip("Select a Person to search"));
                */
                PopUpWindow.popUp();
            }
        });


        bankImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                cout("Blood Bank Button Clicked");
                bloodBank_login_flag = true;
                donor_login_flag = false;
                patient_login_flag = false;
                configureChoicebox();
                personList.getItems().addAll("Choose A Person", "Donor", "Patient");
                personList.setValue("Choose A Person");
                personList.setTooltip(new Tooltip("Select a Person to search"));
                PopUpWindow.popUp();
            }
        });

    }


    //location array
    public static String[] location_array = new String[]{
            "Choose A Location",
            "All",
            "Adabor",
            "Badda",
            "Biman Bandar",
            "Banani",
            "Bangshal",
            "Cantonment",
            "Chawkbazar Model",
            "Dakshinkhan",
            "Darus Salam",
            "Dhanmondi",
            "Demra",
            "Kotwali",
            "Gendaria",
            "Gulshan",
            "Hazaribagh",
            "Jatrabari",
            "Kadamtali",
            "Kafrul",
            "Kalabagan",
            "Kamringir Char",
            "Khilkhet",
            "Khilgaon",
            "Lalbagh",
            "Mirpur",
            "Mohammadpur",
            "Motijheel",
            "New Market",
            "Pallabi",
            "Paltan",
            "Ramna",
            "Rampura",
            "Sabujbagh",
            "Shah Ali",
            "Shahbagh",
            "Sher-e-Bangla Nagor",
            "Shyampur",
            "Sutrapur",
            "Tejgaon",
            "Tejgaon Industrial Area",
            "Turag",
            "Uttar Khan",
            "Uttara"
    };

}


