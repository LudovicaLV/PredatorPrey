package TotalCells;

public class Writing {
	
	//used in SimulatorCells
	public static String Log (){	
		String log = TotalCells.Project + "Output/Log/Output" +  TotalCells._SIMULATION_ID;
		return log;
	}

	//used in SimulatorCells
	public static String Meta (){	
		String meta = TotalCells.Project + "Output/Meta/Output" +  TotalCells._SIMULATION_ID;
		return meta;
	}
	
	//used in SimulatorCells
	public static String Data (){	
		String meta = TotalCells.Project + "Output/Data/Output" +  TotalCells._SIMULATION_ID;
		return meta;
	}

	//used in SimulatorCells
	public static String Cost (){	
		String meta = TotalCells.Project + "Output/Cost/Output" +  TotalCells._SIMULATION_ID;
		return meta;
	}
	
	//used in SimulatorCells
	public static String Agents (){	
		String meta = TotalCells.Project + "Output/Agents/Output" +  TotalCells._SIMULATION_ID;
		return meta;
	}

}
