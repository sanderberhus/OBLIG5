import java.util.ArrayList;
import java.util.HashMap;

public class Verdier<T> {

    ArrayList<HashMap<String, Subsekvens>> verdier = new ArrayList<HashMap<String, Subsekvens>>();

    public Verdier(HashMap<String, Subsekvens>v1, HashMap<String, Subsekvens>v2){
        verdier.add(v1);
        verdier.add(v2);
    }
    

    public HashMap<String, Subsekvens> hentVerdi1(){
        return verdier.get(0);
    }

    public HashMap<String, Subsekvens> hentVerdi2(){
        return verdier.get(1);
    }
}
