package tutor.dsafx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Stack;

public class StackController {

    @FXML
    private TextField inputField;

    @FXML
    private Label stackDisplay;

    private final Stack<String> stack = new Stack<>();

    @FXML
    protected void onPush(ActionEvent event) {
        String value = inputField.getText();
        if (!value.isEmpty()) {
            stack.push(value);
            inputField.clear();
            updateDisplay();
        }
    }

    @FXML
    protected void onPop(ActionEvent event) {
        if (!stack.isEmpty()) {
            stack.pop();
            updateDisplay();
        }
    }

    @FXML
    protected void onBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tutor/dsafx/views/hello-view.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    private void updateDisplay() {
        stackDisplay.setText("Stack: " + stack);
    }
}
