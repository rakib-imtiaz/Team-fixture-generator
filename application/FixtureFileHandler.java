package application;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FixtureFileHandler {
    private static final String FIXTURES_FILE = "fixtures.txt";
    private static final DateTimeFormatter DATE_FORMATTER = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void saveFixtures(List<Match> matches, Team team) {
        File fixtureFile = new File(FIXTURES_FILE);
        boolean append = fixtureFile.exists();  // Append only if file exists
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(FIXTURES_FILE, append))) {
            // Write a header for this team's fixtures
            writer.println("=== Fixtures for " + team.getTeamName() + " ===");
            
            // Write each match
            for (Match match : matches) {
                writer.println(String.format("%d,%s,%s,%s",
                    match.getMatchId(),
                    match.getTeam1().getTeamName(),
                    match.getTeam2().getTeamName(),
                    match.getMatchDateTime().format(DATE_FORMATTER)
                ));
            }
            writer.println(); // Empty line between different teams' fixtures
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Match> loadFixtures(Team team) {
        List<Match> matches = new ArrayList<>();
        File fixtureFile = new File(FIXTURES_FILE);
        
        // If file doesn't exist, return empty list instead of throwing exception
        if (!fixtureFile.exists()) {
            return matches;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(FIXTURES_FILE))) {
            String line;
            boolean teamSection = false;
            
            while ((line = reader.readLine()) != null) {
                if (line.contains("=== Fixtures for " + team.getTeamName() + " ===")) {
                    teamSection = true;
                    continue;
                }
                
                if (teamSection && line.trim().isEmpty()) {
                    break;
                }
                
                if (teamSection && !line.startsWith("===")) {
                    String[] parts = line.split(",");
                    if (parts.length == 4) {
                        Match match = new Match(
                            Integer.parseInt(parts[0]),
                            findTeamByName(parts[1]),
                            findTeamByName(parts[2]),
                            LocalDateTime.parse(parts[3], DATE_FORMATTER)
                        );
                        matches.add(match);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Note: No existing fixtures found for " + team.getTeamName());
        }
        return matches;
    }

    private static Team findTeamByName(String teamName) {
        // This method should find a team from your team list by name
        List<Team> allTeams = TeamFileHandler.loadAllTeams();
        return allTeams.stream()
                      .filter(t -> t.getTeamName().equals(teamName))
                      .findFirst()
                      .orElse(null);
    }
} 