package lecture;

import java.sql.SQLException;
import java.util.List;

public class Program {
    /*
    * DAO - business logic
     */
         private final BoardgameDao bgDao;

    public Program(BoardgameDao bgDao) {
        this.bgDao = bgDao;
    }

    public void run(){

        //BoardGameProvider bgDao = new BoardGameProvider();
        List<BoardGame> boardGames = null;
        try {
            // Getting all board games
            IO.println("Getting all board games");
            boardGames = bgDao.getAllBoardGames();
            printAllBoardGames(boardGames);
            // Getting board game with id=3
            IO.println("Getting board game with id=3");
            BoardGame bg = bgDao.getBoardGame(3);
            IO.println(bg);
            // Adding a board game
            IO.println("Adding board game");
            BoardGame newBoardGame = new BoardGame(6, "Agricola", "Strategi", 5, 90, 12, "agricola.jpg");
            int rowsAffected = bgDao.addBoardGame(newBoardGame);
            IO.println("Rows affected:"+rowsAffected);
            boardGames = bgDao.getAllBoardGames();
            printAllBoardGames(boardGames);
            //Updating a board game
            IO.println("Updating new board game");
            BoardGame updatedBoardGame = new BoardGame(newBoardGame.id(), newBoardGame.name(), newBoardGame.type(), newBoardGame.nrOfPlayers(), 199, 99, newBoardGame.imageUrl());
            rowsAffected = bgDao.updateBoardGame(updatedBoardGame);
            IO.println("Rows affected:"+rowsAffected);
            boardGames = bgDao.getAllBoardGames();
            printAllBoardGames(boardGames);
            //Deleting a board game
            IO.println("Trying to delete the new board game");
            rowsAffected = bgDao.deleteBoardGame(6);
            IO.println("Rows affected:"+rowsAffected);
            boardGames = bgDao.getAllBoardGames();
            printAllBoardGames(boardGames);

        } catch (SQLException e) {
            IO.println("SQL exception caught:"+e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     *
     * For convenience as we do this repeatedly in main.
     */
    private  void printAllBoardGames(List<BoardGame> boardGames) {
        IO.println("All board games:");
        for (BoardGame boardGame : boardGames) {
            IO.println(boardGame);
        }
    }
}
