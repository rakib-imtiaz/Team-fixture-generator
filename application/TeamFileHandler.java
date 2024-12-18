package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TeamFileHandler {
    private static final String TEAM_FILE_PATH = "team_list.txt";

    public static List<Team> loadAllTeams() {
        List<Team> teams = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(TEAM_FILE_PATH))) {
            String line;
            int id = 1;
            boolean isHeader = true;

            while ((line = reader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length == 3) {
                    Team team = new Team(
                        id++,
                        parts[0],
                        parts[1],
                        parts[2]
                    );
                    teams.add(team);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading team file: " + e.getMessage());
        }

        return teams;
    }

    public static Team authenticateTeam(String email, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(TEAM_FILE_PATH))) {
            String line;
            int id = 1;
            boolean isHeader = true;

            while ((line = reader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length == 3 &&
                    parts[1].equals(email) &&
                    parts[2].equals(password)) {

                    return new Team(
                        id,
                        parts[0],
                        parts[1],
                        parts[2]
                    );
                }
                id++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error during authentication: " + e.getMessage());
        }

        return null;
    }
}