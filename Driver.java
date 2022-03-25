import java.util.ArrayList;
import java.util.Scanner;

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

		//This part is where the user is prompted to choose which algorithm to use for scheduling
		System.out.println("Please choose a scheduling algorithm from the list below by typing its acronym: ");
		System.out.println("First Come First Serve -> FCFS");
		System.out.println("Shortest Job First -> SJF");
		System.out.println("Priority Scheduling -> PS");
		System.out.println("Round-Robin -> RR");
		String pick = sc.nextLine();

		//This part is where the user enters how many tasks they wish to schedule
		System.out.print("\nSpecifiy the number of tasks to schedule: ");
		int tasks = sc.nextInt();

		//Calling the buildList method prompts the user to enter the information for each task
		ArrayList<Task> newList = buildList(tasks);
		
		//Schedules the task list as per the scheduling algorithm selection
		switch(pick.toUpperCase()) {
			case "FCFS":
				{
					for(Task each: newList) {
						o1.taskList.add(each);
					}
					o1.schedule();
				}
				break;
				
			case "SJF":
				{
					for(Task each: newList) {
						o2.taskList.add(each);
					}
					o2.schedule();
				}
				break;
				
			case "PS":
				{
					for(Task each: newList) {
						o3.taskList.add(each);
					}
					o3.schedule();
				}
				break;
			case "RR":
				{
					for(Task each: newList) {
						o4.taskList.add(each);
					}
					o4.schedule();
				}
				break;
		}
		sc.close();
	}

}
