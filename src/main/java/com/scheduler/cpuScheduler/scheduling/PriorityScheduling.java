package com.scheduler.cpuScheduler.scheduling;
import java.util.PriorityQueue;
import java.util.Queue;

import com.scheduler.cpuScheduler.models.Task;
import com.scheduler.cpuScheduler.services.CPU;

public class PriorityScheduling extends CPU implements Algorithm {
	
	//These initializations are for the two possible orders of priority (ascending/descending) and the choice is dependent on the user
	private static PriorityQueue<Task> waitDes = new PriorityQueue<Task>((a, b) -> { return b.getPriority() - a.getPriority(); });
	private static PriorityQueue<Task> waitAsc = new PriorityQueue<Task>((a, b) -> { return a.getPriority() - b.getPriority(); });
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
    	printTableHeader(true);

		while(!taskList.isEmpty()) {
			checkArrivals(taskList, typeFlag);
			Task hp = pickNextTask();
			if (hp == null) {//To advance the CPU time in case no task arrived
				CPUTime++;
				continue;
			}
			task_name = hp.getName();
    		burst_time = hp.getBurst();
			task_arrival = hp.getArrivalTime();
    		storeStart(task_name, CPUTime, task_arrival);//To store start info for the task for performance calculations later
    		CPUTime = CPUTime + burst_time;
    		CPU.run(hp, CPUTime);
    		storeCompletion(task_name, CPUTime, task_arrival);//To store completion info for the task for performance calculations later
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
				if (t.getArrivalTime() <= CPUTime && !waitDes.contains(t)) {
					waitDes.add(t);
				}
			}
		} else { //False = ascending priority
			for (Task t : taskList) {
				if (t.getArrivalTime() <= CPUTime && !waitAsc.contains(t)) {
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