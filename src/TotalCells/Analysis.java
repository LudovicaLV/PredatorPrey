package TotalCells;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Analysis {

	public static int numberRuns = 100;
	public static ArrayList<Double> timeValues = new ArrayList<>();
	public static ArrayList<ArrayList<Integer>> separateIntValues = new ArrayList<>();
	
	public static ArrayList<ArrayList<ArrayList<Integer>>> AllValues = new ArrayList<>();
	public static ArrayList<Double> AllTime = new ArrayList<>();
	
	public static ArrayList<ArrayList<Double>> FinalMean = new ArrayList<>();
	
    public static double timeStep = 0.1;
    
	public static void main(String[] args) throws Exception {

	       for (int k=1; k<=numberRuns; k++){
	    	   
	   		ArrayList<Double> timeValuesDiscrete = new ArrayList<>();
	   		ArrayList<ArrayList<Integer>> separateIntValuesDiscrete = new ArrayList<>();
	   					
			BufferedReader buf = new BufferedReader(
					new FileReader("/Users/ludovicaluisavissat/Desktop/SIR/SIR_move10faster/OutputSIR3_10.mela" + k + "_agents.txt"));
			ArrayList<String> values = new ArrayList<>();
			String lineJustFetched = null;
			String[] valuesArray;
			while (true) {
				lineJustFetched = buf.readLine();
				if (lineJustFetched == null) {
					break;
				} else {
					valuesArray = lineJustFetched.split("\t");
					for (String each : valuesArray) {
						if (!"".equals(each)) {
							values.add(each);
						}
					}
				}
			}
			buf.close();
			
			
			// time

			for (int i = 0; i < values.size(); i++) {
				String[] newline = values.get(i).split(" ");
				double timeval = Double.parseDouble(newline[0]);
				timeValues.add(timeval);
			}
		
			
//			int entry = timeValues.size() - 1;
//			Double timeEnd = timeValues.get(entry);
			
					
			// popValues
			for (int i = 0; i < values.size(); i++) {
				String[] newline = values.get(i).split(" ");
				ArrayList<Integer> newone = new ArrayList<>();
				for (int j = 1; j < newline.length; j++) {
					int intvalue = Integer.parseInt(newline[j]);
					newone.add(intvalue);
				}
				separateIntValues.add(newone);
			}	
	       
	       double timeToCheck = 0.1;
	       
//	       ArrayList<Integer> newToAddInit = new ArrayList<>();
//	       newToAddInit.add(100);
//	       newToAddInit.add(100);
//		   separateIntValuesDiscrete.add(newToAddInit); 
	       
	       for (int i=0; i< separateIntValues.size(); i++){ 
	    	   if (timeValues.get(i) > timeToCheck){
	    		   ArrayList<Integer> newToAdd = new ArrayList<>();
	    		   newToAdd.add(separateIntValues.get(i).get(0));
	    		   newToAdd.add(separateIntValues.get(i).get(1));
	    		   newToAdd.add(separateIntValues.get(i).get(2));
	    		   newToAdd.add(separateIntValues.get(i).get(3));
//	    		   double timeToAdd = 0.1 * timeValuesDiscrete.size();
//	    		   timeValuesDiscrete.add(timeToAdd);
	    		   separateIntValuesDiscrete.add(newToAdd);
	    		   timeToCheck = timeToCheck + timeStep;
	    	   }
	       }
	       
	       AllValues.add(separateIntValuesDiscrete);
	       
	   	}
	       
	       System.out.println(AllValues.size() + " " + AllValues.get(0).size() + " " + AllValues.get(0).get(0).size());
	       
	       for (int j=0; j <= 99; j++){
	    	   double value = j * 0.1;
	    	   AllTime.add(value);
	       }
	             
	       
	       for (int i=0; i < AllTime.size(); i++){
		       int Total1 = 0;
		       int Total2 = 0;
		       int Total3 = 0;
		       int Total4 = 0;
	    	   for (int j=0; j < AllValues.size(); j++){
	    		   Total1 = Total1 + AllValues.get(j).get(i).get(0);
	    		   Total2 = Total2 + AllValues.get(j).get(i).get(1);
	    		   Total3 = Total3 + AllValues.get(j).get(i).get(2);
	    		   Total4 = Total4 + AllValues.get(j).get(i).get(3);}
	    	   double Total1double = Total1 * 1.0;
	    	   double Total2double = Total2 * 1.0;
	    	   double Total3double = Total3 * 1.0;
	    	   double Total4double = Total4 * 1.0;
	    	   double Total1Final = Total1double/numberRuns;
	    	   double Total2Final = Total2double/numberRuns;
	    	   double Total3Final = Total3double/numberRuns;
	    	   double Total4Final = Total4double/numberRuns;
	    	   ArrayList<Double> toAdd = new ArrayList<>();
	    	   toAdd.add(Total1Final);
	    	   toAdd.add(Total2Final);
	    	   toAdd.add(Total3Final);
	    	   toAdd.add(Total4Final);
	    	   FinalMean.add(toAdd);
	       }
	       
	       for (int i=0; i < AllTime.size(); i++){
	    	   System.out.println(AllTime.get(i) + " " + FinalMean.get(i).get(0) + " " + FinalMean.get(i).get(1) + " " + FinalMean.get(i).get(2) + " " + FinalMean.get(i).get(3));
	       }
	       
	       
	}
	
}
