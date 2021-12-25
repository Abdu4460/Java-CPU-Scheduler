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
		ArrayList<Task> taskQueue = new ArrayList<>();
		String name = "T";
		int priority;
		int burst;
		int arrivalTime;
		int temp = 1;
		
		while(i > 0) {
			int T_num = temp;
			String T_name = name.concat(String.valueOf(T_num));
			System.out.println("Task name: " + T_name);
			System.out.print("Priority: ");
			priority = sc.nextInt();
			System.out.print("Burst: ");
			burst = sc.nextInt();
			System.out.print("Arrival time: ");
			arrivalTime = sc.nextInt();
			Task t = new Task(T_name, priority, burst, arrivalTime);
			taskQueue.add(t);
			temp++;
			i--;
		}
		
		return taskQueue;
	} 
	
	public static void main(String[] args) {
		
		System.out.println("This program simulates the CPU scheduling using four different scheduling algorithms\n");
		System.out.println("Please choose a scheduling algorithm from the list below by typing its acronym: ");
		System.out.println("First Come First Serve -> FCFS");
		System.out.println("Shortest Job First -> SJF");
		System.out.println("Priority Scheduling -> PS");
		System.out.println("Round-Robin -> RR");
		String pick = sc.nextLine();
		System.out.print("\nSpecifiy the number of tasks to schedule: ");
		int tasks = sc.nextInt();

		ArrayList<Task> newList = buildList(tasks);
		switch(pick.toUpperCase()) {
			case "FCFS":
				{
					for(Task each: newList) {
						o1.addToTaskList(each);
					}
					o1.schedule();
				}
				break;
				
			case "SJF":
				{
					for(Task each: newList) {
						o2.addToTaskList(each);
					}
					o2.schedule();
				}
				break;
				
			case "PS":
				{
					for(Task each: newList) {
						o3.addToTaskList(each);
					}
					o3.schedule();
				}
				break;
			case "RR":
				{
					for(Task each: newList) {
						o4.addToTaskList(each);
					}
					o4.schedule();
				}
				break;
		}
		sc.close();
	}

}
