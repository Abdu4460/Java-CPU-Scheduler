package com.scheduler.cpuScheduler.scheduling;

import com.scheduler.cpuScheduler.models.Task;
import com.scheduler.cpuScheduler.services.CPU;

public class RRscheduling extends CPU implements Algorithm {

	private static int RRQuantum; //Can be specified by the user in the Driver class

	public void setQuantum(int quantumPeriod) {
		RRQuantum = quantumPeriod;
	}
    
	public void schedule() { 
    	//Initializing the necessary variables for scheduling.
		int burst_time;
    	String task_name;
		int arrival_time;
		int fullList = taskList.size();

		//Performs the necessary pre-scheduling methods for calculating performance, sorting the task list, and displaying output
		storeBurst(taskList);
		queueSorting();
		printTableHeader(false); 

		while(!taskList.isEmpty()) { 
			int displayQuantum; 
			Task RR = pickNextTask();
			if (RR == null) {//To advance the CPU time in case no task arrived
				CPUTime++;
				continue;
			}
			task_name = RR.getName();
			burst_time = RR.getBurst();
			arrival_time = RR.getArrivalTime();
			
			for(Object[] each: burst) {//To store start info for the task for performance calculations later
				if (each[0].equals(task_name) && start.size() <= fullList) {
					storeStart(task_name, CPUTime, arrival_time);
				}
			}

			if(burst_time > RRQuantum) {//this if-else-if is to find the next amount of burst to be subtracted
    			displayQuantum = RRQuantum;//first block will subtract RRQuantum if the remaining burst > RRQuantum
    			RR.setBurst(burst_time - RRQuantum);
    			CPUTime = CPUTime + RRQuantum;
    			taskList.add(RR);//pushes the task back into list for its next turn
    		}
    		else if(burst_time == RRQuantum) {//this will subtract RRQuantum and mark the task as complete since remaining burst = RRQuantum
    			displayQuantum = RRQuantum;
    			RR.setBurst(burst_time - RRQuantum);
    			CPUTime = CPUTime + RRQuantum;
    			storeCompletion(task_name, CPUTime, arrival_time);//To store completion info for the task for performance calculations later
    			}
    		else {
    			displayQuantum = burst_time;
    			RR.setBurst(burst_time - burst_time);//this will subtract the burst time if the remaining burst < RRQuantum
    			CPUTime = CPUTime + burst_time;
    			storeCompletion(task_name, CPUTime, arrival_time);//To store completion info for the task for performance calculations later
    			}
			CPU.run(RR, displayQuantum, CPUTime);
		}
		System.out.println("<==================================================================================>");
		printStats();
	}

	public Task pickNextTask() {
		Task nextTask;

		//Round-Robin scheduling has similar rules to FCFS for the next task since it's basically FCFS but with rotating tasks
		if (taskList.peek().getArrivalTime() <= CPUTime) {
			nextTask = taskList.remove();
		} else {
			nextTask = null;
		}

		return nextTask;
	}

}
