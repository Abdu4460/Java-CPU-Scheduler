package com.scheduler.cpu_scheduler.controllers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.scheduler.cpu_scheduler.dtos.RunDetailsResponse;
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
        // Request body
        String algorithmName = submitTasksRequest.getAlgorithmName();
        boolean priority = submitTasksRequest.isPriority();
        int quantum = submitTasksRequest.getQuantum();
        List<Task> taskList = submitTasksRequest.getTasks();

        // Mapping results
        List<RunDetailsResponse> resultingTasks = new LinkedList<>();
        Map<String, Double> stats = new HashMap<>();
        Map<String, Object> resultsMap = new HashMap<>();

        switch (algorithmName.toUpperCase(Locale.ROOT)) {
            case "FCFS":
                firstComeFirstServe.setTaskList(taskList);
                firstComeFirstServe.schedule();
                resultingTasks = firstComeFirstServe.getResultingTasks()
                       .stream()
                       .map(RunDetailsResponse::cloneFrom)
                       .collect(Collectors.toList());
                stats = firstComeFirstServe.getStats();
                resultsMap.put(resultsKey, resultingTasks);
                resultsMap.put(statsKey, stats);
                firstComeFirstServe.resetParameters();
                return resultsMap;
            
            case "PS":
                priorityScheduling.setTaskList(taskList);
                priorityScheduling.setPriority(priority);
                priorityScheduling.schedule();
                resultingTasks = priorityScheduling.getResultingTasks()
                        .stream()
                        .map(RunDetailsResponse::cloneFrom)
                        .collect(Collectors.toList());
                stats = priorityScheduling.getStats();
                resultsMap.put(resultsKey, resultingTasks);
                resultsMap.put(statsKey, stats);
                priorityScheduling.resetParameters();
                return resultsMap;
                
            case "RR":
                roundRobinScheduling.setTaskList(taskList);
                roundRobinScheduling.setQuantum(quantum);
                roundRobinScheduling.schedule();
                resultingTasks = roundRobinScheduling.getResultingTasks()
                        .stream()
                        .map(RunDetailsResponse::cloneFrom)
                        .collect(Collectors.toList());
                stats = roundRobinScheduling.getStats();
                resultsMap.put(resultsKey, resultingTasks);
                resultsMap.put(statsKey, stats);
                roundRobinScheduling.resetParameters();
                return resultsMap;
            
            case "SJF":
                shortestJobFirst.setTaskList(taskList);
                shortestJobFirst.schedule();
                resultingTasks = shortestJobFirst.getResultingTasks()
                        .stream()
                        .map(RunDetailsResponse::cloneFrom)
                        .collect(Collectors.toList());
                stats = shortestJobFirst.getStats();
                resultsMap.put(resultsKey, resultingTasks);
                resultsMap.put(statsKey, stats);
                shortestJobFirst.resetParameters();
                return resultsMap;
        
            default:
                return new HashMap<>();
        }
    }
}
