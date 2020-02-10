package cellmodel;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ErrorPopup {

    public ErrorPopup(Exception e){
        Stage errorStage = new Stage();
        errorStage.setTitle("Error");
        Label errorLabel = new Label(e.getMessage());
        errorLabel.setAlignment(Pos.CENTER);
        Scene errorScene = new Scene(errorLabel);
        errorStage.setScene(errorScene);
        errorStage.showAndWait();
    }

}
