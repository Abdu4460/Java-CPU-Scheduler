import java.util.Collections;

//Turnaround time = completion time - arrival time <-|We want the averages
//Waiting time = turnaround time - burst time <------|of these three times
//Response time = start time - arrival time <--------|for the executions
//pull
public class SJF extends CPU implements Algorithm {
    
    public void schedule() { //"Runs" the task on the CPU and adds its finish information to the "completed" arraylist for calculating performance metrics
    	int burst_time = 0;
    	String task_name;
    	
    	storeBurst(taskList);
		Collections.sort(taskList, new Task());
    	
    	printTableHeader();
    	while(!taskList.isEmpty()) {
    		Task t = pickNextTask();
    		task_name = t.getName();
    		burst_time = t.getBurst();
    		storeStart(task_name, CPUTime);
			checkTime(t);
    		CPUTime = CPUTime + burst_time;
    		CPU.run(t, CPUTime);
    		Object[] finished_task = {task_name, CPUTime};
    		completed.add(finished_task);
    		taskList.remove(t);
    	}
    	printStats();
    }
    
    public Task pickNextTask() { //Will be called to pick the next task for execution based on the rules of SJF
    	Task shortest_job = taskList.get(0);
    	int flag = 0;
		for(Task each : taskList) {
    		if(each.getArrivalTime() >= CPUTime) {
					flag++;
    		}
    	}
		for(Task each : taskList) {
			if(flag == taskList.size()) {
				if(each.getBurst() < shortest_job.getBurst()) {
					shortest_job = each;
				}
			}
			else if(each.getBurst() < shortest_job.getBurst() && each.getArrivalTime() >= CPUTime && each.getArrivalTime() <= shortest_job.getArrivalTime()) {
				shortest_job = each;
			}
	}
    	return shortest_job;
    }
}
