import java.awt.*;
import java.util.*;
import java.util.List;

public class TestBoard {
    public static final int player1WinScore = 50000;
    public static final int player2WinScore = -50000;
    
    public static void main(String[] args)
    {
        //humanPlayers();
        AIPlayers(new Board(), new VerticalHorizontalDisplacement(5), new VerticalHorizontalDisplacement(5), 3, 0);
        //TestGames(10, new VerticalHorizontalDisplacement(5), new VerticalHorizontalDisplacement(5), 3, 80);
        //humanVSAI(new VerticalDisplacement(), 3);
    }

    //TestGames function is used to gather data about time and moves taken from multiple games
    public static void TestGames(int games, Heuristic p1Heuristic, Heuristic p2Heuristic, int depth, int randomMoves)
    {
        int totalMoves = 0;
        int totalTime = 0;
        for(int i = 0; i < games; i++)
        {
            long start = System.currentTimeMillis();
            totalMoves += AIPlayers(new Board(), p1Heuristic, p2Heuristic, depth, randomMoves);
            long end = System.currentTimeMillis();
            totalTime += (end - start);
            System.out.println(end-start);
        }
        System.out.println("Games: "+games);
        System.out.println("Average Time: "+totalTime / games);
        System.out.println("Average Moves: "+totalMoves / games);
    }

    //Plays game between two AIs using minimax with the given heuristic functions
    //depthlimit specifies how deep minimax searches for each AI
    //randomMoves specifies how many random forward moves are taken at the beginning of each game
    //random moves are used to simulate random board states at different stages in the game
    public static int AIPlayers(Board board, Heuristic p1Heuristic, Heuristic p2Heuristic, int depthLimit, int randomMoves)
    {
        Scanner scan = new Scanner(System.in);
        HashSet<List<Coordinate>> p1Table = new HashSet<>();
        p1Table.add(board.getPlayer1Positions());
        HashSet<List<Coordinate>> p2Table = new HashSet<>();
        p2Table.add(board.getPlayer2Positions());
        int moveCount = 0;
        int randomMoveCount = 0;
        while(board.getWinState() == 0 && moveCount < 1000)
        {
            Move move;
            if(randomMoveCount < randomMoves)
            {
                Random rand = new Random();
                List<Move> options = board.getALlValidMoves();
                List<Move> forwardMoves = new ArrayList<>();
                for(Move option : options)
                {
                    if(board.getPlayerTurn() == 1 && option.getStartPos().row < option.getEndPos().row)
                    {
                        forwardMoves.add(option);
                    }
                    else if(board.getPlayerTurn() == 2 && option.getStartPos().row > option.getEndPos().row)
                    {

                    }
                }
                if(!forwardMoves.isEmpty())
                {
                    move = forwardMoves.get(rand.nextInt(forwardMoves.size()));
                }
                else
                {
                    move = options.get(rand.nextInt(options.size()));
                }
                randomMoveCount++;
            }
            else
            {
                //System.out.println("Thinking...");
                if(board.getPlayerTurn() == 1)
                {
                    move = miniMax(board, depthLimit, Integer.MIN_VALUE, Integer.MAX_VALUE, p1Heuristic, p1Table, p2Table);
                }
                else
                {
                    move = miniMax(board, depthLimit, Integer.MIN_VALUE, Integer.MAX_VALUE, p2Heuristic, p1Table, p2Table);
                }
            }

            //System.out.println(move);
            try {
                board = board.makeMove(move.getStartPos(), move.getEndPos());

            } catch (IllegalArgumentException e) {
                System.out.println("Invalid Move");

            }

            if(moveCount % 10 == 0)
            {
            	System.out.println("Move "+ moveCount);
            	board.printBoard();
                //scan.next();
            }

            moveCount++;

            p1Table.add(board.getPlayer1Positions());
            p2Table.add(board.getPlayer2Positions());



        }
        //board.printBoard();
        System.out.println("Total Moves: "+moveCount);
        System.out.println("Winner: "+board.getWinState());

        return moveCount;





    }

    //Takes user input, allowing human player to face AI using minimax with the given heuristic searching to the given depth
    public static void humanVSAI(Heuristic heuristic, int depthLimit)
    {
        Scanner scan = new Scanner(System.in);
        Board board = new Board();
        HashSet<List<Coordinate>> p1Table = new HashSet<>();
        p1Table.add(board.getPlayer1Positions());
        HashSet<List<Coordinate>> p2Table = new HashSet<>();
        p2Table.add(board.getPlayer2Positions());
        boolean exit = false;
        while(!exit)
        {
            board.printBoard();
            if(board.getPlayerTurn() == 1)
            {
                boolean move = false;
                while(!move)
                {
                    System.out.println("Heuristic: "+new VerticalDisplacement().evaluateBoard(board));
                    System.out.println("Moves: ");
                    List<Move> moves = board.getALlValidMoves();
                    for(Move moven : moves)
                    {
                        System.out.print(moven);
                    }
                    System.out.println();
                    System.out.print("Enter start row #: ");
                    int row = scan.nextInt();
                    System.out.print("Enter start col #: ");
                    int col = scan.nextInt();
                    System.out.print("Enter end row #: ");
                    int row2 = scan.nextInt();
                    System.out.print("Enter end col #: ");
                    int col2 = scan.nextInt();


                    try {
                        board = board.makeMove(board.toGridPos(new Coordinate(row, col)), board.toGridPos(new Coordinate(row2, col2)));
                        move = true;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid Move");
                    }
                }
            }
            else
            {
                Move move = miniMax(board, depthLimit, Integer.MIN_VALUE, Integer.MAX_VALUE, heuristic, p1Table, p2Table);
                try {
                    board = board.makeMove(move.getStartPos(), move.getEndPos());

                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid Move");

                }
            }
            p1Table.add(board.getPlayer1Positions());
            p2Table.add(board.getPlayer2Positions());
            if(board.getWinState() != 0)
            {
                exit = true;
            }
            System.out.println();
        }
        if(board.getWinState() == 1)
        {
            System.out.println("You win!");
        }
        else
        {
            System.out.println("The bot wins!");
        }
    }

    //Takes user input to allow to human player to play a game of chinese checkers
    public static void humanPlayers()
    {
        Scanner scan = new Scanner(System.in);
        Board board = new Board();
        boolean exit = false;
        while(!exit)
        {
            board.printBoard();
            boolean move = false;
            while(!move)
            {
                System.out.println("Heuristic: "+new VerticalDisplacement().evaluateBoard(board));
                System.out.println("Moves: ");
                List<Move> moves = board.getALlValidMoves();
                for(Move moven : moves)
                {
                    System.out.print(moven);
                }
                System.out.println();
                System.out.print("Enter start row #: ");
                int row = scan.nextInt();
                System.out.print("Enter start col #: ");
                int col = scan.nextInt();
                System.out.print("Enter end row #: ");
                int row2 = scan.nextInt();
                System.out.print("Enter end col #: ");
                int col2 = scan.nextInt();


                try {
                    board = board.makeMove(board.toGridPos(new Coordinate(row, col)), board.toGridPos(new Coordinate(row2, col2)));
                    move = true;
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid Move");
                }
            }
            if(board.getWinState() != 0)
            {
                exit = true;
            }




            System.out.println();
        }
        System.out.println("Player "+board.getWinState()+" wins!");

    }

    //performs minimax with AlphaBeta pruning on given board to the given depth
    //returns best move as a Move containing start and end position of the move as well as the score of the board determined by the heuristic
    //stops searching when given depth is reached
    public static Move miniMax(Board board, int depth, int alpha, int beta, Heuristic heuristic, HashSet<List<Coordinate>> p1Table, HashSet<List<Coordinate>> p2Table)
    {
        int winState = board.getWinState();
        if(winState == 1)
        {
            return new Move(player1WinScore);
        }
        else if(winState == 2)
        {
            return new Move(player2WinScore);
        }

        if(depth == 0)
        {
            return new Move(heuristic.evaluateBoard(board));
        }
        if(board.getPlayerTurn() == 1)
        {
            return max(board, depth, alpha, beta, heuristic, p1Table, p2Table);
        }
        else
        {
            return min(board, depth, alpha, beta, heuristic, p1Table, p2Table);
        }
    }

    //returns move that results in the maximum evaluation by the given heuristic function
    //will not consider moves that result in previously visited piece configurations for player 1
    public static Move max(Board board, int depth, int alpha, int beta, Heuristic heuristic, HashSet<List<Coordinate>> p1Table, HashSet<List<Coordinate>> p2Table)
    {
        List<Move> options = board.getALlValidMoves();
        Integer bestValue = Integer.MIN_VALUE;
        Move bestMove = new Move(bestValue);
        for(Move option : options)
        {
            Board newBoard = board.makeMove(option.getStartPos(), option.getEndPos());
            if(!p1Table.contains(newBoard.getPlayer1Positions()))
            {
                Move move = miniMax(newBoard, depth-1, alpha, beta, heuristic, p1Table, p2Table);
                Integer value = move.getScore();
                if(value > bestValue)
                {
                    bestValue = value;
                    bestMove = option;
                }
                if(bestValue > alpha)
                {
                    alpha = bestValue;
                }
                if(alpha >= beta)
                {
                    return new Move(bestValue);
                }
            }
            else
            {
                /*
                System.out.println(option);
                if(option.getStartPos().equals(new Coordinate(12, 6)) && option.getEndPos().equals(new Coordinate(13, 5)))
                {
                    for(Coordinate co : newBoard.getPlayer1Positions())
                    {
                        System.out.println(co);
                    }
                    System.out.println(newBoard.getPlayer1Positions().equals(Board.p2Home));
                    System.out.println(p1Table.contains(newBoard.getPlayer1Positions()));

                }

                 */
            }

        }
        return new Move(bestMove.getStartPos(), bestMove.getEndPos(), bestValue);
    }

    //returns move that results in the minimum evaluation by the given heuristic function
    //will not consider moves that result in previously visited piece configurations for player 2
    public static Move min(Board board, int depth, int alpha, int beta, Heuristic heuristic, HashSet<List<Coordinate>> p1Table, HashSet<List<Coordinate>> p2Table)
    {
        List<Move> options = board.getALlValidMoves();
        /*
        board.printBoard();
        for(Move option : options)
        {
            System.out.print(option);
        }
        */
        Integer bestValue = Integer.MAX_VALUE;
        Integer bestDepth = 0;
        Move bestMove = new Move(bestValue);
        for(Move option : options)
        {
            Board newBoard = board.makeMove(option.getStartPos(), option.getEndPos());
            if(!p2Table.contains(newBoard.getPlayer2Positions()))
            {
                Move move = miniMax(newBoard, depth-1, alpha, beta, heuristic, p1Table, p2Table);
                Integer value = move.getScore();
                if(value < bestValue)
                {
                    bestValue = value;
                    bestMove = option;
                }
                if(bestValue < beta)
                {
                    beta = bestValue;
                }
                if(alpha >= beta)
                {
                    return new Move(bestValue);
                }
            }

        }
        return new Move(bestMove.getStartPos(), bestMove.getEndPos(), bestValue);
    }





}
