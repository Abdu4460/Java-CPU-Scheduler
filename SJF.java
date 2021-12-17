
//Turnaround time = completion time - arrival time <-|We want the averages
//Waiting time = turnaround time - burst time <------|of these three times
//Response time = start time - arrival time <--------|for the executions
//pull
public class SJF extends CPU implements Algorithm {
	
    
    public static void addToTaskList(Task t) { //Will be called when we want to add a task to the list
    	readyQueue.add(t);
    }
    
    public Task pickNextTask() { //Will be called to pick the next task for execution based on the rules of SJF
    	Task shortest_job = readyQueue.peek();
    	
    	for(Task n: readyQueue) {
    		if(n.getBurst() < shortest_job.getBurst()) {
    			shortest_job = n;
    		}
    	}
    	
    	return shortest_job;
    }
    
    public void schedule() { //"Runs" the task on the CPU and adds its finish information to the "completed" arraylist for calculating performance metrics
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
    		Task t = pickNextTask();
    		task_name = t.getName();
    		burst_time = t.getBurst();
    		storeStart(task_name, time_elapsed);
    		time_elapsed = time_elapsed + burst_time;
    		CPU.run(t, time_elapsed);
    		Object[] finished_task = {task_name, time_elapsed};
    		completed.add(finished_task);
    		readyQueue.remove(t);
    	}
    	System.out.println("The average turnaround time is: " + avgTurnAroundTime());
		System.out.println("The average waiting time is: " + avgWaitingTime());
		System.out.println("The average response time is: " + avgResponseTime());
    }
    
   
}
