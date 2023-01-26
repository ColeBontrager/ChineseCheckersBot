public class Space {
    //Space class represents one hole on the board
    //player represents the player the space belongs to
    //player is set to 0 if the space is empty
    //home represents the home the space belongs to
    //home is set to 0 if the space is not a part of a player's home
    private int player;
    private int home;

    public Space(int player, int home)
    {
        this.player = player;
        this.home = home;

    }

    public Space()
    {
        home = -1;
        player = -1;
    }

    public int getPlayer()
    {
        return player;
    }

    public int getHome()
    {
        return home;
    }

    public void setPlayer(int player)
    {
        this.player = player;
    }

    public String toString()
    {
        if(player >= 0)
        {
            return ""+player;
        }
        else
        {
            return " ";
        }
    }
}
