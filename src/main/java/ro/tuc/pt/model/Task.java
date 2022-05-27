package ro.tuc.pt.model;

public class Task {
    private int Id;
    private int arrivalTime;
    private int serviceTime;


    public Task(int id, int arrivalTime, int serviceTime) {
        this.Id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public int getId() {
        return Id;
    }


    public int getArrivalTime() {
        return arrivalTime;
    }


    public int getServiceTime() {
        return serviceTime;
    }

    @Override
    public String toString() {
        return "(" + this.Id + ", " + this.arrivalTime + ", " + this.serviceTime + "); ";
    }
}
