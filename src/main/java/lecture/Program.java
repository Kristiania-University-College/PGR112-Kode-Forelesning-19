package lecture;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Program {


    /*
     * DAO - business logic
     */
    private final BoardgameDao bgDao;

    public Program(BoardgameDao bgDao) {
        this.bgDao = bgDao;
    }


    void useOptional(BoardgameDao bgDao, int i) throws SQLException {
        Optional<BoardGame> bg = bgDao.getBoardGame(5);
        bg.ifPresent(boardGame -> IO.println(boardGame.name()));
    }

    void useAutoInc(BoardgameDao bgDao) throws SQLException {
        int generatedId = bgDao.addBoardGame("Citadels", "Strategi", 5, 180, 12, "citadels.jpg");
        IO.println("Generated key:"+generatedId);
        useOptional(bgDao, 5);
    }

    void useTransaction(BoardgameDao bgDao) throws SQLException {
        List<BoardGame> newBoardGames = getBoardGamesToAdd();
        bgDao.addBoardGames(newBoardGames);
    }

    private static List<BoardGame> getBoardGamesToAdd() {
        List<BoardGame> newBoardGames = new ArrayList<>();
        newBoardGames.add(new BoardGame("spill11", "Strategi", 5, 90, 12, "spill6.jpg"));
        newBoardGames.add(new BoardGame("spill12", "Strategi", 5, 90, 12, "spill7.jpg"));
        newBoardGames.add(new BoardGame("spill13", "Strategi", 5, 90, 12, "spill8.jpg"));
        newBoardGames.add(new BoardGame("spill14", "Strategi", 5, 90, 12, "spill9.jpg"));
        newBoardGames.add(new BoardGame("spill15", "Strategi", 5, 90, 12, "spill10.jpg"));
        return newBoardGames;
    }

    public void run() throws SQLException {
        // Optional
        useOptional(bgDao, 5);

        // Auto increment
        useAutoInc(bgDao);

        //transaction
        useTransaction(bgDao);
    }

}
