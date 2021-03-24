package com.codecool.database;

import java.sql.*;

public class RadioCharts {

    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;

    private static int id = 0;

    public RadioCharts(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    private Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException e) {
            System.err.println("Database not reachable.");
        }
        return connection;
    }

    public String getMostPlayedSong() {
        String name = "";
        String query = "SELECT song, SUM(times_aired) as times_aired " +
                "FROM music_broadcast " +
                "GROUP BY song " +
                "ORDER BY times_aired DESC, song DESC";
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            name = resultSet.getString("song");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

    public String getMostActiveArtist() {
        String name = "";
        String query = "SELECT DISTINCT song, artist, COUNT(artist) as occurrence " +
                "FROM music_broadcast " +
                "GROUP BY ysong " +
                "HAVING COUNT(artist) = 1 " +
                "ORDER BY occurrence DESC;";
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            name = resultSet.getString("artist");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

}
