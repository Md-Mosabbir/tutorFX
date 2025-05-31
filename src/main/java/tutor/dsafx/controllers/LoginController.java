package tutor.dsafx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import tutor.dsafx.auth.AuthService;
import tutor.dsafx.auth.SessionManager;

import tutor.dsafx.auth.User;
import tutor.dsafx.util.SceneSwitcher;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Hyperlink registerLink;
    @FXML private Label errorLabel;
    @FXML private VBox loginForm;


    private final AuthService authService = new AuthService();
    private final SessionManager sessionManager = SessionManager.getInstance();

    @FXML
    public void initialize() {
        // Set up UI behaviors
        loginButton.disableProperty().bind(
                usernameField.textProperty().isEmpty()
                        .or(passwordField.textProperty().isEmpty())
        );

        errorLabel.setVisible(false);

    }

    @FXML
    private void handleLogin() {
        showLoading(true);

        String username = usernameField.getText();
        String password = passwordField.getText();

        new Thread(() -> {
            User user = authService.login(username, password);

            javafx.application.Platform.runLater(() -> {
                showLoading(false);

                if (user != null) {
                    sessionManager.startSession(user);
                    SceneSwitcher.switchTo(loginForm.getScene(), "hello-view.fxml");
                } else {
                    showError("Invalid username or password");
                    passwordField.clear();
                }
            });
        }).start();
    }

    @FXML
    private void switchToRegister(ActionEvent event) {
        SceneSwitcher.switchTo(event, "register.fxml");
    }

    private void showLoading(boolean show) {
        loginForm.setDisable(show);

    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }
}