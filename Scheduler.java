import java.util.*;

public abstract class Scheduler {
    Queue<Process> ready;
    Stack<Process> waiting;
    ArrayList<Process> done;

    public Scheduler() {
        this.ready = new PriorityQueue<>();
        this.waiting = new Stack<>();
        this.done = new ArrayList<>();
    }

    void scheduleProcess(Process process) {
        ready.add(process);
    }

    void getWaitingTime() {
        for (Process process : done) {
            System.out.println("Process: " + process.getPid() + " Waiting Time: " + process.getWaitingTime());
        }
    }

    double getAverageWaitingTime() {
        double avg = 0.0;
        for (Process process : done) {
            avg += process.getWaitingTime();
        }
        avg /= done.size();
        System.out.println("The average waiting time is " + avg);
        return avg;
    }

    void getTurnaroundTime() {
        for (Process process : done) {
            int time = process.getWaitingTime() + process.getBurstTime();
            System.out.println("Process: " + process.getPid() + " Turnaround time: " + time);
        }
    }

    double getAverageTurnaroundTime() {
        double avg = 0.0;
        for (Process process : done) {
            avg += (process.getWaitingTime() + process.getBurstTime());
        }
        avg /= done.size();
        System.out.println("The average turnaround time is " + avg);
        return avg;
    }

    boolean isEmpty() {
        return ready.isEmpty();
    }

    public ArrayList<Process> getDone() {
        return done;
    }

    abstract void startExecution();

    abstract void sortWaiting();
}
