import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Scheduler_SJF extends Scheduler {
    @Override
    void startExecution() {
        int currentTime = 0;
        while (!ready.isEmpty() || !waiting.isEmpty()) {
            ArrayList<Process> processes = new ArrayList<>();

            // add processes to waiting stack as they arrive
            while (!ready.isEmpty()) {
                Process currentProcess = ready.poll();
                if (currentProcess.getArrivalTime() > currentTime) {
                    processes.add(currentProcess);
                } else {
                    waiting.push(currentProcess);
                }
            }

            for (Process process : processes) {
                ready.add(process);
            }

            if (!waiting.isEmpty()) {
                // sort waiting stack by burst time
                sortWaiting();

                // allow current process to execute
                waiting.peek().start();
                for (int i = waiting.size() - 2; i >= 0; i--) {
                    // increment process waiting time
                    waiting.get(i).hold();
                }
                // if a process has finished executing
                if (waiting.peek().getRunningTime() >= waiting.peek().getBurstTime()) {
                    Process finished = waiting.pop();
                    System.out.println(finished.getPid() + " finished executing");
                    done.add(finished);
                }
            }
            currentTime++;
        }
    }

    @Override
    void sortWaiting() {
        // sort waiting queue by burst time
        Stack<Process> tmpStack = new Stack<>();
        while (!waiting.isEmpty()) {
            Process tmp = waiting.pop();
            while (!tmpStack.isEmpty() && tmpStack.peek().getBurstTime() < tmp.getBurstTime()) {
                waiting.push(tmpStack.pop());
            }
            tmpStack.push(tmp);
        }
        waiting = tmpStack;
    }
}
