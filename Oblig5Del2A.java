import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
public class Oblig5Del2A {
    private static Monitor1 monitor = new Monitor1();

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        String path = args[0];
        opprettSubsekvensRegister(path);
        // Monitor1 monitor2 = opprettSubsekvensRegister("testfiler/testdatalitenlike/metadata.csv");
        slaaSammenHash();
        skrivUtFlest(monitor);
        // skrivUtFlest(monitor2);
    }


    public static void opprettSubsekvensRegister(String path) throws FileNotFoundException, InterruptedException{
        File newFile = new File(path);
        Scanner tastatur;
        try{
            tastatur = new Scanner(newFile);

        } catch (FileNotFoundException e){
            throw new FileNotFoundException();
        }
        
        //Monitor1 monitor = new Monitor1();
        String linje;
        path = path.replace("metadata.csv", "");
        File nyFil = new File(path);
        CountDownLatch barrier = new CountDownLatch(nyFil.list().length-1);

        while (tastatur.hasNextLine()) {
            linje = tastatur.nextLine();

            Runnable leseTrad = new LeseTrad(path + linje, monitor, barrier);

            Thread traad = new Thread(leseTrad);
            traad.start();

            

        }
        tastatur.close();
        barrier.await();
    }

    private static void slaaSammenHash(){
        while(monitor.hentAntallHashMaps() > 1){
            HashMap<String, Subsekvens> newHash = Monitor1.slaaSammen(monitor.hent(), monitor.hent());
            monitor.settInn(newHash);
            
        }
    }

    private static void skrivUtFlest(Monitor1 monitor){
        HashMap<String, Subsekvens> newHash = monitor.hentRegister().get(0);
        Subsekvens flest = null;
        boolean check = true;

        for (String key : newHash.keySet()){
            if (check) {
                flest = newHash.get(key);
                check = false;
            } else {
                if (newHash.get(key).hentAntall() > flest.hentAntall()){
                    flest = newHash.get(key);
                }
            }
        }
        System.out.println(flest);

        
    }
    
}


