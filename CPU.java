import java.util.*;
/**
 * "Virtual" CPU
 *
 * This virtual CPU also maintains system time.
 *
 * @author Greg Gagne - March 2016
 */
 //pull
 public class CPU
 {
    public static int numberOfTasks; //Number of tasks to process, will be decremented whenever a job is done
	public final static int RRQuantum = 10; //The length of the time quantum, set according to the brief
	public static int reps = 0; //To fill up the ArrayList "start" without repeated values

    public static Queue<Task> readyQueue = new LinkedList<>(); //this is to keep all the tasks we have
	public static ArrayList<Object[]> completed = new ArrayList<>();//Stores object arrays as follows {Task name, finish time}
	public static ArrayList<Object[]> burst = new ArrayList<>();//Stores object arrays as follows {Task name, burst time}
	public static ArrayList<Object[]> start = new ArrayList<>(); //Stores object arrays as follows {Task name, start time}
     /*
      * Note from the students:
      * This virtual CPU runs each task and displays several key information about it, namely:
      * 1. Name
      * 2. Priority
      * 3. Burst/remaining burst (for Round-Robin)
      * 4a.Quantum duration (for Round-Robin)
      * 4b.Time elapsed
      */
     public static void run(Task task, int time_elapsed) {
         System.out.println(" |   " + task.getName()+ "   |  \t" + task.getPriority()+ "      |    " + task.getBurst() + "    |      "  + time_elapsed + "      |");
     }
     
     public static void run(Task task, int Duration, int time_elapsed) { //overridden run method, only for RR beccause of the additional statistic
         System.out.println(" |   " + task.getName()+ "   |  \t" + task.getPriority()+ "      |    " + task.getBurst() + "   |   " + Duration + "   \t|    " + time_elapsed + "      |");
         
     }

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
    		trt += (int)each[1];
        }
        return (trt/completed.size());
    }
    
    public static double waitingTime(String id){
        double wt = 0;
        for(Object[] eachC: completed) {
        	String c_task_id = (String)eachC[0];
	        if (c_task_id.equals(id)) {
	        	for(Object[] eachB: burst) {
	        		String b_task_id = (String)eachB[0];
	        		if(b_task_id.equals(c_task_id)) {
		        		double turnaround_time = turnAroundTime(c_task_id);
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
	            turnaround = finish_time - 0;
	            break;
	        	}
        }
        return (turnaround);
    }

    public static double responseTime(String task_name) {
    	double response = 0;
    	
    	for(Object[] each: start) {
    		String TN = (String)each[0];
    		if(TN.equals(task_name)) {
    			int start_time = (int)each[1];
    			response = start_time - 0;
    			break;
    		}
    	}
    	
    	return response;
    }
    
    public static void storeStart(String task_name, int start_time) {
    	Object[] newStart = {task_name,start_time};
    	start.add(newStart);
    }
 }