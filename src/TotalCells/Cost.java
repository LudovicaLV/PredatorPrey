package TotalCells;

import java.util.Arrays;

public class Cost {
	
	
	public static double Update(double cost, String nameToCheck){
		if (Arrays.asList(TotalCells.ListAction).contains(nameToCheck)){
			int positionAction = Arrays.asList(TotalCells.ListAction).indexOf(nameToCheck);
			double costToAdd = TotalCells.ListCost[positionAction];
		    double costUp = cost + costToAdd;
		    return costUp;
		}else{
		return cost;
	}
	}

}
