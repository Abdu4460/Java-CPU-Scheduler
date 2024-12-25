package com.scheduler.cpu_scheduler.dtos;

import java.util.List;

import com.scheduler.cpu_scheduler.models.Task;

public class SubmitTasksRequest {
    
    private String algorithmName;
    private boolean priority;
    private int quantum;
    private List<Task> tasks;

    public String getAlgorithmName() {
        return algorithmName;
    }
    
    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public boolean isPriority() {
        return priority;
    }

    public void setPriority(boolean priority) {
        this.priority = priority;
    }

    public int getQuantum() {
        return quantum;
    }
    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
    
}
