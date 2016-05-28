/*
 * This work is licensed under the Creative Commons Attribution 3.0 Unported
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
 * Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
 */
package org.tros.utils;

/**
 * This is a wrapper class around an object's wait and notify methods. This is
 * used as a class for familiarity with .NET's event classes.
 *
 * @author matta
 */
public class ManualResetEvent {

    private final Object monitor = new Object();
    private volatile boolean open = false;

    /**
     * Constructor.
     *
     * @param open
     */
    public ManualResetEvent(boolean open) {
        this.open = open;
    }

    /**
     * Wait for signal.
     *
     * @throws InterruptedException
     */
    public void waitOne() throws InterruptedException {
        synchronized (monitor) {
            while (open == false) {
                monitor.wait();
            }
        }
    }

    /**
     * Wait for signal.
     *
     * @param milliseconds
     * @return
     * @throws InterruptedException
     */
    public boolean waitOne(long milliseconds) throws InterruptedException {
        synchronized (monitor) {
            if (open) {
                return true;
            }
            monitor.wait(milliseconds);
            return open;
        }
    }

    /**
     * Signal.
     */
    public void set() {//open start
        synchronized (monitor) {
            open = true;
            monitor.notifyAll();
        }
    }

    /**
     * Reset.
     */
    public void reset() {//close stop
        open = false;
    }

    /**
     * Is the event open?
     *
     * @return
     */
    public boolean isOpen() {
        return open;
    }
}
