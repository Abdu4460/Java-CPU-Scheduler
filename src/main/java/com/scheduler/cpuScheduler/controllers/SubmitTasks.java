package com.scheduler.cpuScheduler.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubmitTasks {

    @PostMapping("/submit-tasks")
    public void submitTasks() {
        //TODO: add the logic for submitting tasks to be processed by the scheduling logic
    }
}
