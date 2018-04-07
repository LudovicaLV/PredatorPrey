package Model;

import java.util.ArrayList;
import Actions.Action;


public class Agent {
	String name;
	ArrayList<Action> actionList = new ArrayList<Action>();
	
	public Agent(String name) {
		this.name = name;
		GlobalManager.getAgentManager().addAgentName(name);
		GlobalManager.getAgentManager().addAgent(this);
	}
	
	public String getName(){
		return name;
	}
	
	public void addAction(Action a){
		actionList.add(a);
	}
	
	public ArrayList<Action> getActionList() {
		return this.actionList;
	}
	
}