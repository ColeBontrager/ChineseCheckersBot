import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    //grid: 2D array of Spaces representing chinese checkers board
    private Space grid[][];
    //playerPositions: Stores board coordinates of each player's pieces for use with heuristic functions
    private List<Coordinate> player1Positions;
    private List<Coordinate> player2Positions;
    //grid height and width represent dimensions of array which represents the board
    private static final int gridWidth = 17;
    private static final int gridHeight = 17;
    private static final int[] rowWidths = {1, 2, 3, 4, 5, 6, 7, 8, 9, 8, 7, 6, 5, 4, 3, 2, 1};
    private static final int[] rowOffsets = {8, 7, 6, 5, 4, 3, 2, 1, 0, 1, 2, 3, 4, 5, 6, 7, 8};
    public static final Coordinate[] p1Home = {new Coordinate(3, 5), new Coordinate(3, 7), new Coordinate(3, 9), new Coordinate(3, 11),
                                                new Coordinate(2, 6), new Coordinate(2, 8), new Coordinate(2, 10),
                                                new Coordinate(1, 7), new Coordinate(1, 9), new Coordinate(0, 8),};
    public static final Coordinate[] p2Home = {new Coordinate(13, 5), new Coordinate(13, 7), new Coordinate(13, 9),
                                                new Coordinate(13, 11), new Coordinate(14, 6), new Coordinate(14, 8),
                                                new Coordinate(14, 10), new Coordinate(15, 7), new Coordinate(15, 9), new Coordinate(16, 8)};
    //playerTurn: stores int corresponding to if it is currently player 1 or 2's turn
    private int playerTurn;
    //winState: int representing if game is over, 0 if game is ongoing, 1 or 2 if a player has won
    private int winState;

    //creates fresh board
    public Board()
    {
        playerTurn = 1;
        winState = 0;
        grid = new Space[gridHeight][];
        for(int y = 0; y < 17; y ++)
        {
            grid[y] = new Space[gridWidth];
            int currSpace = 0;
            for(int i = 0; i < rowOffsets[y]; i++)
            {
                grid[y][currSpace] = new Space();
                currSpace++;
            }
            int player = 0;
            int home = 0;
            if(y <= 3)
            {
                player = 1;
                home = 1;
            }
            else if(y >= 13)
            {
                player = 2;
                home = 2;
            }
            for(int i = 0; i < rowWidths[y]; i++)
            {
                grid[y][currSpace] = new Space(player, home);
                currSpace++;
                if(currSpace < gridWidth)
                {
                    grid[y][currSpace] = new Space();
                }
                currSpace++;
            }
            while(currSpace < gridWidth)
            {
                grid[y][currSpace] = new Space();
                currSpace++;
            }
        }
        updatePlayerPositions();
    }

    //creates board from given grid, used by makeMove
    public Board(Space[][] grid, int playerTurn)
    {
        this.grid = grid;
        this.playerTurn = playerTurn;
        updateWinState();
        updatePlayerPositions();
    }

    //Creates board given lists of the positions of each player's pieces
    //Useful for testing specific piece configurations
    public Board(Coordinate[] p1Pos, Coordinate[] p2Pos, int playerTurn)
    {
        this.playerTurn = playerTurn;
        winState = 0;
        grid = new Space[gridHeight][];
        for(int y = 0; y < 17; y ++)
        {
            grid[y] = new Space[gridWidth];
            int currSpace = 0;
            for(int i = 0; i < rowOffsets[y]; i++)
            {
                grid[y][currSpace] = new Space();
                currSpace++;
            }
            int player = 0;
            int home = 0;
            if(y <= 3)
            {
                home = 1;
            }
            else if(y >= 13)
            {
                home = 2;
            }
            for(int i = 0; i < rowWidths[y]; i++)
            {
                grid[y][currSpace] = new Space(player, home);
                currSpace++;
                if(currSpace < gridWidth)
                {
                    grid[y][currSpace] = new Space();
                }
                currSpace++;
            }
            while(currSpace < gridWidth)
            {
                grid[y][currSpace] = new Space();
                currSpace++;
            }
        }
        for(Coordinate pos : p1Pos)
        {
            Coordinate gridPos = pos;
            int row = gridPos.row;
            int col = gridPos.col;
            grid[row][col] = new Space(1, grid[row][col].getHome());

        }
        for(Coordinate pos : p2Pos)
        {
            Coordinate gridPos = pos;
            int row = gridPos.row;
            int col = gridPos.col;
            grid[row][col] = new Space(2, grid[row][col].getHome());
        }
        updatePlayerPositions();
        updateWinState();
    }

    //prints string representation of board, rows are marked to aid user input
    public void printBoard()
    {
        System.out.println("Player Turn: "+playerTurn);

        for(int i = 0; i < gridHeight; i++)
        {
            if(i < 10)
            {
                System.out.print(i+"  ");
            }
            else
            {
                System.out.print(i+" ");
            }

            for(Space j : grid[i])
            {
                System.out.print(j);
            }
            System.out.println();
        }
    }

    public Space[][] getGrid()
    {
        return grid;
    }

    public int getWinState()
    {
        return winState;
    }

    public List<Coordinate> getPlayer1Positions() {return player1Positions;}

    public List<Coordinate> getPlayer2Positions() {return player2Positions;}

    public int getPlayerTurn() {return playerTurn;}

    //iterates through board and stores positions of each players' pieces
    private void updatePlayerPositions()
    {
        List<Coordinate> onePos = new ArrayList<>();
        List<Coordinate> twoPos = new ArrayList<>();
        for(int i = 0; i < gridHeight; i++)
        {
            for(int j = 0; j < gridWidth; j++)
            {
                if(grid[i][j].getPlayer() == 1)
                {
                    onePos.add(new Coordinate(i, j));
                }
                else if(grid[i][j].getPlayer() == 2)
                {
                    twoPos.add(new Coordinate(i, j));
                }
            }
        }
        player1Positions = onePos;
        player2Positions = twoPos;

    }

    //Checks if player 1 or 2 has all pieces in the other players home and updates winState accordingly
    private void updateWinState()
    {
        boolean playerWin = true;
        for(Coordinate home : p1Home)
        {
            if(grid[home.row][home.col].getPlayer() != 2)
            {
                playerWin = false;
            }
        }
        if(playerWin)
        {
            winState = 2;
        }
        else
        {
            playerWin = true;
            for(Coordinate home : p2Home)
            {
                if(grid[home.row][home.col].getPlayer() != 1)
                {
                    playerWin = false;
                }
            }
            if(playerWin)
            {
                winState = 1;
            }
            else
            {
                winState = 0;
            }

        }


    }

    //returns if given Coordinate is a valid position within the grid
    private boolean inBounds(Coordinate gridPos)
    {
        if(gridPos.row < 0 || gridPos.row >= gridHeight || gridPos.col < 0 || gridPos.col >= gridWidth)
        {
            return false;
        }
        return true;
    }

    //Given the row and col of a piece, returns coordinate representation of position on grid
    public Coordinate toGridPos(Coordinate boardPos)
    {
        int rowIndex = boardPos.row;
        int colIndex = boardPos.col;
        for(int i = 0; i < gridWidth; i++)
        {

            if(grid[rowIndex][i].getPlayer() >= 0)
            {
                colIndex--;
                if(colIndex == 0)
                {
                    return new Coordinate(rowIndex, i);
                }
            }
        }
        return null;
    }

    //Given the position of a piece on the grid, returns all empty adjacent positions the piece can move to
    private List<Coordinate> getAdjMoves(Coordinate gridPos)
    {
        List<Coordinate> adjMoves = new ArrayList<Coordinate>();
        int x = gridPos.row;
        int y = gridPos.col;
        adjMoves.add(new Coordinate(x + 1, y + 1));
        adjMoves.add(new Coordinate(x + 1, y - 1));
        adjMoves.add(new Coordinate(x - 1, y + 1));
        adjMoves.add(new Coordinate(x - 1, y - 1));
        adjMoves.add(new Coordinate(x, y + 2));
        adjMoves.add(new Coordinate(x, y - 2));
        for(int i = adjMoves.size() - 1; i >= 0; i--)
        {
            if(!inBounds(adjMoves.get(i)) || grid[adjMoves.get(i).row][adjMoves.get(i).col].getPlayer() != 0)
            {
                adjMoves.remove(i);
            }
        }
        return adjMoves;
    }

    //Given the position of a piece on the grid, returns all valid positions the piece can reach by hopping over adjacent pieces
    private List<Coordinate> getHopMoves(Coordinate gridPos)
    {
        List<Coordinate> hopMoves = new ArrayList<>();
        int y = gridPos.row;
        int x = gridPos.col;
        Coordinate adjMove = new Coordinate(y+1, x+1);
        Coordinate hopMove = new Coordinate(y+2,x+2);
        if(inBounds(new Coordinate(y+1, x+1)) && grid[y+1][x+1].getPlayer() > 0 && inBounds(new Coordinate(y+2, x+2)) && grid[y+2][x+2].getPlayer() == 0)
        {
            hopMoves.add(new Coordinate(y+2, x+2));
        }
        if(inBounds(new Coordinate(y-1, x+1)) && grid[y-1][x+1].getPlayer() > 0 && inBounds(new Coordinate(y-2, x+2)) && grid[y-2][x+2].getPlayer() == 0)
        {
            hopMoves.add(new Coordinate(y-2, x+2));
        }
        if(inBounds(new Coordinate(y-1, x-1)) && grid[y-1][x-1].getPlayer() > 0 && inBounds(new Coordinate(y-2, x-2)) && grid[y-2][x-2].getPlayer() == 0)
        {
            hopMoves.add(new Coordinate(y-2, x-2));
        }
        if(inBounds(new Coordinate(y+1, x-1)) && grid[y+1][x-1].getPlayer() > 0 && inBounds(new Coordinate(y+2, x-2)) && grid[y+2][x-2].getPlayer() == 0)
        {
            hopMoves.add(new Coordinate(y+2, x-2));
        }
        if(inBounds(new Coordinate(y, x-2)) && grid[y][x-2].getPlayer() > 0 && inBounds(new Coordinate(y, x-4)) && grid[y][x-4].getPlayer() == 0)
        {
            hopMoves.add(new Coordinate(y, x-4));
        }
        if(inBounds(new Coordinate(y, x+2)) && grid[y][x+2].getPlayer() > 0 && inBounds(new Coordinate(y, x+4)) && grid[y][x+4].getPlayer() == 0)
        {
            hopMoves.add(new Coordinate(y, x+4));
        }
        return hopMoves;
    }

    //Given the position of a piece on the grid, returns all valid positions for that piece to move to
    public List<Coordinate> validMoves(Coordinate gridPos)
    {
        List<Coordinate> valMoves = new ArrayList<Coordinate>();
        Space currSpace = grid[gridPos.row][gridPos.col];
        if(currSpace.getPlayer() == playerTurn)
        {
            valMoves.addAll(getAdjMoves(gridPos));
            List<Coordinate> hopMoves = getHopMoves(gridPos);
            valMoves.addAll(hopMoves);
            List<Coordinate> processHops = new ArrayList<>();
            processHops.addAll(hopMoves);
            while(!processHops.isEmpty())
            {
                List<Coordinate> newHops = new ArrayList<>();
                for(int i = 0; i < processHops.size(); i++)
                {
                    Coordinate hop = processHops.get(i);
                    processHops.remove(i);
                    newHops.addAll(getHopMoves(hop));
                }
                for(Coordinate hop : newHops)
                {
                    if(!valMoves.contains(hop))
                    {
                        valMoves.add(hop);
                        processHops.add(hop);
                    }
                }
            }
        }

        return valMoves;
    }

    //Returns list of all moves that the player whose turn it is can make
    public List<Move> getALlValidMoves()
    {
        List<Move> moves = new ArrayList<>();
        List<Coordinate> playerPos;
        if(playerTurn == 1)
        {
            playerPos = player1Positions;
        }
        else
        {
            playerPos = player2Positions;
        }
        for(Coordinate piece : playerPos)
        {
            List<Coordinate> options = validMoves(piece);
            for(Coordinate move : options)
            {
                moves.add(new Move(piece, move));
            }
        }
        return moves;
    }

    //Returns new board where the piece at the given start position has been moved to the given end position
    //Throws exception if the given positions do not result in a legal move
    public Board makeMove(Coordinate startPos, Coordinate endPos) throws IllegalArgumentException {
        if(!inBounds(startPos))
        {
            throw new IllegalArgumentException();
        }
        List<Coordinate> validMoves = validMoves(startPos);
        boolean valid = validMoves.contains(endPos);
        if(!valid)
        {
            System.out.println(startPos+" "+endPos);
            printBoard();
            throw new IllegalArgumentException();
        }
        Space[][] newGrid = new Space[gridHeight][];
        for(int i = 0; i < gridHeight; i++)
        {
            newGrid[i] = new Space[gridWidth];
            for(int j = 0; j < gridWidth; j++)
            {
                newGrid[i][j] = new Space(grid[i][j].getPlayer(), grid[i][j].getHome());
            }
        }
        newGrid[startPos.row][startPos.col].setPlayer(0);
        newGrid[endPos.row][endPos.col].setPlayer(playerTurn);
        if(playerTurn == 1)
        {
            return new Board(newGrid, 2);
        }
        else
        {
            return new Board(newGrid, 1);
        }

    }


}


