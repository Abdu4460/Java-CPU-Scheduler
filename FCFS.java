
public class FCFS extends CPU implements Algorithm {

	
	public static void addToReadyQueue(Task t) {
		readyQueue.add(t);
	}
	
	public void schedule() { //this is to do the running
		int time_elapsed = 0; 
    	int burst_time = 0;
    	String task_name;
    	
    	for(Task each: readyQueue) {//for-each loop used to go through the task list and copy the initial burst... 
			String burst_task_name = each.getName();//...info into the "burst" ArrayList
			int initial_burst = each.getBurst();
			Object[] burst_info = {burst_task_name, initial_burst};
			burst.add(burst_info);
		}
    	
    	System.out.println("<=====================================================>");
    	System.out.println(" | Process |  Priority | CPU Burst |  Time Elapsed |");
		while(!readyQueue.isEmpty()) {//runs until the tasks are all "processed"
			Task t = pickNextTask();
    		task_name = t.getName();
    		burst_time = t.getBurst();
    		storeStart(task_name, time_elapsed);
    		time_elapsed = time_elapsed + burst_time;
    		CPU.run(t, time_elapsed);
    		Object [] finished_task = {task_name, time_elapsed};
    		completed.add(finished_task);
		}
		System.out.println("The average turnaround time is: " + avgTurnAroundTime());
		System.out.println("The average waiting time is: " + avgWaitingTime());
		System.out.println("The average response time is: " + avgResponseTime());
	}
	

	public Task pickNextTask() { 
		Task FC = readyQueue.remove();//just fetches the first task in the queue according to FCFS rules
		return FC;
	}
		
}