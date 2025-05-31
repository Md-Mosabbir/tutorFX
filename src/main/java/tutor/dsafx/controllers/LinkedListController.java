package tutor.dsafx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
public class LinkedListController extends BaseController {

    @FXML
    private TextField inputField;

    @FXML
    private Label linkedListDisplay;

    private Node head = null;

    @Override
    protected void updateDisplay() {

        StringBuilder sb = new StringBuilder();
        sb.append("Linked List: ");
        Node current = head;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) {
                sb.append(" -> ");
            }
            current = current.next;
        }
        linkedListDisplay.setText(sb.toString());

    }

    private static class Node {
        String data;
        Node next;
        Node(String data) {
            this.data = data;
            this.next = null;
        }
    }

    @FXML
    protected void onAddNode(ActionEvent event) {
        String value = inputField.getText();
        if (!value.isEmpty()) {
            Node newNode = new Node(value);
            if (head == null) {
                head = newNode;
            } else {
                Node current = head;
                while (current.next != null) {
                    current = current.next;
                }
                current.next = newNode;
            }
            inputField.clear();
            updateDisplay();
        }
    }

    @FXML
    protected void onRemoveNode(ActionEvent event) {
        if (head != null) {
            head = head.next;
            updateDisplay();
        }
    }




}
