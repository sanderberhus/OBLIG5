import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
public abstract class LagSubsekvenser {

    public static HashMap<String, Subsekvens> lagSubsekvenser(Scanner input){

     //Oppretter Arraylist for bokstaver og sekvenser
     HashMap<String, Subsekvens> nyHash = new HashMap<>();
     ArrayList<String> bokstaver = new ArrayList<>();
     ArrayList<String> sekvens = new ArrayList<>();
     String line;
    //Går gjennom scanner
     while (input.hasNextLine()){
         line = input.nextLine();

         //Sjekker om lengden er mindre enn 3 og avslutter dersom den er det.
         if (line.length() < 3){
             System.exit(-2);
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
     input.close();
     return nyHash;
    
}

}
