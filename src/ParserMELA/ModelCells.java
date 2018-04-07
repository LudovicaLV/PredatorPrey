package ParserMELA;


import java.util.ArrayList;
import java.util.HashMap;

import ParserRules.MELArules;

public class ModelCells {
	
	public static int _SIMULATION_ID;
	public static int numberOfRuns = TotalCells.TotalCells.numberOfRuns;

	public static void main(String[] args) throws Exception {
			
     	_SIMULATION_ID = 1;
		for(int i = 1; i <= numberOfRuns; i++)
		{   ParserMELA Parser= new ParserMELA();
		    Parser.parseFromFile("/Users/ludovicaluisavissat/workspacejSSTL/MovementCells5/src/ModelNew3Sema2_both3A3F.mela");
 		    System.out.println("Model parsed correctly."); 
	        System.out.println("Simulation -> " + _SIMULATION_ID ); 
			MELArules Parser2= new MELArules();
			Parser2.parseFromFile("/Users/ludovicaluisavissat/GitHub/MELATotalDistance/Rules.txt");
			_SIMULATION_ID++;
		}	
	}
}




