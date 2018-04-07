package Model;

import Utility.Utility;

public class ExpEvaluator {
	
	public static double evalParamExp(String exp) {
		double ret = Utility.evalParamExpressionForSimulation(exp);
		return ret;
	}
}
