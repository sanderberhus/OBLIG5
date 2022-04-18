import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Monitor2 extends SubsekvensRegister{
    private static Lock laas = new ReentrantLock(true);
    private Condition merEnnTo = laas.newCondition();
    private final int MAX = 2;
    private CountDownLatch barrier;
    // private ArrayList<HashMap<String, Subsekvens>> verdier = new ArrayList<>();
    
    


    // public ArrayList<HashMap<String, Subsekvens>> hentToHashMaps() throws InterruptedException{
    //     laas.lock();

    //     try {
    //         if(hentAntallHashMaps() < MAX){
    //             merEnnTo.await();   
    //         }
    //         ArrayList<HashMap<String,Subsekvens>> verdi = new ArrayList<>();
    //         verdi.add(hent());
    //         verdi.add(hent());
    //         return verdi;
            
    //     }finally{laas.unlock();}
    // }

    public void settInnFlettet(HashMap<String, Subsekvens> hash){
        laas.lock();
        try {
            hashBeholder.add(hash);
            if (hentAntallHashMaps() >= MAX){
                merEnnTo.signalAll();
            }
            System.out.println("Setter inn flettet...");    
            
        } finally{
            laas.unlock();
        }   
    }


    public Verdier<HashMap<String,Subsekvens>> hentTo() throws InterruptedException{
        laas.lock();

        try{
            while(hentAntallHashMaps() < MAX){
                merEnnTo.await();
            }
            System.out.println("Henter to kart!");
            return new Verdier<HashMap<String,Subsekvens>> (hent(), hent());

        }finally{laas.unlock();}

    }
        
        

    
    
    @Override
    public void settInn(HashMap<String, Subsekvens> hash){
        laas.lock();
        try {
            hashBeholder.add(hash);
            if (hentAntallHashMaps() >= MAX){
                merEnnTo.signalAll();
            }
            
            
        } finally{
            laas.unlock();
        }   
    }
    
    @Override
    public HashMap<String, Subsekvens> hent(){
        laas.lock();
        try{
        HashMap<String, Subsekvens> hash = hashBeholder.size() > 0 ? hashBeholder.get(0) : null;
        hashBeholder.remove(0);
        return hash;
        } finally{
            laas.unlock();
        }
     }
    
     @Override
    public int hentAntallHashMaps(){
        laas.lock();
        try{
        return hashBeholder.size();
        } finally{
            laas.unlock();
        }
    }
    
   
    

    public static HashMap<String, Subsekvens> lesFil(String filnavn) throws FileNotFoundException{
        laas.lock();
        try{
            HashMap<String, Subsekvens> subsekvenser = new HashMap<>();
            try{
                Scanner input = new Scanner(new File(filnavn));
                subsekvenser = LagSubsekvenser.lagSubsekvenser(input);
            
            } catch (FileNotFoundException e){
                throw new FileNotFoundException();
                }
            return subsekvenser;

            }finally
                {laas.unlock();}
        
    }

    public static HashMap<String, Subsekvens> slaaSammen(HashMap<String, Subsekvens> hm1, HashMap<String, Subsekvens> hm2) {
        
        HashMap<String, Subsekvens> nyHash = new HashMap<String,Subsekvens> (hm1);
        laas.lock();
        try {
            for (String key : hm2.keySet()){
                if (hm1.containsKey(key)){
                    int antall = hm2.get(key).hentAntall();
                    nyHash.get(key).leggTilAntall(antall);
                } else {
                    nyHash.put(key, hm2.get(key));
                }
            }
        } finally{
            laas.unlock();
        }
        
        System.out.println("Slaar sammen to kart!");
        return nyHash;
         
    }
}

