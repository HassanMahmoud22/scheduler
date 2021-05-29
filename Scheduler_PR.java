import java.util.ArrayList;
import java.util.Stack;

public class Scheduler_PR extends Scheduler {
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
                // sort waiting stack by priority
                sortWaiting();

                // allow current process to execute
                waiting.peek().start();
                for (int i = waiting.size() - 2; i >= 0; i--) {
                    // increment process waiting time
                    waiting.get(i).hold();
                    // solve starvation if a process waits for time equals its burst time
                    if (waiting.get(i).getWaitingTime() == waiting.get(i).getBurstTime()) {
                        waiting.get(i).promote();
                        sortWaiting();
                    }
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
        // sort waiting queue by priority
        Stack<Process> tmpStack = new Stack<>();
        while (!waiting.isEmpty()) {
            Process tmp = waiting.pop();
            while (!tmpStack.isEmpty() && tmpStack.peek().getPriority() < tmp.getPriority()) {
                waiting.push(tmpStack.pop());
            }
            tmpStack.push(tmp);
        }
        waiting = tmpStack;
    }
}
