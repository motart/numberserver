package com.newrelic.codingchallenge;

import com.google.common.util.concurrent.AbstractScheduledService;

import java.util.concurrent.TimeUnit;

/**
 * Metrics tracking utility.
 */
public class ReportPrinter extends AbstractScheduledService {
    private static final int DELAY = 10;
    private volatile int diffUniqueCount = 0;
    private volatile int diffDupCount = 0;
    private volatile int totalUniqueCount = 0;

    @Override
    protected void runOneIteration() {
        System.out.println("Received " + getDiffUniqueCount() + " unique numbers, " + getDiffDupCount() + " duplicates. Unique total: " + getTotalUniqueCount() );

        // reset diff count after each report
        diffUniqueCount = 0;
        diffDupCount = 0;
    }

    @Override
    protected Scheduler scheduler() {
        return Scheduler.newFixedRateSchedule(0, DELAY, TimeUnit.SECONDS);
    }

    public synchronized void incrementTotalUniqueCount() {
        this.totalUniqueCount++;
    }

    public synchronized void incrementDiffDupCount() {
        this.diffDupCount++;
    }

    public synchronized void incrementDiffUniqueCount() {
        this.diffUniqueCount++;
    }

    public int getDiffUniqueCount() {
        return diffUniqueCount;
    }

    public int getDiffDupCount() {
        return diffDupCount;
    }

    public int getTotalUniqueCount() {
        return totalUniqueCount;
    }
}
