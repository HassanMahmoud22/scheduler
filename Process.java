public class Process implements Comparable<Process> {
    private String pid;
    private int priority;
    private int burstTime;
    private int arrivalTime;
    private int waitingTime;
    private int runningTime;

    public Process(String pid, int burstTime, int arrivalTime, int priority) {
        this.pid = pid;
        this.priority = priority;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.waitingTime = 0;
        this.runningTime = 0;
    }

    public Process(String pid, int burstTime, int arrivalTime) {
        this(pid, burstTime, arrivalTime, -1);
    }

    public Process(String pid, int burstTime) {
        this(pid, burstTime, 0, -1);
    }

    public String getPid() {
        return pid;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getPriority() {
        return priority;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getRunningTime() {
        return runningTime;
    }

    public void hold() {
        this.waitingTime++;
    }

    public void start() {
        this.runningTime++;
    }

    public void promote() {
        this.priority = 0;
    }

    @Override
    public int compareTo(Process o) {
        if (this.arrivalTime < o.arrivalTime) return -1;
        else if (this.arrivalTime > o.arrivalTime) return 1;
        return 0;
    }
}
