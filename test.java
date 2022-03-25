
public class test extends CPU {
    
    static PriorityScheduling PO = new PriorityScheduling();
    public static void main(String[] args) {
        Task t1 = new Task("t1", 3, 15, 3);
        Task t2 = new Task("t2", 1, 20, 2);
        Task t3 = new Task("t3", 5, 25, 1);
        Task t4 = new Task("t4", 4, 5, 1);
        Task t5 = new Task("t5", 6, 10, 1);
        Task t6 = new Task("t6", 2, 7, 10);
        Task t7 = new Task("t7", 7, 20, 1);

        taskList.add(t1);
        taskList.add(t2);
        taskList.add(t3);
        taskList.add(t4);
        taskList.add(t5);
        taskList.add(t6);
        taskList.add(t7);

        PO.schedule();
    }

}