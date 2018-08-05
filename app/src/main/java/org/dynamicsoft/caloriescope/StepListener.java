package org.dynamicsoft.caloriescope;

// Will listen to step alerts
public interface StepListener {
    public void step(long timeNs);
}