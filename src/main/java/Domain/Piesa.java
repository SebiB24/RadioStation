package Domain;

public class Piesa {
    private int id;
    private String formatie;
    private String titlu;
    private String gen;
    private String durata;

    public Piesa(final int id, final String formatie, final String titlu, final String gen, final String durata) {
        this.id = id;
        this.formatie = formatie;
        this.titlu = titlu;
        this.gen = gen;
        this.durata = durata;
    }

    public int getId() {
        return id;
    }
    public void setId(final int id) {
        this.id = id;
    }

    public String getFormatie() {
        return formatie;
    }
    public void setFormatie(final String formatie) {
        this.formatie = formatie;
    }

    public String getTitlu() {
        return titlu;
    }
    public void setTitlu(final String titlu) {
        this.titlu = titlu;
    }

    public String getGen() {
        return gen;
    }
    public void setGen(final String gen) {
        this.gen = gen;
    }

    public String getDurata() {
        return durata;
    }
    public void setDurata(final String durata) {
        this.durata = durata;
    }

    @Override
    public String toString() {
        return "ID: " + id + " ; " + "Formatie: " + formatie + " ; " + "Titlu: " + titlu + " ; " + "Gen: " + gen + " ; " + "Durata: " + durata;
    }

    public String toSpacedString(){
        return id + " " + formatie + " " + titlu + " " + gen + " " + durata;
    }

    public int getDurataSecunde(){
        int minute = Integer.parseInt(durata.split(":")[0]);
        int second = Integer.parseInt(durata.split(":")[1]);
        return minute * 60 + second;
    }


}
