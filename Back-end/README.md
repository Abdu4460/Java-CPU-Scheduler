# Java-CPU-Scheduler
This is an implementation of four process-scheduling algorithm done for the group project for our Operating Systems course. The four algorithms (all non-preemptive) are:

1. First-Come-First-Serve.
2. Shortest Job First.
3. Priority Scheduling.
4. Round-Robin Scheduling.

The book we relied on is Operating Systems Concepts (10th Edition) by Silberschatz et al. and we used 3 of the classes provided with the source code (all of which originally authored by Gagne) for chapter 5 which are:

1. Task.java (unaltered).
2. CPU.java (heavily altered).
3. Driver.java (heavily altered).

The scheduler is terminal-based and it asks for however many tasks you want to be scheduled, asks for their info (Burst time, arrival time, and priority), and then asks for which algorithm would you prefer to use. The output is in the shape of the table and will be printed out on the console.