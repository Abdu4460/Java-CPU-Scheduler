package com.scheduler.cpu_scheduler.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
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

    // Keys
    private String resultsKey = "results";
    private String statsKey = "statistics";

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

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/submit-tasks")
    public Map<String, Object> submitTasks(@RequestBody SubmitTasksRequest submitTasksRequest) {
        String algorithmName = submitTasksRequest.getAlgorithmName();
        System.out.println(algorithmName);
        boolean priority = submitTasksRequest.isPriority();
        System.out.println(priority);
        int quantum = submitTasksRequest.getQuantum();
        System.out.println(quantum);
        List<Task> taskList = submitTasksRequest.getTasks();
        System.out.println(taskList);
        Map<String, Object> resultsMap = new HashMap<>();

        switch (algorithmName.toUpperCase(Locale.ROOT)) {
            case "FCFS":
                firstComeFirstServe.setTaskList(taskList);
                firstComeFirstServe.schedule();
                resultsMap.put(resultsKey, firstComeFirstServe.getResultingTasks());
                resultsMap.put(statsKey, firstComeFirstServe.getStats());
                firstComeFirstServe.clearTaskList();
                return resultsMap;
            
            case "PS":
                priorityScheduling.setTaskList(taskList);
                priorityScheduling.setPriority(priority);
                priorityScheduling.schedule();
                resultsMap.put(resultsKey, priorityScheduling.getResultingTasks());
                resultsMap.put(statsKey, priorityScheduling.getStats());
                priorityScheduling.clearTaskList();
                return resultsMap;
                
            case "RR":
                roundRobinScheduling.setTaskList(taskList);
                roundRobinScheduling.setQuantum(quantum);
                roundRobinScheduling.schedule();
                resultsMap.put(resultsKey, roundRobinScheduling.getResultingTasks());
                resultsMap.put(statsKey, roundRobinScheduling.getStats());
                roundRobinScheduling.clearTaskList();
                return resultsMap;
            
            case "SJF":
                shortestJobFirst.setTaskList(taskList);
                shortestJobFirst.schedule();
                resultsMap.put(resultsKey, shortestJobFirst.getResultingTasks());
                resultsMap.put(statsKey, shortestJobFirst.getStats());
                shortestJobFirst.clearTaskList();
                return resultsMap;
        
            default:
                return new HashMap<>();
        }
    }
}
