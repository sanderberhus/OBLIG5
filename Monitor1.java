import java.util.concurrent.locks.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.*;


public class Monitor1 extends SubsekvensRegister {
    private static Lock laas = new ReentrantLock(true);

    //Overskriver metode fra SusbsekvensRegister og legger til laas.
    @Override
    public void settInn(HashMap<String, Subsekvens> hash){
        laas.lock();
        try {
            hashBeholder.add(hash);
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
    
    @Override
    public ArrayList<HashMap<String, Subsekvens>> hentRegister(){
        laas.lock();
        try{
        return hashBeholder;
    }finally{
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
    
}

