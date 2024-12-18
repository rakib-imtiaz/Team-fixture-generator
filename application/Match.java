package application;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Match {
    private int matchId;
    private Team team1;
    private Team team2;
    private LocalDateTime matchDateTime;

    public Match(int matchId, Team team1, Team team2, LocalDateTime matchDateTime) {
        this.matchId = matchId;
        this.team1 = team1;
        this.team2 = team2;
        this.matchDateTime = matchDateTime;
    }

    // This will be used by the TableView
    public String getMatchTeams() {
        return team1.getTeamName() + " vs " + team2.getTeamName();
    }

    // This will be used by the TableView
    public String getMatchDate() {
        return matchDateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm"));
    }

    // Regular getters
    public int getMatchId() { return matchId; }
    public Team getTeam1() { return team1; }
    public Team getTeam2() { return team2; }
    public LocalDateTime getMatchDateTime() { return matchDateTime; }
} 