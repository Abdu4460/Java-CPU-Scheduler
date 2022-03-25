import java.util.PriorityQueue;
import java.util.Queue;

public class PriorityScheduling extends CPU implements Algorithm {
	
	PriorityQueue<Task> wait = new PriorityQueue<Task>((a, b) -> { return b.getPriority() - a.getPriority(); });

	public void schedule() {
    	int burst_time;
    	String task_name;
		int task_arrival;

		storeBurst(taskList);
		queueSorting();
    	printTableHeader();

		while(!taskList.isEmpty()) {
			checkArrivals(taskList);
			Task hp = pickNextTask();
			if (hp == null) {
				CPUTime++;
				continue;
			}
			task_name = hp.getName();
    		burst_time = hp.getBurst();
			task_arrival = hp.getArrivalTime();
    		storeStart(task_name, CPUTime, task_arrival);
    		CPUTime = CPUTime + burst_time;
    		CPU.run(hp, CPUTime);
    		storeCompletion(task_name, CPUTime, task_arrival);
			taskList.remove(hp);
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