package tutor.dsafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import tutor.dsafx.util.SceneSwitcher;

public class HelloController {



    @FXML
    protected void goToStackScene(ActionEvent event) {
        SceneSwitcher.switchTo(event, "stack-view.fxml");
    }

    @FXML
    protected void goToQueueScene(ActionEvent event) {
        SceneSwitcher.switchTo(event, "queue-view.fxml");
    }

    @FXML
    protected void goToLinkedListScene(ActionEvent event) {
        SceneSwitcher.switchTo(event, "linkedlist-view.fxml");
    }

    @FXML
    protected void goToSortingScene(ActionEvent event) {
        SceneSwitcher.switchTo(event, "sorting-view.fxml");
    }

}
