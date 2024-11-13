package com.scheduler.cpu_scheduler.controllers;

import java.util.List;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.GetMapping;

public class GetTasks {
    
    @GetMapping
    public List<String> getTasks() {
        //TODO: add logic for returning the tasks with their details from the scheduler
        List<String> placeholder = new ArrayList<>();
        return placeholder;
    }

}
