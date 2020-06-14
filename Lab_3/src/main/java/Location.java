import java.util.Objects;

public class Location
{
    public int xCoord;

    public int yCoord;

    public Location(int x, int y)
    {
        xCoord = x;
        yCoord = y;
    }

    public Location()
    {
        this(0, 0);
    }

    @Override
    public boolean equals(Object loc) {
    	Location location = (Location) loc;

    	if(location.xCoord == this.xCoord && location.yCoord == this.yCoord)
    		return true;

    	return false;
    }

    @Override
    public int hashCode() {
    	return Objects.hash(xCoord, yCoord);
    }
}
