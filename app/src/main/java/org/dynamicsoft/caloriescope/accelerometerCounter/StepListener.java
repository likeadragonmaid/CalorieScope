package org.dynamicsoft.caloriescope.accelerometerCounter;

// Will listen to step alerts
public interface StepListener {
    public void step(long timeNs);
}