package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class dashboardController {
    
    @FXML
    private Label welcomeLabel;
    
    @FXML
    private TableView<Match> fixturesTable;
    
    private Team currentTeam;

    @FXML
    public void initialize() {
        // Create simple columns
        TableColumn<Match, String> teamColumn = new TableColumn<>("Teams");
        TableColumn<Match, String> dateColumn = new TableColumn<>("Match Date");
        
        teamColumn.setPrefWidth(300);
        dateColumn.setPrefWidth(200);
        
        teamColumn.setCellValueFactory(new PropertyValueFactory<>("matchTeams"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("matchDate"));
        
        fixturesTable.getColumns().addAll(teamColumn, dateColumn);
        
        // Try to load existing fixtures if any
        if (currentTeam != null) {
            List<Match> savedFixtures = FixtureFileHandler.loadFixtures(currentTeam);
            if (!savedFixtures.isEmpty()) {
                fixturesTable.setItems(FXCollections.observableArrayList(savedFixtures));
            }
        }
    }

    @FXML
    private void generateFixtures(ActionEvent event) {
        try {
            List<Team> allTeams = TeamFileHandler.loadAllTeams();
            
            // For testing, use first team if currentTeam is null
            if (currentTeam == null) {
                currentTeam = allTeams.get(0);
            }
            
            List<Match> matches = new ArrayList<>();
            for(int i = 1; i <= 9; i++) {
                Team opponent = getRandomOpponent(allTeams, currentTeam);
                LocalDateTime matchDate = LocalDateTime.now().plusDays(i * 3);
                Match match = new Match(i, currentTeam, opponent, matchDate);
                matches.add(match);
            }
            
            fixturesTable.setItems(FXCollections.observableArrayList(matches));
            welcomeLabel.setText("Fixtures for " + currentTeam.getTeamName());
            
        } catch (Exception e) {
            showAlert("Error", "Could not generate fixtures: " + e.getMessage());
        }
    }

    @FXML
    private void saveFixtures(ActionEvent event) {
        try {
            List<Match> matches = new ArrayList<>(fixturesTable.getItems());
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
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            showAlert("Error", "Could not logout: " + e.getMessage());
        }
    }
    
    private Team getRandomOpponent(List<Team> allTeams, Team currentTeam) {
        List<Team> availableTeams = new ArrayList<>(allTeams);
        availableTeams.remove(currentTeam);
        int randomIndex = (int)(Math.random() * availableTeams.size());
        return availableTeams.get(randomIndex);
    }
    
    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setCurrentTeam(Team team) {
        this.currentTeam = team;
        welcomeLabel.setText("Welcome, " + team.getTeamName());
        
        // Load existing fixtures if any
        List<Match> savedFixtures = FixtureFileHandler.loadFixtures(team);
        if (!savedFixtures.isEmpty()) {
            fixturesTable.setItems(FXCollections.observableArrayList(savedFixtures));
        }
    }
}
