package com.scheduler.cpu_scheduler.services;

import com.scheduler.cpu_scheduler.models.Task;

import java.util.Map;
import java.util.List;
import java.util.Queue;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Collections;

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
@Service
 public class CPU
 {
	/*
     * These are the initializations of the necessary variables and data structures for the scheduling algorithms. 
     */
    private int cpuTime = 0;

    protected Queue<Task> taskList = new LinkedList<>();
    protected List<Task> sortingList = new ArrayList<>();
	protected Map<String, Map<String, Object>> completed = new HashMap<>();
	protected Map<String, Integer> burst = new HashMap<>();
	protected Map<String, Map<String, Object>> start = new HashMap<>();
    protected List<Map<String, Object>> runTasks = new LinkedList<>();
    protected Map<String, Double> statsMap = new HashMap<>();

    // Keys
    String startTimeKey = "startTime";
    String remainingTimeKey = "remainingTime";
    String finishTimeKey = "finishTime";
    String arrivalTimeKey = "arrivalTime";
    String burstKey = "burst";
    String taskNameKey = "taskName";
    String priorityKey = "priority";
    String timeElapsedKey = "timeElapsed";
    String durationKey = "duration";
    String averageTurnaroundKey = "averageTurnaroundTime";
    String averageWaitingKey = "averageWaitingTime";
    String averageResponseKey = "averageResponseTime";
        
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

    public void setTaskList(List<Task> tasklList) {
        this.taskList = new LinkedList<>(tasklList);
    }

    public void resetParameters() {
        setCpuTime(0);
        this.taskList.clear();
        this.sortingList.clear();
        this.completed.clear();
        this.burst.clear();
        this.start.clear();
        this.runTasks.clear();
    }

    public List<Map<String, Object>> getResultingTasks() {
        return runTasks;
    }

    public void run(Task task, int timeElapsed, int startTime, int remainingTime, int finishTime) {
        Map<String, Object> runningDetails = new HashMap<>();

        runningDetails.put(taskNameKey, task.getName());
        runningDetails.put(priorityKey, task.getPriority());
        runningDetails.put(burstKey, task.getBurst());
        runningDetails.put(arrivalTimeKey, task.getArrivalTime());
        runningDetails.put(startTimeKey, startTime);
        runningDetails.put(remainingTimeKey, remainingTime);
        runningDetails.put(finishTimeKey, finishTime);
        runningDetails.put(timeElapsedKey, timeElapsed);

        runTasks.add(runningDetails);
    }
    
    public void run(Task task, int duration, int timeElapsed) {
        Map<String, Object> runningDetails = new HashMap<>();
        
        runningDetails.put(taskNameKey, task.getName());
        runningDetails.put(priorityKey, task.getPriority());
        runningDetails.put(burstKey, task.getBurst());
        runningDetails.put(durationKey, duration);
        runningDetails.put(timeElapsedKey, timeElapsed);

        runTasks.add(runningDetails);
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
        for(String taskName: completed.keySet()){
            twt += waitingTime(taskName);
        }
        return (twt/completed.size());
    }

    public double avgTurnAroundTime(){
        double ttt = 0;
        for(String taskName: completed.keySet()){
            ttt += turnAroundTime(taskName);
        }
        return (ttt/completed.size());
    }

    public double avgResponseTime() {
        double trt = 0;
        
        for(String taskName: start.keySet()){
            trt += responseTime(taskName);
        }
        return (trt/completed.size());
    }

    public double waitingTime(String taskName){
        double turnaroundTime = turnAroundTime(taskName);
        int burstTime = burst.get(taskName);
        
        return turnaroundTime - burstTime;
    }

    public double turnAroundTime(String taskName){
        Map<String, Object> completedTask = completed.get(taskName);
        int finishTime = (int) completedTask.get(finishTimeKey);
        int arrivalTime = (int) completedTask.get(arrivalTimeKey);
        return (double) finishTime - arrivalTime;
    }

    public double responseTime(String taskName) {        
        Map<String, Object> taskInfo = start.get(taskName);
        int startTime = (int) taskInfo.get(startTimeKey);
        int arrivalTime = (int) taskInfo.get(arrivalTimeKey);
        return (double) startTime - arrivalTime;        
    }

    public void storeStart(String taskName, int startTime, int arrivalTime) {
        Map<String, Object> newStart = new HashMap<>();
        newStart.put(startTimeKey, startTime);
        newStart.put(arrivalTimeKey, arrivalTime);
        start.put(taskName, newStart);
    }

    public void storeBurst() {
        for(Task task : taskList) {
            burst.put(task.getName(), task.getBurst());
        }
    }

    public void storeCompletion(String taskName, int finishTime, int arrivalTime) {
        Map<String, Object> finishedTask = new HashMap<>();
        finishedTask.put(finishTimeKey, finishTime);
        finishedTask.put(arrivalTimeKey, arrivalTime);
        completed.put(taskName, finishedTask);
    }

    public void storeStats() {
        statsMap.put(averageTurnaroundKey, avgTurnAroundTime());
        statsMap.put(averageWaitingKey, avgWaitingTime());
        statsMap.put(averageResponseKey, avgResponseTime());
    }

    public Map<String, Double> getStats() {
        return statsMap;
    }
 }