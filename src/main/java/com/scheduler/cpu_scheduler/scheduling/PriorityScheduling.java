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

	private boolean priority;

	public void setPriority(boolean priority) {
		this.priority = priority;
	}

	public void schedule() {
    	//Initializing the necessary variables for scheduling.
		int burstTime;
    	String taskName;
		int taskArrival;

		//Performs the necessary pre-scheduling methods for calculating performance, sorting the task list, and displaying output
		storeBurst();
		queueSorting();

		while(!taskList.isEmpty()) {
			checkArrivals(taskList);
			Task task = pickNextTask();
			if (task == null) {//To advance the CPU time in case no task arrived
				incrementCpuTime();;
				continue;
			}
			taskName = task.getName();
    		burstTime = task.getBurst();
			taskArrival = task.getArrivalTime();
			int startTime = getCpuTime();
    		storeStart(taskName, getCpuTime(), taskArrival);//To store start info for the task for performance calculations later
    		updateCpuTime(burstTime);
			int finishTime = getCpuTime();
			// Hard-coded remaining time because in this case the task will run fully before releasing resources
    		run(task, finishTime, startTime, 0, burstTime, finishTime);
    		storeCompletion(taskName, getCpuTime(), taskArrival);//To store completion info for the task for performance calculations later
			taskList.remove(task);
		}

		storeStats();
	}

	public void checkArrivals(Queue<Task> taskList) {
		/*
		 * Method to add tasks to the wait queue which orders them per PS rules
		 */
		if (priority) {//True = descending priority
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

		if (priority) {//True = descending priority
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