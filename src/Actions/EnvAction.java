package Actions;

public class EnvAction implements Action  {
	private String name;
	private double rate;
	private String infset;
	private String update;
	
	public EnvAction(String name, double rate, String infset, String update){
		this.name = name;
		this.rate = rate;
		this.infset = infset;
		this.update = update;
	}

@Override
public int getType() {
	return Action.ACTION_TYPE_Env;
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

@Override
public String getUpdate() {
	return update;
}

@Override
public String printStr() {
	String ret = "->{" + infset + "}(" + name + "," + rate + ")." + update;
	return ret;
}

}



