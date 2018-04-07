package Model;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import Actions.Action;
import Model.SimulatorCells;
import ParserMELA.ParserMELA;

public class GlobalManager {
	static AgentManager agentManager;
	static LocationManager locationManager;
	static ParamManager paramManager;
	
	static String outputFileLog = "/Users/ludovicaluisavissat/anaconda/pyTSA/Output/Log/Output" + "Info";
	
	//new part for rules
	
	public static String actionChosenType;
	public static String actionToTrack;
	
	public static String boundary;
	
	//time
	public static double timeTotal;
	
	//runs
	public static int Runs;
	public static double Step;

	//population
	public static String nameAgentToTrack;
	
	//location
	public static ArrayList<Integer> LocToTrack;
	
	//actionCount
	public static String nameActionToCount;
	public static int valueToCount;	
	public static int valueForLoop;
	
	//for specific traces
	public static String SpecificType;
	
	//end new part
	
	public static void init(){
			agentManager = new AgentManager();
			locationManager = new LocationManager();
			paramManager = new ParamManager();		
		}		

	public static ParamManager getParamManager() {
			return paramManager;
			}
	
	public static LocationManager getLocationManager() {
		return locationManager;
		}
	
	public static AgentManager getAgentManager() {
		return agentManager;
		}
	
	public static void PrintHashMap (HashMap<String, Double> Map){
		for (String key : Map.keySet()) {
			System.out.println("The value of parameter " + key + " is " + Map.get(key));
		}
	}	
	
	public static void PrintNames (ArrayList<String> List){
		System.out.println("We have the following agents:");
		for (String name : List) {
			System.out.println(name);
		}
	}
	
	public static void PrintActions (ArrayList<Agent> List){
		System.out.println("and they can perform these actions:");
		for (Agent agent : List) {
			ArrayList<Action> ListActions = agent.getActionList();
			System.out.println(agent.getName() + ":");
			for (Actions.Action action : ListActions) {
			System.out.println("- " + action.getName() + " ");
			}
		}
	}
	
	public static void PrintLocations (ArrayList<ArrayList<Integer>> List){
		System.out.println("We have the following locations:");
		for (int i=0; i < List.size(); i++){
			System.out.print(List.get(i).get(0));
			for (int j=1; j < List.get(i).size(); j++){
				System.out.print("," + List.get(i).get(j));
			}
			System.out.println();
		}			
	}
	
	public static void PrintInitCondition(){
		System.out.println("The initial number of each type of agent in each location:");
		for (int i=0; i < GlobalManager.getAgentManager().AgentNames.size(); i++){
			for (int j=0; j < GlobalManager.getLocationManager().AllLoc.size(); j++){
				System.out.print("The initial number of agent " + GlobalManager.getAgentManager().AgentNames.get(i) + " in location " + GlobalManager.getLocationManager().AllLoc.get(j).get(0));
				for (int k=1; k < GlobalManager.getLocationManager().AllLoc.get(j).size(); k++){
					System.out.print("," + GlobalManager.getLocationManager().AllLoc.get(j).get(k));
				}
				System.out.println(" is " + GlobalManager.getAgentManager().GlobalMatrix[i][j]);
			}
		}
	}
	
	
	public static void PrintNoZeroInitCondition(){
		System.out.println("Just the no-zero initial number:");
		for (int i=0; i < GlobalManager.getAgentManager().AgentNames.size(); i++){
			for (int j=0; j < GlobalManager.getLocationManager().AllLoc.size(); j++){
				if (GlobalManager.getAgentManager().GlobalMatrix[i][j] != 0){
				System.out.print("The initial number of agent " + GlobalManager.getAgentManager().AgentNames.get(i) + " in location (" + GlobalManager.getLocationManager().AllLoc.get(j).get(0));
				for (int k=1; k < GlobalManager.getLocationManager().AllLoc.get(j).size(); k++){
					System.out.print("," + GlobalManager.getLocationManager().AllLoc.get(j).get(k));
				}
				System.out.println(") is " + GlobalManager.getAgentManager().GlobalMatrix[i][j]);}
			}
		}
		
//		for (int i=0; i < GlobalManager.getAgentManager().AgentNames.size(); i++){
//			for (int j=0; j < GlobalManager.getLocationManager().AllLoc.size(); j++){
//				if (GlobalManager.getAgentManager().GlobalMatrix[i][j] != 0){
//				System.out.print("\"I(" + GlobalManager.getLocationManager().AllLoc.get(j).get(0));
//				for (int k=1; k < GlobalManager.getLocationManager().AllLoc.get(j).size(); k++){
//					System.out.print("," + GlobalManager.getLocationManager().AllLoc.get(j).get(k) + ")\", ");
//				}}
//			}
//		}	
	}
		
		
		
	public static void ForPlotting(){		
		for (int i=0; i < GlobalManager.getAgentManager().AgentNames.size(); i++){
			for (int j=0; j < GlobalManager.getLocationManager().AllLoc.size(); j++){
				if (GlobalManager.getAgentManager().GlobalMatrix[i][j] != 0){
				System.out.print(GlobalManager.getLocationManager().AllLoc.get(j).get(0) + ", ");
			}
		}}
		System.out.println(" ");
		
		for (int i=0; i < GlobalManager.getAgentManager().AgentNames.size(); i++){
			for (int j=0; j < GlobalManager.getLocationManager().AllLoc.size(); j++){
				if (GlobalManager.getAgentManager().GlobalMatrix[i][j] != 0){
				//System.out.print(GlobalManager.getLocationManager().AllLoc.get(j).get(0) + " ");
				for (int k=1; k < GlobalManager.getLocationManager().AllLoc.get(j).size(); k++){
					System.out.print(GlobalManager.getLocationManager().AllLoc.get(j).get(k) + ", ");
				}}}}
		System.out.println(" ");
		
		for (int i=0; i < 10; i++){
			for (int j=0; j < 10; j++){
			System.out.print(i +", ");
		}}
		System.out.println(" ");
		
		for (int i=0; i < 10; i++){
			for (int j=0; j < 10; j++){
			System.out.print(j +", ");				
			}}
		System.out.println(" ");
	
	}
		
	public static boolean isInteger(String s) {
		try
		{
		Integer.parseInt(s);
		// s is a valid integer
		return true;
		}
		catch (NumberFormatException ex)
		{System.out.println("The initial value has to be integer");
		return false;
		}
		}
	
	public static ArrayList<Integer> createListName (int name){
		 ArrayList<Integer> ListName = new ArrayList<Integer>();
		 ListName.add(name);
		 return ListName;
		}
	
	
	public static ArrayList<Integer> createListOneD (int x){
		 ArrayList<Integer> ListOneD = new ArrayList<Integer>();
		 ListOneD.add(x);
		 return ListOneD;
		}
	
		public static ArrayList<Integer> createListTwoD (int x, int y){
		 ArrayList<Integer> ListTwoD = new ArrayList<Integer>();
		 ListTwoD.add(x);
		 ListTwoD.add(y);
		 return ListTwoD;
		}

		public static ArrayList<Integer> createListThreeD (int x, int y, int z){
		 ArrayList<Integer> ListThreeD = new ArrayList<Integer>();
		 ListThreeD.add(x);
		 ListThreeD.add(y);
		 ListThreeD.add(z);
		 return ListThreeD;
		}

		public static ArrayList<Integer> createListZero (){
		ArrayList<Integer> Zero = new ArrayList<Integer>();
		Zero.add(0);
		return Zero;
		}	 
		

		public static ArrayList<ArrayList<Integer>> createAllLocOneD(int x){
		 for (int i = 0; i < x; i++){
			 ArrayList<Integer> newList = createListOneD(i);	 
			 GlobalManager.getLocationManager().AllLoc.add(newList);
		 }
		 return GlobalManager.getLocationManager().AllLoc;
		}

		public static ArrayList<ArrayList<Integer>> createAllLocTwoD(int x, int y){
		 for (int i = 0; i < x; i++){
			   for (int j = 0; j < y; j++){
				   ArrayList<Integer> newList = createListTwoD(i, j);
				   GlobalManager.getLocationManager().AllLoc.add(newList);
			   }
		    }
		 return GlobalManager.getLocationManager().AllLoc;
		}

		public static ArrayList<ArrayList<Integer>> createAllLocThreeD(int x, int y, int z){
		 for (int i = 0; i < x; i++){
			   for (int j = 0; j < y; j++){
				   for (int k = 0; k < z; k++){
				   ArrayList<Integer> newList = createListThreeD(i,j,k);
				   GlobalManager.getLocationManager().AllLoc.add(newList);
			   }
		    }
		 }
		 return GlobalManager.getLocationManager().AllLoc;
		}	
	   
		
		public static int SumPopulation (String name){
			int sum = 0;
			for (int j = 0; j < GlobalManager.getAgentManager().GlobalMatrix[0].length; j++){
		         sum = sum + GlobalManager.getAgentManager().GlobalMatrix[GlobalManager.getAgentManager().MatrixAgent.get(name)][j];
		        }
			return sum;
		    }
		
		public static int EntryPopLoc (String name, ArrayList<Integer> loc){
			int Value = GlobalManager.getAgentManager().GlobalMatrix[GlobalManager.getAgentManager().MatrixAgent.get(name)][GlobalManager.getLocationManager().MatrixLoc.get(loc)];
			return Value;
		    }
		
		
	 //new code - for rules
		public static String GetAction(){
			return actionToTrack;
		}
		
		public static double GetTime(){
			return timeTotal;
		}
		
		public static boolean WhileMethod(){
			if(boundary == "#Time"){
				 if (SimulatorCells.getTime() < timeTotal){return true;}
		         else{return false;}}
			else{
			if(boundary == "#Population"){
				if ((SumPopulation(nameAgentToTrack) > 0) && SimulatorCells.getTime() < 1000 ){return true;}
		    	else{return false;}}
			else{
				if(boundary == "#Pop_loc"){
					if ((EntryPopLoc(nameAgentToTrack, LocToTrack) > 0) && SimulatorCells.getTime() < 1000 ){return true;}
			    	else{return false;}}
			else{
				if ((valueForLoop < valueToCount) || SimulatorCells.getTime() < 1000){return true;}
		    	else{return false;}    
				}}}}
		
}
					

	    
	    