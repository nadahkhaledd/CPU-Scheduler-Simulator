package Process;

public class process {

    public String name;
    public int ArrivalTime;
    public int ExecutionTime;
    public int waitingTime;
    public int turnAroundTime;
    public int completionTime;
    public int extime;
    public boolean isDone = false;
    public int startingTime;
    private int priority;

    public process(String name, int ArrivalTime, int ExecutionTime, int priority) {
        this.name = name;
        this.ArrivalTime = ArrivalTime;
        this.ExecutionTime = ExecutionTime;
        this.priority = priority;
        extime = ExecutionTime;
        startingTime = -1;
    }

    public int getArrivalTime() {
        return ArrivalTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getTurnAroundTime() {
        return turnAroundTime;
    }

    public void setTurnAroundTime(int turnAroundTime) {
        this.turnAroundTime = turnAroundTime;
    }

    @Override
    public String toString()
    {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArrivalTime(int arrivalTime) {
        ArrivalTime = arrivalTime;
    }

    public int getExecutionTime() {
        return ExecutionTime;
    }

    public void setExecutionTime(int executionTime) {
        ExecutionTime = executionTime;
    }

    public int getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
    }

    public int getExtime() {
        return extime;
    }

    public void setExtime(int extime) {
        this.extime = extime;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public void setStartingTime(int startingTime) {
        if(this.startingTime == -1){
            this.startingTime = startingTime;
        }
    }

    public int getStartingTime() {
        return startingTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
