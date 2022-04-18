class Subsekvens {
    public final String subsekvens;
    private int antall;

    public Subsekvens(String subsekvens, int antall){
        this.subsekvens = subsekvens;
        this.antall = antall;
    }

    public int hentAntall(){
        return antall;
    }

    public int endreAntall(int nyVerdi){
        nyVerdi = antall;
        return nyVerdi;
    }
    
    public void leggTilAntall(int antall){
        this.antall += antall;
    }

    public String toString(){
        return "("+subsekvens + "," + antall + ")";

    }
    
} 