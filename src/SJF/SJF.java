package SJF;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import Process.process;

public class SJF {

	static int processesNum;
	static int contextSwitch;
	static int time = 0;
	static process pIn = null;
	static List<process> processes = new ArrayList<process>();
	static Queue<process> execute = new LinkedList<process>();
	static Scanner input = new Scanner(System.in);

	public static void set(int num) {
		System.out.println("Enter Process Name, Arrival Time, Execution Time");
		for (int i = 0; i < num; i++) {
			String name = input.next();
			int arr = input.nextInt();
			int exe = input.nextInt();
			pIn = new process(name, arr, exe, 0);
			processes.add(pIn);
		}
	}

	public static void sort() {
		Collections.sort(processes, Comparator.comparing(process::getArrivalTime));
	}

	public static void run() {
		List<process> order = new ArrayList<process>();
		int i = 0;
		while ( i < processes.size()) 
		{
			if (processes.get(i).ArrivalTime <= time && !processes.get(i).isDone) 
			{
				order.add(processes.get(i));
				i++;
			}
			process p = null;
			int index = 0;
			p = order.get(index);
			while (index < order.size()) 
			{
				if (p.ExecutionTime > order.get(index).ExecutionTime) 
				{
					p = order.get(index);
				}
				index++;
			}
			execute.add(p);
			time++;
			p.ExecutionTime -= 1;
			if (p.ExecutionTime == 0) 
			{
				p.isDone = true;
				time += contextSwitch;
				p.completionTime = time;
				order.remove(p);
			}
		}
		while (!order.isEmpty()) 
		{
			process p = null;
			int index = 0;
			p = order.get(index);
			while (index < order.size()) 
			{
				if (p.ExecutionTime > order.get(index).ExecutionTime)
				{
					p = order.get(index);
				}
				index++;
			}
			execute.add(p);
			time++;
			p.ExecutionTime -= 1;
			if (p.ExecutionTime == 0) 
			{
				p.isDone = true;
				time += contextSwitch;
				p.completionTime = time;
				order.remove(p);
			}
		}
	}

	public static void main(String[] args) 
	{
		System.out.println("Enter number of processes");
		processesNum = input.nextInt();
		set(processesNum);
		sort();
		System.out.println("Enter context switch ");
		contextSwitch = input.nextInt();
		run();
		System.out.println("\nProcesses execution order : ");
		System.out.println(execute + "\t");
		System.out.println("\nProcess | Waiting time  | Turnaround  Time ");
		System.out.println("---------------------------------------------");
		int totalWait = 0;
		int totalAround = 0;
		double avgWaiting = 0;
		double avgTurnAround = 0;
		for(int i=0; i<processesNum; i++)
		{
			totalWait = processes.get(i).completionTime - processes.get(i).ArrivalTime - processes.get(i).extime;
			totalAround = processes.get(i).completionTime - processes.get(i).ArrivalTime;
			avgWaiting += totalWait;
			avgTurnAround += totalAround;
			System.out.println(processes.get(i).name + "\t|\t" + totalWait + "\t|\t" + totalAround);
		}
		System.out.println("\nAverage Waiting Time =  " + avgWaiting/processesNum);
		System.out.println("Average Turnaround Time =  " + avgTurnAround/processesNum);
	}

}
