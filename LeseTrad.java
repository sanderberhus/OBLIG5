import java.io.FileNotFoundException;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

public class LeseTrad implements Runnable{
    private Monitor2 monitor;
    private final String FILNAVN;
    private CountDownLatch barrier;
    

    public LeseTrad(String filnavn, Monitor2 monitor, CountDownLatch barrier){
        this.FILNAVN = filnavn;
        this.monitor = monitor;
        this.barrier = barrier;

    }

    @Override
    public void run() {
        System.out.println("Starter Lesetrad!");
        HashMap<String, Subsekvens> hash;
        try {
            hash = Monitor2.lesFil(FILNAVN);
            monitor.settInn(hash);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        barrier.countDown();
    }
    
}
