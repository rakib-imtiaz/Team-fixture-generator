package application;

public class Team {
    private int teamId;
    private String teamName;
    private String email;
    private String password;

    public Team(int teamId, String teamName, String email, String password) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.email = email;
        this.password = password;
    }

    // Getters and setters
    public int getTeamId() { return teamId; }
    public String getTeamName() { return teamName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
} 