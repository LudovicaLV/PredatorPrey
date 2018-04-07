package TotalCells;

import java.util.ArrayList;
import java.util.Random;

public class ListInit {

	
	public static void main(String[] args){
	
		int value = 0;
		for (int i=0; i<25; i++){
			for (int j=0; j<25; j++){
				if (value < 413){
			
				ArrayList<ArrayList<Integer>> list = list();
				
				ArrayList<Integer> listxy = new ArrayList<>();
				listxy.add(i);
				listxy.add(j);
				if (list.contains(listxy)){
				listxy.clear();

	     }else{
	    	 Random ran = new Random();
	    	 int x = ran.nextInt(10);
	    	 if (x < 7){
	    	 System.out.print("P(" + i + "," + j + ")[1]||");
	    	 listxy.clear();
	    	 value++;}else{
	    		 listxy.clear();
	    	 }
	    }
				}}}
		}
	
	public static 		ArrayList<ArrayList<Integer>> list (){
		
		ArrayList<ArrayList<Integer>> list = new ArrayList<>();
		int[] x = {10,11,11,11,12,12,12,12,12,13,13,13,14};
		int[] y = {12,11,12,13,10,11,12,13,14,11,12,13,12};
		
		for (int i=0; i<x.length; i++){	
		ArrayList<Integer> listxy = new ArrayList<>();
		listxy.add(x[i]);
		listxy.add(y[i]);
		list.add(listxy);
}
		return list;}
}
