package Model;

import java.util.ArrayList;

public class Location {
    ArrayList<Integer> LocationName = new ArrayList<>();    
    
    public Location(ArrayList<Integer> LocName) {
    	this.LocationName = LocName;
    }
	
    public ArrayList<Integer> getLocName(){
		return LocationName;
	}
    
   //add LocName to AllLoc
   public void addLocName(Location Loc){
	   GlobalManager.getLocationManager().AllLoc.add(Loc.getLocName());
   }
   
}
   
