
public class FCFS extends CPU implements Algorithm {
	
	public void schedule() { //this is to do the running 
    	int burst_time = 0;
    	String task_name;
    	
		storeBurst();

    	System.out.println("<=====================================================>");
    	System.out.println(" | Process |  Priority | CPU Burst |  Time Elapsed |");
		while(!taskList.isEmpty()) {//runs until the tasks are all "processed"
			Task t = pickNextTask();
    		task_name = t.getName();
    		burst_time = t.getBurst();
    		storeStart(task_name, CPUTime);
    		CPUTime = CPUTime + burst_time;
    		CPU.run(t, CPUTime);
    		storeCompletion(task_name, CPUTime);
		}
		System.out.println("The average turnaround time is: " + avgTurnAroundTime());
		System.out.println("The average waiting time is: " + avgWaitingTime());
		System.out.println("The average response time is: " + avgResponseTime());
	}
	

	public Task pickNextTask() {
		Task FC = taskList.get(0);
		for (Task each : taskList) {
			if(each.getArrivalTime() < FC.getArrivalTime()) {
				FC = each;
			}
		}
		return FC;
	}
		
}