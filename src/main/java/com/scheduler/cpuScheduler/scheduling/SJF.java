package com.scheduler.cpuScheduler.scheduling;
import java.util.PriorityQueue;
import java.util.Queue;

import com.scheduler.cpuScheduler.models.Task;
import com.scheduler.cpuScheduler.services.CPU;

public class SJF extends CPU implements Algorithm {
    
	PriorityQueue<Task> wait = new PriorityQueue<Task>((a, b) -> { return a.getBurst() - b.getBurst(); });

    public void schedule() { //"Runs" the task on the CPU and adds its finish information to the "completed" arraylist for calculating performance metrics
    	int burst_time;
    	String task_name;
    	int task_arrival;

    	storeBurst(taskList);
		queueSorting();
    	printTableHeader(true);

    	while(!taskList.isEmpty()) {
			checkArrivals(taskList);
			Task t = pickNextTask();
			if (t == null) {
				CPUTime++;
				continue;
			}
			task_name = t.getName();
			burst_time = t.getBurst();
			task_arrival = t.getArrivalTime();
			storeStart(task_name, CPUTime, task_arrival);//To store start info for the task for performance calculations later
			CPUTime = CPUTime + burst_time;
			CPU.run(t, CPUTime);
			storeCompletion(task_name, CPUTime, task_arrival);//To store completion info for the task for performance calculations later
			taskList.remove(t);
    	}
		System.out.println("<==========================================================================>");
    	printStats();
    }

	public void checkArrivals(Queue<Task> taskList) {
		/*
		 * Method to add tasks to the wait queue which orders them per SJF rules
		 */
		for (Task t : taskList) {
			if (t.getArrivalTime() <= CPUTime && !wait.contains(t)) {
				wait.add(t);
			}
		}
	}

    public Task pickNextTask() {
		Task nextTask = null;

		if (wait.isEmpty()) {
			nextTask = null;
		} else {
			nextTask = wait.remove();
		}

		return nextTask;
		}
}