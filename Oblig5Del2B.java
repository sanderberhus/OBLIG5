import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
public class Oblig5Del2B {
    private static Monitor2 monitor = new Monitor2();

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
        System.out.println("Ferdig med LeseTråd.");
        barrier.await();

        int antallFiler = nyFil.list().length-1;
    
        final int ANTALL_FLETTETRAADER = antallFiler -1;

        CountDownLatch barrier2 = new CountDownLatch(ANTALL_FLETTETRAADER);



        for (int i = 0; i < ANTALL_FLETTETRAADER; i++){
            Runnable fletteTrad = new FletteTraad(monitor, antallFiler, barrier2);
            Thread traad = new Thread(fletteTrad);

            traad.start();
        }
        barrier2.await();
        System.out.println("Ferdig med Fletting.");
    }


    
    

    private static void slaaSammenHash(){
        while(monitor.hentAntallHashMaps() > 1){
            HashMap<String, Subsekvens> newHash = Monitor2.slaaSammen(monitor.hent(), monitor.hent());
            monitor.settInn(newHash);
            
        }
    }

    private static void skrivUtFlest(Monitor2 monitor){
        HashMap<String, Subsekvens> newHash = monitor.hentRegister();
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


