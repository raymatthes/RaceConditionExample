package rce;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Ray Matthes
 */
public class DstMqService {

    private int count = 0;

    // careful, this getter is not thread safe
    public int getCount() {
        return count;
    }

    private static final DstMqService instance = new DstMqService();

    // make the constructor private so that this class cannot be instantiated
    private DstMqService() {
    }

    // get the only object available
    public static DstMqService getInstance() {
        return instance;
    }

    AtomicInteger atomicCount = new AtomicInteger();

    public int getCountViaAtomicInteger() {
        count = atomicCount.incrementAndGet();
        return count;
    }

    public int increment() {
        return count++;
    }

    public synchronized int incrementSyncMethod1() {
        System.out.print(" 1");
        return count++;
    }

    public synchronized int incrementSyncMethod2() {
        System.out.print(" 2");
        return count++;
    }

    public int incrementSyncBlock() {
        int result;
        synchronized (this) {
            result = count++;
        }
        return result;
    }

    // mutual exclusion, same as synchronized
    ReentrantLock lock = new ReentrantLock();

    public int incrementLock() {
        int result;
        lock.lock();
        try {
            result = count++;
        } finally {
            lock.unlock();
        }
        return result;
    }

}
