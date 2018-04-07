package Actions;

public class NoInfAction implements Action {
	// public class SpBrAction implements Action {
		private String name;
		private double rate;
        private String symbol;
		private String update;
		private int range;
		
		//int DETERMINISTIC 
		
		public NoInfAction(String name, double rate, String symbol, String update, int range){
			this.name = name;
			this.rate = rate;
			this.symbol = symbol;
			this.update = update;
			this.range = range;
		}
		
@Override
public int getType() {
	return Action.ACTION_TYPE_NoInf;
}

public String getName() {
	return name;
}

public Double getRate() {
	return rate;
}

public String getSymbol() {
	return symbol;
}

@Override
public String getUpdate() {
	return update;
}

public int getRangeNoInf() {
	return range;
}

@Override
public String printStr() {
	String ret = "(" + name + "," + rate + ")" + symbol + update;
	return ret;
}

}
