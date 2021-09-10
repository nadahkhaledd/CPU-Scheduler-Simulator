package MultiLevel;

import java.util.*;
import Process.process;

public class roundrobin {

    List<process> processes = new ArrayList<>();
    Queue<process> execute = new LinkedList<>();

    public void calcTurnAround()
    {
        for (process process : processes)
            process.turnAroundTime += process.waitingTime + process.extime;
    }

    public boolean areExecuted()
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

    public void checkExecutions(int clock)
    {
        for (process process : processes) {
            if (!(process.getArrivalTime() > clock) && !process.isDone) {
                process.waitingTime = clock - process.getArrivalTime();
                execute.add(process);
                process.isDone = true;
            }
        }
    }

    public void intoTheQueue(process current)
    {
        if (processes.contains(current))
            execute.add(processes.get(processes.indexOf(current)));
    }

    public void addWait(int time)
    {
        for (process process : processes) {
            if (execute.contains(process))
                process.waitingTime += time;
        }
    }

    public int calculations( int quantum, int clock)
    {

        process current  = null;
        while(!areExecuted())
        {
            checkExecutions(clock);
            if(current != null)
            {
                intoTheQueue(current);
                current = null;
            }
            if(!execute.isEmpty())
            {

                if(execute.peek().ExecutionTime > quantum)
                {
                    clock += quantum;
                    current = execute.peek();
                    execute.peek().ExecutionTime -= quantum;
                    execute.poll();
                    addWait(quantum);
                }
                else
                {
                    int tempExecution = execute.peek().ExecutionTime;
                    clock+= execute.peek().ExecutionTime;
                    execute.peek().ExecutionTime = 0;
                    execute.poll();
                    addWait(tempExecution);
                }
            }
            else
                clock++;
        }
       calcTurnAround();
       return clock;
    }
}
