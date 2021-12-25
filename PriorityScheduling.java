import java.util.Collections;

public class PriorityScheduling extends CPU implements Algorithm {
	
	public void schedule() {
    	int burst_time;
    	String task_name;

		storeBurst(taskList);
    	Collections.sort(taskList, new Task());
		
    	System.out.println("<=====================================================>");
    	System.out.println(" | Process |  Priority | CPU Burst |  Time Elapsed |");
		while(!taskList.isEmpty()) {
			Task hp = pickNextTask();
			task_name = hp.getName();
    		burst_time = hp.getBurst();
    		storeStart(task_name, CPUTime);
			checkTime(hp);
    		CPUTime = CPUTime + burst_time;
    		CPU.run(hp, CPUTime);
    		storeCompletion(task_name, CPUTime);
			taskList.remove(hp);
		}
		printStats();
		
	}

	@Override
	public Task pickNextTask() { // Selects the next task to be scheduled to the CPU
		Task highest_priority = taskList.get(0);
		for(Task each: taskList) {
			if(each.getPriority() > highest_priority.getPriority() && each.getArrivalTime() >= CPUTime) {
					highest_priority = each;
			}
		}
		return highest_priority;
	}

}