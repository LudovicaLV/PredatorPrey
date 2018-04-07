package Model;


//TO DO!! add absorbing option in simulator [already added the necessary methods in LocationManager]


import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import Actions.Action;
import Actions.EnvAction;
import Actions.InfAction;
import Actions.NoInfAction;
import Actions.PassAction;
import Model.Agent;
import Model.GlobalManager;
import Model.Samples;
import ParserMELA.ModelCells;
import TotalCells.Writing;
import TotalCells.Cost;
import Utility.StdRandom;

public class SimulatorCells {

	// setting time for the simulation
	static double time;
	static String WhatToPrintA;
	static String WhatToPrintP;
	static String outputFileLog;
	static String outputFileData;
	static String outputFileMeta;
	static String outputFileCost;
	static String outputFileSinglePop;
	static String outputAgents;
	public static double[][][][] dataFinal;
	public static double[][][] data;
	public static double[] timeArray;
	public static double cost;

	public static void main(String[] args) {
		
		int totalTime = TotalCells.TotalCells.simulationTime;	
		outputFileLog = Writing.Log();
		outputFileData = Writing.Data();
		outputFileMeta = Writing.Meta();
		outputFileCost = Writing.Cost();
		outputAgents = Writing.Agents();
		PrintWriter writer_log = null, writer_data = null, writer_meta = null, writer_cost = null, writer_Agents = null;
    	try {
			writer_log = new PrintWriter(outputFileLog+"_log.txt", "UTF-8");
			writer_data = new PrintWriter(outputFileData+"_data.txt", "UTF-8");
			writer_meta = new PrintWriter(outputFileMeta+"_meta.txt", "UTF-8");	
			writer_cost = new PrintWriter(outputFileCost+"_cost.txt", "UTF-8");	
			writer_Agents = new PrintWriter(outputAgents+"_agents.txt", "UTF-8");		
	} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<ArrayList<Action>> allActions = new ArrayList<ArrayList<Action>>();
		for (Agent a : GlobalManager.getAgentManager().Agents) {
			allActions.add(a.getActionList());
		}
	
		for (int i = 0; i < GlobalManager.getAgentManager().AgentNames.size(); i++) {
			for (int j = 0; j < GlobalManager.getLocationManager().AllLoc.size(); j++) {
				String formatedString = GlobalManager.getLocationManager().AllLoc.get(j).toString().replace("[","") // remove
																														// the
																														// right
																														// bracket
						.replace("]","") // remove the left bracket
						.trim();
				String formatedString2 = formatedString.replaceAll("\\s","");
				writer_log.print(GlobalManager.getAgentManager().AgentNames.get(i) + "(" + formatedString2 + ") ");			
			}
		}

		
		time = 0;
		cost = 0.0;
		ArrayList<ArrayList<Integer>> LocationR = new ArrayList<ArrayList<Integer>>();
		
//		for (int i = 0; i <  GlobalManager.getAgentManager().AgentNames.size(); i++) {
//			for (int j = 0; j < GlobalManager.getLocationManager().AllLoc.size(); j++) {
//				System.out.println(GlobalManager.getAgentManager().GlobalMatrix[i][j] + " " + GlobalManager.getAgentManager().AgentNames.get(i) + " " + GlobalManager.getLocationManager().AllLoc.get(j)  );}}
	

		writer_data.print(time + " ");
		for (int i = 0; i < GlobalManager.getAgentManager().AgentNames.size(); i++) {
			for (int j = 0; j < GlobalManager.getLocationManager().AllLoc.size(); j++) {
				writer_data.print(GlobalManager.getAgentManager().GlobalMatrix[i][j] + " ");
			}
		}
		writer_meta.print("Initial condition - time 0.0 - no actions ");
		writer_meta.println(" ");
		writer_data.println(" ");
						
//		ArrayList<ArrayList<Integer>> TotalAgents = new ArrayList<>();
//		ArrayList<ArrayList<ArrayList<Integer>>> BigAgents = new ArrayList<>();
		

//		for (int i = 0; i < GlobalManager.getAgentManager().AgentNames.size(); i++) {
//			for (int j = 0; j < GlobalManager.getLocationManager().AllLoc.size(); j++) {	
//				ArrayList<Integer> arr = new ArrayList<>();
//				arr.add(j);
//				arr.add(i);
//				arr.add(GlobalManager.getAgentManager().GlobalMatrix[i][j]);			
//				TotalAgents.add(arr);	
//				//System.out.println(j + " " + i + " " + GlobalManager.getAgentManager().GlobalMatrix[i][j]);
//			}}
//		BigAgents.add(TotalAgents);
		
		HashMap<ArrayList<Integer>, Double> SaveAll = new HashMap<>();
		
		
		for (int i = 0; i < GlobalManager.getAgentManager().AgentNames.size(); i++) {
			for (int j = 0; j < GlobalManager.getLocationManager().AllLoc.size(); j++) {	
				ArrayList<Integer> arr = new ArrayList<>();
				arr.add(0);
				arr.add(i);
				arr.add(j);
				Double value = GlobalManager.getAgentManager().GlobalMatrix[i][j] * 1.0;
				SaveAll.put(arr,value);	
				//System.out.println(arr.get(0) + " " + arr.get(1) + " " + arr.get(2) + " " + value);				
			}}
		

		//for final matrix
		ArrayList<Double> TimeArrayList = new ArrayList<>();
		TimeArrayList.add(time);
		
        //GlobalManager.WhileMethod() - changed for simulation
		while (time < totalTime) {
			System.out.println("Simulation step " + time);
            ArrayList<Double> PropFunc = new ArrayList<>();
			ArrayList<Integer> agentArrayList = new ArrayList<>();
			ArrayList<ArrayList<Integer>> agentPositionArray = new ArrayList<>();
			ArrayList<String> nameActions = new ArrayList<>();
			ArrayList<ArrayList<Integer>> passiveAgentPositionArray = new ArrayList<>(); // name
																							// of
																							// the
																							// location
																							// where
																							// the
																							// passive
																							// agent
																							// is
			ArrayList<Double> passiveProbArray = new ArrayList<>();
			ArrayList<Integer> Type = new ArrayList<>();
			ArrayList<ArrayList<Integer>> newPositionArrayA = new ArrayList<>(); //name of the location where the active agent moves, if does
			ArrayList<ArrayList<Integer>> newPositionArrayP = new ArrayList<>(); //name of the location where the passive agent moves, if does
			ArrayList<Integer> NoMove = new ArrayList<Integer>();
			NoMove.add(-1);	
			ArrayList<Integer> NoPassive = new ArrayList<Integer>();
			NoPassive.add(-1);

			for (int i = 0; i < GlobalManager.getAgentManager().Agents.size(); i++) {
				for (int j = 0; j < GlobalManager.getLocationManager().AllLoc.size(); j++) {
					if (GlobalManager.getAgentManager().GlobalMatrix[i][j] == 0) {
					} else {
						for (Action action : GlobalManager.getAgentManager().Agents.get(i).getActionList()) {	
							// no-inf action - mass action
							if (action.getType() == Action.ACTION_TYPE_NoInf) {
								NoInfAction noinfaction = (NoInfAction) action;
								PropFunc.add(noinfaction.getRate() * GlobalManager.getAgentManager().GlobalMatrix[i][j]);
					            agentArrayList.add(i);
								agentPositionArray.add(GlobalManager.getLocationManager().AllLoc.get(j));
								nameActions.add(noinfaction.getName());
								passiveAgentPositionArray.add(NoPassive);// there
																			// is
																			// no
																			// passive
																			// agent
								passiveProbArray.add(0.0);
								Type.add(noinfaction.getType());
								newPositionArrayA.add(NoMove);
								newPositionArrayP.add(NoMove);
							}
							// inf action - check all the couple act-pass
							// (depending on influence set)
							if (action.getType() == Action.ACTION_TYPE_Inf) {
								InfAction infaction = (InfAction) action;
								if (infaction.getInfSet() == "l") {
									for (int k = 0; k < allActions.size(); k++) {
										//if (k != i) {
											for (Action actionToCheck : allActions.get(k)) {
												if (action.getName().equals(actionToCheck.getName())
														&& actionToCheck.getType() == Action.ACTION_TYPE_Pass) {
													// now: passive agent same
													// loc and >0
													if (GlobalManager.getAgentManager().GlobalMatrix[k][j] > 0) {
														PropFunc.add(infaction.getRate()
																* GlobalManager.getAgentManager().GlobalMatrix[i][j]
																* GlobalManager.getAgentManager().GlobalMatrix[k][j]);
														agentArrayList.add(i);
														agentPositionArray
																.add(GlobalManager.getLocationManager().AllLoc.get(j));
														nameActions.add(action.getName());
														passiveAgentPositionArray
																.add(GlobalManager.getLocationManager().AllLoc.get(j));
														PassAction passAction = (PassAction) actionToCheck;
														passiveProbArray.add(passAction.getInfProb());
														Type.add(action.getType());
														newPositionArrayA.add(NoMove);
														newPositionArrayP.add(NoMove);
													}
												}
											}
										//}
									}
								}
								if (infaction.getInfSet().contains("N")) {							
									for (int k = 0; k < allActions.size(); k++) {
										if (k != i) {
											for (Action actionToCheck : allActions.get(k)) {
												if (action.getName().equals(actionToCheck.getName())
														&& actionToCheck.getType() == Action.ACTION_TYPE_Pass) {
													// now: passive agent same
													// loc and >0
													
													if (GlobalManager.getLocationManager().boundary.equals("Periodic")){
													for (ArrayList<Integer> t : GlobalManager.getLocationManager()
															.getNeigh(
																	GlobalManager.getLocationManager().AllLoc.get(j), infaction.getRangeNeigh())) {								
														if (GlobalManager
																.getAgentManager().GlobalMatrix[k][GlobalManager
																		.getLocationManager().MatrixLoc.get(t)] > 0) {
															PropFunc.add(infaction.getRate()
																	* GlobalManager.getAgentManager().GlobalMatrix[i][j]
																	* GlobalManager
																			.getAgentManager().GlobalMatrix[k][GlobalManager
																					.getLocationManager().MatrixLoc
																							.get(t)]);
															agentArrayList.add(i);
															agentPositionArray.add(
																	GlobalManager.getLocationManager().AllLoc.get(j));
															nameActions.add(action.getName());
															passiveAgentPositionArray.add(t);
															PassAction passAction = (PassAction) actionToCheck;
															passiveProbArray.add(passAction.getInfProb());
															Type.add(action.getType());
															newPositionArrayA.add(NoMove);
															newPositionArrayP.add(NoMove);
														}
													}
												}
												
												if (GlobalManager.getLocationManager().boundary.equals("Bouncing")){
													for (ArrayList<Integer> t : GlobalManager.getLocationManager().getNeighBouncing(
																	GlobalManager.getLocationManager().AllLoc.get(j), infaction.getRangeNeigh())) {														
														if (GlobalManager
																.getAgentManager().GlobalMatrix[k][GlobalManager
																		.getLocationManager().MatrixLoc.get(t)] > 0) {
															PropFunc.add(infaction.getRate()
																	* GlobalManager.getAgentManager().GlobalMatrix[i][j]
																	* GlobalManager
																			.getAgentManager().GlobalMatrix[k][GlobalManager
																					.getLocationManager().MatrixLoc
																							.get(t)]);														
															agentArrayList.add(i);
															agentPositionArray.add(
																	GlobalManager.getLocationManager().AllLoc.get(j));
															nameActions.add(action.getName());
															passiveAgentPositionArray.add(t);
															PassAction passAction = (PassAction) actionToCheck;
															passiveProbArray.add(passAction.getInfProb());
															Type.add(action.getType());
															newPositionArrayA.add(NoMove);
															newPositionArrayP.add(NoMove);
														}
													}
												}
												if (GlobalManager.getLocationManager().boundary.equals("Absorbing")){
													for (ArrayList<Integer> t : GlobalManager.getLocationManager().getNeighAbsorbing(
																	GlobalManager.getLocationManager().AllLoc.get(j), infaction.getRangeNeigh())) {														
														if (GlobalManager
																.getAgentManager().GlobalMatrix[k][GlobalManager
																		.getLocationManager().MatrixLoc.get(t)] > 0) {
															PropFunc.add(infaction.getRate()
																	* GlobalManager.getAgentManager().GlobalMatrix[i][j]
																	* GlobalManager
																			.getAgentManager().GlobalMatrix[k][GlobalManager
																					.getLocationManager().MatrixLoc
																							.get(t)]);														
															agentArrayList.add(i);
															agentPositionArray.add(
																	GlobalManager.getLocationManager().AllLoc.get(j));
															nameActions.add(action.getName());
															passiveAgentPositionArray.add(t);
															PassAction passAction = (PassAction) actionToCheck;
															passiveProbArray.add(passAction.getInfProb());
															Type.add(action.getType());
															newPositionArrayA.add(NoMove);
															newPositionArrayP.add(NoMove);
														}
													}
												}
											}
										}
									}
								}}
								if (infaction.getInfSet() == "all") {
									for (int k = 0; k < allActions.size(); k++) {
										if (k != i) {
											for (Action actionToCheck : allActions.get(k)) {
												if (action.getName().equals(actionToCheck.getName())
														&& actionToCheck.getType() == Action.ACTION_TYPE_Pass) {
													// now: passive agent >0
													for (ArrayList<Integer> s : GlobalManager
															.getLocationManager().AllLoc) {
														if (GlobalManager
																.getAgentManager().GlobalMatrix[k][GlobalManager
																		.getLocationManager().MatrixLoc.get(s)] > 0) {
															PropFunc.add(infaction.getRate()
																	* GlobalManager.getAgentManager().GlobalMatrix[i][j]
																	* GlobalManager
																			.getAgentManager().GlobalMatrix[k][GlobalManager
																					.getLocationManager().MatrixLoc
																							.get(s)]);
															agentArrayList.add(i);
															agentPositionArray.add(
																	GlobalManager.getLocationManager().AllLoc.get(j));
															nameActions.add(action.getName());
															passiveAgentPositionArray.add(s);
															PassAction passAction = (PassAction) actionToCheck;
															passiveProbArray.add(passAction.getInfProb());
															Type.add(action.getType());
															newPositionArrayA.add(NoMove);
															newPositionArrayP.add(NoMove);
														}
													}
												}
											}
										}
									}
								}
							}

							if (action.getType() == Action.ACTION_TYPE_Pass) {
								PassAction passaction = (PassAction) action;
								PropFunc.add(0.0);
								agentArrayList.add(i);
								agentPositionArray.add(GlobalManager.getLocationManager().AllLoc.get(j));
								nameActions.add(passaction.getName());
								passiveAgentPositionArray.add(GlobalManager.getLocationManager().AllLoc.get(j));
								// the probability is added while dealing with
								// the influence action
								passiveProbArray.add(0.0);
								Type.add(passaction.getType());
								newPositionArrayA.add(NoMove);
								newPositionArrayP.add(NoMove);
							}

							if (action.getType() == Action.ACTION_TYPE_Env) {
								EnvAction envaction = (EnvAction) action;
								if (envaction.getInfSet() == "all") {
									for (int k = 0; k < allActions.size(); k++) {
										if (k != i) {
											for (Action actionToCheck : allActions.get(k)) {
												if (action.getName().equals(actionToCheck.getName())
														&& actionToCheck.getType() == Action.ACTION_TYPE_Pass) {
													// now: passive agent >0
													for (ArrayList<Integer> s : GlobalManager
															.getLocationManager().AllLoc) {
														if (GlobalManager
																.getAgentManager().GlobalMatrix[k][GlobalManager
																		.getLocationManager().MatrixLoc.get(s)] > 0) {
															PropFunc.add(envaction.getRate());
															agentArrayList.add(i);
															agentPositionArray.add(NoPassive);// env
																								// action
																								// no
																								// location
															nameActions.add(action.getName());
															passiveAgentPositionArray.add(s);
															PassAction passAction = (PassAction) actionToCheck;
															passiveProbArray.add(passAction.getInfProb());
															Type.add(action.getType());
															newPositionArrayA.add(NoMove);
															newPositionArrayP.add(NoMove);
														}
													}
												}
											}
										}
									}
								}
								// the case of just one location (TO DO: more
								// than one)
								else {
									ArrayList<Integer> locName = new ArrayList<Integer>();
									for (int r = 0; r < GlobalManager.getAgentManager().GlobalMatrix.length; r++) {
										if (GlobalManager.getAgentManager().GlobalMatrix[r][GlobalManager
												.getLocationManager().MatrixLoc.get(locName)] > 0) {
											for (Action actionToCheck : allActions.get(r)) {
												if (action.getName().equals(actionToCheck.getName())
														&& actionToCheck.getType() == Action.ACTION_TYPE_Pass) {
													// now: passive agent same
													// loc and >0
													PropFunc.add(envaction.getRate());
													agentArrayList.add(r);
													agentPositionArray.add(locName);
													nameActions.add(action.getName());
													PassAction passAction = (PassAction) actionToCheck;
													passiveProbArray.add(passAction.getInfProb());
													passiveAgentPositionArray.add(locName);
													newPositionArrayA.add(NoMove);
													newPositionArrayP.add(NoMove);
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}

//			 for(int i=0; i < PropFunc.size(); i++){
//			 System.out.print(PropFunc.get(i) + " ");
//			 System.out.print(agentArrayList.get(i) + " ");
//			 System.out.print(agentPositionArray.get(i) + " ");
//			 System.out.print(nameActions.get(i) + " ");
//			 System.out.print(passiveProbArray.get(i) + " ");
//			 System.out.print(passiveAgentPositionArray.get(i) + " ");
//			 System.out.println(GlobalManager.getAgentManager().AgentNames.get(agentArrayList.get(i)));
//			 System.out.println("end");
//      	 }

//			int x = 10;
//			int y = 10;
//			ArrayList<Integer> listludo = new ArrayList<>();
//			listludo.add(x);
//			listludo.add(y);
//			ArrayList<ArrayList<Integer>> nei = GlobalManager.getLocationManager().getNeigh(listludo, 3);
//			
//			for (int i=0; i < nei.size(); i++){
//					System.out.println(nei.get(i).get(0) + " , " + nei.get(i).get(1));
//				
//			}
//			System.out.println("----");
			
			double sumPropFunc = sum(PropFunc);

			if (sumPropFunc == 0) {
				System.out.println("End - null population");
				break;
			} else {

				double[] Prob = new double[PropFunc.size()];

				double time_passed = Samples.getExp(sumPropFunc);

				//System.out.println(time_passed + " time passed"); 
				
				time = time + time_passed;
				
				if (time < totalTime){
				for (int i = 0; i < PropFunc.size(); i++) {
					Prob[i] = (PropFunc.get(i) / sumPropFunc);
					}
				
				int[] ActionRef = new int[PropFunc.size()];

				for (int i = 0; i < PropFunc.size(); i++) {
					ActionRef[i] = i;
				}

				// for (int i = 0; i < PropFunc.size(); i++){
				// System.out.println(PropFunc.get(i));
				// System.out.println(Prob[i]);
				// System.out.println(ActionRef[i]);
				// }

				int action_index = Samples.getDiscrete(ActionRef, Prob);

//				 System.out.println("ACTION " + nameActions.get(action_index));
//				 System.out.print(agentArrayList.get(action_index) + " ");
//				 System.out.print(agentPositionArray.get(action_index) + " ");
//				 System.out.print(nameActions.get(action_index) + " ");
//				 System.out.print(passiveProbArray.get(action_index) + " ");
//				 System.out.print(passiveAgentPositionArray.get(action_index) + " ");
				
				
				// PIECE OF CODE USED IF WE ARE COUNTING THE ACTIONS
				if (GlobalManager.boundary == "#action") {
					if (nameActions.get(action_index).equals(GlobalManager.nameActionToCount)) {
						GlobalManager.valueForLoop++;
					}
				}
			
				if (Type.get(action_index) == Action.ACTION_TYPE_NoInf) {
					String nameToCheck = nameActions.get(action_index);
					
					//cost section - no inf
                    cost = Cost.Update(cost, nameToCheck);
					
					for (int i = 0; i < GlobalManager.getAgentManager().Agents.size(); i++) {
						for (Action action : GlobalManager.getAgentManager().Agents.get(i).getActionList()) {
							if (action.getName().equals(nameToCheck)) {
								NoInfAction chosenAction = (NoInfAction) action;
								String symbol = chosenAction.getSymbol();
								// demo+
								if (symbol == ">>") {
									GlobalManager.getAgentManager().GlobalMatrix[agentArrayList
											.get(action_index)][GlobalManager.getLocationManager().MatrixLoc
													.get(agentPositionArray.get(action_index))]++;
									WhatToPrintA = "Demographic+Action ";
									WhatToPrintP = "x ";
								}
								// demo-
								if (symbol == "<<") {
									GlobalManager.getAgentManager().GlobalMatrix[agentArrayList
											.get(action_index)][GlobalManager.getLocationManager().MatrixLoc
													.get(agentPositionArray.get(action_index))]--;
									WhatToPrintA = "Demographic-Action ";
									WhatToPrintP = "x ";
								}
								if (symbol == ".") {
									if (chosenAction.getUpdate().matches("(.*)new(.*)")) {
										// so far just random movement									
										ArrayList<Integer> actualposition = agentPositionArray.get(action_index);										
										
									    if (GlobalManager.getLocationManager().boundary.equals("Periodic")){	
										ArrayList<ArrayList<Integer>> neighbourhood = GlobalManager.getLocationManager()
												.getNeigh(actualposition, chosenAction.getRangeNoInf());																
										double[] ProbLoc = new double[neighbourhood.size()];
										for (int j = 0; j < neighbourhood.size(); j++) {
											for (int k = 0; k < neighbourhood.get(j).size(); k++) {
												ProbLoc[j] = (1.0 / neighbourhood.size());
											}
										}
										int[] neighbourhoodEntries = new int[neighbourhood.size()];
										for (int k = 0; k < neighbourhood.size(); k++) {
											neighbourhoodEntries[k] = k;
										}
										int locNewEntry = Samples.getDiscrete(neighbourhoodEntries, ProbLoc);
										ArrayList<Integer> locNewName = neighbourhood.get(locNewEntry);
										GlobalManager.getAgentManager().GlobalMatrix[agentArrayList
												.get(action_index)][GlobalManager.getLocationManager().MatrixLoc
														.get(actualposition)]--;
										int s = agentArrayList.get(action_index);
										int t = GlobalManager.getLocationManager().MatrixLoc.get(locNewName);
										GlobalManager.getAgentManager().GlobalMatrix[s][t]++;
										WhatToPrintA = "SpatialAction ";
										WhatToPrintP = "x ";
										newPositionArrayA.set(action_index,locNewName);
										}else{
										if (GlobalManager.getLocationManager().boundary.equals("Bouncing")){	
										ArrayList<ArrayList<Integer>> neighbourhoodB = GlobalManager.getLocationManager()
												.getNeighBouncing(actualposition, chosenAction.getRangeNoInf());																
										double[] ProbLocB = new double[neighbourhoodB.size()];
										for (int j = 0; j < neighbourhoodB.size(); j++) {
											for (int k = 0; k < neighbourhoodB.get(j).size(); k++) {
												ProbLocB[j] = (1.0 / neighbourhoodB.size());
											}
										}
										int[] neighbourhoodEntriesB = new int[neighbourhoodB.size()];
										for (int k = 0; k < neighbourhoodB.size(); k++) {
											neighbourhoodEntriesB[k] = k;
										}
										int locNewEntryB = Samples.getDiscrete(neighbourhoodEntriesB, ProbLocB);
										ArrayList<Integer> locNewNameB = neighbourhoodB.get(locNewEntryB);										
										if(GlobalManager.getLocationManager().AllLoc.contains(locNewNameB)){																			
										GlobalManager.getAgentManager().GlobalMatrix[agentArrayList
												.get(action_index)][GlobalManager.getLocationManager().MatrixLoc
														.get(actualposition)]--;
										int sB = agentArrayList.get(action_index);
										int tB = GlobalManager.getLocationManager().MatrixLoc.get(locNewNameB);
										GlobalManager.getAgentManager().GlobalMatrix[sB][tB]++;
										WhatToPrintA = "SpatialAction ";
										WhatToPrintP = "x ";
										newPositionArrayA.set(action_index,locNewNameB);									
										}else{
										WhatToPrintA = "NoChange ";
										WhatToPrintP = "x ";
										}
										
										//}else{
										//if (GlobalManager.getLocationManager().boundary.equals("fixed")){											
										
										}else{
											if (GlobalManager.getLocationManager().boundary.equals("Absorbing")){	
												ArrayList<ArrayList<Integer>> neighbourhoodB = GlobalManager.getLocationManager()
														.getNeighAbsorbing(actualposition, chosenAction.getRangeNoInf());																
												double[] ProbLocB = new double[neighbourhoodB.size()];
												for (int j = 0; j < neighbourhoodB.size(); j++) {
													for (int k = 0; k < neighbourhoodB.get(j).size(); k++) {
														ProbLocB[j] = (1.0 / neighbourhoodB.size());
													}
												}
												int[] neighbourhoodEntriesB = new int[neighbourhoodB.size()];
												for (int k = 0; k < neighbourhoodB.size(); k++) {
													neighbourhoodEntriesB[k] = k;
												}
												int locNewEntryB = Samples.getDiscrete(neighbourhoodEntriesB, ProbLocB);
												ArrayList<Integer> locNewNameB = neighbourhoodB.get(locNewEntryB);										
												if(GlobalManager.getLocationManager().AllLoc.contains(locNewNameB)){																			
												GlobalManager.getAgentManager().GlobalMatrix[agentArrayList
														.get(action_index)][GlobalManager.getLocationManager().MatrixLoc
																.get(actualposition)]--;
												int sB = agentArrayList.get(action_index);
												int tB = GlobalManager.getLocationManager().MatrixLoc.get(locNewNameB);
												GlobalManager.getAgentManager().GlobalMatrix[sB][tB]++;
												WhatToPrintA = "SpatialAction ";
												WhatToPrintP = "x ";
												newPositionArrayA.set(action_index,locNewNameB);									
												}else{
												WhatToPrintA = "NoChange ";
												WhatToPrintP = "x ";
												}
											}
											
										}
										
										
										}} else {
										// change of state
										String newState = action.getUpdate();
										int newStatePos = 0;
										for (int k = 0; k < GlobalManager.getAgentManager().AgentNames.size(); k++) {
											if (newState.equals(
													GlobalManager.getAgentManager().AgentNames.get(k) + "(l)")) {
												newStatePos = k;
											}
										}
										GlobalManager.getAgentManager().GlobalMatrix[agentArrayList
												.get(action_index)][GlobalManager.getLocationManager().MatrixLoc
														.get(agentPositionArray.get(action_index))]--;
										GlobalManager.getAgentManager().GlobalMatrix[newStatePos][GlobalManager
												.getLocationManager().MatrixLoc
														.get(agentPositionArray.get(action_index))]++;
										WhatToPrintA = "StateAction ";
										WhatToPrintP = "x ";
									}
								}
							}
						}
					}
				}else{if (Type.get(action_index) == Action.ACTION_TYPE_Inf) {
					double probEffect = passiveProbArray.get(action_index);
					if (StdRandom.bernoulli(probEffect)) {
						String nameToCheck = nameActions.get(action_index);
						
						//cost section - inf
						cost = Cost.Update(cost, nameToCheck);
						
						for (int i = 0; i < GlobalManager.getAgentManager().Agents.size(); i++) {
							for (Action action : GlobalManager.getAgentManager().Agents.get(i).getActionList()) {
								if (action.getName().equals(nameToCheck)
										&& action.getType() == Action.ACTION_TYPE_Inf) {
									InfAction chosenAction = (InfAction) action;
									String symbolInf = chosenAction.getSymbol();
									// update for the influence agent
									// demo+
									if (symbolInf == ">>") {
										GlobalManager.getAgentManager().GlobalMatrix[agentArrayList
												.get(action_index)][GlobalManager.getLocationManager().MatrixLoc
														.get(agentPositionArray.get(action_index))]++;
										WhatToPrintA = "Demographic+Action ";
									}
									// demo-
									if (symbolInf == "<<") {
										GlobalManager.getAgentManager().GlobalMatrix[agentArrayList
												.get(action_index)][GlobalManager.getLocationManager().MatrixLoc
														.get(agentPositionArray.get(action_index))]--;
										WhatToPrintA = "Demographic-Action ";
									}
									if (symbolInf == ".") {
										if (chosenAction.getUpdate().matches("(.*)new(.*)")) {							
											// so far just random movement
										if (GlobalManager.getLocationManager().boundary.equals("Periodic")){											
											ArrayList<Integer> actualposition = agentPositionArray.get(action_index);
											ArrayList<ArrayList<Integer>> neighbourhood = GlobalManager
													.getLocationManager().getNeigh(actualposition, chosenAction.getRangeInf());
											double[] ProbLoc = new double[neighbourhood.size()];
											for (int j = 0; j < neighbourhood.size(); j++) {
												ProbLoc[j] = (1.0 / neighbourhood.size());
											}
											int[] neighbourhoodEntries = new int[neighbourhood.size()];
											for (int j = 0; j < neighbourhood.size(); j++) {
												neighbourhoodEntries[j] = j;
											}
											int locNewEntry = Samples.getDiscrete(neighbourhoodEntries, ProbLoc);
											ArrayList<Integer> locNewName = neighbourhood.get(locNewEntry);
											GlobalManager.getAgentManager().GlobalMatrix[agentArrayList
													.get(action_index)][GlobalManager.getLocationManager().MatrixLoc
															.get(actualposition)]--;
											GlobalManager.getAgentManager().GlobalMatrix[agentArrayList
													.get(action_index)][GlobalManager.getLocationManager().MatrixLoc
															.get(locNewName)]++;
											WhatToPrintA = "SpatialAction ";
											newPositionArrayA.set(action_index,locNewName);}else{
											if (GlobalManager.getLocationManager().boundary.equals("Bouncing")){	
												ArrayList<Integer> actualposition = agentPositionArray.get(action_index);
												ArrayList<ArrayList<Integer>> neighbourhood = GlobalManager
														.getLocationManager().getNeighBouncing(actualposition, chosenAction.getRangeInf());
												double[] ProbLoc = new double[neighbourhood.size()];
												for (int j = 0; j < neighbourhood.size(); j++) {
													ProbLoc[j] = (1.0 / neighbourhood.size());
												}
												int[] neighbourhoodEntries = new int[neighbourhood.size()];
												for (int j = 0; j < neighbourhood.size(); j++) {
													neighbourhoodEntries[j] = j;
												}
												int locNewEntry = Samples.getDiscrete(neighbourhoodEntries, ProbLoc);
												ArrayList<Integer> locNewName = neighbourhood.get(locNewEntry);											
												   if(GlobalManager.getLocationManager().AllLoc.contains(locNewName)){	
												
												GlobalManager.getAgentManager().GlobalMatrix[agentArrayList
														.get(action_index)][GlobalManager.getLocationManager().MatrixLoc
																.get(actualposition)]--;
												GlobalManager.getAgentManager().GlobalMatrix[agentArrayList
														.get(action_index)][GlobalManager.getLocationManager().MatrixLoc
																.get(locNewName)]++;
												WhatToPrintA = "SpatialAction ";
												newPositionArrayA.set(action_index,locNewName);}else{
												WhatToPrintA = "NoChange ";
												}	
											  }	else{
													if (GlobalManager.getLocationManager().boundary.equals("Absorbing")){	
														ArrayList<Integer> actualposition = agentPositionArray.get(action_index);
														ArrayList<ArrayList<Integer>> neighbourhood = GlobalManager
																.getLocationManager().getNeighAbsorbing(actualposition, chosenAction.getRangeInf());
														double[] ProbLoc = new double[neighbourhood.size()];
														for (int j = 0; j < neighbourhood.size(); j++) {
															ProbLoc[j] = (1.0 / neighbourhood.size());
														}
														int[] neighbourhoodEntries = new int[neighbourhood.size()];
														for (int j = 0; j < neighbourhood.size(); j++) {
															neighbourhoodEntries[j] = j;
														}
														int locNewEntry = Samples.getDiscrete(neighbourhoodEntries, ProbLoc);
														ArrayList<Integer> locNewName = neighbourhood.get(locNewEntry);											
														   if(GlobalManager.getLocationManager().AllLoc.contains(locNewName)){	
														
														GlobalManager.getAgentManager().GlobalMatrix[agentArrayList
																.get(action_index)][GlobalManager.getLocationManager().MatrixLoc
																		.get(actualposition)]--;
														GlobalManager.getAgentManager().GlobalMatrix[agentArrayList
																.get(action_index)][GlobalManager.getLocationManager().MatrixLoc
																		.get(locNewName)]++;
														WhatToPrintA = "SpatialAction ";
														newPositionArrayA.set(action_index,locNewName);}else{
														WhatToPrintA = "NoChange ";
														}	
													  }
												  
											  }
											}										
										} else {
											// change of state
											String newState = chosenAction.getUpdate();
											int newStatePos = 0;										
											for (int k = 0; k < GlobalManager.getAgentManager().Agents.size(); k++) {
												if (newState.equals(
														GlobalManager.getAgentManager().AgentNames.get(k) + "(l)")) {
													newStatePos = k;
												}
											}
											GlobalManager.getAgentManager().GlobalMatrix[agentArrayList
													.get(action_index)][GlobalManager.getLocationManager().MatrixLoc
															.get(agentPositionArray.get(action_index))]--;
											GlobalManager.getAgentManager().GlobalMatrix[newStatePos][GlobalManager
													.getLocationManager().MatrixLoc
															.get(agentPositionArray.get(action_index))]++;
											WhatToPrintA = "StateAction ";
										}
									}

									// update of passive agents
									for (int k = 0; k < GlobalManager.getAgentManager().AgentNames.size(); k++) {
										for (Action actionToCheckPass : GlobalManager.getAgentManager().Agents.get(k)
												.getActionList()) {
											if (actionToCheckPass.getName().equals(nameToCheck)
													&& actionToCheckPass.getType() == Action.ACTION_TYPE_Pass) {
												
												
												PassAction chosenActionPass = (PassAction) actionToCheckPass;
												
												String symbolPass = chosenActionPass.getSymbol();
												int PassAgentName = k;
												ArrayList<Integer> PassAgentPosition = passiveAgentPositionArray
														.get(action_index);
												// demo+
												if (symbolPass == ">>") {
													GlobalManager
															.getAgentManager().GlobalMatrix[PassAgentName][GlobalManager
																	.getLocationManager().MatrixLoc
																			.get(PassAgentPosition)]++;
													WhatToPrintP = "Demographic+Action ";
												}
												// demo-
												if (symbolPass == "<<") {
													GlobalManager
															.getAgentManager().GlobalMatrix[PassAgentName][GlobalManager
																	.getLocationManager().MatrixLoc
																			.get(PassAgentPosition)]--;
													WhatToPrintP = "Demographic-Action ";
												}
												if (symbolPass == ".") {
													if (chosenActionPass.getUpdate().matches("(.*)new(.*)")) {
														
														// so far just random movement
														if (GlobalManager.getLocationManager().boundary.equals("Periodic")){	
														ArrayList<ArrayList<Integer>> neighbourhood = GlobalManager
																.getLocationManager().getNeigh(PassAgentPosition, chosenActionPass.getRangePass());
														double[] ProbLoc = new double[neighbourhood.size()];
														for (int j = 0; j < neighbourhood.size(); j++) {
															ProbLoc[j] = (1.0 / neighbourhood.size());
														}
														int[] neighbourhoodEntries = new int[neighbourhood.size()];
														for (int j = 0; j < neighbourhood.size(); j++) {
															neighbourhoodEntries[j] = j;
														}
														int locNewEntry = Samples.getDiscrete(neighbourhoodEntries,
																ProbLoc);
														ArrayList<Integer> locNewName = neighbourhood.get(locNewEntry);
														GlobalManager.getAgentManager().GlobalMatrix[PassAgentName][GlobalManager
																		.getLocationManager().MatrixLoc
																				.get(PassAgentPosition)]--;
														GlobalManager.getAgentManager().GlobalMatrix[agentArrayList
																.get(action_index)][GlobalManager
																		.getLocationManager().MatrixLoc
																				.get(locNewName)]++;
														WhatToPrintP = "SpatialAction ";
														newPositionArrayP.set(action_index,locNewName);
														}else{if(GlobalManager.getLocationManager().boundary.equals("Bouncing")){
															//TO ADD: if the action involves agent R (repellent) then movement is different, away from R
															// AgentNames.get(agentArrayList.get(action_index))
															if(GlobalManager.getAgentManager().AgentNames.get(agentArrayList.get(action_index)).equals("R")){
															InfAction infaction = (InfAction) action;
															ArrayList<Integer> actualpositionInf = agentPositionArray.get(action_index);
															ArrayList<ArrayList<Integer>> newPosPassCandidates = new ArrayList<>();
															for (ArrayList<Integer> t : GlobalManager.getLocationManager().getNeighBouncing(
																	PassAgentPosition, infaction.getRangeNeigh())) {														
															if ((GlobalManager.getLocationManager().distance(PassAgentPosition, actualpositionInf) <= GlobalManager.getLocationManager().distance(t, actualpositionInf)) 
															){
																newPosPassCandidates.add(t);
															}}
															
															//select a random new position, in the list of possible ones
															Random rand = new Random();
															int range = newPosPassCandidates.size();
															int newPosIndex = rand.nextInt(range);
															
															ArrayList<Integer> newPos = newPosPassCandidates.get(newPosIndex);

															GlobalManager.getAgentManager().GlobalMatrix[PassAgentName][GlobalManager
																			.getLocationManager().MatrixLoc
																					.get(PassAgentPosition)]--;
															
															GlobalManager.getAgentManager().GlobalMatrix[PassAgentName][GlobalManager.getLocationManager().MatrixLoc.get(newPos)]++;
															WhatToPrintP = "SpatialAction ";
															newPositionArrayP.set(action_index,newPos);
															
															}else{
																		
//															//the passive agent will move to the cell of the active one
//															
//															ArrayList<Integer> actualpositionActive = agentPositionArray.get(action_index);
//															
//															GlobalManager.getAgentManager().GlobalMatrix[PassAgentName][GlobalManager
//																			.getLocationManager().MatrixLoc
//																					.get(PassAgentPosition)]--;
//															
////															System.out.println(PassAgentName + " -1 " + GlobalManager
////																	.getLocationManager().MatrixLoc
////																	.get(PassAgentPosition) + " " + PassAgentPosition);
//															GlobalManager.getAgentManager().GlobalMatrix[PassAgentName][GlobalManager
//																			.getLocationManager().MatrixLoc
//																					.get(actualpositionActive)]++;
//															WhatToPrintP = "SpatialAction ";
//															newPositionArrayP.set(action_index,actualpositionActive);
//															
////															System.out.println(agentArrayList
////																				.get(action_index) + " +1 " + GlobalManager
////																				.getLocationManager().MatrixLoc
////																				.get(actualpositionActive) + " " + actualpositionActive);
																
															//same idea, but now attraction, therefore locations closer to the agent	
																
															InfAction infaction = (InfAction) action;
															ArrayList<Integer> actualpositionInf = agentPositionArray.get(action_index);
																
															ArrayList<ArrayList<Integer>> newPosPassCandidates = new ArrayList<>();
															for (ArrayList<Integer> t : GlobalManager.getLocationManager().getNeighBouncing(
																	PassAgentPosition, infaction.getRangeNeigh())) {		
															if ((GlobalManager.getLocationManager().distance(PassAgentPosition, actualpositionInf) >= GlobalManager.getLocationManager().distance(t, actualpositionInf)) 
															){
																newPosPassCandidates.add(t);
																System.out.println(t);
															}else{newPosPassCandidates.add(PassAgentPosition);}}
															
															//select a random new position, in the list of possible ones
															Random rand = new Random();
															int range = newPosPassCandidates.size();
															System.out.println(range);
															int newPosIndex = rand.nextInt(range);
														    
															ArrayList<Integer> newPos = newPosPassCandidates.get(newPosIndex);

															GlobalManager.getAgentManager().GlobalMatrix[PassAgentName][GlobalManager
																			.getLocationManager().MatrixLoc
																					.get(PassAgentPosition)]--;
																
															GlobalManager.getAgentManager().GlobalMatrix[PassAgentName][GlobalManager.getLocationManager().MatrixLoc.get(newPos)]++;
															WhatToPrintP = "SpatialAction ";
															newPositionArrayP.set(action_index,newPos);
														
														}}else{if(GlobalManager.getLocationManager().boundary.equals("Absorbing")){
															//DOUBLE CHECK THIS PART!!
															if(GlobalManager.getAgentManager().AgentNames.get(agentArrayList.get(action_index)).equals("R")){
															InfAction infaction = (InfAction) action;
															ArrayList<Integer> actualpositionInf = agentPositionArray.get(action_index);
															ArrayList<ArrayList<Integer>> newPosPassCandidates = new ArrayList<>();
															for (ArrayList<Integer> t : GlobalManager.getLocationManager().getNeighAbsorbing(
																	PassAgentPosition, infaction.getRangeNeigh())) {														
															if ((GlobalManager.getLocationManager().distance(PassAgentPosition, actualpositionInf) <= GlobalManager.getLocationManager().distance(t, actualpositionInf)) 
															){
																newPosPassCandidates.add(t);
															}}
															
															//select a random new position, in the list of possible ones
															Random rand = new Random();
															int range = newPosPassCandidates.size();
															int newPosIndex = rand.nextInt(range);
															
															ArrayList<Integer> newPos = newPosPassCandidates.get(newPosIndex);

															GlobalManager.getAgentManager().GlobalMatrix[PassAgentName][GlobalManager
																			.getLocationManager().MatrixLoc
																					.get(PassAgentPosition)]--;
															
															GlobalManager.getAgentManager().GlobalMatrix[PassAgentName][GlobalManager.getLocationManager().MatrixLoc.get(newPos)]++;
															WhatToPrintP = "SpatialAction ";
															newPositionArrayP.set(action_index,newPos);
															
															}else{
																		
//															//the passive agent will move to the cell of the active one
//															
//															ArrayList<Integer> actualpositionActive = agentPositionArray.get(action_index);
//															
//															GlobalManager.getAgentManager().GlobalMatrix[PassAgentName][GlobalManager
//																			.getLocationManager().MatrixLoc
//																					.get(PassAgentPosition)]--;
//															
////															System.out.println(PassAgentName + " -1 " + GlobalManager
////																	.getLocationManager().MatrixLoc
////																	.get(PassAgentPosition) + " " + PassAgentPosition);
//															GlobalManager.getAgentManager().GlobalMatrix[PassAgentName][GlobalManager
//																			.getLocationManager().MatrixLoc
//																					.get(actualpositionActive)]++;
//															WhatToPrintP = "SpatialAction ";
//															newPositionArrayP.set(action_index,actualpositionActive);
//															
////															System.out.println(agentArrayList
////																				.get(action_index) + " +1 " + GlobalManager
////																				.getLocationManager().MatrixLoc
////																				.get(actualpositionActive) + " " + actualpositionActive);
																
															//same idea, but now attraction, therefore locations closer to the agent	
																
															InfAction infaction = (InfAction) action;
															ArrayList<Integer> actualpositionInf = agentPositionArray.get(action_index);
																
															ArrayList<ArrayList<Integer>> newPosPassCandidates = new ArrayList<>();
															for (ArrayList<Integer> t : GlobalManager.getLocationManager().getNeighAbsorbing(
																	PassAgentPosition, infaction.getRangeNeigh())) {		
															if ((GlobalManager.getLocationManager().distance(PassAgentPosition, actualpositionInf) >= GlobalManager.getLocationManager().distance(t, actualpositionInf)) 
															){
																newPosPassCandidates.add(t);
																System.out.println(t);
															}else{newPosPassCandidates.add(PassAgentPosition);}}
															
															//select a random new position, in the list of possible ones
															Random rand = new Random();
															int range = newPosPassCandidates.size();
															System.out.println(range);
															int newPosIndex = rand.nextInt(range);
														    
															ArrayList<Integer> newPos = newPosPassCandidates.get(newPosIndex);

															GlobalManager.getAgentManager().GlobalMatrix[PassAgentName][GlobalManager
																			.getLocationManager().MatrixLoc
																					.get(PassAgentPosition)]--;
																
															GlobalManager.getAgentManager().GlobalMatrix[PassAgentName][GlobalManager.getLocationManager().MatrixLoc.get(newPos)]++;
															WhatToPrintP = "SpatialAction ";
															newPositionArrayP.set(action_index,newPos);
														
														}}}}} else {

														// change of state
														String newState = chosenActionPass.getUpdate();
														int newStatePos = 0;
														for (int q = 0; q < GlobalManager.getAgentManager().AgentNames
																.size(); q++) {
															if (newState.equals(
																	GlobalManager.getAgentManager().AgentNames.get(q)
																			+ "(l)")) {
																newStatePos = q;
															}
														}
														GlobalManager
																.getAgentManager().GlobalMatrix[PassAgentName][GlobalManager
																		.getLocationManager().MatrixLoc
																				.get(PassAgentPosition)]--;
														GlobalManager
																.getAgentManager().GlobalMatrix[newStatePos][GlobalManager
																		.getLocationManager().MatrixLoc
																				.get(PassAgentPosition)]++;
														WhatToPrintP = "StateAction ";
													}
												}
											}
										}
									}
								}
							}}}}else{if (Type.get(action_index) == Action.ACTION_TYPE_Env) {
								double probEffect = passiveProbArray.get(action_index);
								if (StdRandom.bernoulli(probEffect)) {
										String nameToCheckEnv = nameActions.get(action_index);
										
										//cost section - env
										 cost = Cost.Update(cost, nameToCheckEnv);
										
										
										for (int k = 0; k < GlobalManager.getAgentManager().AgentNames.size(); k++) {
											for (Action actionToCheckPass : GlobalManager.getAgentManager().Agents
													.get(k).getActionList()) {
														if (actionToCheckPass.getName().equals(nameToCheckEnv)
														&& actionToCheckPass.getType() == Action.ACTION_TYPE_Pass) {															
													PassAction chosenActionPass = (PassAction) actionToCheckPass;
													String symbolPassEnv = chosenActionPass.getSymbol();
													int PassAgentName = k;
													ArrayList<Integer> PassAgentPosition = passiveAgentPositionArray
															.get(action_index);
													// demo+
													if (symbolPassEnv == "<<") {
														GlobalManager
																.getAgentManager().GlobalMatrix[PassAgentName][GlobalManager
																		.getLocationManager().MatrixLoc
																				.get(PassAgentPosition)]++;
														WhatToPrintA = "x ";
														WhatToPrintP = "Demographic+Action ";
													}
													// demo-
													if (symbolPassEnv == ">>") {
														GlobalManager
																.getAgentManager().GlobalMatrix[PassAgentName][GlobalManager
																		.getLocationManager().MatrixLoc
																				.get(PassAgentPosition)]--;
														WhatToPrintA = "x ";
														WhatToPrintP = "Demographic-Action ";
													}
													if (symbolPassEnv == ".") {
														if (chosenActionPass.getUpdate().matches("(.*)new(.*)")) {
															// so far just random movement
															if(GlobalManager.getLocationManager().boundary.equals("Periodic")){
															ArrayList<ArrayList<Integer>> neighbourhood = GlobalManager
																	.getLocationManager().getNeigh(PassAgentPosition, chosenActionPass.getRangePass());
															double[] ProbLoc = new double[neighbourhood.size()];
															for (int j = 0; j < neighbourhood.size(); j++) {
																ProbLoc[j] = (1.0 / neighbourhood.size());
															}
															int[] neighbourhoodEntries = new int[neighbourhood.size()];
															for (int j = 0; j < neighbourhood.size(); j++) {
																neighbourhoodEntries[j] = j;
															}
															int locNewEntry = Samples.getDiscrete(neighbourhoodEntries,
																	ProbLoc);
															ArrayList<Integer> locNewName = neighbourhood
																	.get(locNewEntry);
															GlobalManager
																	.getAgentManager().GlobalMatrix[PassAgentName][GlobalManager
																			.getLocationManager().MatrixLoc
																					.get(PassAgentPosition)]--;
															GlobalManager
																	.getAgentManager().GlobalMatrix[PassAgentName][GlobalManager
																			.getLocationManager().MatrixLoc
																					.get(locNewName)]++;
															WhatToPrintA = "x ";
															WhatToPrintP = "SpatialAction ";
															newPositionArrayP.set(action_index,locNewName);
														}else{
															if(GlobalManager.getLocationManager().boundary.equals("Bouncing")){
																ArrayList<ArrayList<Integer>> neighbourhood = GlobalManager
																		.getLocationManager().getNeigh(PassAgentPosition, chosenActionPass.getRangePass());
																double[] ProbLoc = new double[neighbourhood.size()];
																for (int j = 0; j < neighbourhood.size(); j++) {
																	ProbLoc[j] = (1.0 / neighbourhood.size());
																}
																int[] neighbourhoodEntries = new int[neighbourhood.size()];
																for (int j = 0; j < neighbourhood.size(); j++) {
																	neighbourhoodEntries[j] = j;
																}
																int locNewEntry = Samples.getDiscrete(neighbourhoodEntries,
																		ProbLoc);
																ArrayList<Integer> locNewName = neighbourhood
																		.get(locNewEntry);
																if(GlobalManager.getLocationManager().AllLoc.contains(locNewName)){
																GlobalManager
																		.getAgentManager().GlobalMatrix[PassAgentName][GlobalManager
																				.getLocationManager().MatrixLoc
																						.get(PassAgentPosition)]--;
																GlobalManager
																		.getAgentManager().GlobalMatrix[PassAgentName][GlobalManager
																				.getLocationManager().MatrixLoc
																						.get(locNewName)]++;
																WhatToPrintA = "x ";
																WhatToPrintP = "SpatialAction ";
																newPositionArrayP.set(action_index,locNewName);}else{
																WhatToPrintA = "x ";
																WhatToPrintP = "NoChange ";
																}
															}
														}
														
														} else {
															// change of state
															String newState = chosenActionPass.getUpdate();
															int newStatePos = 0;
															for (int q = 0; q < GlobalManager
																	.getAgentManager().AgentNames.size(); q++) {
																if (newState == GlobalManager
																		.getAgentManager().AgentNames.get(q) + "(l)") {
																	newStatePos = q;
																}
															}
															GlobalManager
																	.getAgentManager().GlobalMatrix[PassAgentName][GlobalManager
																			.getLocationManager().MatrixLoc
																					.get(PassAgentPosition)]--;
															GlobalManager
																	.getAgentManager().GlobalMatrix[newStatePos][GlobalManager
																			.getLocationManager().MatrixLoc
																					.get(PassAgentPosition)]++;
															WhatToPrintA = "x ";
															WhatToPrintP = "StateAction ";
														}
													}
												}
											}
										}
									}else{
						WhatToPrintA = "No_change ";
						WhatToPrintP = "No_change ";
					}}}}
				

				// for(int i=0; i <
				// GlobalManager.getAgentManager().AgentNames.size(); i++){
				// for(int j=0; j <
				// GlobalManager.getLocationManager().AllLoc.size(); j++){
				// String formatedString =
				// GlobalManager.getLocationManager().AllLoc.get(j).toString()
				// .replace("[", "") //remove the right bracket
				// .replace("]", "") //remove the left bracket
				// .trim();
				// System.out.println(GlobalManager.getAgentManager().AgentNames.get(i)
				// + "(" + formatedString + ")=" +
				// GlobalManager.getAgentManager().GlobalMatrix[i][j]);
				// }}}
				// System.out.println("Time: " + time);
				// System.out.println("-----");
				// }}

				// piece of code used to print just line of a specific action
				// type

//				if (GlobalManager.SpecificType != null) {
//			    //this option will not be used, since we wil not have a specific type of action directly in the simulation anymore
//					if (WhatToPrint.contains(GlobalManager.SpecificType)) {
//						writer_data.print(time + " ");
//						for (int i = 0; i < GlobalManager.getAgentManager().AgentNames.size(); i++) {
//							for (int j = 0; j < GlobalManager.getLocationManager().AllLoc.size(); j++) {
//								writer_data.print(GlobalManager.getAgentManager().GlobalMatrix[i][j] + " ");
//							}
//						}			
//						writer_meta.print(nameActions.get(action_index) + " ");
//						writer_meta.print(Type.get(action_index) + " ");
//						writer_meta.println(WhatToPrintA);
//				        writer_meta.print(WhatToPrintP);
//						writer_meta.println(" ");
//						writer_data.println(" ");
//					}
//				} else {
				
				
					writer_data.print(time + " ");
					writer_cost.println(time + " " + cost);
					for (int i = 0; i < GlobalManager.getAgentManager().AgentNames.size(); i++) {
						for (int j = 0; j < GlobalManager.getLocationManager().AllLoc.size(); j++) {
							writer_data.print(GlobalManager.getAgentManager().GlobalMatrix[i][j] + " ");
						}
					}
					
					//for total matrix

					for (int i = 0; i < GlobalManager.getAgentManager().AgentNames.size(); i++) {
						for (int j = 0; j < GlobalManager.getLocationManager().AllLoc.size(); j++) {	
							ArrayList<Integer> arr = new ArrayList<>();
							arr.add(TimeArrayList.size());
							arr.add(i);
							arr.add(j);
							Double value = GlobalManager.getAgentManager().GlobalMatrix[i][j] * 1.0;
							SaveAll.put(arr,value);		
							//System.out.println(arr.get(0) + " " + arr.get(1) + " " + arr.get(2) + " " + value);
						}}

					TimeArrayList.add(time);
					
             		writer_meta.print(nameActions.get(action_index) + " ");
					writer_meta.print(Type.get(action_index) + " ");
					writer_meta.print(WhatToPrintA);
					writer_meta.print(WhatToPrintP);
						if (Type.get(action_index) == Action.ACTION_TYPE_NoInf) {
						if (WhatToPrintA == "SpatialAction "){
							writer_meta.print("(" + agentPositionArray.get(action_index) + "," + newPositionArrayA.get(action_index) + ") x ");
						}else{
							writer_meta.print(agentPositionArray.get(action_index) + " x ");
						}
					}
					
					if (Type.get(action_index) == Action.ACTION_TYPE_Inf) {
						if (WhatToPrintA == "SpatialAction "){
							writer_meta.print("(" + agentPositionArray.get(action_index) + "," + newPositionArrayA.get(action_index) + ") x ");
						}else{
							writer_meta.print(agentPositionArray.get(action_index) + " ");
						}
						if (WhatToPrintP == "SpatialAction "){
							writer_meta.print("(" + passiveAgentPositionArray.get(action_index) + "," + newPositionArrayP.get(action_index) + ") x ");
						}else{
							writer_meta.print(passiveAgentPositionArray.get(action_index));
						}
					}
					
					if (Type.get(action_index) == Action.ACTION_TYPE_Env) {
						if (WhatToPrintP == "SpatialAction "){
							writer_meta.print("x (" + passiveAgentPositionArray.get(action_index) + "," + newPositionArrayP.get(action_index) + ")");
						}else{
							writer_meta.print("x " + passiveAgentPositionArray.get(action_index));
						}
					}
												
					writer_meta.println(" ");
					writer_data.println(" ");
					
	//			}

				// PIECE OF CODE THAT TRACK PASSIVE AGENT POSITION -> WHERE
				// contact1 HAPPENS
				if (nameActions.get(action_index).equals(GlobalManager.GetAction())) {
					if (GlobalManager.actionChosenType.equals("p")) {
						System.out.println("Location where " + GlobalManager.GetAction() + " happened: "
								+ passiveAgentPositionArray.get(action_index));
						if (!LocationR.contains(passiveAgentPositionArray.get(action_index))) {
							LocationR.add(passiveAgentPositionArray.get(action_index));
						}
					} else {
						System.out.println("Location where " + GlobalManager.GetAction() + " happened: "
								+ agentPositionArray.get(action_index));
						if (!LocationR.contains(agentPositionArray.get(action_index))) {
							LocationR.add(agentPositionArray.get(action_index));
						}
					}
				}
				
				
				}else{
					//System.out.println("Time expired");
				}

				// System.out.println();
				
				int value = 0;
				
				for (int i = 0; i < GlobalManager.getAgentManager().AgentNames.size(); i++) {
					for (int j = 0; j < GlobalManager.getLocationManager().AllLoc.size(); j++) {	
						if (GlobalManager.getAgentManager().AgentNames.get(i).equals(TotalCells.TotalCells.AgentName[0])){
							value = value +  GlobalManager.getAgentManager().GlobalMatrix[i][j];
						}}}
				
				int value2 = 0;
				
				for (int i = 0; i < GlobalManager.getAgentManager().AgentNames.size(); i++) {
					for (int j = 0; j < GlobalManager.getLocationManager().AllLoc.size(); j++) {	
						if (GlobalManager.getAgentManager().AgentNames.get(i).equals(TotalCells.TotalCells.AgentName[1])){
							value2 = value2 +  GlobalManager.getAgentManager().GlobalMatrix[i][j];
						}}}
				writer_Agents.println(time + " " + value + " " + value2);
				
			
			
		}}
		
		writer_log.close();
		writer_data.close();
		writer_meta.close();
		writer_cost.close();
		writer_Agents.close();
//		System.out.println(BigAgents.size());
//		System.out.println(BigAgents.get(0).size());
//		System.out.println(BigAgents.get(0).get(0).size());
//		System.out.println(TimeArray.size());

		int intLoc = GlobalManager.getLocationManager().AllLoc.size();
		int intTime = TimeArrayList.size();
		int intAgents = GlobalManager.getAgentManager().AgentNames.size();
		System.out.println("intLoc:" + intLoc + ", intTime:" + intTime + ", intAgents:" + intAgents);
		data = new double [intLoc][intTime][intAgents];		

//		for(int j=0; j < TimeArrayList.size(); j++){
//			System.out.println(TimeArrayList.get(j));
//		}
		
		for (int i=0; i< intTime; i++){
			for (int j = 0; j < intAgents; j++){
				for (int k=0; k< intLoc; k++){
					ArrayList<Integer> Key = new ArrayList<>();
					Key.add(i);
					Key.add(j);
					Key.add(k);
					//System.out.println(i + " " + j + " "+ k);
					//System.out.println(SaveAll.get(Key));
					data[k][i][j]= SaveAll.get(Key);
				}
			  }
			}

		
		timeArray = new double[TimeArrayList.size()];
		
		for(int j=0; j < TimeArrayList.size(); j++){
			timeArray[j] = TimeArrayList.get(j);
		}
		
//		for (int i=0; i < BigAgents.size(); i++){
//			for (int j = 0; j < intAgents; j++){
//				for (int k = 0; k < intLoc; k++){
//		System.out.println("At time " + TimeArray.get(i) + " value of agent " + GlobalManager.getAgentManager().AgentNames.get(j) + " at loc "  + GlobalManager.getLocationManager().AllLoc.get(k) + " -> " + data[i][k][j]);
//		}}}
		
//		int valueX = GlobalManager.getLocationManager().TwoDx;
//		int valueY = GlobalManager.getLocationManager().TwoDy;
//		
//		dataFinal = new double [intTime][valueX][valueY][intAgents];
//		
//		for (int i=0; i < BigAgents.size(); i++){
//			for (int j=0; j < valueX; j++){
//				for (int k=0; k < valueY; k++){
//					for (int s = 0; s < intAgents; s++){			
//			dataFinal[i][j][k][s] = BigAgents.get(i).get(s).get(2);
//		}}}}
		
//		for (int i=0; i < BigAgents.size(); i++){
//			for (int j=0; j < valueX; j++){
//				for (int k=0; k < valueY; k++){
//					for (int s = 0; s < intAgents; s++){	
//		System.out.println("At time " + TimeArray.get(i) + " value of agent " + GlobalManager.getAgentManager().AgentNames.get(s) + " at loc "  + j + " " + k + " -> " + dataFinal[i][j][k][s]);
//		}}}}
		
		if (GlobalManager.GetAction() == null) {
			System.out.println("There was no action to track.");
		} else {
			System.out.println("The locations where " + GlobalManager.GetAction() + " happened are: ");
			for (int k = 0; k < LocationR.size(); k++) {
				System.out.println(LocationR.get(k));
			}
			System.out.println("Number of locations: " + LocationR.size());
		}
		
		}

	// methods

	public static double sum(ArrayList<Double> list) {
		double sum = 0;
		for (int i = 0; i < list.size(); i++) {
			sum = sum + list.get(i);
		}
		return sum;
	}

	public static void addAction(Action a, ArrayList<Action> list) {
		list.add(a);
	}

	public static void addName(String name, ArrayList<String> list) {
		list.add(name);
	}

	public static void addLoc(Integer loc, ArrayList<Integer> list) {
		list.add(loc);
	}

	public static double getTime() {
		return time;
	}

}
