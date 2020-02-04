package display;

import cellsociety.Configuration;
import cellsociety.Simulation;
import java.io.File;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;

/**
 * Class to control user input and change the simulation accordingly
 */
public class UserInterface extends Application {
    private static final String RESOURCES = "resources";
    public static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";
    public static final String DEFAULT_RESOURCE_FOLDER = "/" + RESOURCES + "/";
    public static final String STYLESHEET = "default.css";
    public static final String DEFAULT_LANGUAGE = "English";
    public static final String DEBUG_FILENAME = "XMLFiles/percolation6by6.xml";
    public static final boolean DEBUG = false;

    private ResourceBundle myResources;
    private Group UIroot;
    private Stage UIstage;
    private Simulation mySim;
    private TextField speedSetter;

    /**
     * Starts the program by opening an input screen for the xml file
     * Is launched by Main.java
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        UIroot = new Group();
        UIstage = primaryStage;
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + DEFAULT_LANGUAGE);
        Configuration config;
        if(DEBUG){
            config = new Configuration(DEBUG_FILENAME);
        }
        else{
            config = new Configuration(getFileName());
        }
        mySim = config.getInitSim();
        createController();
    }

    /**
     * Creates a FileTextPrompt and returns the inputted string
     * @return the typed file name from the user
     */
    private String getFileName(){
        FileTextPrompt fileInput = new FileTextPrompt();
        return fileInput.getResult();
    }

    /**
     * Creates the UI to control the simulation
     */
    private void createController(){
        HBox controls = new HBox();
        addButtonToHBox("PauseCommand", event -> mySim.resetKeyFrame(0), controls);
        addButtonToHBox("StepCommand", event -> mySim.step(), controls);
        addButtonToHBox("ContinueCommand", event -> mySim.resetKeyFrame(1), controls);
        addButtonToHBox("RestartCommand", event -> onRestart(), controls);
        speedSetter = createSpeedSetter();
        controls.getChildren().add(speedSetter);
        addButtonToHBox("SetSpeedCommand", event -> mySim.resetKeyFrame(Integer.parseInt(speedSetter.getText())),controls);

        UIroot.getChildren().add(controls);
        Scene controllerScreen = new Scene(UIroot, 500, 500);
        controllerScreen.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());
        UIstage.setScene(controllerScreen);
        UIstage.show();
    }

    /**
     * @return a textfield to take in a speed from the user
     */
    private TextField createSpeedSetter(){
        TextField setter = new TextField();
        setter.setMaxWidth(50);
        setter.setText(Simulation.DEFAULT_FRAMES_PER_SECOND+"");
        return setter;
    }

    /**
     * Closes the UI window, ends the simulation, and restarts it by running the start method
     */
    private void onRestart(){
        UIstage.close();
        mySim.endSim();
        this.start(new Stage());
    }

    /**
     * makes a button using either an image or a label
     * @param property is the name of the button type to get from the properties file
     * @param handler is the event to occur when the button is pressed
     * @return a button based on the desired text and event
     */
    private Button makeButton (String property, EventHandler<ActionEvent> handler) {
        // represent all supported image suffixes
        final String IMAGEFILE_SUFFIXES = String.format(".*\\.(%s)", String.join("|", ImageIO.getReaderFileSuffixes()));
        Button result = new Button();
        String label = myResources.getString(property);
        if (label.matches(IMAGEFILE_SUFFIXES)) {
            result.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(DEFAULT_RESOURCE_FOLDER + label))));
        }
        else {
            result.setText(label);
        }
        result.setOnAction(handler);
        return result;
    }

    /**
     * @param property name of the button to pass to makeButton
     * @param handler event to occur on click of button
     * @param container the HBox that the button is being added to
     */
    private void addButtonToHBox(String property, EventHandler<ActionEvent> handler, HBox container){
        container.getChildren().add(makeButton(property, handler));
    }

    /**
     * Class for file input
     */
    class FileTextPrompt {
        private final String result;

        /**
         * Creates a prompting textfield to take in a string
         */
        FileTextPrompt() {
            final Stage dialog = new Stage();

            dialog.setTitle("Enter File Path");
            dialog.initStyle(StageStyle.UTILITY);
            dialog.initModality(Modality.WINDOW_MODAL);


            final TextField textField = new TextField();
            final Button submitButton = new Button("Submit");
            submitButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent t) {
                    if(isValidFile(textField.getText())) {
                        dialog.close();
                    }
                    else{
                        displayErrorMessage();
                    }
                }
            });
            textField.setMinHeight(TextField.USE_PREF_SIZE);

            final VBox layout = new VBox();
            layout.setAlignment(Pos.CENTER_RIGHT);
            layout.getChildren().setAll(textField, submitButton);
            Scene fileScreen = new Scene(layout);
            fileScreen.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());
            dialog.setScene(fileScreen);
            dialog.showAndWait();

            result = textField.getText();
        }

        /**
         * Displays the error message when an invalid file is inputted
         * Still needs to be implemented
         */
        private void displayErrorMessage() {
            return;
        }

        /**
         * @param filename name to check if it is valid
         * @return whether the filename is valid
         */
        private boolean isValidFile(String filename) {
            File caFile = new File(filename);
            return caFile.exists();
        }

        /**
         * @return the value in the textfield
         */
        private String getResult() {
            return result;
        }
    }
}
