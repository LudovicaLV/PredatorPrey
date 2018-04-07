package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class LocationManager {
	public ArrayList<ArrayList<Integer>> AllLoc = new ArrayList<ArrayList<Integer>>();	
	
	//Graph, OneD, TwoD, ThreeD
    public HashMap<ArrayList<Integer>, ArrayList<ArrayList<Integer>>> NeighGraph = new HashMap<>();		    		
    public HashMap<ArrayList<Integer>, ArrayList<ArrayList<Integer>>> NeighOneD = new HashMap<>();		    	    		  
    public HashMap<ArrayList<Integer>, ArrayList<ArrayList<Integer>>> NeighTwoD = new HashMap<>();		    	    		  
    public HashMap<ArrayList<Integer>, ArrayList<ArrayList<Integer>>> NeighThreeD = new HashMap<>();		    	    		       
   
    
    //public HashMap<ArrayList<Integer>,ArrayList<ArrayList<Integer>>> globalMap  = new HashMap<ArrayList<Integer>,ArrayList<ArrayList<Integer>>>();       
	
    public int BracketsCounter;	
	public String SpatialSt = new String();
	
	public int OneD;	
	
	public int TwoDx;	
	public int TwoDy;	
	
	public int ThreeDx;	
	public int ThreeDy;	
	public int ThreeDz;	
	
	public String boundary;
	 		    
	public ArrayList<ArrayList<Integer>> getAllLoc(){
    	return AllLoc;
    }  

    //Hashmap name - Matrix position
	
	public HashMap<ArrayList<Integer>, Integer> MatrixLoc = new HashMap<ArrayList<Integer>, Integer>();    

    public HashMap<ArrayList<Integer>, Integer> MatrixLocationCreation() {
    	for(int i=0; i< AllLoc.size(); i++){
    	   ArrayList<Integer> name = AllLoc.get(i);
     	   MatrixLoc.put(name,i);   	  
    	   }
     	return MatrixLoc;
    }
    
	//Hashmap name - neigh (given differently if graph or grid)	
	
	public ArrayList<ArrayList<Integer>> getNeigh(ArrayList<Integer> LocName, int d){
	    if(SpatialSt == "Graph") {
	   		return GlobalManager.getLocationManager().getNeighGraph(LocName, d);	    		
	   	}else{if (SpatialSt == "OneD"){
          return GlobalManager.getLocationManager().getNeighOneD(LocName, d);		    	    		  
	   	}else{if (SpatialSt == "TwoD"){
    		return GlobalManager.getLocationManager().getNeighTwoD(LocName, d);	
	    }else{
	   		return GlobalManager.getLocationManager().getNeighThreeD(LocName, d);			    	    		  
	    }}}}
	
	public ArrayList<ArrayList<Integer>> getNeighBouncing(ArrayList<Integer> LocName, int d){
	    if(SpatialSt == "Graph") {
	   		return GlobalManager.getLocationManager().getNeighGraph(LocName, d);	    		
	   	}else{if (SpatialSt == "OneD"){
          return GlobalManager.getLocationManager().getNeighOneDBouncing(LocName, d);		    	    		  
	   	}else{if (SpatialSt == "TwoD"){
    		return GlobalManager.getLocationManager().getNeighTwoDBouncing(LocName, d);	
	    }else{
	   		return GlobalManager.getLocationManager().getNeighThreeDBouncing(LocName, d);			    	    		  
	    }}}}
	
	public ArrayList<ArrayList<Integer>> getNeighAbsorbing(ArrayList<Integer> LocName, int d){
	    if(SpatialSt == "Graph") {
	   		return GlobalManager.getLocationManager().getNeighGraph(LocName, d);	    		
	   	}else{if (SpatialSt == "OneD"){
          return GlobalManager.getLocationManager().getNeighOneDAbsorbing(LocName, d);		    	    		  
	   	}else{if (SpatialSt == "TwoD"){
    		return GlobalManager.getLocationManager().getNeighTwoDAbsorbing(LocName, d);	
	    }else{
	   		return GlobalManager.getLocationManager().getNeighThreeDAbsorbing(LocName, d);			    	    		  
	    }}}}

	public ArrayList<ArrayList<Integer>> getNeighOneD(ArrayList<Integer> LocName, int d){
		ArrayList<ArrayList<Integer>> Neigh1 = new ArrayList<ArrayList<Integer>>();		
		for (int i=1; i <= d; i++){
		int neigh1 = ((LocName.get(0) + i) % OneD);			
		ArrayList<Integer> neigh1_list = GlobalManager.createListName(neigh1);
		if (!Neigh1.contains(neigh1_list)){
		Neigh1.add(neigh1_list);}}				
		for (int i=1; i <= d; i++){
			int neigh2 = ((LocName.get(0) - i + OneD) % OneD);			
			ArrayList<Integer> neigh2_list = GlobalManager.createListName(neigh2);
			if (!Neigh1.contains(neigh2_list)){
			Neigh1.add(neigh2_list);}}	
        return Neigh1;	
	   }		

	public ArrayList<ArrayList<Integer>> getNeighTwoD(ArrayList<Integer> LocName, int d){
		ArrayList<ArrayList<Integer>> Neigh2 = new ArrayList<ArrayList<Integer>>(); 		
		for (int i=0; i <= d; i++){
			for (int j=0; j <= d - i; j++){
			ArrayList<Integer> Neigh2_1 = new ArrayList<Integer>();	
			Neigh2_1.add((LocName.get(0) + i) % TwoDx);
			Neigh2_1.add((LocName.get(1) + j) % TwoDy);
			if (!Neigh2.contains(Neigh2_1)){
			Neigh2.add(Neigh2_1); 				
		}}}		
		for (int i=0; i <= d; i++){
			for (int j=0; j <= d - i; j++){
			ArrayList<Integer> Neigh2_1 = new ArrayList<Integer>();	
			Neigh2_1.add((LocName.get(0) - i + TwoDx) % TwoDx);
			Neigh2_1.add((LocName.get(1) + j) % TwoDy);
			if (!Neigh2.contains(Neigh2_1)){
			Neigh2.add(Neigh2_1); 				
		}}}		
		for (int i=0; i <= d; i++){
			for (int j=0; j <= d - i; j++){
			ArrayList<Integer> Neigh2_1 = new ArrayList<Integer>();	
			Neigh2_1.add((LocName.get(0) - i + TwoDx) % TwoDx);
			Neigh2_1.add((LocName.get(1) - j + TwoDy) % TwoDy);
			if (!Neigh2.contains(Neigh2_1)){
			Neigh2.add(Neigh2_1); 				
		}}}		
		for (int i=0; i <= d; i++){
			for (int j=0; j <= d - i; j++){
			ArrayList<Integer> Neigh2_1 = new ArrayList<Integer>();	
			Neigh2_1.add((LocName.get(0) + i) % TwoDx);
			Neigh2_1.add((LocName.get(1) - j + TwoDy) % TwoDy);
			if (!Neigh2.contains(Neigh2_1)){
			Neigh2.add(Neigh2_1); 				
		}}}		
		Neigh2.remove(LocName);	 
        return Neigh2;	
	   }
			
	public ArrayList<ArrayList<Integer>> getNeighThreeD(ArrayList<Integer> LocName, int d){
		ArrayList<ArrayList<Integer>> Neigh3 = new ArrayList<ArrayList<Integer>>(); 	
		  for (int i=0; i <= d; i++){
			for (int j=0; j <= d - i; j++){
				for (int z=0; z <= d - i - j ; z++){
				ArrayList<Integer> Neigh3_1 = new ArrayList<Integer>();
				Neigh3_1.add((LocName.get(0) + i) % ThreeDx);
				Neigh3_1.add((LocName.get(1) + j) % ThreeDy);	
				Neigh3_1.add((LocName.get(2) + z) % ThreeDz);	
				if (!Neigh3.contains(Neigh3_1)){
				Neigh3.add(Neigh3_1); }				
		}}}		
		  for (int i=0; i <= d; i++){
				for (int j=0; j <= d - i; j++){
					for (int z=0; z <= d - i - j ; z++){
				ArrayList<Integer> Neigh3_1 = new ArrayList<Integer>();
				Neigh3_1.add((LocName.get(0) + i) % ThreeDx);
				Neigh3_1.add((LocName.get(1) + j) % ThreeDy);	
				Neigh3_1.add((LocName.get(2) - z + ThreeDz) % ThreeDz);	
				if (!Neigh3.contains(Neigh3_1)){
				Neigh3.add(Neigh3_1); }				
		}}}
		  for (int i=0; i <= d; i++){
				for (int j=0; j <= d - i; j++){
					for (int z=0; z <= d - i - j ; z++){
				ArrayList<Integer> Neigh3_1 = new ArrayList<Integer>();
				Neigh3_1.add((LocName.get(0) + i) % ThreeDx);
				Neigh3_1.add((LocName.get(1) - j + ThreeDy) % ThreeDy);	
				Neigh3_1.add((LocName.get(2) - z + ThreeDz) % ThreeDz);	
				if (!Neigh3.contains(Neigh3_1)){
				Neigh3.add(Neigh3_1); }			
		}}}
		  for (int i=0; i <= d; i++){
				for (int j=0; j <= d - i; j++){
					for (int z=0; z <= d - i - j ; z++){
				ArrayList<Integer> Neigh3_1 = new ArrayList<Integer>();
				Neigh3_1.add((LocName.get(0) + i) % ThreeDx);
				Neigh3_1.add((LocName.get(1) - j + ThreeDy) % ThreeDy);	
				Neigh3_1.add((LocName.get(2) + z) % ThreeDz);	
				if (!Neigh3.contains(Neigh3_1)){
				Neigh3.add(Neigh3_1); }			
		}}}
		  
		  for (int i=0; i <= d; i++){
			for (int j=0; j <= d - i; j++){
				for (int z=0; z <= d - i - j ; z++){
				ArrayList<Integer> Neigh3_1 = new ArrayList<Integer>();
				Neigh3_1.add((LocName.get(0) - i + ThreeDx) % ThreeDx);
				Neigh3_1.add((LocName.get(1) + j) % ThreeDy);	
				Neigh3_1.add((LocName.get(2) + z) % ThreeDz);	
				if (!Neigh3.contains(Neigh3_1)){
				Neigh3.add(Neigh3_1); }				
		}}}		
		  for (int i=0; i <= d; i++){
				for (int j=0; j <= d - i; j++){
					for (int z=0; z <= d - i - j ; z++){
				ArrayList<Integer> Neigh3_1 = new ArrayList<Integer>();
				Neigh3_1.add((LocName.get(0) - i + ThreeDx) % ThreeDx);
				Neigh3_1.add((LocName.get(1) + j) % ThreeDy);	
				Neigh3_1.add((LocName.get(2) - z + ThreeDz) % ThreeDz);	
				if (!Neigh3.contains(Neigh3_1)){
				Neigh3.add(Neigh3_1); }				
		}}}
		  for (int i=0; i <= d; i++){
				for (int j=0; j <= d - i; j++){
					for (int z=0; z <= d - i - j ; z++){
				ArrayList<Integer> Neigh3_1 = new ArrayList<Integer>();
				Neigh3_1.add((LocName.get(0) - i + ThreeDx) % ThreeDx);
				Neigh3_1.add((LocName.get(1) - j + ThreeDy) % ThreeDy);	
				Neigh3_1.add((LocName.get(2) - z + ThreeDz) % ThreeDz);	
				if (!Neigh3.contains(Neigh3_1)){
				Neigh3.add(Neigh3_1); }			
		}}}
		  for (int i=0; i <= d; i++){
				for (int j=0; j <= d - i; j++){
					for (int z=0; z <= d - i - j ; z++){
				ArrayList<Integer> Neigh3_1 = new ArrayList<Integer>();
				Neigh3_1.add((LocName.get(0) - i + ThreeDx) % ThreeDx);
				Neigh3_1.add((LocName.get(1) - j + ThreeDy) % ThreeDy);	
				Neigh3_1.add((LocName.get(2) + z) % ThreeDz);	
				if (!Neigh3.contains(Neigh3_1)){
				Neigh3.add(Neigh3_1); }			
		}}}		  
		Neigh3.remove(LocName);	 
        return Neigh3;	
	   }
	
	
	public ArrayList<ArrayList<Integer>> getNeighOneDBouncing(ArrayList<Integer> LocName, int d){
		ArrayList<ArrayList<Integer>> Neigh1 = new ArrayList<ArrayList<Integer>>();		
		for (int i=1; i <= d; i++){
		int neigh1 = ((LocName.get(0) + i));			
		ArrayList<Integer> neigh1_list = GlobalManager.createListName(neigh1);
		if ((!Neigh1.contains(neigh1_list)) && ((LocName.get(0) + i) < OneD)){
		Neigh1.add(neigh1_list);}}				
		for (int i=1; i <= d; i++){
			int neigh2 = (LocName.get(0) - i);			
			ArrayList<Integer> neigh2_list = GlobalManager.createListName(neigh2);
			if ((!Neigh1.contains(neigh2_list)) && ((LocName.get(0) - i) >= 0)){
			Neigh1.add(neigh2_list);}}	
        return Neigh1;	
	   }		

	public ArrayList<ArrayList<Integer>> getNeighTwoDBouncing(ArrayList<Integer> LocName, int d){
		ArrayList<ArrayList<Integer>> Neigh2 = new ArrayList<ArrayList<Integer>>(); 		
		for (int i=0; i <= d; i++){
			int j = d - i;
			ArrayList<Integer> Neigh2_1 = new ArrayList<Integer>();	
			Neigh2_1.add(LocName.get(0) + i);
			Neigh2_1.add(LocName.get(1) + j);
			if ((!Neigh2.contains(Neigh2_1)) && (LocName.get(0) + i) >= 0 && ((LocName.get(0) + i) < TwoDx) && ((LocName.get(1) + j) >= 0) && ((LocName.get(1) + j) < TwoDy)){
			Neigh2.add(Neigh2_1); 				
		}}	
		for (int i=0; i <= d; i++){
			int j = d - i;
			ArrayList<Integer> Neigh2_1 = new ArrayList<Integer>();	
			Neigh2_1.add(LocName.get(0) - i);
			Neigh2_1.add(LocName.get(1) + j);
			if (!Neigh2.contains(Neigh2_1) && (LocName.get(0) - i) >= 0 && ((LocName.get(0) - i) < TwoDx) && ((LocName.get(1) + j) >= 0) && ((LocName.get(1) + j) < TwoDy)){
			Neigh2.add(Neigh2_1); 				
		}}
		for (int i=0; i <= d; i++){
			int j = d - i;
			ArrayList<Integer> Neigh2_1 = new ArrayList<Integer>();	
			Neigh2_1.add(LocName.get(0) - i);
			Neigh2_1.add(LocName.get(1) - j);
			if (!Neigh2.contains(Neigh2_1)&& (LocName.get(0) - i) >= 0 && ((LocName.get(0) - i) < TwoDx) && ((LocName.get(1) - j) >= 0) && ((LocName.get(1) - j) < TwoDy)){			
			Neigh2.add(Neigh2_1); 				
		}}	
		for (int i=0; i <= d; i++){
			int j = d - i;
			ArrayList<Integer> Neigh2_1 = new ArrayList<Integer>();	
			Neigh2_1.add(LocName.get(0) + i);
			Neigh2_1.add(LocName.get(1) - j);
			if (!Neigh2.contains(Neigh2_1)&& (LocName.get(0) + i) >= 0 && ((LocName.get(0) + i) < TwoDx) && ((LocName.get(1) - j) >= 0) && ((LocName.get(1) - j) < TwoDy)){				
			Neigh2.add(Neigh2_1); 				
		}}	
		Neigh2.remove(LocName);	 
        return Neigh2;	
	   }
			
	
	//SOLVE THE NEGATIVE PROBLEM, as done for TwoD
	public ArrayList<ArrayList<Integer>> getNeighThreeDBouncing(ArrayList<Integer> LocName, int d){
		ArrayList<ArrayList<Integer>> Neigh3 = new ArrayList<ArrayList<Integer>>(); 	
		  for (int i=0; i <= d; i++){
			for (int j=0; j <= d - i; j++){
				for (int z=0; z <= d - i - j ; z++){
				ArrayList<Integer> Neigh3_1 = new ArrayList<Integer>();
				Neigh3_1.add(LocName.get(0) + i);
				Neigh3_1.add(LocName.get(1) + j);	
				Neigh3_1.add(LocName.get(2) + z);	
				if (!Neigh3.contains(Neigh3_1)){
				Neigh3.add(Neigh3_1); }				
		}}}		
		  for (int i=0; i <= d; i++){
				for (int j=0; j <= d - i; j++){
					for (int z=0; z <= d - i - j ; z++){
				ArrayList<Integer> Neigh3_1 = new ArrayList<Integer>();
				Neigh3_1.add(LocName.get(0) + i);
				Neigh3_1.add(LocName.get(1) + j);	
				Neigh3_1.add(LocName.get(2) - z);	
				if (!Neigh3.contains(Neigh3_1)){
				Neigh3.add(Neigh3_1); }				
		}}}
		  for (int i=0; i <= d; i++){
				for (int j=0; j <= d - i; j++){
					for (int z=0; z <= d - i - j ; z++){
				ArrayList<Integer> Neigh3_1 = new ArrayList<Integer>();
				Neigh3_1.add(LocName.get(0) + i);
				Neigh3_1.add(LocName.get(1) - j);	
				Neigh3_1.add(LocName.get(2) - z);	
				if (!Neigh3.contains(Neigh3_1)){
				Neigh3.add(Neigh3_1); }			
		}}}
		  for (int i=0; i <= d; i++){
				for (int j=0; j <= d - i; j++){
					for (int z=0; z <= d - i - j ; z++){
				ArrayList<Integer> Neigh3_1 = new ArrayList<Integer>();
				Neigh3_1.add(LocName.get(0) + i);
				Neigh3_1.add(LocName.get(1) - j);	
				Neigh3_1.add(LocName.get(2) + z);	
				if (!Neigh3.contains(Neigh3_1)){
				Neigh3.add(Neigh3_1); }			
		}}}
		  
		  for (int i=0; i <= d; i++){
			for (int j=0; j <= d - i; j++){
				for (int z=0; z <= d - i - j ; z++){
				ArrayList<Integer> Neigh3_1 = new ArrayList<Integer>();
				Neigh3_1.add(LocName.get(0) - i);
				Neigh3_1.add(LocName.get(1) + j);	
				Neigh3_1.add(LocName.get(2) + z);	
				if (!Neigh3.contains(Neigh3_1)){
				Neigh3.add(Neigh3_1); }				
		}}}		
		  for (int i=0; i <= d; i++){
				for (int j=0; j <= d - i; j++){
					for (int z=0; z <= d - i - j ; z++){
				ArrayList<Integer> Neigh3_1 = new ArrayList<Integer>();
				Neigh3_1.add(LocName.get(0) - i);
				Neigh3_1.add(LocName.get(1) + j);	
				Neigh3_1.add(LocName.get(2) - z);	
				if (!Neigh3.contains(Neigh3_1)){
				Neigh3.add(Neigh3_1); }				
		}}}
		  for (int i=0; i <= d; i++){
				for (int j=0; j <= d - i; j++){
					for (int z=0; z <= d - i - j ; z++){
				ArrayList<Integer> Neigh3_1 = new ArrayList<Integer>();
				Neigh3_1.add(LocName.get(0) - i);
				Neigh3_1.add(LocName.get(1) - j);	
				Neigh3_1.add(LocName.get(2) - z);	
				if (!Neigh3.contains(Neigh3_1)){
				Neigh3.add(Neigh3_1); }			
		}}}
		  for (int i=0; i <= d; i++){
				for (int j=0; j <= d - i; j++){
					for (int z=0; z <= d - i - j ; z++){
				ArrayList<Integer> Neigh3_1 = new ArrayList<Integer>();
				Neigh3_1.add(LocName.get(0) - i);
				Neigh3_1.add(LocName.get(1) - j);	
				Neigh3_1.add(LocName.get(2) + z);	
				if (!Neigh3.contains(Neigh3_1)){
				Neigh3.add(Neigh3_1); }			
		}}}		  
		Neigh3.remove(LocName);	 
        return Neigh3;	
	   }
	
	public ArrayList<ArrayList<Integer>> getNeighOneDAbsorbing(ArrayList<Integer> LocName, int d){
		ArrayList<ArrayList<Integer>> Neigh1 = new ArrayList<ArrayList<Integer>>();		
		for (int i=1; i <= d; i++){
		int neigh1 = (LocName.get(0) + i);			
		ArrayList<Integer> neigh1_list = GlobalManager.createListName(neigh1);
		if (!Neigh1.contains(neigh1_list)){
		Neigh1.add(neigh1_list);}}				
		for (int i=1; i <= d; i++){
			int neigh2 = (LocName.get(0) - i);			
			ArrayList<Integer> neigh2_list = GlobalManager.createListName(neigh2);
			if (!Neigh1.contains(neigh2_list)){
			Neigh1.add(neigh2_list);}}	
        return Neigh1;	
	   }		

	public ArrayList<ArrayList<Integer>> getNeighTwoDAbsorbing(ArrayList<Integer> LocName, int d){
		ArrayList<ArrayList<Integer>> Neigh2 = new ArrayList<ArrayList<Integer>>(); 		
		for (int i=0; i <= d; i++){
			for (int j=0; j <= d - i; j++){
			ArrayList<Integer> Neigh2_1 = new ArrayList<Integer>();	
			Neigh2_1.add(LocName.get(0) + i);
			Neigh2_1.add(LocName.get(1) + j);
			if (!Neigh2.contains(Neigh2_1)){
			Neigh2.add(Neigh2_1); 				
		}}}		
		for (int i=0; i <= d; i++){
			for (int j=0; j <= d - i; j++){
			ArrayList<Integer> Neigh2_1 = new ArrayList<Integer>();	
			Neigh2_1.add(LocName.get(0) - i);
			Neigh2_1.add(LocName.get(1) + j);
			if (!Neigh2.contains(Neigh2_1)){
			Neigh2.add(Neigh2_1); 				
		}}}		
		for (int i=0; i <= d; i++){
			for (int j=0; j <= d - i; j++){
			ArrayList<Integer> Neigh2_1 = new ArrayList<Integer>();	
			Neigh2_1.add(LocName.get(0) - i);
			Neigh2_1.add(LocName.get(1) - j);
			if (!Neigh2.contains(Neigh2_1)){
			Neigh2.add(Neigh2_1); 				
		}}}		
		for (int i=0; i <= d; i++){
			for (int j=0; j <= d - i; j++){
			ArrayList<Integer> Neigh2_1 = new ArrayList<Integer>();	
			Neigh2_1.add(LocName.get(0) + i);
			Neigh2_1.add(LocName.get(1) - j);
			if (!Neigh2.contains(Neigh2_1)){
			Neigh2.add(Neigh2_1); 				
		}}}		
		Neigh2.remove(LocName);	 
        return Neigh2;	
	   }
			
	public ArrayList<ArrayList<Integer>> getNeighThreeDAbsorbing(ArrayList<Integer> LocName, int d){
		ArrayList<ArrayList<Integer>> Neigh3 = new ArrayList<ArrayList<Integer>>(); 	
		  for (int i=0; i <= d; i++){
			for (int j=0; j <= d - i; j++){
				for (int z=0; z <= d - i - j ; z++){
				ArrayList<Integer> Neigh3_1 = new ArrayList<Integer>();
				Neigh3_1.add(LocName.get(0) + i);
				Neigh3_1.add(LocName.get(1) + j);	
				Neigh3_1.add(LocName.get(2) + z);	
				if (!Neigh3.contains(Neigh3_1)){
				Neigh3.add(Neigh3_1); }				
		}}}		
		  for (int i=0; i <= d; i++){
				for (int j=0; j <= d - i; j++){
					for (int z=0; z <= d - i - j ; z++){
				ArrayList<Integer> Neigh3_1 = new ArrayList<Integer>();
				Neigh3_1.add(LocName.get(0) + i);
				Neigh3_1.add(LocName.get(1) + j);	
				Neigh3_1.add(LocName.get(2) - z);	
				if (!Neigh3.contains(Neigh3_1)){
				Neigh3.add(Neigh3_1); }				
		}}}
		  for (int i=0; i <= d; i++){
				for (int j=0; j <= d - i; j++){
					for (int z=0; z <= d - i - j ; z++){
				ArrayList<Integer> Neigh3_1 = new ArrayList<Integer>();
				Neigh3_1.add(LocName.get(0) + i);
				Neigh3_1.add(LocName.get(1) - j);	
				Neigh3_1.add(LocName.get(2) - z);	
				if (!Neigh3.contains(Neigh3_1)){
				Neigh3.add(Neigh3_1); }			
		}}}
		  for (int i=0; i <= d; i++){
				for (int j=0; j <= d - i; j++){
					for (int z=0; z <= d - i - j ; z++){
				ArrayList<Integer> Neigh3_1 = new ArrayList<Integer>();
				Neigh3_1.add(LocName.get(0) + i);
				Neigh3_1.add(LocName.get(1) - j);	
				Neigh3_1.add(LocName.get(2) + z);	
				if (!Neigh3.contains(Neigh3_1)){
				Neigh3.add(Neigh3_1); }			
		}}}
		  
		  for (int i=0; i <= d; i++){
			for (int j=0; j <= d - i; j++){
				for (int z=0; z <= d - i - j ; z++){
				ArrayList<Integer> Neigh3_1 = new ArrayList<Integer>();
				Neigh3_1.add(LocName.get(0) - i);
				Neigh3_1.add(LocName.get(1) + j);	
				Neigh3_1.add(LocName.get(2) + z);	
				if (!Neigh3.contains(Neigh3_1)){
				Neigh3.add(Neigh3_1); }				
		}}}		
		  for (int i=0; i <= d; i++){
				for (int j=0; j <= d - i; j++){
					for (int z=0; z <= d - i - j ; z++){
				ArrayList<Integer> Neigh3_1 = new ArrayList<Integer>();
				Neigh3_1.add(LocName.get(0) - i);
				Neigh3_1.add(LocName.get(1) + j);	
				Neigh3_1.add(LocName.get(2) - z);	
				if (!Neigh3.contains(Neigh3_1)){
				Neigh3.add(Neigh3_1); }				
		}}}
		  for (int i=0; i <= d; i++){
				for (int j=0; j <= d - i; j++){
					for (int z=0; z <= d - i - j ; z++){
				ArrayList<Integer> Neigh3_1 = new ArrayList<Integer>();
				Neigh3_1.add(LocName.get(0) - i);
				Neigh3_1.add(LocName.get(1) - j);	
				Neigh3_1.add(LocName.get(2) - z);	
				if (!Neigh3.contains(Neigh3_1)){
				Neigh3.add(Neigh3_1); }			
		}}}
		  for (int i=0; i <= d; i++){
				for (int j=0; j <= d - i; j++){
					for (int z=0; z <= d - i - j ; z++){
				ArrayList<Integer> Neigh3_1 = new ArrayList<Integer>();
				Neigh3_1.add(LocName.get(0) - i);
				Neigh3_1.add(LocName.get(1) - j);	
				Neigh3_1.add(LocName.get(2) + z);	
				if (!Neigh3.contains(Neigh3_1)){
				Neigh3.add(Neigh3_1); }			
		}}}		  
		Neigh3.remove(LocName);	 
        return Neigh3;	
	   }
		   
	   
	   //method to use for the parser 
	   public HashMap<ArrayList<Integer>,ArrayList<ArrayList<Integer>>> prepareMap(ArrayList<Integer> NodeName){	   	   
		   ArrayList<ArrayList<Integer>> NeighNodes = new ArrayList<ArrayList<Integer>>();
		   NeighGraph.put(NodeName, NeighNodes);
		   return NeighGraph; 
		  } 
	   
	   public void addNeighNode(ArrayList<Integer> NodeNeigh, int BracketsCounter){
		   int positionMap = BracketsCounter-2;
		   ArrayList<ArrayList<Integer>> List = NeighGraph.get(GlobalManager.getLocationManager().AllLoc.get(positionMap));		
		   List.add(NodeNeigh);
		  } 
	   
	   //method for graph to get vertices at distance d
	   public ArrayList<ArrayList<Integer>> getNeighGraph(ArrayList<Integer> LocName, int d){
		   if (d==1){
		   return GlobalManager.getLocationManager().NeighGraph.get(LocName);}
		   else{
			   ArrayList<ArrayList<Integer>> neighLoop = new ArrayList<ArrayList<Integer>>();
			   neighLoop.add(LocName);			   			   
		       ArrayList<ArrayList<Integer>> neighStore = new ArrayList<ArrayList<Integer>>();
		       int check = 0;
		       int count = 1;		       
		       while (check <= d){
		    	   int start = neighLoop.size() - count;
				   for (int j= start; j < neighLoop.size(); j++){
					   count = 0;							  
					    //get a node, ask for neighbourhood
					     ArrayList<ArrayList<Integer>> ListNeigh = GlobalManager.getLocationManager().NeighGraph.get(neighLoop.get(j));
					     //for each node in the neighbourhood, add to neighStore and to node to explore (neighLoop)				     
					     for (int k=0; k < ListNeigh.size(); k++){	
					    	 ArrayList<Integer> OneValue = GlobalManager.getLocationManager().NeighGraph.get(neighLoop.get(j)).get(k);						    	 
					    	 if (!neighStore.contains(OneValue)){	
							   neighStore.add(GlobalManager.getLocationManager().NeighGraph.get(neighLoop.get(j)).get(k)); 
							   neighLoop.add(GlobalManager.getLocationManager().NeighGraph.get(neighLoop.get(j)).get(k)); 
							   count++;		
						   }					   
					   }
				   }
				   check++;		       
		     }	
		   return neighStore;		   
		   }
	   }	 
	   
	   //distance between 2 points on a grid
	   
	   public double distance (ArrayList<Integer> point1, ArrayList<Integer> point2){
		   double toSum1 = Math.pow(point2.get(0) - point1.get(0), 2);
		   double toSum2 = Math.pow(point2.get(1) - point1.get(1), 2);
		   double toSquare = toSum1 + toSum2;
		   double result = Math.sqrt(toSquare);
		   return result;
	   }
	   
}