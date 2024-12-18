package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.stage.Stage;
import java.util.List;

public class TeamDashboardController {

    @FXML
    private Label welcomeLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private TableView<Match> fixturesTable;
    @FXML
    private TableColumn<Match, String> teamsColumn;
    @FXML
    private TableColumn<Match, String> dateColumn;

    private Team currentTeam;

    @FXML
    public void initialize() {
        // Set up table columns
        teamsColumn.setCellValueFactory(new PropertyValueFactory<>("matchTeams"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("matchDate"));

        // Make columns fill the width of the table
        teamsColumn.prefWidthProperty().bind(fixturesTable.widthProperty().multiply(0.67));
        dateColumn.prefWidthProperty().bind(fixturesTable.widthProperty().multiply(0.33));
    }

    @FXML
    private void generateFixtures(ActionEvent event) {
        try {
            List<Team> allTeams = TeamFileHandler.loadAllTeams();
            List<Match> fixtures = FixtureGenerator.generateFixtures(currentTeam, allTeams);
            fixturesTable.setItems(FXCollections.observableArrayList(fixtures));
            showAlert("Success", "New fixtures generated successfully!");
        } catch (Exception e) {
            showAlert("Error", "Could not generate fixtures: " + e.getMessage());
        }
    }

    @FXML
    private void saveFixtures(ActionEvent event) {
        try {
            List<Match> matches = fixturesTable.getItems();
            if (matches.isEmpty()) {
                showAlert("Warning", "No fixtures to save!");
                return;
            }

            FixtureFileHandler.saveFixtures(matches, currentTeam);
            showAlert("Success", "Fixtures saved successfully!");

        } catch (Exception e) {
            showAlert("Error", "Could not save fixtures: " + e.getMessage());
        }
    }

    @FXML
    private void logout(ActionEvent event) {
        // Show confirmation dialog
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Logout");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to logout?");

        // Wait for user response
        if (confirmAlert.showAndWait().get() == ButtonType.OK) {
            try {
                // Show success message
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Logout Successful");
                successAlert.setHeaderText(null);
                successAlert.setContentText("You have been successfully logged out!");
                successAlert.showAndWait();

                // Redirect to login page
                Parent root = FXMLLoader.load(getClass().getResource("loginpage.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Login");
                stage.setScene(scene);
                stage.show();

            } catch (Exception e) {
                showAlert("Error", "Could not logout: " + e.getMessage());
            }
        }
    }

    public void setCurrentTeam(Team team) {
        this.currentTeam = team;
        welcomeLabel.setText("Welcome, " + team.getTeamName());
        if (titleLabel != null) {
            titleLabel.setText(team.getTeamName());
        }

        // Load existing fixtures if any
        List<Match> savedFixtures = FixtureFileHandler.loadFixtures(team);
        if (!savedFixtures.isEmpty()) {
            fixturesTable.setItems(FXCollections.observableArrayList(savedFixtures));
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}