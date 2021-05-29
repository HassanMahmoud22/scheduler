import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Scheduler_RR extends Scheduler {
    private int quantum;
    private int contextSwitch;
    private Queue<Process> waitingQueue;

    public Scheduler_RR(int quantum, int contextSwitch) {
        super();
        ready = new LinkedList<>();
        waitingQueue = new LinkedList<>();
        this.quantum = quantum;
        this.contextSwitch = contextSwitch;
    }

    @Override
    void startExecution() {
        while (!ready.isEmpty()) {
            Process currentProcess = ready.poll();
            int rounds = currentProcess.getBurstTime() - currentProcess.getRunningTime();
            if (rounds >= quantum) rounds = quantum;
            for (int j = 0; j < rounds; j++)
                currentProcess.start();

            while (!ready.isEmpty()) {
                Process process = ready.poll();
                for (int i = 0; i < rounds + contextSwitch; i++)
                    process.hold();
                waitingQueue.add(process);
            }
            if (currentProcess.getRunningTime() >= currentProcess.getBurstTime()) {
                System.out.println(currentProcess.getPid() + " finished executing");
                done.add(currentProcess);
            } else {
                waitingQueue.add(currentProcess);
            }
            while (!waitingQueue.isEmpty()) {
                ready.add(waitingQueue.poll());
            }
        }

    }

    int startExecution(int cycles) {
        int counter = 0;
        int rounds = 0;
        while (counter < cycles && !ready.isEmpty()) {
            Process currentProcess = ready.poll();
            rounds = currentProcess.getBurstTime() - currentProcess.getRunningTime();
            if (rounds > quantum) rounds = quantum;
            for (int j = 0; j < rounds; j++)
                currentProcess.start();
            while (!ready.isEmpty()) {
                Process process = ready.poll();
                for (int i = 0; i < rounds + contextSwitch; i++)
                    process.hold();
                waitingQueue.add(process);
            }
            if (currentProcess.getRunningTime() >= currentProcess.getBurstTime()) {
                System.out.println(currentProcess.getPid() + " finished executing");
                done.add(currentProcess);
            } else {
                waitingQueue.add(currentProcess);
            }
            while (!waitingQueue.isEmpty()) {
                ready.add(waitingQueue.poll());
            }
            counter++;
        }
        return rounds;
    }

    @Override
    void sortWaiting() {
    }
}
