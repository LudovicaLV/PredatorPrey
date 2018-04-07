package Actions;

public class PassAction implements Action {
	//public class InUnAction implements Action
	private String name;
	private double infProb;
	private String symbol;
	private String update;
	private int range;
	
	public PassAction(String name, double infProb, String symbol, String update, int range){
		this.name = name;
		this.infProb = infProb;
		this.symbol = symbol;
		this.update = update;
		this.range = range;
	}

@Override
public int getType() {
	return Action.ACTION_TYPE_Pass;
}

public String getName() {
	return name;
}

public Double getInfProb() {
	return infProb;
}

public String getSymbol() {
	return symbol;
}

@Override
public String getUpdate() {
	return update;
}

public int getRangePass() {
	return range;
}

@Override
public String printStr() {
	String ret = "<-(" + name + "," + infProb + ")" + symbol + update;
	return ret;
}

}


