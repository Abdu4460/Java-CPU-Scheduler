package com.scheduler.cpu_scheduler.scheduling;

import org.springframework.stereotype.Component;

import com.scheduler.cpu_scheduler.models.Algorithm;
import com.scheduler.cpu_scheduler.models.Task;
import com.scheduler.cpu_scheduler.services.CPU;

@Component("roundRobinScheduling")
public class RoundRobinScheduling extends CPU implements Algorithm {

	private int quantum; //Can be specified by the user in the Driver class

	public void setQuantum(int quantumPeriod) {
		this.quantum = quantumPeriod;
	}
    
	public void schedule() { 
    	//Initializing the necessary variables for scheduling.
		int burstTime;
    	String taskName;
		int arrivalTime;
		int fullListSize = taskList.size();

		//Performs the necessary pre-scheduling methods for calculating performance, sorting the task list, and displaying output
		storeBurst();
		queueSorting();
		
		while(!taskList.isEmpty()) { 
			int displayQuantum; 
			Task roundRobinTask = pickNextTask();
			if (roundRobinTask == null) {//To advance the CPU time in case no task arrived
				getCpuTime();
				continue;
			}
			taskName = roundRobinTask.getName();
			burstTime = roundRobinTask.getBurst();
			arrivalTime = roundRobinTask.getArrivalTime();
			
			//To store start info for the task for performance calculations later
			if (start.size() <= fullListSize);
			storeStart(taskName, getCpuTime(), arrivalTime);

			if(burstTime > quantum) {//this if-else-if is to find the next amount of burst to be subtracted
    			displayQuantum = quantum;//first block will subtract RRQuantum if the remaining burst > RRQuantum
    			roundRobinTask.setBurst(burstTime - quantum);
    			updateCpuTime(quantum);
    			taskList.add(roundRobinTask);//pushes the task back into list for its next turn
    		}
    		else if(burstTime == quantum) {//this will subtract RRQuantum and mark the task as complete since remaining burst = RRQuantum
    			displayQuantum = quantum;
    			roundRobinTask.setBurst(burstTime - quantum);
				updateCpuTime(quantum);
    			storeCompletion(taskName, getCpuTime(), arrivalTime);//To store completion info for the task for performance calculations later
    			}
    		else {
    			displayQuantum = burstTime;
    			roundRobinTask.setBurst(burstTime - burstTime);//this will subtract the burst time if the remaining burst < RRQuantum
    			updateCpuTime(burstTime);
    			storeCompletion(taskName, getCpuTime(), arrivalTime);//To store completion info for the task for performance calculations later
    			}
			run(roundRobinTask, displayQuantum, getCpuTime());
		}
		storeStats();
	}

	public Task pickNextTask() {
		Task nextTask;

		//Round-Robin scheduling has similar rules to FCFS for the next task since it's basically FCFS but with rotating tasks
		if (taskList.peek().getArrivalTime() <= getCpuTime()) {
			nextTask = taskList.remove();
		} else {
			nextTask = null;
		}

		return nextTask;
	}

}
