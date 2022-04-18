import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
public class Oblig5Hele {
    private static Monitor2 harHattSykdom = new Monitor2();
    private static Monitor2 harIkkeHattSykdom = new Monitor2();
    private static int antallHarHattSykdom = 0;
    private static int antallHarIkkeHattSykdom = 0;
    private final static int MAX = 8;
    //private final int ANTALL_FLETTETRAADER;

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        String path = args[0];
        opprettSubsekvensRegister(path);
        skrivUtFlestSubsekvenser();

        System.exit(0);
    
        
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
            String biter[] = linje.split(",");
            
            if (biter[1].equals("True")){
                antallHarHattSykdom++;
            }else{
                antallHarIkkeHattSykdom++;
            }

            if (Boolean.parseBoolean(biter[1])){
                // antallHarHattSykdom++;
                Runnable leseTrad = new LeseTrad(path + biter[0], harHattSykdom, barrier);
                Thread traad = new Thread(leseTrad);
                traad.start();
            }else{
                // antallHarIkkeHattSykdom ++;
                Runnable leseTrad = new LeseTrad(path + biter[0], harIkkeHattSykdom, barrier);
                Thread traad = new Thread(leseTrad);
                traad.start();
            }
        }
        tastatur.close();
        barrier.await();
        System.out.println("Ferdig med LeseTrådbarriere.");

        int antallFiler = nyFil.list().length-1;

        final int ANTALL_FLETTETRAADER;

        if (antallHarHattSykdom > 1 && antallHarIkkeHattSykdom > 1){
            ANTALL_FLETTETRAADER = antallFiler - 2;
        } else{
            ANTALL_FLETTETRAADER = antallFiler - 1;
        }
    

    
        CountDownLatch barrier2 = new CountDownLatch(ANTALL_FLETTETRAADER);

        

        for (int i = 0; i < MAX; i++){
            Runnable fletteTraad = new FletteTraad(harHattSykdom, antallHarHattSykdom - 1, barrier2);
            Thread traad = new Thread(fletteTraad);
            traad.start();
        }
        for (int i = 0; i < MAX; i++){
            Runnable fletteTraad = new FletteTraad(harIkkeHattSykdom, antallHarIkkeHattSykdom - 1, barrier2);
            Thread traad = new Thread(fletteTraad);
            traad.start();
        }

         //Fletter de som ikke har hatt sykdom
        //  if (antallHarIkkeHattSykdom > 1) {
             
        //     for (int i = 0; i < antallHarIkkeHattSykdom - 1; i++) {
        //         Runnable fletteTraad = new FletteTraad(harIkkeHattSykdom, antallHarIkkeHattSykdom, barrier2);
        //         Thread traad = new Thread(fletteTraad);
        //         traad.start();
        //     }
        // } else {
        //     Runnable fletteTraad = new FletteTraad(harIkkeHattSykdom, antallHarIkkeHattSykdom, barrier2);
        //     Thread traad = new Thread(fletteTraad);
        //     traad.start();
        // }
        // //Flett de som har hatt sykdom
        // if (antallHarHattSykdom > 1) {
        //     for (int i = 0; i < antallHarHattSykdom - 1; i++) {
        //         Runnable fletteTraad = new FletteTraad(harHattSykdom, antallHarHattSykdom, barrier2);
        //         Thread traad = new Thread(fletteTraad);
        //         traad.start();
        //     }
        // } else {
        //     Runnable fletteTraad = new FletteTraad(harHattSykdom, antallHarHattSykdom, barrier2);
        //     Thread traad = new Thread(fletteTraad);
        //     traad.start();
        
        barrier2.await();
    
        System.out.println("Ferdig med Fletting.");
    }

    

    private static void slaaSammenHash(Monitor2 monitor){
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
    private static void skrivUtFlestSubsekvenser(){
        HashMap<String, Subsekvens> harHatt = harHattSykdom.hentRegister();
        HashMap<String, Subsekvens> harIkkeHatt = harIkkeHattSykdom.hentRegister();
        int antall = 0;

        ArrayList<String> flest = new ArrayList<>();

        for (String nokkel1 : harHatt.keySet()){
            if (harIkkeHatt.keySet().contains(nokkel1)){
                for (String nokkel2 : harIkkeHatt.keySet()){
                    if (nokkel1.equals(nokkel2)){
                        antall = harHatt.get(nokkel1).hentAntall() - harIkkeHatt.get(nokkel2).hentAntall();
                    }
                }
            }
            else {
                antall = harHatt.get(nokkel1).hentAntall();
            }
            
            if (antall > 6){
                flest.add(nokkel1);
            } 
        }
        for (String mindre : flest) {
            System.out.println(mindre);
        }

       

    
       
    
    }
    
}


