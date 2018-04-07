package Model;

import java.util.HashMap;
//import Utility.Utility;

public class ParamManager {
	
	
	public HashMap<String, Double> paramMap = new HashMap<String, Double>();

	public void addParam(String name, double value) {
		if(ParamExist(name)) {
			System.err.println("The param " + name + " already exists!");
		}else {
			paramMap.put(name, value);
			//Utility.setParamInInterpreter(name, value);
		}
	}
	
	public boolean ParamExist(String name) {
		if(paramMap.get(name) != null) {
			return true;
		}else {
			return false;
		}
	}
	
	public double getParamValue(String name) {
		double ret = (Double) paramMap.get(name);
		return ret;
	}
	
	public HashMap<String, Double> getParamMap() {
		return this.paramMap;
	}
	
	public void clear() {
		paramMap.clear();
	}
}
