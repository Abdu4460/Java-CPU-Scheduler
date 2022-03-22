import java.util.*;
/**
 * "Virtual" CPU that maintains system time and "runs" tasks.
 *
 * It runs tasks and displays the related information in the console which includes:
 * 
 *  - Task name
 *  - Task priority
 *  - Task burst
 *  - CPU time elapsed
 *  - Duration (for Round-Robin scheduling as it is relevant there)
 * 
 * @author Greg Gagne - March 2016 (also edited by the contributors on the repository)
 */
 public class CPU
 {
	/*
     * These are the initializations of the necessary variables and data structures for the scheduling algorithms. 
     */
    public static int CPUTime = 0;

    public static Queue<Task> taskList = new LinkedList<>();
    public static ArrayList<Task> sortingList = new ArrayList<>();
	public static ArrayList<Object[]> completed = new ArrayList<>();
	public static ArrayList<Object[]> burst = new ArrayList<>();
	public static ArrayList<Object[]> start = new ArrayList<>();
    
    /*
     * The following three methods are concerned with the output on the console.
     */
    public static void printTableHeader() {
        System.out.println("<==========================================================================>");
    	System.out.format("%20s%18s%14s%16s\n", "Task name", "Priority level", "Burst time", "Time elapsed");
    }

    public static void run(Task task, int time_elapsed) {
        System.out.format("%16s%16d%16d%16d\n", task.getName(), task.getPriority(), task.getBurst(), time_elapsed);
    }
    
    public static void run(Task task, int Duration, int time_elapsed) { //overridden run method, only for RR beccause of the additional statistic
        System.out.format("%16s%16d%16d%16d%16d\n", task.getName(), task.getPriority(), task.getBurst(), Duration, time_elapsed);
        
    }

    /*
     * Method to pop everything from the taskList queue into the sorting list for it to be sorted and then pushed back into the queue
     */
    public static void queueSorting() {
        while(!taskList.isEmpty()) {
            Task temp = taskList.remove();
            sortingList.add(temp);
        }

        Collections.sort(sortingList, new Task());

        while(!sortingList.isEmpty()) {
            taskList.add(sortingList.remove(0));
        }
    }
    
    /*
     * The rest of the class contains all the methods concerned with performance metrics of the scheduling algorithms which are:
     *  - Average turnaround time
     *  - Average waiting time
     *  - Average response time
     */

    public static double avgWaitingTime(){
        double twt = 0;
        for(Object[] each: completed){
            twt += waitingTime((String)each[0]);
        }
        return (twt/completed.size());
    }

    public static double avgTurnAroundTime(){
        double ttt = 0;
        for(Object[] each: completed){
            ttt += turnAroundTime((String)each[0]);
        }
        return (ttt/completed.size());
    }

    public static double avgResponseTime() {
        double trt = 0;
        
        for(Object[] each: start){
            trt += responseTime((String)each[0]);
        }
        return (trt/completed.size());
    }

    public static double waitingTime(String id){
        double wt = 0;
        String completed_task_id;
        String burst_task_id;
        double turnaround_time;


        for(Object[] eachC: completed) {
            completed_task_id = (String)eachC[0];
            if (completed_task_id.equals(id)) {
                for(Object[] eachB: burst) {
                    burst_task_id = (String)eachB[0];
                    if(burst_task_id.equals(completed_task_id)) {
                        turnaround_time = turnAroundTime(completed_task_id);
                        int burst_time = (int)eachB[1];
                        wt = turnaround_time - burst_time;	
                        break;
                    }
                }
                break;
            }
        }
        
        return wt;
    }

    public static double turnAroundTime(String id){
        int turnaround = 0;
        
        for(Object[] each: completed){
            String task_n = (String)each[0];
            if(task_n.equals(id)){
                int finish_time = (int)each[1];
                int arrival_time = (int)each[2];
                turnaround = finish_time - arrival_time;
                break;
                }
        }
        return (turnaround);
    }

    public static double responseTime(String task_name) {
        double response = 0;
        
        for(Object[] each: start) {
            String response_task = (String)each[0];
            if(response_task.equals(task_name)) {
                int start_time = (int)each[1];
                int arrival_time = (int)each[2];
                response = start_time - arrival_time;
                break;
            }
        }
        
        return response;
    }

    public static void storeStart(String task_name, int start_time, int arrival_time) {
        Object[] newStart = {task_name,start_time, arrival_time};
        start.add(newStart);
    }

    public void storeBurst(Queue<Task> taskList) {
        for(Task task : taskList) {
            Object[] burst_info = {task.getName(), task.getBurst()};
            burst.add(burst_info);
        }
    }

    public void storeCompletion(String task_name, int finish_time, int arrival_time) {
        Object [] finished_task = {task_name, finish_time, arrival_time};
        completed.add(finished_task);
    }

    public static void printStats() {
        System.out.println("The average turnaround time is: " + avgTurnAroundTime());
        System.out.println("The average waiting time is: " + avgWaitingTime());
        System.out.println("The average response time is: " + avgResponseTime());
    }
 }