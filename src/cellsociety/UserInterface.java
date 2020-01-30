package cellsociety;



import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UserInterface {

    private Group UIroot;
    private Stage UIstage;
    private String filename;


    public UserInterface(){
        UIroot = new Group();
        UIstage = new Stage();
    }

    public void setFileInputScreen(){
        TextField fileInput = new TextField();
        Button submit = new Button();
        submit.setLayoutY(50);
        submit.setText("Submit");
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                setFileName(fileInput.getText());
            }
        });
        UIroot.getChildren().add(fileInput);
        UIroot.getChildren().add(submit);
        Scene inputScene = new Scene(UIroot, 500, 100);
        UIstage.setScene(inputScene);
        UIstage.show();
    }

    private void setFileName(String text) {
        if(isValidFile(text)) {
            filename = text;
        }
    }

    private boolean isValidFile(String text) {
        return true;
    }

    public String getFilename(){
        return filename;
    }

}
