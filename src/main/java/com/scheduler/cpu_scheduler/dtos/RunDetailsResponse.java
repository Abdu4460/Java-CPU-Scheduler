package com.scheduler.cpu_scheduler.dtos;

public class RunDetailsResponse {
    private String taskName;
    private int priority;
    private int burst;
    private int arrivalTime;
    private int startTime;
    private int remainingTime;
    private int durationRun;
    private int finishTime;
    private int cpuTime;

    public static RunDetailsResponse cloneFrom(RunDetailsResponse original) {
        RunDetailsResponse clone = new RunDetailsResponse();
        clone.setTaskName(original.getTaskName());
        clone.setPriority(original.getPriority());
        clone.setBurst(original.getBurst());
        clone.setArrivalTime(original.getArrivalTime());
        clone.setStartTime(original.getStartTime());
        clone.setRemainingTime(original.getRemainingTime());
        clone.setDurationRun(original.getDurationRun());
        clone.setFinishTime(original.getFinishTime());
        clone.setCpuTime(original.getCpuTime());
        return clone;
    }
    

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getBurst() {
        return burst;
    }

    public void setBurst(int burst) {
        this.burst = burst;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public int getDurationRun() {
        return durationRun;
    }

    public void setDurationRun(int durationRun) {
        this.durationRun = durationRun;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    public int getCpuTime() {
        return cpuTime;
    }

    public void setCpuTime(int cpuTime) {
        this.cpuTime = cpuTime;
    }
}
