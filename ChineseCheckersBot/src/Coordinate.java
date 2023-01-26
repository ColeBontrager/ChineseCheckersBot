public class Coordinate {
    //Coordinate is a hashable class that stores the position of a space on the board
    //row stores row of piece
    public int row;
    //col stores column of piece
    public int col;

    public Coordinate(int y, int x)
    {
        row = y;
        col = x;
    }

    @Override
    public int hashCode()
    {
        int hash = row + col * 16;
        if(row < col)
        {
            hash *=-1;
        }

        return hash;
    }

    @Override
    public boolean equals(Object o)
    {
        if(o == this)
        {
            return true;
        }

        if(!(o instanceof Coordinate))
        {
            return false;
        }
        Coordinate other = (Coordinate) o;
        return other.row == this.row && other.col == this.col;
    }

    @Override
    public String toString() {
        return "("+row+", "+col+")";
    }
}
