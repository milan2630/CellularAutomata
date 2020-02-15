package display;

import cellmodel.*;

import cellmodel.errorhandling.ErrorPopup;
import cellmodel.errorhandling.SaveException;
import cellmodel.errorhandling.XMLException;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

/**
 * Class to control user input and change the simulation accordingly
 */
public class UserInterface extends Application {
    private static final String RESOURCES = "resources";
    public static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";
    private static final String DEFAULT_RESOURCE_FOLDER = "/" + RESOURCES + "/";
    public static final String STYLE_PROPERTIES_FILENAME = DEFAULT_RESOURCE_PACKAGE + "StyleComponents";
    private static final String XML_PROPERTIES_FILENAME = DEFAULT_RESOURCE_PACKAGE + "XMLTagNames";
    private static final String STYLESHEET = "default.css";
    public static final int UI_GAP = 100;

    private ResourceBundle myResources;
    private ResourceBundle styleResources;
    private ResourceBundle xmlResources;
    private Group UIroot;
    private Stage UIstage;
    private Simulation mySim;
    private TextField speedSetter;
    private Configuration myConfig;

    /**
     * Starts the program by opening an input screen for the xml file
     * Is launched by Main.java
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        styleResources = ResourceBundle.getBundle(STYLE_PROPERTIES_FILENAME);
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + styleResources.getString("Language"));
        xmlResources = ResourceBundle.getBundle(XML_PROPERTIES_FILENAME);
        myConfig = new Configuration(getFileName());
        try {
            mySim = myConfig.getInitSim();
        }
        catch (XMLException e){
            createErrorDialog(e);
            start(primaryStage);
        }
        UIroot = new Group();
        UIstage = primaryStage;
        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        //UIstage.setX(screen.getWidth());
        createController();
        UIstage.setOnCloseRequest(e->stopEverything());
    }


    /**
     * Creates a FileTextPrompt and returns the inputted string
     * @return the typed file name from the user
     */
    private String getFileName(){
        FileTextPrompt fileInput = new FileTextPrompt("FileInputPrompt", "ChooseCommand");
        return addXMLFileFolder(fileInput.getResult());
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
        addButtonToHBox("SaveCommand", event -> saveCurrent(), controls);
        addButtonToHBox("NewSimCommand", event -> startAnother(), controls);
        speedSetter = createSpeedSetter();
        controls.getChildren().add(speedSetter);
        addButtonToHBox("SetSpeedCommand", event -> mySim.resetKeyFrame(Integer.parseInt(speedSetter.getText())),controls);

        UIroot.getChildren().add(controls);
        Scene controllerScreen = new Scene(UIroot, controls.getPrefWidth(), controls.getPrefHeight());
        controllerScreen.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());
        UIstage.setScene(controllerScreen);
        UIstage.setX(0);
        UIstage.setY(0);
        UIstage.show();
    }

    private void startAnother() {
        UserInterface ui = new UserInterface();
        ui.start(new Stage());

    }

    private void saveCurrent() {
        try{
            mySim.saveCurrent();
        }
        catch (SaveException e){
            createErrorDialog(e);
        }
    }

    /**
     * @return a textfield to take in a speed from the user
     */
    private TextField createSpeedSetter(){
        TextField setter = new TextField();
        setter.setMaxWidth(Integer.parseInt(styleResources.getString("SpeedSetterWidth")));
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

    private String addXMLFileFolder(String filename){
        return xmlResources.getString("XMLFilesFolder") + filename + ".xml";
    }

    private void createErrorDialog(Exception e){
        ErrorPopup ep = new ErrorPopup(e);
    }

    /**
     * @param filename is the text in the textfield when the submit button is clicked
     * @param stage is the stage holding the textfield
     */
    private void handleFileSubmit(String filename, Stage stage){
        try {
            myConfig = new Configuration(addXMLFileFolder(filename));
            stage.close();
        } catch (XMLException e) {
            createErrorDialog(e);
        }
    }

    private void stopEverything(){
        mySim.endSim();
        System.exit(1);
    }

    /**
     * Class for file input
     */
    class FileTextPrompt {
        private final String result;

        /**
         * Creates a prompting textfield to take in a string
         */
        FileTextPrompt(String titleProperty, String buttonProperty) {
            final Stage dialog = new Stage();

            dialog.setTitle(myResources.getString(titleProperty));
            final TextField textField = new TextField();
            final Button submitButton = makeButton(buttonProperty, event -> handleFileSubmit(textField.getText(), dialog));
            dialog.setOnCloseRequest(t->System.exit(1));
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
         * @return the value in the textfield
         */
        private String getResult() {
            return result;
        }
    }
}
