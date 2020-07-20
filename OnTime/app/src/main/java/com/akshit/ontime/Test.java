package com.akshit.ontime;

import java.util.HashMap;
import java.util.Map;

class Solution {
    public static class PlayerStatisticsCollectorImpl {
        final Map<String, PlayerStats> playerScoreMap = new HashMap<>();
        public void putNewInnings(String player, int runs) {
            final PlayerStats playerStats = playerScoreMap.get(player);
            if (playerStats == null) {
                playerScoreMap.put(player, new PlayerStats(player, 1, runs));
            } else {
                playerStats.addInnings(runs);
            }
        }

        public double getAverageRuns(String player) {
            if (player == null) {
                System.out.println("Invalid player");
                throw new IllegalArgumentException("Invalid player");
            }
            PlayerStats playerStats = playerScoreMap.get(player);
            if (playerStats == null) {
                System.out.println("Player has not played inning yet");
                throw new PlayerNotFoundException(String.format("Player %s has not played inning yet", player));
            } else {
                return playerStats.getTotalScore() / (double) playerStats.getNoOfInnings();
            }
        }

        public int getInningsCount(String player) {
            if (player == null) {
                System.out.println("Invalid player");
                throw new IllegalArgumentException("Invalid player");
            }
            final PlayerStats playerStats = playerScoreMap.get(player);
            if (playerStats == null) {
                System.out.println("Player has not played inning yet");
                throw new PlayerNotFoundException(String.format("Player %s has not played inning yet", player));
            } else {
                return playerStats.getNoOfInnings();
            }

        }
    }
}
class PlayerStats {
    private String playerName;
    private int noOfInnings;
    private int totalScore;

    public PlayerStats() {
    }

    public PlayerStats(String playerName, int noOfInnings, int totalScore) {
        this.playerName = playerName;
        this.noOfInnings = noOfInnings;
        this.totalScore = totalScore;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getNoOfInnings() {
        return noOfInnings;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void addInnings(final int runs) {
        this.noOfInnings++;
        this.totalScore += runs;
    }
}
class PlayerNotFoundException extends RuntimeException {
    public PlayerNotFoundException() {
    }

    public PlayerNotFoundException(String message) {
        super(message);
    }

    public PlayerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
