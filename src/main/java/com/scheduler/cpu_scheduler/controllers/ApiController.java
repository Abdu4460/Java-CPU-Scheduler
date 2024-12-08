package com.scheduler.cpu_scheduler.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.scheduler.cpu_scheduler.dtos.SubmitTasksRequest;
import com.scheduler.cpu_scheduler.models.Task;
import com.scheduler.cpu_scheduler.scheduling.FirstComeFirstServe;
import com.scheduler.cpu_scheduler.scheduling.PriorityScheduling;
import com.scheduler.cpu_scheduler.scheduling.RoundRobinScheduling;
import com.scheduler.cpu_scheduler.scheduling.ShortestJobFirst;

@RestController
public class ApiController {

    private FirstComeFirstServe firstComeFirstServe;
    private PriorityScheduling priorityScheduling;
    private RoundRobinScheduling roundRobinScheduling;
    private ShortestJobFirst shortestJobFirst;

    public ApiController(@Qualifier("firstComeFirstServe") FirstComeFirstServe firstComeFirstServe,
    @Qualifier("priorityScheduling") PriorityScheduling priorityScheduling,
    @Qualifier("roundRobinScheduling") RoundRobinScheduling roundRobinScheduling,
    @Qualifier("shortestJobFirst") ShortestJobFirst shortestJobFirst) {
        this.firstComeFirstServe = firstComeFirstServe;
        this.priorityScheduling = priorityScheduling;
        this.roundRobinScheduling = roundRobinScheduling;
        this.shortestJobFirst = shortestJobFirst;
    }

    @PostMapping("/submit-tasks")
    public Map<String, Map<String, Object>> submitTasks(@RequestBody SubmitTasksRequest submitTasksRequest) {
        String algorithmName = submitTasksRequest.getAlgorithmName();
        int quantum = submitTasksRequest.getQuantum();
        List<Task> taskList = submitTasksRequest.getTasks();

        switch (algorithmName.toUpperCase(Locale.ROOT)) {
            case "FCFS":
                firstComeFirstServe.setTaskList(taskList);
                firstComeFirstServe.schedule();
                return firstComeFirstServe.getResultingTasks();
            
            case "PS":
                priorityScheduling.setTaskList(taskList);
                priorityScheduling.schedule();
                return priorityScheduling.getResultingTasks();
                
            case "RR":
                roundRobinScheduling.setTaskList(taskList);
                roundRobinScheduling.setQuantum(quantum);
                roundRobinScheduling.schedule();
                return roundRobinScheduling.getResultingTasks();
            
            case "SJF":
                shortestJobFirst.setTaskList(taskList);
                shortestJobFirst.schedule();
                return shortestJobFirst.getResultingTasks();
        
            default:
                return new HashMap<>();
        }
    }
}
