import java.util.Collections;

public class FCFS extends CPU implements Algorithm {
	
	public void schedule() { //this is to do the running 
    	int burst_time = 0;
    	String task_name;
    	
		storeBurst(taskList);
		Collections.sort(taskList, new Task());

    	System.out.println("<=====================================================>");
    	System.out.println(" | Process |  Priority | CPU Burst |  Time Elapsed |");
		while(!taskList.isEmpty()) {//runs until the tasks are all "processed"
			Task t = pickNextTask();
    		task_name = t.getName();
    		burst_time = t.getBurst();
    		storeStart(task_name, CPUTime);
			checkTime(t);
    		CPUTime = CPUTime + burst_time;
    		CPU.run(t, CPUTime);
    		storeCompletion(task_name, CPUTime);
			taskList.remove(t);
		}
		printStats();
	}
	

	public Task pickNextTask() {
		Task FC = taskList.get(0);
		return FC;
	}
		
}