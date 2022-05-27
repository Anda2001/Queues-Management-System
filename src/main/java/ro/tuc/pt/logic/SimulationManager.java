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
    private int ID=0;

    public int timeLimit;
    public int maxProcessingTime;
    public int minProcessingTime;
    public int minArrivalTime;
    public int maxArrivalTime;
    public int numberOfServers;
    public int numberOfClients;

    private int totalServiceTime;
    private int peekHour;
    private int maxClients;
    private PrintWriter pw;
    private int id=0;




    public SimulationManager(){}

    public SimulationManager(SimulationFrame view, Integer timeLimit, Integer numberOfServers, Integer numberOfClients, Integer minArrivalTime, Integer maxArrivalTime, Integer minProcessingTime,Integer maxProcessingTime,SelectionPolicy selectionPolicy,  PrintWriter pw) {

        this.timeLimit = timeLimit;
        this.maxProcessingTime = maxProcessingTime;
        this.minProcessingTime = minProcessingTime;
        this.numberOfServers = numberOfServers;
        this.numberOfClients = numberOfClients;
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;
        this.view = view;
        this.scheduler = new Scheduler(this.numberOfServers, this.numberOfClients);
        this.pw = pw;
        tasks=generateRandomTasks(numberOfClients);
        this.scheduler.changeStrategy(selectionPolicy);


    }

    public ArrayList<Task> generateRandomTasks(int N){
        ArrayList<Task> newTasks = new ArrayList<Task>();
        for (int i = 0; i < N; ++i) {
            int arrivalTime = ThreadLocalRandom.current().nextInt(maxArrivalTime-minArrivalTime+1)+minArrivalTime;
            int processingTime = ThreadLocalRandom.current().nextInt(maxProcessingTime-minProcessingTime+1)+minProcessingTime;
            ID=ID+1;
            Task t = new Task(ID,arrivalTime, processingTime);
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
        AtomicInteger ct = new AtomicInteger(0);
        generateFile();
        while(currentTime< timeLimit){
            for(Task tmp : tasks) {
                if (tmp.getArrivalTime()==currentTime) {

                    totalWaitingTime = totalWaitingTime - scheduler.dispatchTask(tmp);
                    totalServiceTime = totalServiceTime+tmp.getServiceTime();
                }
            }
            ct.set(currentTime);
            if(!tasks.isEmpty())
                tasks.removeIf(c -> (c.getArrivalTime()==(ct.get())));
            int currentClients = 0;
            for(Server queue : scheduler.getServers()) {
                currentClients+=queue.getClients().length;
            }
            if(currentClients > maxClients) {
                this.peekHour = currentTime;
                maxClients = currentClients;
            }
            updateText(currentTime);
            updateSimulationView(currentTime);
            currentTime++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("err");
            }
        }
        pw.println("\nAverage waiting time: " + (float) totalWaitingTime /size);
        pw.println("Average service time: " + (float) totalServiceTime/size);
        pw.println("Peek hour: " + peekHour + " with " + maxClients + " in total.");
        pw.flush();
        updateDone();
    }

    public void updateText(Integer time) {
        int i = 1;
        pw.println("\nTime " + time);
        pw.println("Waiting clients: ");
        for(Task client : tasks)
            pw.print(client.toString());
        pw.print("\n");

        for(Server queue : scheduler.getServers()) {
            pw.print("Queue: " + i + ": ");
            if(queue.getTasks().size() == 0) {
                pw.print("Empty");
            }
            else {
                for (Task client : queue.getTasks()) {
                    pw.print(client.toString());
                }
            }
            pw.print("\n");
            i++;
        }
        pw.flush();

    }

    public void updateSimulationView(int time){
        String resultedString = new String();

        resultedString = resultedString + "  Time : " + time + "\n   ";
        for(Task client : tasks) {
            resultedString = resultedString + "(" + client.getId() + ") ";
        }
        resultedString = resultedString + "\n";
        int i = 1;
        for(Server queue : scheduler.getServers()) {
            resultedString = resultedString + "\n"+ "   Queue " + i + ": ";
            i++;
            for(Task client : queue.getTasks()) {
                resultedString = resultedString + "(" + client.getId() + ") ";
            }
        }

        view.setUpdated(resultedString);
    }

    public void updateDone(){
        view.setUpdated(new String("  Simulation done!"));
        System.out.println("Done!");
    }

    public static void main (String[] args){
        SimulationManager m=new SimulationManager();
        Thread t= new Thread(m);
        t.start();
    }
}
