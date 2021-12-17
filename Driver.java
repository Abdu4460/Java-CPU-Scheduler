import java.util.*;
public class Driver {
	//pull
	static Random r = new Random();
	static FCFS o1 = new FCFS();
	static SJF o2 = new SJF();
	static PriorityScheduling o3 = new PriorityScheduling();
	static RRscheduling o4 = new RRscheduling();
	
	public static Queue<Task> generateQueue(int i){
		Queue<Task> randomQueue = new LinkedList<>();
		String name = "T";
		int priority;
		int burst;
		
		for (int j = 0; j < i; j++) {
			int i1 = j+1;
			String T_name = name.concat(String.valueOf(i1));
			priority = r.nextInt(10) + 1;
			burst = r.nextInt(500) + 1;
			Task t = new Task(T_name, priority, burst);
			randomQueue.add(t);
		}
		
		return randomQueue;
	}
	
	public static ArrayList<Task> generateArrayList(int i){
		ArrayList<Task> randomArrayList = new ArrayList<>();
		String name = "T";
		int priority;
		int burst;
		
		for (int j = 0; j < i; j++) {
			int i1 = j+1;
			String T_name = name.concat(String.valueOf(i1));
			priority = r.nextInt(10) + 1;
			burst = r.nextInt(500) + 1;
			Task t = new Task(T_name, priority, burst);
			randomArrayList.add(t);
		}
		
		return randomArrayList;
	}
	
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("This program simulates the CPU scheduling using four different scheduling algorithms\n");
		System.out.println("Please choose a scheduling algorithm from the list below by typing the associated acronym: ");
		System.out.println("First Come First Serve -> FCFS");
		System.out.println("Shortest Job First -> SJF");
		System.out.println("Priority Scheduling -> PS");
		System.out.println("Round-Robin -> RR");
		String pick = sc.nextLine();
		System.out.println("\nSpecifiy the number of tasks to generate:");
		int tasks = sc.nextInt();
		switch(pick.toUpperCase()) {
		case "FCFS":
			{
			Queue<Task> newQ = generateQueue(tasks);
			for(Task each: newQ) {
				FCFS.addToReadyQueue(each);
			}
			o1.schedule();
			}
			break;
			
		case "SJF":
			{
			ArrayList<Task> newAL = generateArrayList(tasks);
			for(Task each: newAL) {
				SJF.addToTaskList(each);
			}
			o2.schedule();
			}
			break;
			
		case "PS":
			{
			ArrayList<Task> newAL = generateArrayList(tasks);
			for(Task each: newAL) {
				PriorityScheduling.addToReadyQueue(each);
			}
			o3.schedule();
			}
			break;
		case "RR":
			{
			Queue<Task> newQ = generateQueue(tasks);
			for(Task each: newQ) {
				RRscheduling.addToReadyQueue(each);
			}
			o4.schedule();
			}
			break;
		}
		sc.close();
	}

}
