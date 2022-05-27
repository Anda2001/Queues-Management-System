package ro.tuc.pt.model;

public class Task {
    private int Id;
    private int arrivalTime;
    private int serviceTime;

    public Task() {
        this.Id = 0;
        this.arrivalTime = 0;
        this.serviceTime = 0;
    }

    public Task(int id, int arrivalTime, int serviceTime) {
        this.Id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getId() {
        return Id;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    @Override
    public String toString() {
        return "(" + this.Id + ", " + this.arrivalTime + ", " + this.serviceTime + "); ";
    }
}
