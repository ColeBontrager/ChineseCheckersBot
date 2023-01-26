public class Move {
    //The move class represents a potential move that can be made on a board

    //startPos is the starting position of the piece to be moved
    private Coordinate startPos;
    //endPos is the position where the piece will be moved to
    private Coordinate endPos;
    //score represents what the board resulting from the move is evaluated to by a heuristic function
    private Integer score;

    public Move(Coordinate startPos, Coordinate endPos, int score)
    {
        this.startPos = startPos;
        this.endPos = endPos;
        this.score = score;
    }

    public Move(int score)
    {
        startPos = null;
        endPos = null;
        this.score = score;
    }

    public Move(Coordinate startPos, Coordinate endPos)
    {
        this.startPos = startPos;
        this.endPos = endPos;
        this.score = null;
    }

    public String toString()
    {
        return "["+startPos+", "+endPos+", "+score+"]";
    }

    public Coordinate getStartPos() {return  startPos;}

    public Coordinate getEndPos() {return endPos;}

    public int getScore() {return score;}
}
