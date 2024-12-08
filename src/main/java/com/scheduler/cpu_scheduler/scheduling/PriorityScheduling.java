package com.scheduler.cpu_scheduler.scheduling;

import java.util.Queue;

import org.springframework.stereotype.Component;

import java.util.PriorityQueue;

import com.scheduler.cpu_scheduler.models.Algorithm;
import com.scheduler.cpu_scheduler.models.Task;
import com.scheduler.cpu_scheduler.services.CPU;

@Component("priorityScheduling")
public class PriorityScheduling extends CPU implements Algorithm {
	
	//These initializations are for the two possible orders of priority (ascending/descending) and the choice is dependent on the user
	private PriorityQueue<Task> waitDescending = new PriorityQueue<>((a, b) -> { return b.getPriority() - a.getPriority(); });
	private PriorityQueue<Task> waitAscending = new PriorityQueue<>((a, b) -> { return a.getPriority() - b.getPriority(); });
	private boolean typeFlag;

	public void setTypeFlag(boolean type) {
		this.typeFlag = type;
	}


	public void schedule() {
    	//Initializing the necessary variables for scheduling.
		int burstTime;
    	String taskName;
		int taskArrival;

		//Performs the necessary pre-scheduling methods for calculating performance, sorting the task list, and displaying output
		storeBurst(taskList);
		queueSorting();

		while(!taskList.isEmpty()) {
			checkArrivals(taskList, typeFlag);
			Task hp = pickNextTask();
			if (hp == null) {//To advance the CPU time in case no task arrived
				incrementCpuTime();;
				continue;
			}
			taskName = hp.getName();
    		burstTime = hp.getBurst();
			taskArrival = hp.getArrivalTime();
    		storeStart(taskName, getCpuTime(), taskArrival);//To store start info for the task for performance calculations later
    		updateCpuTime(burstTime);
    		run(hp, getCpuTime());
    		storeCompletion(taskName, getCpuTime(), taskArrival);//To store completion info for the task for performance calculations later
			taskList.remove(hp);
		}
	}

	public void checkArrivals(Queue<Task> taskList, boolean type) {
		/*
		 * Method to add tasks to the wait queue which orders them per PS rules
		 */
		if (type) {//True = descending priority
			for (Task t : taskList) {
				if (t.getArrivalTime() <= getCpuTime() && !waitDescending.contains(t)) {
					waitDescending.add(t);
				}
			}
		} else { //False = ascending priority
			for (Task t : taskList) {
				if (t.getArrivalTime() <= getCpuTime() && !waitAscending.contains(t)) {
					waitAscending.add(t);
				}
			}
		}
	}

    public Task pickNextTask() {
		Task nextTask = null;

		if (typeFlag) {//True = descending priority
			if (waitDescending.isEmpty()) {
				nextTask = null;
			} else {
				nextTask = waitDescending.remove();
			}
		} else {//False = ascending priority
			if (waitAscending.isEmpty()) {
				nextTask = null;
			} else {
				nextTask = waitAscending.remove();
			}
		}

		return nextTask;
		}
}