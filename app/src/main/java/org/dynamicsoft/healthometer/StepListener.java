package org.dynamicsoft.healthometer;

/**
 * Created by karan on 5/11/17.
 */

// Will listen to step alerts
public interface StepListener {
    public void step(long timeNs);
}