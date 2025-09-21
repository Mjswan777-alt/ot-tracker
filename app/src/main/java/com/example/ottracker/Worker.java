package com.example.ottracker;

/**
 * Represents a worker in the overtime rotation. Stores the worker's
 * name, opt-out status, and the total number of overtime shifts they
 * have been assigned.
 */
public class Worker {
    private String name;
    private boolean optOut;
    private int overtimeCount;

    public Worker(String name) {
        this.name = name;
        this.optOut = false;
        this.overtimeCount = 0;
    }

    public String getName() { return name; }

    public boolean isOptOut() { return optOut; }

    public void setOptOut(boolean optOut) { this.optOut = optOut; }

    public int getOvertimeCount() { return overtimeCount; }

    public void addOvertime() { overtimeCount++; }
}
