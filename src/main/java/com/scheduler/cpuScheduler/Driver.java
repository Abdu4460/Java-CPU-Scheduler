package com.scheduler.cpuScheduler;
import java.util.ArrayList;
import java.util.Scanner;

import com.scheduler.cpuScheduler.models.Task;
import com.scheduler.cpuScheduler.scheduling.*;

public class Driver {

	//Creating objects of the necessary classes to use during execution
	static FCFS o1 = new FCFS();
	static SJF o2 = new SJF();
	static PriorityScheduling o3 = new PriorityScheduling();
	static RRscheduling o4 = new RRscheduling();
	static Scanner sc = new Scanner(System.in);
	
	//Method to create a queue of a specified size
	public static ArrayList<Task> buildList(int i){
		/*
		 * The initializations of the different things used in adding the tasks 
		 */
		ArrayList<Task> taskList = new ArrayList<>();
		String name = "T";
		int priority;
		int burst;
		int arrivalTime;
		int temp = 1;
		
		while(i > 0) {
			//This first part handles the name of the new task
			int T_num = temp;
			String T_name = name.concat(String.valueOf(T_num));
			System.out.println("\nTask name: " + T_name);

			//This part asks for the priority of the task
			System.out.print("\nPriority: ");
			priority = sc.nextInt();

			//This part asks for the burst of the task
			System.out.print("\nBurst: ");
			burst = sc.nextInt();

			//This part asks for the arrival time of the task
			System.out.print("\nArrival time: ");
			arrivalTime = sc.nextInt();
			System.out.println("---------------------");
			
			/*
			 *As for the task ID, it is handled automatically by
			 *the AtomicInteger object in the 'Task' class definition
			 */
			Task t = new Task(T_name, priority, burst, arrivalTime);
			taskList.add(t);
			temp++;//->|Both of these are changed so that the next tasks
			i--;//---->|are updated as necessary
		}
		
		return taskList;
	} 
	
	public static void main(String[] args) {
		
		//The user is informed of what the program does here
		System.out.println("This program simulates the CPU scheduling using four different scheduling algorithms\n");

		//This part is where the user enters how many tasks they wish to schedule
		System.out.print("\nSpecifiy the number of tasks to schedule: ");
		int tasks = sc.nextInt();

		//Calling the buildList method prompts the user to enter the information for each task
		ArrayList<Task> newList = buildList(tasks);

		//This part is where the user is prompted to choose which algorithm to use for scheduling
		System.out.println("Please choose a scheduling algorithm from the list below by typing its acronym: ");
		System.out.println("First Come First Serve -> FCFS");
		System.out.println("Shortest Job First -> SJF");
		System.out.println("Priority Scheduling -> PS");
		System.out.println("Round-Robin -> RR");
		String pick;
		boolean flag = true;
		
		do {
			pick = sc.nextLine();
			/*
			 * Schedules the task list as per the scheduling algorithm selection.
			 * The do-while block is to counter any wrong inputs by having the input be taken again in case of a mis-input.
			 */
			switch(pick.toUpperCase()) {
				case "FCFS":
					{
						for(Task each: newList) {
							FCFS.taskList.add(each);
						}
						flag = false;
						o1.schedule();
					}
					break;
					
				case "SJF":
					{
						for(Task each: newList) {
							SJF.taskList.add(each);
						}
						flag = false;
						o2.schedule();
					}
					break;
					
				case "PS":
					{//Here, the priority order could be ascending or descending so the user is prompted to choose accordingly
						for(Task each: newList) {
							PriorityScheduling.taskList.add(each);
						}
						flag = false;
						System.out.println("What order is the priority for the tasks? (ascending/descending)");
						while (true) {
							String order = sc.nextLine();
							if (order.toLowerCase().equals("ascending")) {
								o3.setTypeFlag(false);
								break;
							} else if (order.toLowerCase().equals("descending")) {
								o3.setTypeFlag(true);
								break;
							} else {
								System.out.println("Wrong input, try again.");
							}
						}
						o3.schedule();
					}
					break;

				case "RR":
					{//Here, the user can pick their preferred quantum period.
						for(Task each: newList) {
							RRscheduling.taskList.add(each);
						}
						flag = false;
						System.out.println("Specify the quantum period that you want: ");
						int answer = sc.nextInt();
						o4.setQuantum(answer);
						o4.schedule();
					}
					break;
				default:
					System.out.println("Please type the algorithm name.");
					break;
			}
		} while (flag);
		sc.close();
	}

}
