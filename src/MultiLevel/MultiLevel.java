package MultiLevel;

import java.util.*;
import Process.process;

public class MultiLevel {
    static int timeQuantum;
    static int processesNum;
    static process p = null;
    static List<process> processes1 = new ArrayList<>();
    static List<process> processes2 = new ArrayList<>();
    static List<process> processes = new ArrayList<>();
    static Scanner input = new Scanner(System.in);

    public static void set(int num) {
        System.out.println("Enter Process Name, queue number, Arrival Time, Execution Time");
        for (int i = 0; i < num; i++) {
            String name = input.next();
            int q = input.nextInt();
            int arr = input.nextInt();
            int exe = input.nextInt();
            p = new process(name, arr, exe, 0);
            if(q == 1){
                processes1.add(p);
            }else {
                processes2.add(p);
            }
            processes.add(p);
        }
    }

    public static void sort(List<process> p,Comparator x) {
        Collections.sort(p, x);
    }

    public static void run( int quantum) {
        int time = 0, j = 0, i = 0;
        List<process> execute1 = new ArrayList<>();
        Queue<process> execute2 = new LinkedList<>();
        sort(processes2,Comparator.comparing(process::getArrivalTime));
        while (j < processes2.size() || !execute2.isEmpty() || i < processes1.size()){
            for (i = 0; i < processes1.size(); i++){
                if(processes1.get(i).getArrivalTime() <= time && !processes1.get(i).isDone){
                    execute1.add(processes1.get(i));
                }
            }
            for (j = 0; j < processes2.size(); j++){
                if(processes2.get(j).getArrivalTime() <= time && !processes1.get(j).isDone){
                    execute2.add(processes2.get(j));
                }
            }
            if(execute1.isEmpty() && !execute2.isEmpty()){
                process p1 = execute2.peek();

                p1.setStartingTime(time);
                p1.ExecutionTime--;
                time++;
                if(p1.ExecutionTime == 0){
                    p1.waitingTime = time - p1.extime - p1.getArrivalTime();
                    p1.turnAroundTime = p1.waitingTime + p1.extime;
                    p1.isDone = true;
                    execute2.poll();
                }
            }else{
                roundrobin rb = new roundrobin();
                rb.processes = execute1;
                time = rb.calculations(quantum,time);
                execute1 = new ArrayList<>();
            }
        }
    }

    public static void print(){
        sort(processes,Comparator.comparing(process::getStartingTime));
        int avrgt = 0, avrgw = 0;

        System.out.println("\nProcess | Arrival Time | Execution Time | Waiting Time | Turn Around Time");
        for (process process : processes) {
            avrgt += process.turnAroundTime;
            avrgw += process.waitingTime;

            System.out.println(process.name + " \t\t|\t"
                    + process.getArrivalTime() + "\t\t\t|\t\t"
                    + process.extime + "\t\t|\t\t"
                    + process.waitingTime + "\t\t|\t\t"
                    + process.turnAroundTime);
        }
        System.out.println("\nAverage time of waiting :" + (double)avrgw/processes.size());
        System.out.println("Average time of turn around :" + (double)avrgt/processes.size());
    }

    public static void main(String[] args) {
        System.out.println("Enter number of processes");
        processesNum = input.nextInt();

        set(processesNum);

        System.out.println("Enter time Quantum");
        timeQuantum = input.nextInt();
        run(timeQuantum);
        print();
    }
}
