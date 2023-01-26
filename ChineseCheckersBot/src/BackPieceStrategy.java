public class BackPieceStrategy implements Heuristic{
    //uses square of each piece's distance to opposite end of board to prioritize pieces in the back
    public int evaluateBoard(Board board)
    {
        int score = 0;
        for(Coordinate pos : board.getPlayer1Positions())
        {
            int distance = 16 - pos.row;
            score -= distance * distance;
        }
        for(Coordinate pos : board.getPlayer2Positions())
        {
            int distance = pos.row;
            score += distance * distance;
        }
        return score;
    }
}
