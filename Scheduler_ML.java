import java.util.ArrayList;
import java.util.Stack;

public class Scheduler_ML extends Scheduler {
    int contextSwitch;
    Scheduler_RR rr;

    public Scheduler_ML(int quantum, int contextSwitch) {
        this.contextSwitch = contextSwitch;
        rr = new Scheduler_RR(quantum, contextSwitch);
    }

    @Override
    void startExecution() {
        int currentTime = 0;
        boolean flag = false;
        while (!ready.isEmpty() || !waiting.isEmpty() || !rr.isEmpty()) {
            ArrayList<Process> processes = new ArrayList<>();
            // add processes to waiting stack as they arrive
            while (!ready.isEmpty()) {
                Process currentProcess = ready.poll();
                if (currentProcess.getArrivalTime() > currentTime) {
                    processes.add(currentProcess);
                } else {
                    if (currentProcess.getPriority() == 0) {
                        rr.scheduleProcess(currentProcess);
                        flag = true;
                    } else {
                        waiting.push(currentProcess);
                    }
                }
            }

            int x = rr.startExecution(1);

            for (Process process : processes) {
                ready.add(process);
            }

            if (!waiting.isEmpty()) {
                // sort waiting stack by arrival time
                sortWaiting();
                // allow current process to execute
                if (rr.isEmpty()) {
                    waiting.peek().start();
                }
                for (int i = waiting.size() - 1; i >= 0; i--) {
                    // increment process waiting time
                    if (!flag) waiting.get(i).hold();
                    int holdTime = contextSwitch + x;
                    for (int j = 0; j < holdTime; j++) {
                        waiting.get(i).hold();
                    }
                }
                // if a process has finished executing
                if (waiting.peek().getRunningTime() >= waiting.peek().getBurstTime()) {
                    Process finished = waiting.pop();
                    if (finished.getPriority() != 0) {
                        System.out.println(finished.getPid() + " finished executing");
                    }
                    done.add(finished);
                }
            }
            if (x > 0) currentTime += x;
            else currentTime++;
        }
        for (Process process : rr.getDone()) {
            done.add(process);
        }
    }

    @Override
    void sortWaiting() {
        // sort waiting queue by burst time
        Stack<Process> tmpStack = new Stack<>();
        while (!waiting.isEmpty()) {
            Process tmp = waiting.pop();
            while (!tmpStack.isEmpty() && tmpStack.peek().getArrivalTime() < tmp.getArrivalTime()) {
                waiting.push(tmpStack.pop());
            }
            tmpStack.push(tmp);
        }
        waiting = tmpStack;
    }
}