package rce;

import java.util.concurrent.Callable;

/**
 * @author Ray Matthes
 */
public class TaskThread implements Callable<Integer> {

    public static boolean classFlip = false;

    private final DstMqService dstMqService = DstMqService.getInstance();
    private final String type;
    private boolean flip = false;

    public TaskThread(String type) {
        classFlip = !classFlip;

        this.type = type;
        this.flip = classFlip;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Integer call() throws Exception {

        int result = 0;

        switch (type) {
            case "RaceCondition":
                result = dstMqService.increment();
                break;
            case "Lock":
                result = dstMqService.incrementLock();
                break;
            case "SynchronizedBlock":
                result = dstMqService.incrementSyncBlock();
                break;
            case "SynchronizedMethod":
                result = dstMqService.incrementSyncMethod1();
                break;
            case "AtomicInteger":
                result = dstMqService.getCountViaAtomicInteger();
                break;
            case "SynchronizedMethodFlip":
                result = flip ?
                        dstMqService.incrementSyncMethod1() :
                        dstMqService.incrementSyncMethod2();
                break;
        }

        return result;
    }

}
