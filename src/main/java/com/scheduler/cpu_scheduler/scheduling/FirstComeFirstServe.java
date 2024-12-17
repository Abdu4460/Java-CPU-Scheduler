package com.scheduler.cpu_scheduler.scheduling;

import org.springframework.stereotype.Component;

import com.scheduler.cpu_scheduler.models.Algorithm;
import com.scheduler.cpu_scheduler.models.Task;
import com.scheduler.cpu_scheduler.services.CPU;

@Component("firstComeFirstServe")
public class FirstComeFirstServe extends CPU implements Algorithm {
	
	public void schedule() { 
    	//Initializing the necessary variables for scheduling.
		int burstTime;
    	String taskName;
    	int taskArrival;

		//Performs the necessary pre-scheduling methods for calculating performance, sorting the task list, and displaying output
		storeBurst();
		queueSorting();

		while(!taskList.isEmpty()) {
			Task task = pickNextTask();
    		taskName = task.getName();
    		burstTime = task.getBurst();
			taskArrival = task.getArrivalTime();
			int startTime = getCpuTime();
    		storeStart(taskName, startTime, taskArrival);//To store start info for the task for performance calculations later
    		updateCpuTime(burstTime);
			int finishTime = getCpuTime();
			// Hard-coded remaining time because in this case the task will run fully before releasing resources
    		run(task, finishTime, 0, burstTime, finishTime);
    		storeCompletion(taskName, finishTime, taskArrival);//To store completion info for the task for performance calculations later
		}

		storeStats();
	}
	

	public Task pickNextTask() {
		Task nextTask = taskList.remove();
		
		//To advance the CPU time in case no task arrived
		while(nextTask.getArrivalTime() > getCpuTime()) {
			incrementCpuTime();
		}

		return nextTask;
	}
		
}