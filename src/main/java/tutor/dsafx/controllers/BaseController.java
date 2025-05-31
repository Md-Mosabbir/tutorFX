package tutor.dsafx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import tutor.dsafx.util.SceneSwitcher;
import java.io.IOException;

public abstract class BaseController {
    @FXML
    protected TextField inputField;

    @FXML
    protected Label dataStructureDisplay;

    protected abstract void updateDisplay();

    @FXML
    protected void onBack(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(event, "hello-view.fxml");
    }

    protected void clearInput() {
        inputField.clear();
    }

    protected void showError(String message) {
        dataStructureDisplay.setText("Error: " + message);
    }
}