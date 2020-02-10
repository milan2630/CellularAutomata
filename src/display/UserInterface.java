package display;

import cellmodel.Configuration;
import cellmodel.SaveException;
import cellmodel.Simulation;
import java.io.File;
import java.util.ResourceBundle;

import cellmodel.XMLException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;

/**
 * Class to control user input and change the simulation accordingly
 */
public class UserInterface extends Application {
    private static final String RESOURCES = "resources";
    private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";
    private static final String DEFAULT_RESOURCE_FOLDER = "/" + RESOURCES + "/";
    private static final String STYLESHEET = "default.css";
    private static final String DEFAULT_LANGUAGE = "English";
    private static final String XMLFOLDER = "XMLFiles/";
    private static final int UI_SCREEN_WIDTH = 600;
    private static final int UI_SCREEN_HEIGHT = 80;
    private static final int SPEED_SETTER_WIDTH_MAX = 50;

    private ResourceBundle myResources;
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
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + DEFAULT_LANGUAGE);
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
        UIstage.setX(screen.getWidth());
        createController();
    }

    /**
     * Creates a FileTextPrompt and returns the inputted string
     * @return the typed file name from the user
     */
    private String getFileName(){
        FileTextPrompt fileInput = new FileTextPrompt();
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
        speedSetter = createSpeedSetter();
        controls.getChildren().add(speedSetter);
        addButtonToHBox("SetSpeedCommand", event -> mySim.resetKeyFrame(Integer.parseInt(speedSetter.getText())),controls);

        UIroot.getChildren().add(controls);
        Scene controllerScreen = new Scene(UIroot, UI_SCREEN_WIDTH, UI_SCREEN_HEIGHT);
        controllerScreen.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());
        UIstage.setScene(controllerScreen);
        UIstage.show();
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
        setter.setMaxWidth(SPEED_SETTER_WIDTH_MAX);
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
        return XMLFOLDER + filename + ".xml";
    }

    private void createErrorDialog(Exception e){
        Stage errorStage = new Stage();
        errorStage.setTitle("Error");
        Label errorLabel = new Label(e.getMessage());
        errorLabel.setAlignment(Pos.CENTER);
        Scene errorScene = new Scene(errorLabel);
        errorStage.setScene(errorScene);
        errorStage.showAndWait();
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

            dialog.setTitle(myResources.getString("FileInputPrompt"));
            dialog.initStyle(StageStyle.UTILITY);
            dialog.initModality(Modality.WINDOW_MODAL);


            final TextField textField = new TextField();
            final Button submitButton = makeButton("FileChooseCommand", event -> handleFileSubmit(textField.getText(), dialog));
            dialog.setOnCloseRequest(t->stopEverything());
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
         * @param filename is the text in the textfield when the submit button is clicked
         * @param stage is the stage holding the textfield
         */
        private void handleFileSubmit(String filename, Stage stage){
            try{
                myConfig = new Configuration(addXMLFileFolder(filename));
                stage.close();
            }
            catch(XMLException e){
                createErrorDialog(e);
            }
        }

        private void stopEverything(){
            System.exit(1);
        }

        /**
         * @return the value in the textfield
         */
        private String getResult() {
            return result;
        }
    }
}
