package tutor.dsafx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import tutor.dsafx.auth.AuthService;

import tutor.dsafx.auth.BaseUser;
import tutor.dsafx.util.SceneSwitcher;

public class RegisterController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private TextField emailField;
    @FXML private Button registerButton;
    @FXML private Hyperlink loginLink;
    @FXML private Label errorLabel;
    @FXML private VBox registerForm;


    private final AuthService authService = new AuthService();

    @FXML
    public void initialize() {
        registerButton.disableProperty().bind(
                usernameField.textProperty().isEmpty()
                        .or(passwordField.textProperty().isEmpty())
                        .or(confirmPasswordField.textProperty().isEmpty())
                        .or(emailField.textProperty().isEmpty())
        );

        errorLabel.setVisible(false);

    }

    @FXML
    private void handleRegister() {
        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            showError("Passwords don't match");
            return;
        }

        showLoading(true);

        BaseUser user = new BaseUser(
                usernameField.getText(),
                emailField.getText()
        );
        user.setPassword(passwordField.getText());

        new Thread(() -> {
            boolean success = authService.register(user);

            javafx.application.Platform.runLater(() -> {
                showLoading(false);

                if (success) {
                    SceneSwitcher.switchTo(registerForm.getScene(), "login.fxml");
                } else {
                    showError("Username already exists");
                }
            });
        }).start();
    }

    @FXML
    private void switchToLogin(ActionEvent event) {
        SceneSwitcher.switchTo(event, "login.fxml");
    }

    private void showLoading(boolean show) {
        registerForm.setDisable(show);

    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }
}