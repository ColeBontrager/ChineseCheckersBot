public class VerticalDisplacement implements Heuristic {

    //uses each piece's distance from opposite end of board
    public int evaluateBoard(Board board)
    {
        int score = 0;
        for(Coordinate pos : board.getPlayer1Positions())
        {
            score -= 16 - pos.row;
        }
        for(Coordinate pos : board.getPlayer2Positions())
        {
            score += pos.row;
        }
        return score;
    }
}
