# Java CPU Scheduling Simulator

## Overview

This project is an implemenation of four (non-preemptive) CPU scheduling algorithms using Java Spring Boot for the back-end and React JS for the front-end. The four algorithms are:

1. First-Come-First-Serve.
2. Shortest Job First.
3. Priority Scheduling.
4. Round-Robin Scheduling.

Some of the main classes used in the back-end were adapted from the source code of chapter 5 from the Operating Systems Concepts (10th Edition) by Silberschatz et al. which are:

1. Task.java
2. CPU.java
3. Driver.java

## Setup

The required project files are separated into their respective front-end and back-end directories, each with a Dockerfile with the required commands to setup containers for both services. You can try this project out by cloning the repo and then in the root directory of the repo, run the following commands:

```
docker compose build
docker compose up
```

This of course requires [Docker](https://www.docker.com/) to be installed on your system. Then, you can go to your browser and type in the address bar:

```
http://localhost:3000/
```

Which will show you the front-end application and you can test the application out!