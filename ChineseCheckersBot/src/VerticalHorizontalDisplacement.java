public class VerticalHorizontalDisplacement implements Heuristic{

    private int weight;

    public VerticalHorizontalDisplacement(int weight)
    {
        this.weight = weight;
    }

    //uses each piece's distance from opposite end of board and distance from center of the board
    //vertical distance is weighted to prioritize moving forward while keeping the pieces closer to the center
    public int evaluateBoard(Board board)
    {
        int score = 0;
        for(Coordinate pos : board.getPlayer1Positions())
        {
            int vertDist = 16 - pos.row;
            int horzDist = Math.abs(8 - pos.col) / 2;
            score -= vertDist * weight + horzDist;
        }
        for(Coordinate pos : board.getPlayer2Positions())
        {
            int vertDist = pos.row;
            int horzDist = Math.abs(8 - pos.col);
            score += vertDist * weight + horzDist;
        }
        return score;
    }
}
