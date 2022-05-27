package ro.tuc.pt.logic;

import ro.tuc.pt.GUI.SimulationFrame;
import ro.tuc.pt.model.Server;
import ro.tuc.pt.model.Task;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class SimulationManager implements Runnable{
    private SimulationFrame view;
    private Scheduler scheduler;
    private ArrayList<Task> tasks;
    private int id =0;

    public int numberOfClients;
    public int numberOfServers;
    public int simulationInterval;
    public int maxServiceTime;
    public int minServiceTime;
    public int minArrivalTime;
    public int maxArrivalTime;
    //private final int id=0;

    private int totalServiceTime;
    private int peekTime;
    private int maxNoOfTasks;
    private PrintWriter pw;


    public SimulationManager(){}

    public SimulationManager(SimulationFrame view, Integer simulationInterval, Integer numberOfServers, Integer numberOfClients, Integer minArrivalTime, Integer maxArrivalTime, Integer minServiceTime, Integer maxServiceTime, SelectionPolicy selectionPolicy, PrintWriter pw) {

        this.simulationInterval = simulationInterval;
        this.maxServiceTime = maxServiceTime;
        this.minServiceTime = minServiceTime;
        this.numberOfServers = numberOfServers;
        this.numberOfClients = numberOfClients;
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;
        this.view = view;
        this.scheduler = new Scheduler(this.numberOfServers, this.numberOfClients);
        this.pw = pw;
        tasks=generateNRandomTasks(numberOfClients);
        this.scheduler.changeStrategy(selectionPolicy);

    }

    public ArrayList<Task> generateNRandomTasks(int N){
        ArrayList<Task> newTasks = new ArrayList<Task>();
        for (int i = 0; i < N; ++i) {
            int arrivalTime = ThreadLocalRandom.current().nextInt(maxArrivalTime-minArrivalTime+1)+minArrivalTime;
            int processingTime = ThreadLocalRandom.current().nextInt(maxServiceTime - minServiceTime +1)+ minServiceTime;
            id = id +1;
            Task t = new Task(id,arrivalTime, processingTime);
            newTasks.add(t);
        }
       newTasks.sort(Comparator.comparing(Task::getArrivalTime));
        return newTasks;
    }

    public void generateFile(){
        //id++;id++;id++;
        File file = new File("out.txt");
        FileWriter fw = null;
        try {
            fw = new FileWriter(file);
        } catch (
                IOException ioException) {
            ioException.printStackTrace();
        }
        assert fw != null;
         pw = new PrintWriter(fw);
    }


    @Override
    public void run() {
        int currentTime =0;
        int totalWaitingTime = 0;
        int size=tasks.size();
        AtomicInteger time = new AtomicInteger(0);
        generateFile();
        while(currentTime< simulationInterval){
            for(Task t : tasks) {
                if (t.getArrivalTime()==currentTime) {

                    totalWaitingTime = totalWaitingTime + scheduler.dispatchTask(t);
                    totalServiceTime = totalServiceTime+t.getServiceTime();
                }
            }
            time.set(currentTime);
            if(!tasks.isEmpty())
                tasks.removeIf(c -> (c.getArrivalTime()==(time.get())));
            int noClientsInQueues = 0;
            for(Server queue : scheduler.getServers()) {
                noClientsInQueues+=queue.getClients().length;
            }
            if(noClientsInQueues > maxNoOfTasks) {
                this.peekTime = currentTime;
                maxNoOfTasks = noClientsInQueues;
            }
            updateText(currentTime);
            updateSimulationFrame(currentTime);
            currentTime++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("err");
            }
        }
        pw.println("\nAverage waiting time: " + (float) totalWaitingTime /size);
        pw.println("\nAverage service time: " + (float) totalServiceTime/size);
        pw.println("\nPeek hour: " + peekTime + " with " + maxNoOfTasks + " in total.");
        pw.flush();
        updateWhenDone();
    }

    public void updateSimulationFrame(int time){
        StringBuilder event = new StringBuilder();

        event.append("  Time : ").append(time).append("\n   ");
        for(Task client : tasks) {
            event.append("(").append(client.getId()).append(") ");
        }
        event.append("\n");
        int i = 1;
        for(Server queue : scheduler.getServers()) {
            event.append("\n").append("   Queue ").append(i).append(": ");
            i++;
            for(Task client : queue.getTasks()) {
                event.append("(").append(client.getId()).append(") ");
            }
        }

        view.updatedFrame(event.toString());
    }

    public void updateText(Integer time) {
        int i = 0;
        pw.println("\nTime " + time);
        pw.println("Waiting clients: ");
        for(Task task : tasks)
            pw.print(task.toString());
        pw.print("\n");

        for(Server server : scheduler.getServers()) {
            i++;
            pw.print("Server: " + i + ": ");
            if(server.getTasks().size() != 0) {
                for (Task task : server.getTasks()) {
                    pw.print(task.toString());
                }
            }
            else {
                pw.print("Empty");

            }
            pw.print("\n");

        }
        pw.flush();

    }



    public void updateWhenDone(){
        view.updatedFrame("  Simulation done!");
    }

    public static void main (String[] args){
        SimulationManager m=new SimulationManager();
        Thread t= new Thread(m);
        t.start();
    }
}
