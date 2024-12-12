package com.scheduler.cpu_scheduler.scheduling;
import java.util.PriorityQueue;
import java.util.Queue;

import org.springframework.stereotype.Component;

import com.scheduler.cpu_scheduler.models.Algorithm;
import com.scheduler.cpu_scheduler.models.Task;
import com.scheduler.cpu_scheduler.services.CPU;

@Component("shortestJobFirst")
public class ShortestJobFirst extends CPU implements Algorithm {
    
	PriorityQueue<Task> processingQueue = new PriorityQueue<Task>((a, b) -> { return a.getBurst() - b.getBurst(); });

    public void schedule() { //"Runs" the task on the CPU and adds its finish information to the "completed" arraylist for calculating performance metrics
    	int burstTime;
    	String taskName;
    	int taskArrival;

    	storeBurst();
		queueSorting();

    	while(!taskList.isEmpty()) {
			checkArrivals(taskList);
			Task t = pickNextTask();
			if (t == null) {
				incrementCpuTime();
				continue;
			}
			taskName = t.getName();
			burstTime = t.getBurst();
			taskArrival = t.getArrivalTime();
			storeStart(taskName, getCpuTime(), taskArrival);//To store start info for the task for performance calculations later
			updateCpuTime(burstTime);
			run(t, getCpuTime());
			storeCompletion(taskName, getCpuTime(), taskArrival);//To store completion info for the task for performance calculations later
			taskList.remove(t);
    	}
    	storeStats();
    }

	public void checkArrivals(Queue<Task> taskList) {
		/*
		 * Method to add tasks to the processing queue which orders them per SJF rules
		 */
		for (Task t : taskList) {
			if (t.getArrivalTime() <= getCpuTime() && !processingQueue.contains(t)) {
				processingQueue.add(t);
			}
		}
	}

    public Task pickNextTask() {
		Task nextTask = null;

		if (processingQueue.isEmpty()) {
			nextTask = null;
		} else {
			nextTask = processingQueue.remove();
		}

		return nextTask;
		}
}