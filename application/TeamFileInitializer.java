package application;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class TeamFileInitializer {
    private static final String TEAM_FILE = "team_list.txt";
    
    // Hardcoded team data
    private static final List<String[]> INITIAL_TEAMS = Arrays.asList(
        new String[]{"Real Madrid", "real@madrid.com", "madrid123"},
        new String[]{"Barcelona", "barca@fcb.com", "barca123"},
        new String[]{"Liverpool", "liverpool@lfc.com", "liver123"},
        new String[]{"Bayern Munich", "bayern@fcb.com", "bayern123"},
        new String[]{"PSG", "psg@paris.com", "paris123"},
        new String[]{"Manchester City", "city@mcfc.com", "city123"},
        new String[]{"Juventus", "juve@juventus.com", "juve123"},
        new String[]{"PSV Eindhoven", "psv@eindhoven.com", "psv123"},
        new String[]{"Rangers", "rangers@rfc.com", "rangers123"},
        new String[]{"Red Bull Salzburg", "rb@salzburg.com", "salzburg123"},
        new String[]{"Young Boys", "young@boys.com", "young123"},
        new String[]{"Lazio", "lazio@rome.com", "lazio123"},
        new String[]{"Ajax", "ajax@amsterdam.com", "ajax123"},
        new String[]{"Porto", "porto@fcp.com", "porto123"},
        new String[]{"Benfica", "benfica@slb.com", "benfica123"},
        new String[]{"Celtic", "celtic@glasgow.com", "celtic123"},
        new String[]{"Feyenoord", "feyenoord@rotterdam.com", "feye123"},
        new String[]{"Sporting CP", "sporting@scp.com", "sporting123"},
        new String[]{"Napoli", "napoli@ssc.com", "napoli123"},
        new String[]{"AC Milan", "milan@acm.com", "milan123"},
        new String[]{"Inter Milan", "inter@fcim.com", "inter123"},
        new String[]{"Roma", "roma@asroma.com", "roma123"},
        new String[]{"Sevilla", "sevilla@sfc.com", "sevilla123"},
        new String[]{"Atletico Madrid", "atletico@atm.com", "atleti123"},
        new String[]{"Valencia", "valencia@vcf.com", "valencia123"},
        new String[]{"Lyon", "lyon@ol.com", "lyon123"},
        new String[]{"Marseille", "marseille@om.com", "marseille123"},
        new String[]{"Monaco", "monaco@asm.com", "monaco123"},
        new String[]{"Dortmund", "dortmund@bvb.com", "bvb123"},
        new String[]{"Leipzig", "leipzig@rbl.com", "leipzig123"},
        new String[]{"Leverkusen", "bayer@b04.com", "bayer123"},
        new String[]{"Wolfsburg", "wolfsburg@vfl.com", "wolfs123"},
        new String[]{"Galatasaray", "gala@gs.com", "gala123"},
        new String[]{"Fenerbahce", "fener@fb.com", "fener123"},
        new String[]{"Besiktas", "besiktas@bjk.com", "besiktas123"},
        new String[]{"Olympiacos", "olympiacos@ol.com", "olymp123"}
    );

    public static void initializeTeamFile() {
        File file = new File(TEAM_FILE);
        
        if (!file.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                // Write header without any extra spaces
                writer.write("TeamName,Email,Password");
                writer.newLine();
                
                // Write team data with clean formatting
                for (String[] team : INITIAL_TEAMS) {
                    writer.write(String.format("%s,%s,%s", team[0], team[1], team[2]));
                    writer.newLine();
                }
                
                System.out.println("team_list.txt has been created successfully!");
                
            } catch (IOException e) {
                System.err.println("Error creating team_list.txt: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
} 