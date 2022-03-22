
public class FCFS extends CPU implements Algorithm {
	
	public void schedule() { 
    	int burst_time = 0;
    	String task_name;
    	int task_arrival;

		storeBurst(taskList);
		queueSorting();

    	printTableHeader();
		while(!taskList.isEmpty()) {
			Task t = pickNextTask();
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
	

	public Task pickNextTask() {
		Task nextTask = taskList.remove();

		while(nextTask.getArrivalTime() > CPUTime) {
			CPUTime++;
		}

		return nextTask;
	}
		
}