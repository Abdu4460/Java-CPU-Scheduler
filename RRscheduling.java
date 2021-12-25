import java.util.Collections;

public class RRscheduling extends CPU implements Algorithm {
    
	public void schedule() {//the main scheduling algorithm, the initializations at the start here are for setting up necessary variables and stuff. 
    	int burst_time;
    	String task_name;
    	System.out.println("<================================================================>");//For the output
    	System.out.println(" | Process |  Priority | CPU Burst | Duration  |  Time Elapsed |");

		storeBurst(taskList);
		Collections.sort(taskList, new Task());

		while(!taskList.isEmpty()) { // while loop that goes until the number of tasks in "processingNow" is 0
			int displayQuantum; //i made this variable so that it can display any differences in the duration of running during execution...
			Task RR = pickNextTask();//...for example, if a task has 3ms left, the output under "duration" will be 3 instead of 10...
			task_name = RR.getName();//...because of DisplayQ
			burst_time = RR.getBurst();
			
			for(Object[] each: burst) {//this for-each loop is set to ensure that the "start" arraylist is populated without duplicates
				if (each[0].equals(task_name) && start.size() <= taskList.size()) {
					storeStart(task_name, CPUTime);
				}
			}

			checkTime(RR);

			if(burst_time > RRQuantum) {//this if-else-if is to find the next amount of burst to be subtracted
    			displayQuantum = 10;//first block will subtract 10 if the remaining burst>10
    			RR.setBurst(burst_time - RRQuantum);
    			CPUTime = CPUTime + RRQuantum;
    			taskList.add(RR);//pushes the task back into list for its next turn
    		}
    		else if(burst_time == RRQuantum) {//this will subtract 10 and mark the task as complete since remaining burst = 10
    			displayQuantum = 10;
    			RR.setBurst(burst_time-RRQuantum);
    			CPUTime = CPUTime + RRQuantum;
    			storeCompletion(task_name, CPUTime);
    			}
    		else {
    			displayQuantum = burst_time;
    			RR.setBurst(burst_time - burst_time);//this will subtract the burst time if the remaining burst < 10
    			CPUTime = CPUTime + burst_time;
    			storeCompletion(task_name, CPUTime);
    			}
			CPU.run(RR, displayQuantum, CPUTime);
		}
		printStats();
	}

	public Task pickNextTask() {
		return taskList.get(0);
	}

}
