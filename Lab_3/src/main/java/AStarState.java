import java.util.HashMap;
import java.util.Map;

public class AStarState
{
    private Map2D map;

    private HashMap<Location, Waypoint> open_waypoint_list = new HashMap<Location, Waypoint>();
    private HashMap<Location, Waypoint> close_waypoint_list = new HashMap<Location, Waypoint>();

    public AStarState(Map2D map)
    {
        if (map == null)
            throw new NullPointerException("map cannot be null");

        this.map = map;
    }

    public Map2D getMap()
    {
        return map;
    }

    public Waypoint getMinOpenWaypoint()
    {
    	Waypoint output = (Waypoint)open_waypoint_list.values().toArray()[0];
    	float cost = output.getTotalCost();

        if(numOpenWaypoints() != 0) {
        	for(Map.Entry<Location, Waypoint> entry : open_waypoint_list.entrySet()) {

        		if(cost >= entry.getValue().getTotalCost()) {
        			output = entry.getValue();
        			cost = output.getTotalCost();
        		}
        	}
        }
        return output;
    }

    public boolean addOpenWaypoint(Waypoint newWP)
    {
    	Waypoint oldWP = open_waypoint_list.get(newWP.getLocation());
    	if(oldWP == null) {
    		open_waypoint_list.put(newWP.getLocation(), newWP);
    		return true;
    	}else {
    		if(oldWP.getPreviousCost() > newWP.getPreviousCost()) {
    			open_waypoint_list.put(newWP.getLocation(), newWP);
    		}
    	}
    	return false;
    }

    public int numOpenWaypoints()
    {
    	return open_waypoint_list.size();
    }

    public void closeWaypoint(Location loc)
    {
        Waypoint stream = open_waypoint_list.remove(loc);
        close_waypoint_list.put(loc, stream);

    }

    public boolean isLocationClosed(Location loc)
    {
        if(close_waypoint_list.get(loc) != null) {
        	return true;
        }
        return false;
    }
}
