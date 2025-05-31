package tutor.dsafx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tutor.dsafx.util.SceneSwitcher;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class QueueController extends BaseController {

    @FXML
    private TextField inputField;

    @FXML
    private Label queueDisplay;

    private final Queue<String> queue = new LinkedList<>();

    @FXML
    protected void onEnqueue(ActionEvent event) {
        String value = inputField.getText();
        if (!value.isEmpty()) {
            queue.add(value);
            inputField.clear();
            updateDisplay();
        }
    }

    @FXML
    protected void onDequeue(ActionEvent event) {
        if (!queue.isEmpty()) {
            queue.poll();
            updateDisplay();
        }
    }


    @Override
    protected void updateDisplay() {
        queueDisplay.setText("Queue: " + queue);
    }
}
