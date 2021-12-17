
public class PriorityScheduling extends CPU implements Algorithm {

    public static void addToReadyQueue(Task t) {
		readyQueue.add(t);
	}
	
	public void schedule() {
		int time_elapsed = 0; 
    	int burst_time = 0;
    	String task_name;
    	
    	for(Task each: readyQueue) {//for-each loop used to go through the task list and copy the initial burst... 
			String burst_task_name = each.getName();//...info into the "burst" ArrayList before we alter the burst times during...
			int initial_burst = each.getBurst();//...RR scheduling, we'll use this for waiting time later
			Object[] burst_info = {burst_task_name, initial_burst};
			burst.add(burst_info);
		}
    	
    	System.out.println("<=====================================================>");
    	System.out.println(" | Process |  Priority | CPU Burst |  Time Elapsed |");
		while(!readyQueue.isEmpty()) {
			Task hp = pickNextTask();
			task_name = hp.getName();
    		burst_time = hp.getBurst();
    		storeStart(task_name, time_elapsed);
    		time_elapsed = time_elapsed + burst_time;
    		CPU.run(hp, time_elapsed);
    		Object[] finished_task = {task_name, time_elapsed};
    		completed.add(finished_task);
    		readyQueue.remove(hp);
		}
		System.out.println("The average turnaround time is: " + avgTurnAroundTime());
		System.out.println("The average waiting time is: " + avgWaitingTime());
		System.out.println("The average response time is: " + avgResponseTime());
		
	}

	@Override
	public Task pickNextTask() { // Selects the next task to be scheduled to the CPU
		Task highest_priority = readyQueue.peek();
		for(Task each: readyQueue) {
			if(each.getPriority() > highest_priority.getPriority()) {
				highest_priority = each;
			}
		}
		return highest_priority;
	}

}