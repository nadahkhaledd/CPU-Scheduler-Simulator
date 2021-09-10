package Priority;

import java.util.*;
import Process.process;

public class Priority {
    static int processesNum;
    static process p = null;
    static List<process> processes = new ArrayList<>();
    static Scanner input = new Scanner(System.in);
    static int aging = 50;

    public static void set(int num) {
        System.out.println("Enter process Name, priority, Arrival Time, Execution Time");
        for (int i = 0; i < num; i++) {
            System.out.println("process " + (i+1) + ":");
            String name = input.next();
            int pri = input.nextInt();
            int arr = input.nextInt();
            int exe = input.nextInt();
            p = new process(name, arr, exe,pri);
            processes.add(p);
        }
    }

    public static void sort(List<process> p,Comparator x) {
        p.sort(x);
    }

    public static void print(){
        int avrgW = 0, avrgT = 0;
        System.out.println('\n');
        for (process process : processes) {
            avrgW += process.getWaitingTime();
            avrgT += process.getTurnAroundTime();
            System.out.println("name: " + process.getName() + ", waiting time: " + process.getWaitingTime() + ", turn around time: " + process.getTurnAroundTime());
            System.out.println();
        }
        System.out.println("Average time of waiting :" + (double)avrgW/processes.size());
        System.out.println("Average time of turn around :" + (double)avrgT/processes.size());
        System.out.println("\n----------------------------------------------------------------------");
    }

    public static void Run()
    {
        List<process> execute = new ArrayList<>();
        int time = 0, i = 0, j = 1;
        process x = null;
        sort(processes,Comparator.comparing(process::getArrivalTime));
        System.out.println("\n----------------------------------------------------------------------\n");
        while(i < processes.size() || !execute.isEmpty())
        {
            for (; i < processes.size(); i++){
                if(processes.get(i).getArrivalTime() == time){
                    execute.add(processes.get(i));
                }else if(processes.get(i).getArrivalTime() > time){
                    break;
                }
            }

            if(!execute.isEmpty()) {
                sort(execute, Comparator.comparing(process::getPriority));
                if(x != execute.get(0))
                    System.out.print(execute.get(0).getName() + " | ");
                x = execute.get(0);
                execute.get(0).setExecutionTime((execute.get(0).getExecutionTime()) - 1);
                if (execute.get(0).getExecutionTime() == 0) {
                    execute.get(0).setWaitingTime(time - execute.get(0).getTurnAroundTime() - execute.get(0).getArrivalTime() + 1);
                    execute.get(0).setTurnAroundTime(execute.get(0).getWaitingTime() + execute.get(0).getTurnAroundTime());
                    execute.remove(execute.get(0));
                    j = 0;
                }
            }
            if(execute.size() > j){
                for(; j < execute.size(); j++){
                    execute.get(j).setWaitingTime(execute.get(j).getWaitingTime()+1);
                    if(execute.get(j).getWaitingTime()%aging == 0 && execute.get(j).getPriority() > 0){
                        execute.get(j).setPriority(execute.get(j).getPriority()-1);
                    }
                }
                j = 1;
            }
            time++;
        }
        print();
    }

    public static void main(String[] args) {

        System.out.println("Enter number of processes");
        processesNum = input.nextInt();
        set(processesNum);
        Run();
    }

}
