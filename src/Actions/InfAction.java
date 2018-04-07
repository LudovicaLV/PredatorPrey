package Actions;

public class InfAction implements Action {
			private String name;
			private double rate;
			private String infset;
			private int rangeNeigh;
			private String symbol;
			private String update;
			private int range;
			
			public InfAction(String name, double rate, String infset, int rangeNeigh, String symbol, String update, int range){
				this.name = name;
				this.rate = rate;
				this.infset = infset;
				this.rangeNeigh = rangeNeigh;
				this.symbol = symbol;
				this.update = update;	
				this.range = range;
			}
					
@Override
public int getType() {
	return Action.ACTION_TYPE_Inf;
}

public String getName() {
	return name;
}

public Double getRate() {
	return rate;
}

public String getInfSet() {
	return infset;
}

public int getRangeNeigh() {
	return rangeNeigh;
}

public int getRangeInf() {
	return range;
}

public String getSymbol() {
	return symbol;
}

@Override
public String getUpdate() {
	return update;
}
		
@Override
public String printStr() {
	String ret = "->{" + infset + "}(" + name + "," + rate + ")" + symbol + update;
	return ret;
}


}





