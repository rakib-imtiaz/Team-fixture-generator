package application;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FixtureGenerator {
    private static final int MATCHES_PER_TEAM = 9;

    public static List<Match> generateFixtures(Team currentTeam, List<Team> allTeams) {
        List<Match> fixtures = new ArrayList<>();
        Set<Integer> usedOpponentIds = new HashSet<>();
        List<Team> availableTeams = new ArrayList<>(allTeams);

        // Remove current team from available teams
        availableTeams.removeIf(team -> team.getTeamId() == currentTeam.getTeamId());

        // Check if we have enough teams
        if (availableTeams.size() < MATCHES_PER_TEAM) {
            throw new IllegalStateException("Not enough teams available for fixtures");
        }

        // Load existing fixtures to check for previous matches
        List<Match> existingFixtures = FixtureFileHandler.loadFixtures(currentTeam);
        for (Match match : existingFixtures) {
            usedOpponentIds.add(match.getTeam2().getTeamId());
        }

        // Generate new matches
        LocalDateTime startDate = LocalDateTime.now().plusDays(1).withHour(10).withMinute(0);
        int matchId = existingFixtures.size() + 1;
        int attempts = 0;
        int maxAttempts = 100; // Prevent infinite loop

        while (fixtures.size() < MATCHES_PER_TEAM && attempts < maxAttempts) {
            int randomIndex = (int) (Math.random() * availableTeams.size());
            Team opponent = availableTeams.get(randomIndex);

            // Check if we haven't played this opponent before
            if (!usedOpponentIds.contains(opponent.getTeamId())) {
                Match match = new Match(
                        matchId++,
                        currentTeam,
                        opponent,
                        startDate.plusDays(attempts));
                fixtures.add(match);
                usedOpponentIds.add(opponent.getTeamId());
            }
            attempts++;
        }

        return fixtures;
    }
}