package org.dynamicsoft.caloriescope.accelerometerCounter;

// Will listen to step alerts
public interface StepListener {
    void step(long timeNs);
}