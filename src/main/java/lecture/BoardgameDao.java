package lecture;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoardgameDao {
    /*
    * DAO- design pattern
    * -------------------
    * Data Access Object (DAO) design pattern is a crucial pattern used to separate data persistence logic from business logic.
    * DAO defines standard CRUD (Create, Read, Update, Delete) methods and encapsulates them in a separate class, making code easier to read and maintain.
    *
     */
    private final Connection conn;
    private static final String GET_ALL_BOARDGAMES_SQL = "SELECT brettspill_id, navn, type, antall_spillere, spilletid, aldersgrense, bilde FROM Brettspill";
    private static final String GET_BOARDGAME_SQL = "SELECT brettspill_id, navn, type, antall_spillere, spilletid, aldersgrense, bilde FROM Brettspill WHERE brettspill_id=?";
    private static final String ADD_BOARDGAME_SQL = "INSERT INTO Brettspill VALUES(?,?,?,?,?,?,?)";
    private static final String UPDATE_BOARDGAME_SQL = "UPDATE Brettspill SET navn=?, type=?, antall_spillere=?, spilletid=?, aldersgrense=?, bilde=? WHERE brettspill_id=?";
    private static final String DELETE_BOARDGAME_SQL = "DELETE FROM Brettspill WHERE brettspill_id =?";

    public BoardgameDao(Connection conn) {
        this.conn = conn;
    }

    public List<BoardGame> getAllBoardGames() {

        List<BoardGame> boardGames = new ArrayList<>();
        try (
                Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery("SELECT * FROM brettspill")
        ) {


            while (rs.next()) {
                int id = rs.getInt("brettspill_id");
                String name = rs.getString("navn");
                String type = rs.getString("type");
                int nrOfPlayers = rs.getInt("antall_spillere");
                int minutes = rs.getInt("spilletid");
                int ageLimit = rs.getInt("aldersgrense");
                String imageUrl = rs.getString("bilde");
                BoardGame bg = new BoardGame(id, name, type, nrOfPlayers, minutes, ageLimit, imageUrl);
                boardGames.add(bg);
            }

        } catch (SQLException e) {
            IO.println("Unable to connect to database:" + e.getMessage());
            e.printStackTrace();
        }
    return boardGames;
    }

    public BoardGame getBoardGame(int boardGameId) throws SQLException {
        try (
             PreparedStatement statement = conn.prepareStatement(GET_BOARDGAME_SQL);
        ) {
            statement.setInt(1, boardGameId);
            try (ResultSet rs = statement.executeQuery()){
                if(rs.next()){
                    String name = rs.getString("navn");
                    String type = rs.getString("type");
                    int nrOfPlayers = rs.getInt("antall_spillere");
                    int minutes = rs.getInt("spilletid");
                    int ageLimit = rs.getInt("aldersgrense");
                    String imageUrl = rs.getString("bilde");
                    return new BoardGame(boardGameId, name, type, nrOfPlayers, minutes, ageLimit, imageUrl);
                }
            }
            // No board game found...
            return null; // We will look at other options than returning null later...
        }
    }

    public int addBoardGame(BoardGame bg) throws SQLException {
        try (
             PreparedStatement statement = conn.prepareStatement(ADD_BOARDGAME_SQL);
        ) {
            statement.setInt(1, bg.id());
            statement.setString(2, bg.name());
            statement.setString(3, bg.type());
            statement.setInt(4, bg.nrOfPlayers());
            statement.setInt(5, bg.minutes());
            statement.setInt(6, bg.ageLimit());
            statement.setString(7, bg.imageUrl());
            return statement.executeUpdate();
        }
    }

    public int updateBoardGame(BoardGame bg) throws SQLException {
        try (
             PreparedStatement statement = conn.prepareStatement(UPDATE_BOARDGAME_SQL);
        ) {
            statement.setString(1, bg.name());
            statement.setString(2, bg.type());
            statement.setInt(3, bg.nrOfPlayers());
            statement.setInt(4, bg.minutes());
            statement.setInt(5, bg.ageLimit());
            statement.setString(6, bg.imageUrl());
            statement.setInt(7, bg.id());
            return statement.executeUpdate();
        }
    }

    public int deleteBoardGame(int boardGameId) throws SQLException {
        try (
             PreparedStatement statement = conn.prepareStatement(DELETE_BOARDGAME_SQL);
        ) {
            statement.setInt(1, boardGameId);
            return statement.executeUpdate();
        }
    }



}
