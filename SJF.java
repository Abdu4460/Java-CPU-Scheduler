import java.util.PriorityQueue;
import java.util.Queue;

public class SJF extends CPU implements Algorithm {
    
	PriorityQueue<Task> wait = new PriorityQueue<Task>((a, b) -> { return a.getBurst() - b.getBurst(); });

    public void schedule() { //"Runs" the task on the CPU and adds its finish information to the "completed" arraylist for calculating performance metrics
    	int burst_time;
    	String task_name;
    	int task_arrival;

    	storeBurst(taskList);
		queueSorting();
    	printTableHeader();

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
			storeStart(task_name, CPUTime, task_arrival);
			CPUTime = CPUTime + burst_time;
			CPU.run(t, CPUTime);
			storeCompletion(task_name, CPUTime, task_arrival);
			taskList.remove(t);
    	}
		System.out.println("<==========================================================================>");
    	printStats();
    }

	public void checkArrivals(Queue<Task> taskList) {
		for (Task t : taskList) {
			if (t.getArrivalTime() <= CPUTime && !wait.contains(t)) {
				wait.add(t);
			}
		}
	}

    public Task pickNextTask() { //Will be called to pick the next task for execution based on the rules of SJF
		Task nextTask = null;

		if (wait.isEmpty()) {
			nextTask = null;
		} else {
			nextTask = wait.remove();
		}

		return nextTask;
		}
}