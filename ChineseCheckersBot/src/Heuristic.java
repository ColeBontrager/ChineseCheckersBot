public interface Heuristic {
    //Heuristic interface enforces that all heuristic classes must have a function which returns an int evaluation of a board
    //Used to pass different heuristic functions to the minimax algorithm
    public int evaluateBoard(Board board);
}
