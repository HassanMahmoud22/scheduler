import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner inputString = new Scanner(System.in);
        Scanner inputInt = new Scanner(System.in);

        String choice = "", processName = "";
        int processesCount = 0, burstTime = 0, arrivalTime = 0, quantum = 0, priority = 0, contextSwitch = 0;

        while (true) {
            printMenu();
            choice = inputString.nextLine();
            if (choice.equals("1")) {
                Scheduler_SJF sjf = new Scheduler_SJF();
                ArrayList<Process> processes_sjf = new ArrayList<>();
                System.out.println("Enter number of processes");
                processesCount = inputInt.nextInt();
                for (int i = 0; i < processesCount; i++) {
                    System.out.println("Enter name of process no." + (i + 1));
                    processName = inputString.nextLine();
                    System.out.println("Enter burst time of process no." + (i + 1));
                    burstTime = inputInt.nextInt();
                    System.out.println("Enter arrival time of process no." + (i + 1));
                    arrivalTime = inputInt.nextInt();
                    processes_sjf.add(new Process(processName, burstTime, arrivalTime));
                }
                testScheduler(sjf, processes_sjf);
            } else if (choice.equals("2")) {
                ArrayList<Process> processes_rr = new ArrayList<>();
                System.out.println("Enter quantum time");
                quantum = inputInt.nextInt();
                System.out.println("Enter context switch time");
                contextSwitch = inputInt.nextInt();
                Scheduler_RR rr = new Scheduler_RR(quantum, contextSwitch);
                System.out.println("Enter number of processes");
                processesCount = inputInt.nextInt();
                for (int i = 0; i < processesCount; i++) {
                    System.out.println("Enter name of process no." + (i + 1));
                    processName = inputString.nextLine();
                    System.out.println("Enter burst time of process no." + (i + 1));
                    burstTime = inputInt.nextInt();
                    processes_rr.add(new Process(processName, burstTime));
                }
                testScheduler(rr, processes_rr);
            } else if (choice.equals("3")) {
                Scheduler_PR pr = new Scheduler_PR();
                ArrayList<Process> processes_pr = new ArrayList<>();
                System.out.println("Enter number of processes");
                processesCount = inputInt.nextInt();
                for (int i = 0; i < processesCount; i++) {
                    System.out.println("Enter name of process no." + (i + 1));
                    processName = inputString.nextLine();
                    System.out.println("Enter burst time of process no." + (i + 1));
                    burstTime = inputInt.nextInt();
                    System.out.println("Enter arrival time of process no." + (i + 1));
                    arrivalTime = inputInt.nextInt();
                    System.out.println("Enter priority of process no." + (i + 1));
                    priority = inputInt.nextInt();
                    processes_pr.add(new Process(processName, burstTime, arrivalTime, priority));
                }
                testScheduler(pr, processes_pr);
            } else if (choice.equals("4")) {
                System.out.println("Enter quantum time");
                quantum = inputInt.nextInt();
                System.out.println("Enter context switch time");
                contextSwitch = inputInt.nextInt();
                Scheduler_ML ml = new Scheduler_ML(quantum, contextSwitch);
                ArrayList<Process> processes_ml = new ArrayList<>();
                System.out.println("Enter number of processes");
                processesCount = inputInt.nextInt();
                for (int i = 0; i < processesCount; i++) {
                    System.out.println("Enter name of process no." + (i + 1));
                    processName = inputString.nextLine();
                    System.out.println("Enter burst time of process no." + (i + 1));
                    burstTime = inputInt.nextInt();
                    System.out.println("Enter arrival time of process no." + (i + 1));
                    arrivalTime = inputInt.nextInt();
                    System.out.println("Enter queue priority of process no." + (i + 1));
                    priority = inputInt.nextInt();
                    processes_ml.add(new Process(processName, burstTime, arrivalTime, priority));
                }
                testScheduler(ml, processes_ml);
            } else if (choice.equals("5"))
                System.exit(0);
            else
                printMenu();
        }
    }

    public static void printMenu() {
        System.out.println("1- shortest job first");
        System.out.println("2- round robin");
        System.out.println("3- preemptive");
        System.out.println("4- multiple level");
        System.out.println("5- exit");
        System.out.println("choose what type of scheduling you want to test: ");
    }

    public static void testScheduler(Scheduler scheduler, ArrayList<Process> processes) {
        for (Process process : processes) scheduler.scheduleProcess(process);
        scheduler.startExecution();
        System.out.println("###Waiting times###");
        scheduler.getWaitingTime();
        scheduler.getAverageWaitingTime();
        System.out.println("######################");
        System.out.println("###Turnaround times###");
        scheduler.getTurnaroundTime();
        scheduler.getAverageTurnaroundTime();
        System.out.println("######################");
    }
}