package Actions;


public interface Action {
	public final static int ACTION_TYPE_NoInf = 101;
	public final static int ACTION_TYPE_Inf = 102;
	public final static int ACTION_TYPE_Pass = 103;
	public final static int ACTION_TYPE_Env = 104;
	
	public int getType();
	
	public String getName();
	
	public String getUpdate();
	
	public String printStr(); 

	
}
