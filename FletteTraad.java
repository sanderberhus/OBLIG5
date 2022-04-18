
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
public class FletteTraad implements Runnable {
    private Monitor2 monitor;
    private CountDownLatch barrier;
    private int antallFiler;
    

    public FletteTraad(Monitor2 monitor, int antallFiler, CountDownLatch barrier){
        this.monitor = monitor;
        this.barrier = barrier;
        this.antallFiler = antallFiler;
    
    }
        

    @Override
    public void run() {
        try {
            if (antallFiler >= 1) {
                System.out.println("Starter flettetraad!");
                Verdier<HashMap<String, Subsekvens>> toHash = monitor.hentTo();
                HashMap<String, Subsekvens> hashMap = Monitor2.slaaSammen(toHash.hentVerdi1(), toHash.hentVerdi2());
                monitor.settInnFlettet(hashMap);
                System.out.println("Ferdig flettet.");
            }

        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        barrier.countDown();
     
    }


}
    

