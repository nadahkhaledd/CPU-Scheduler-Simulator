package RoundRobin;

import java.util.*;
import Process.process;

public class RoundRobin {

    static int timeQuantum;
    static int processesNum;
    static int contextSwitch;
    static int clock = 0;
    static  float avgWaiting = 0;
    static float avgTurnAround = 0;
    static process p = null;
    static List<process> processes = new ArrayList<>();
    static Queue<process> execute = new LinkedList<>();
    static Scanner input = new Scanner(System.in);

    public static void set(int num) {
        System.out.println("Enter Process Name, Arrival Time, Execution Time");
        for (int i = 0; i < num; i++) {
            String name = input.next();
            int arr = input.nextInt();
            int exe = input.nextInt();
            p = new process(name, arr, exe, 0);
            processes.add(p);
        }
    }

    public static void sort() {
        processes.sort(Comparator.comparing(process::getArrivalTime));
    }

    public static void calcTurnAround()
    {
        for (process process : processes)
            process.turnAroundTime += process.waitingTime + process.extime;
    }

    public static void calcCompletion()
    {
        for (process process : processes)
            process.completionTime = process.turnAroundTime + process.getArrivalTime();
    }

    public static void calcAvgWaiting()
    {
        for (process process : processes)
            avgWaiting += process.waitingTime;

        avgWaiting = avgWaiting / processes.size();
    }

    public static void calcAvgTurnAround()
    {
        for (process process : processes)
            avgTurnAround += process.turnAroundTime;

        avgTurnAround = avgTurnAround / processes.size();
    }

    public  static boolean areExecuted()
    {
        boolean isFinished = true;
        for (process process : processes) {
            if (process.ExecutionTime != 0) {
                isFinished = false;
                break;
            }
        }
        return isFinished;
    }

    public static void checkExecutions()
    {
        for (process process : processes) {
            if (!(process.getArrivalTime() > clock) && !process.isDone) {
                process.waitingTime = clock - process.getArrivalTime();
                execute.add(process);
                process.isDone = true;
            }
        }
    }

    public static void intoTheQueue(process current)
    {
        if (processes.contains(current))
            execute.add(processes.get(processes.indexOf(current)));
    }

    public static void addWait(int time, int context)
    {
        for (process process : processes) {
            if (execute.contains(process))
                process.waitingTime += time + context;
        }
    }

    public static void calculations( int quantum, int context)
    {
        System.out.println("--------------------Execution order-------------------\n");

        process current  = null;
        while(!areExecuted())
        {
            checkExecutions();
            if(current != null)
            {
                intoTheQueue(current);
                current = null;
            }
            if(!execute.isEmpty())
            {
                System.out.print( execute.peek().name + " | ");

                if(execute.peek().ExecutionTime > quantum)
                {
                    clock += quantum;
                    current = execute.peek();
                    execute.peek().ExecutionTime -= quantum;
                    processes.get(processes.indexOf(execute.peek())).turnAroundTime += context;
                    execute.poll();
                    addWait(quantum, context);
                }
                else
                {
                    int tempExecution = execute.peek().ExecutionTime;
                    clock+= execute.peek().ExecutionTime;
                    execute.peek().ExecutionTime = 0;
                    processes.get(processes.indexOf(execute.peek())).turnAroundTime += context;
                    execute.poll();
                    addWait(tempExecution, context);
                }
                clock += context;
            }
            else
                clock++;
        }
       calcTurnAround();
       calcCompletion();
       calcAvgWaiting();
       calcAvgTurnAround();
    }

    public static void print()
    {
        System.out.println("\n-------------------------------------------------------------------------------------------");
        System.out.println("Process | Arrival Time | Execution Time | Completion Time | Waiting Time | Turn Around Time");
        for (process process : processes) {
            System.out.println(process.name + " \t\t|\t"
                    + process.getArrivalTime() + "\t\t\t|\t\t"
                    + process.extime + "\t\t|\t\t"
                    + process.completionTime + "\t\t|\t\t"
                    + process.waitingTime + "\t\t|\t\t"
                    + process.turnAroundTime);
        }

        System.out.println("\nAverage Waiting time = " + avgWaiting);
        System.out.println("Average Turn Around Time = " + avgTurnAround);
    }


    public static void main(String[] args) {

        System.out.println("Enter number of processes");
        processesNum = input.nextInt();

        set(processesNum);
        sort();

        System.out.println("Enter time Quantum");
        timeQuantum = input.nextInt();
        System.out.println("Enter context switch ");
        contextSwitch = input.nextInt();

        calculations(timeQuantum, contextSwitch);
        print();
    }

}
