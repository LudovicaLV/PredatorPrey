package Actions;

//needed like this??

public class PropensityFunction {
	private String name;
	private Action action;
	private double rate;
	private double internalRate;
	
	public PropensityFunction(String name, Action action, double internalRate) {
		this.name = name;
		this.action = action;
		this.internalRate = internalRate;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Action getAction() {
		return action;
	}
	public void setActionIndex(Action action) {
		this.action = action;
	}
	public double getApparentRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	
	public double getInternalRate() {
		return internalRate;
	}
}
