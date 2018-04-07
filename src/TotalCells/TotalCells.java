package TotalCells;

import java.util.ArrayList;
import java.util.Random;

import ParserMELA.ParserMELA;
import ParserRules.MELArules;

public class TotalCells {
	
	public static int numberOfRuns = 10;
	public static int simulationTime = 10;
	
	public static String Project = "/Users/ludovicaluisavissat/workspacejSSTL/PreyPredator/src/";
	public static String MELAmodel = "PP10.mela";
	
	public static int _SIMULATION_ID;
	
	//cost analysis
	public static String[] ListAction = {};
	public static double[] ListCost = {};
	
	//agents to count
	public static String[] AgentName = {"P", "Q"};
	
	public static void main(String[] args) throws Exception {
     	_SIMULATION_ID = 1;
		for(int i = 1; i <= numberOfRuns; i++)
		{   ParserMELA Parser= new ParserMELA();
		    Parser.parseFromFile(TotalCells.Project + TotalCells.MELAmodel);
 		    System.out.println("Model parsed correctly."); 
	        System.out.println("Simulation -> " + _SIMULATION_ID ); 
			MELArules Parser2= new MELArules();
			Parser2.parseFromFile(TotalCells.Project + "Rules.txt");
			_SIMULATION_ID++;
		}	
	}


}

