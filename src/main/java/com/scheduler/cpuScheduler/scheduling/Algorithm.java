package com.scheduler.cpuScheduler.scheduling;

import com.scheduler.cpuScheduler.models.Task;
/**
 * Interface representing a generic scheduling algorithm.
 *
 * @author Greg Gagne - March 2016
 */

public interface Algorithm
{
    /**
     * Invokes the scheduler
     */
    public abstract void schedule();

    /**
     * Selects the next task using the appropriate scheduling algorithm
     */
    public abstract Task pickNextTask();
}