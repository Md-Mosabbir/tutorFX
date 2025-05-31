package tutor.dsafx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import java.util.Stack;

public class StackController extends BaseController {

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



    @Override
    protected void updateDisplay() {
        stackDisplay.setText("Stack: " + stack);
    }
}
