
public class RRscheduling extends CPU implements Algorithm {
    
	public void schedule() {//the main scheduling algorithm, the initializations at the start here are for setting
		int time_elapsed = 0; //up necessary variables and stuff. 
    	int burst_time = 0;
    	String task_name;
    	numberOfTasks = readyQueue.size();
    	System.out.println("<================================================================>");//For the output
    	System.out.println(" | Process |  Priority | CPU Burst | Duration  |  Time Elapsed |");

		for(Task each: readyQueue) {//for-each loop used to go through the task list and copy the initial burst... 
			String burst_task_name = each.getName();//...info into the "burst" ArrayList before we alter the burst times during...
			int initial_burst = each.getBurst();//...RR scheduling, we'll use this for waiting time later
			Object[] burst_info = {burst_task_name, initial_burst};
			burst.add(burst_info);
		}
		
		while(!(numberOfTasks == 0)) { // while loops that goes until the number of tasks in "processingNow" is 0
			int DisplayQ = 10; //i made this variable so that it can display any differences in the duration of running during execution...
			Task RR = pickNextTask();//...for example, if a task has 3ms left, the output under "duration" will be 3 instead of 10...
			task_name = RR.getName();//...because of DisplayQ
			burst_time = RR.getBurst();
			for(Object[] test: burst) {//this for-each loop is set to ensure that the "start" arraylist is populated without duplicates
				if (test[0].equals(task_name) && reps < numberOfTasks) {
					storeStart(task_name, time_elapsed);
				}
			}
			if(burst_time>RRQuantum) {//this if-else-if is to find the next amount of burst to be subtracted
    			DisplayQ = 10;//first block will subtract 10 if the remaining burst>10
    			RR.setBurst(burst_time-RRQuantum);
    			time_elapsed = time_elapsed + RRQuantum;
    			readyQueue.add(RR);//pushes the task back into queue for its next turn
    			reps++;//like i said before, just to keep track of how "start" is populated
    		}
    		else if(burst_time == RRQuantum) {//this will subtract 10 and mark the task as complete since remaining burst = 10
    			DisplayQ = 10;
    			RR.setBurst(burst_time-RRQuantum);
    			time_elapsed = time_elapsed + RRQuantum;
    			Object[] finished_task = {task_name, time_elapsed};
    			completed.add(finished_task);
    			numberOfTasks--;//i decrement this here and in the 3rd block only, this is because only in these two...
    			reps++;//...block is a task ever considered finished, hence the previous line of code adding the...
    			//...task to "completed"
    			}
    		else {
    			DisplayQ = burst_time;
    			RR.setBurst(burst_time - burst_time);//this will subtract the burst time if the remaining burst < 10
    			time_elapsed = time_elapsed + burst_time;
    			Object[] finished_task = {task_name, time_elapsed};
    			completed.add(finished_task);
    			numberOfTasks--;
    			reps++;
    			}
			CPU.run(RR, DisplayQ, time_elapsed);
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
