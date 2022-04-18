import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;

class SubsekvensRegister {
    protected ArrayList <HashMap<String, Subsekvens>> hashBeholder = new ArrayList<>() ;

    public SubsekvensRegister(){
        hashBeholder = new ArrayList<HashMap<String, Subsekvens>>();
    }

    public void settInn(HashMap<String, Subsekvens> hash){
        hashBeholder.add(hash);

    }

    public HashMap<String, Subsekvens> hent(){
       HashMap<String, Subsekvens> hash = hashBeholder.size() > 0 ? hashBeholder.get(0) : null;
       hashBeholder.remove(0);
       return hash;
    }

    public int hentAntallHashMaps(){
        return hashBeholder.size();
    }

    public HashMap<String, Subsekvens> hentRegister(){
        return hashBeholder.get(0);
    }

    
    
    public static HashMap<String, Subsekvens> lesFil(String filnavn) throws FileNotFoundException {
         HashMap<String, Subsekvens> nyHash = new HashMap<>();
         File newFile = new File(filnavn);
         Scanner tastatur;
        try{
            tastatur = new Scanner(newFile);
        
        } catch (FileNotFoundException e){
            throw new FileNotFoundException();
            }
        
        //Oppretter Arraylist for bokstaver og sekvenser
        ArrayList<String> bokstaver = new ArrayList<>();
        ArrayList<String> sekvens = new ArrayList<>();
        String line;
        
        //Går gjennom scanner
        while (tastatur.hasNextLine()){
            line = tastatur.nextLine();

            //Sjekker om lengden er mindre enn 3 og avslutter dersom den er det.
            if (line.length() < 3){
                System.exit(-1);
            }
            //Deler opp linjene fra scan og legger til i liste biter.
            String[] biter = line.split("");
            
            //går gjennom hver bit i biter og legger til i bokstaver.
            for (String bit: biter) {
                bokstaver.add(bit);
            }
            //Så lenge bokstaver er større eller lik tre kegger jeg til i sekvens.
            while (bokstaver.size() >= 3){
                String sekv = bokstaver.get(0) + bokstaver.get(1) + bokstaver.get(2);
                if (!sekvens.contains(sekv)){
                    sekvens.add(sekv);
                }
                //Dersom den ikke, fjernes bokstaver
                bokstaver.remove(0);
            }
            //Looper gjennom sekvens og legger til i ny Hash.
            for (String s : sekvens){
                Subsekvens ny = new Subsekvens(s,1);
                nyHash.put(s,ny);
            }
            //Fjerner alt
            bokstaver.clear();
            sekvens.clear();
        }
        //Lukker tastatur og returnerer Hash.
        tastatur.close();
        return nyHash;
    }
        

    
    public static HashMap<String, Subsekvens> slaaSammen(HashMap<String, Subsekvens> hm1, HashMap<String, Subsekvens> hm2) {
        
        HashMap<String, Subsekvens> nyHash = new HashMap<String,Subsekvens> (hm1);
        
        for (String key : hm2.keySet()){
            if (hm1.containsKey(key)){
                int antall = hm2.get(key).hentAntall();
                nyHash.get(key).leggTilAntall(antall);
            } else {
                nyHash.put(key, hm2.get(key));
            }
        }
        return nyHash;
    }
}


