
public class FCFS extends CPU implements Algorithm {
	
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
			Task t = pickNextTask();
    		task_name = t.getName();
    		burst_time = t.getBurst();
			task_arrival = t.getArrivalTime();
    		storeStart(task_name, CPUTime, task_arrival);//To store start info for the task for performance calculations later
    		CPUTime = CPUTime + burst_time;
    		CPU.run(t, CPUTime);
    		storeCompletion(task_name, CPUTime, task_arrival);//To store completion info for the task for performance calculations later
		}
		System.out.println("<==========================================================================>");
		printStats();
	}
	

	public Task pickNextTask() {
		Task nextTask = taskList.remove();

		while(nextTask.getArrivalTime() > CPUTime) {//To advance the CPU time in case no task arrived
			CPUTime++;
		}

		return nextTask;
	}
		
}