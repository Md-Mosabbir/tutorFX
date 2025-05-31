package tutor.dsafx.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;

public class SceneSwitcher {
    public static void switchTo(ActionEvent event, String fxmlPath) {
        try {
            // IMPORTANT: Use absolute path starting with "/"
            FXMLLoader loader = new FXMLLoader(SceneSwitcher.class.getResource("/tutor/dsafx/" + fxmlPath));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show(); // Optional, but ensures the scene updates
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Could not switch to scene: " + fxmlPath);
        }
    }
}
