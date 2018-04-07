# ProjectMS

- Actions: package containing all the information needed to deal with the different action type in MELA (no influence, influence, passive, environmental)  and the class used when calculating the propensity function in the Simulator, step needed in the Gillespie algorithm

- Model: 
-- Agent, AgentManager, Location, LocationManager, ParamManager, GlobalManager: information needed to deal with agents, their actions, locations, parameters - information included in a MELA model. Agentmanager contains GlobalManager, that is the matrix used to count the population, for each agent type in each location. 
-- Simulator, ExpEvaluator, Samples: simulator and classes needed to perform the simulation (Gillespie algorithm)

- ParserMELA: parser for MELA models (to store information about the model and set the initial conditions)

- ParserRules: parser for extra rules (e.g. type of boundaries - in this case bouncing - not correct, absorbing is better, change the simulator as well 

- TotalCells:
-- Cost: to deal with the option of calculating the cost of given actions, to declare in TotalCells
-- ListInit: to easily write the initial conditions, once declare where the initial lesion is located, respecting the expected density of precursor cells in the area
-- TotalCells: the class to run to perform the simulations. To declare: number of runs, time, project folder, MELA model
-- Writing: for the simulations output file

- Utility: necessary for the simulations


