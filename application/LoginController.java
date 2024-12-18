package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label errorLabel;

    @FXML
    private void handleLogin(ActionEvent event) {
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        if (email.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please enter both email and password");
            return;
        }

        try {
            // Attempt to authenticate team
            Team team = TeamFileHandler.authenticateTeam(email, password);

            if (team != null) {
                // Load TeamDashboard.fxml instead of dashboard.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("TeamDashboard.fxml"));
                Parent root = loader.load();

                // Get controller and set the team
                TeamDashboardController controller = loader.getController();
                controller.setCurrentTeam(team);

                // Switch to dashboard scene
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Team Dashboard - " + team.getTeamName());
                stage.setScene(scene);
                stage.show();

            } else {
                errorLabel.setText("Invalid email or password");
            }

        } catch (Exception e) {
            errorLabel.setText("Error: " + e.getMessage());
            e.printStackTrace(); // For debugging
        }
    }
}