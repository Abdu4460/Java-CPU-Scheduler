package com.scheduler.cpu_scheduler.scheduling;
import java.util.PriorityQueue;
import java.util.Queue;

import com.scheduler.cpu_scheduler.models.Task;
import com.scheduler.cpu_scheduler.services.CPU;

public class PriorityScheduling extends CPU implements Algorithm {
	
	//These initializations are for the two possible orders of priority (ascending/descending) and the choice is dependent on the user
	private static PriorityQueue<Task> waitDes = new PriorityQueue<>((a, b) -> { return b.getPriority() - a.getPriority(); });
	private static PriorityQueue<Task> waitAsc = new PriorityQueue<>((a, b) -> { return a.getPriority() - b.getPriority(); });
	private static boolean typeFlag;

	public void setTypeFlag(boolean type) {
		typeFlag = type;
	}


	public void schedule() {
    	//Initializing the necessary variables for scheduling.
		int burst_time;
    	String task_name;
		int task_arrival;

		//Performs the necessary pre-scheduling methods for calculating performance, sorting the task list, and displaying output
		storeBurst(taskList);
		queueSorting();

		while(!taskList.isEmpty()) {
			checkArrivals(taskList, typeFlag);
			Task hp = pickNextTask();
			if (hp == null) {//To advance the CPU time in case no task arrived
				cpuTime++;
				continue;
			}
			task_name = hp.getName();
    		burst_time = hp.getBurst();
			task_arrival = hp.getArrivalTime();
    		storeStart(task_name, cpuTime, task_arrival);//To store start info for the task for performance calculations later
    		cpuTime = cpuTime + burst_time;
    		CPU.run(hp, cpuTime);
    		storeCompletion(task_name, cpuTime, task_arrival);//To store completion info for the task for performance calculations later
			taskList.remove(hp);
		}
		System.out.println("<==========================================================================>");
		printStats();
	}

	public void checkArrivals(Queue<Task> taskList, boolean type) {
		/*
		 * Method to add tasks to the wait queue which orders them per PS rules
		 */
		if (type) {//True = descending priority
			for (Task t : taskList) {
				if (t.getArrivalTime() <= cpuTime && !waitDes.contains(t)) {
					waitDes.add(t);
				}
			}
		} else { //False = ascending priority
			for (Task t : taskList) {
				if (t.getArrivalTime() <= cpuTime && !waitAsc.contains(t)) {
					waitAsc.add(t);
				}
			}
		}
	}

    public Task pickNextTask() {
		Task nextTask = null;

		if (typeFlag) {//True = descending priority
			if (waitDes.isEmpty()) {
				nextTask = null;
			} else {
				nextTask = waitDes.remove();
			}
		} else {//False = ascending priority
			if (waitAsc.isEmpty()) {
				nextTask = null;
			} else {
				nextTask = waitAsc.remove();
			}
		}

		return nextTask;
		}
}