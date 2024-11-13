package com.scheduler.cpu_scheduler.services;

import java.text.DecimalFormat;
import java.util.*;

import com.scheduler.cpu_scheduler.models.Task;
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
 * This class also has a method to sort by arrival times since that is useful to any of the scheduling algorithms (subclasses of CPU)
 * in addition to the performance metric methods used for calculations
 * 
 * @author Greg Gagne - March 2016 (also edited by the contributors on the repository)
 */
 public class CPU
 {
	/*
     * These are the initializations of the necessary variables and data structures for the scheduling algorithms. 
     */
    private int cpuTime = 0;

    protected Queue<Task> taskList = new LinkedList<>();
    protected List<Task> sortingList = new ArrayList<>();
	protected Map<String, Map<String, Object>> completed = new HashMap<>();
	protected Map<String, Map<String, Object>> burst = new HashMap<>();
	protected Map<String, Map<String, Object>> start = new HashMap<>();

    public int getCpuTime() {
        return cpuTime;
    }

    public void setCpuTime(int cpuTime) {
        this.cpuTime = cpuTime;
    }

    public void updateCpuTime(int burstTime) {
        this.cpuTime+=burstTime;
    }

    public void incrementCpuTime() {
        this.cpuTime++;
    }

    public void run(Task task, int timeElapsed) {
        System.out.format("%16s%16d%16d%16d\n", task.getName(), task.getPriority(), task.getBurst(), timeElapsed);
    }
    
    public void run(Task task, int duration, int timeElapsed) {
        System.out.format("%8s%14d%16d%20d%20d\n", task.getName(), task.getPriority(), task.getBurst(), duration, timeElapsed);
        
    }

    /*
     * Method to sort the queue by arrival times.
     */
    public void queueSorting() {
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

    public double avgWaitingTime(){
        double twt = 0;
        for(Map<String, Object> completedTask: completed.values()){
            twt += waitingTime((String)completedTask.get("name"));
        }
        return (twt/completed.size());
    }

    public double avgTurnAroundTime(){
        double ttt = 0;
        for(Map<String, Object> each: completed.values()){
            ttt += turnAroundTime((String) each.get("name"));
        }
        return (ttt/completed.size());
    }

    public double avgResponseTime() {
        double trt = 0;
        
        for(Map<String, Object> each: start.values()){
            trt += responseTime((String) each.get("name"));
        }
        return (trt/completed.size());
    }

    public double waitingTime(String id){
        double turnaroundTime = turnAroundTime((String) completed.get(id).get("name"));
        int burstTime = (int) burst.get(id).get("burst");
        
        return turnaroundTime - burstTime;
    }

    public double turnAroundTime(String id){
        int turnaround = 0;
        
        for(Map<String, Object> completedTask: completed){
            String taskId = (String)completedTask.get("name");
            if(taskId.equals(id)){
                int finishTime = (int) completedTask.get("finishTime");
                int arrivalTime = (int) completedTask.get("arrivalTime");
                turnaround = finishTime - arrivalTime;
                break;
                }
        }
        return turnaround;
    }

    public double responseTime(String taskName) {
        double response = 0;
        
        for(Map<String, Object> each: start) {
            String responseTask = (String) each.get("name");
            if(responseTask.equals(taskName)) {
                int startTime = (int) each.get("startTime");
                int arrivalTime = (int) each.get("arrivalTime");
                response = startTime - arrivalTime;
                break;
            }
        }
        
        return response;
    }

    public void storeStart(String taskName, int startTime, int arrivalTime) {
        Map<String, Object> newStart = new HashMap<>();
        newStart.put("name", taskName);
        newStart.put("startTime", startTime);
        newStart.put("arrivalTime", arrivalTime);
        start.add(newStart);
    }

    public void storeBurst() {
        for(Task task : taskList) {
            Map<String, Object> burstInfo = new HashMap<>();
            burstInfo.put("name", task.getName());
            burstInfo.put("burst", task.getBurst());
            burst.add(burstInfo);
        }
    }

    public void storeCompletion(String taskName, int finishTime, int arrivalTime) {
        Map<String, Object> finishedTask = new HashMap<>();
        finishedTask.put("name", taskName);
        finishedTask.put("finishTime", finishTime);
        finishedTask.put("arrivalTime", arrivalTime);;
        completed.add(finishedTask);
    }

    public void printStats() {
        double att = avgTurnAroundTime();
        double awt = avgWaitingTime();
        double art = avgResponseTime();
    }
 }