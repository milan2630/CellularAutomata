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

    @Override
    public void start(Stage primaryStage) {
        UIroot = new Group();
        UIstage = new Stage();
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

    private String getFileName(){
        FileTextPrompt fileInput = new FileTextPrompt();
        return fileInput.getResult();
    }

    private void createController(){
        HBox controls = new HBox();
        Button stopButton = makeButton("PauseCommand", event -> mySim.resetKeyFrame(0));
        controls.getChildren().add(stopButton);

        Button stepButton = makeButton("StepCommand", event -> mySim.step());
        controls.getChildren().add(stepButton);

        Button continueButton = makeButton("ContinueCommand", event -> mySim.resetKeyFrame(1));
        controls.getChildren().add(continueButton);

        Button restartButton = makeButton("RestartCommand", event -> onRestart());
        controls.getChildren().add(restartButton);

        speedSetter = new TextField();
        speedSetter.setMaxWidth(50);
        speedSetter.setText(Simulation.DEFAULT_FRAMES_PER_SECOND+"");
        controls.getChildren().add(speedSetter);

        Button changeSpeedButton = makeButton("SetSpeedCommand", event -> mySim.resetKeyFrame(Integer.parseInt(speedSetter.getText())));
        controls.getChildren().add(changeSpeedButton);


        UIroot.getChildren().add(controls);
        Scene controllerScreen = new Scene(UIroot, 500, 500);
        controllerScreen.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());
        UIstage.setScene(controllerScreen);
        UIstage.show();
    }

    private void onRestart(){
        UIstage.close();
        this.start(new Stage());
    }

    // makes a button using either an image or a label
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



    class FileTextPrompt {
        private final String result;
        FileTextPrompt() {
            final Stage dialog = new Stage();

            dialog.setTitle("Enter Missing Text");
            dialog.initStyle(StageStyle.UTILITY);
            dialog.initModality(Modality.WINDOW_MODAL);


            final TextField textField = new TextField();
            final Button submitButton = new Button("Submit");
            submitButton.setDefaultButton(true);
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

            final VBox layout = new VBox(10);
            layout.setAlignment(Pos.CENTER_RIGHT);
            layout.setStyle("-fx-background-color: azure; -fx-padding: 10;");
            layout.getChildren().setAll(
                    textField,
                    submitButton
            );

            dialog.setScene(new Scene(layout));
            dialog.showAndWait();

            result = textField.getText();
        }

        private void displayErrorMessage() {
            return;
        }

        private boolean isValidFile(String filename) {
            File caFile = new File(filename);
            return caFile.exists();
        }

        private String getResult() {
            return result;
        }
    }
}
