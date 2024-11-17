package com.scheduler.cpu_scheduler.models;
/**
 * Task to be scheduled by the scheduling alogrithm.
 *
 * Each task is represented by:
 *
 *  - String name: a task name, not necessarily unique
 *
 *  - int tid: unique task identifier
 *
 *  - int priority: the relative priority of a task where a higher number indicates
 *    higher relative priority.
 *
 *  - int burst: the CPU burst of this this task
 * 
 *  - int ArrivalTime: the time a task is set to arrive in the queue
 * 
 *  @author Greg Gagne - March 2016 (also edited by the contributors on the repository)
 */

import java.util.concurrent.atomic.AtomicInteger;
import java.util.*;

public class Task implements Comparator<Task>, Comparable<Task> 
{
    // the representation of each task
    private String name;
    private int tid;
    private int priority;
    private int burst;
    private int arrivalTime;

    /**
     * We use an atomic integer to assign each task a unique task id.
     */
    private static AtomicInteger tidAllocator = new AtomicInteger();

    @Override
    public int compare(Task t1, Task t2) {
        return t1.getArrivalTime() - t2.getArrivalTime();
    }

    @Override
    public int compareTo(Task t) {
        return (this.name).compareTo(t.name);
    }

    public Task() {

    }

    public Task(String name, int priority, int burst, int arrivalTime) {
        this.name = name;
        this.priority = priority;
        this.burst = burst;
        this.arrivalTime = arrivalTime;
        this.tid = tidAllocator.getAndIncrement();
    }

    /**
     * Appropriate getters
     */
    public String getName() {
        return name;
    }

    public int getTid() {
        return tid;
    }

    public int getPriority() {
        return priority;
    }

    public int getBurst() {
        return burst;
    }
    
    public int getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Appropriate setters
     */
    public int setPriority(int priority) {
        this.priority = priority;

        return priority;
    }
    
    public int setBurst(int burst) {
        this.burst = burst;

        return burst;
    }

    public int setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
        
        return arrivalTime;
    }

    /**
     * We override equals() so we can use a
     * Task object in Java collection classes.
     */
    public boolean equals(Object other) {
        if (other == this)
            return true;

        if (!(other instanceof Task))
            return false;

        /**
         * Otherwise we are dealing with another Task.
         * two tasks are equal if they have the same tid.
         */
        Task rhs = (Task)other;
        return (this.tid == rhs.tid) ? true : false;
    }

    public String toString() {
        return
            "Name: " + name + "\n" + 
            "Tid: " + tid + "\n" + 
            "Priority: " + priority + "\n" + 
            "Burst: " + burst + "\n" +
            "Arrival time: " + arrivalTime + "\n";
    }
}