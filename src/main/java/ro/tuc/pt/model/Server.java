package ro.tuc.pt.model;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    public int serverNo;
    public Thread serverThread = new Thread();
    private boolean running;
    private Task currentTask;

    public Server(int N){
       this.tasks=new LinkedBlockingQueue<Task>(N);
       waitingPeriod=new AtomicInteger();
       waitingPeriod.set(0);
       serverNo=0;
       running = false;
       serverThread= new Thread(this);
    }


    public void addTask(Task newTask){
        tasks.add(newTask);
        waitingPeriod.addAndGet(1);
    }

    @Override
    public void run() {

        while (!Thread.currentThread().isInterrupted()) {
            currentTask  = new Task(0,0,0);

            if (!tasks.isEmpty()) {
                for(int i=0; i<tasks.peek().getServiceTime();i++) {
                    try {
                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        System.out.print("");
                    }
                    waitingPeriod.set(waitingPeriod.get()-1);

                }
                try {
                    currentTask = tasks.take();
                } catch (InterruptedException e) {
                    System.out.println("Waiting for clients.. (1)");
                }

            }
        }

    }

    public BlockingQueue<Task> getTasks() {
        return tasks;
    }

    public Task[] getClients() {
        Object[] clientsArray = this.tasks.toArray();
        Task[] resultedArray = new Task[clientsArray.length];
        for(int i=0;i< clientsArray.length;i++)
            resultedArray[i] = (Task) clientsArray[i];

        return resultedArray;
    }

    public int getWaitingTime() {
        int result = 0;
        for (Task task : tasks) {
            result += task.getServiceTime();
        }
        if(currentTask!=null){
            return result + currentTask.getServiceTime();
        } else return result;
    }


    public void setWaitingPeriod(AtomicInteger waitingPeriod) {
        this.waitingPeriod = waitingPeriod;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }
}
