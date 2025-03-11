package Service;

import Domain.Piesa;
import Repo.Repository;
import Repo.SQLRepository;

import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PiesaService {
    private SQLRepository repo;

    public PiesaService(SQLRepository repo) {
        this.repo = repo;
    }

    public List<Piesa> getAll() {
        ArrayList<Piesa> piesaList = repo.getLista();
        List<Piesa> listaSort = piesaList.stream().sorted(Comparator.comparing(Piesa::getTitlu))
                .sorted(Comparator.comparing(Piesa::getFormatie)).collect(Collectors.toList());
        return listaSort;
    }

    public void add(int id, String formatie, String titlu, String gen, String durata){
        Piesa p = new Piesa(id, formatie, titlu, gen, durata);
        repo.add(p);
    }

    public void removeById(int id){
        repo.removeByID(id);
    }

    public void update(int id, String formatie, String titlu, String gen, String durata){
        Piesa p = new Piesa(id, formatie, titlu, gen, durata);
        repo.update(p);
    }

    public ArrayList<Piesa> search(String secventa){
        ArrayList<Piesa> List = repo.getLista();
        ArrayList<Piesa> outList = new ArrayList<>();
        for(Piesa p : List){
            if(p.toSpacedString().toLowerCase().contains(secventa.toLowerCase())){
                outList.add(p);
            }
        }
        return outList;
    }

    public void create(String nume) throws Exception{

        int durataLista = 0;
        ArrayList<Piesa> OutList = new ArrayList<>();
        ArrayList<Piesa> piesaList = repo.getLista();
        Collections.shuffle(piesaList);
        for(Piesa p : piesaList){
            if(durataLista >= 900){
                break;
            }
            if(OutList.isEmpty()){
                OutList.add(p);
                durataLista += p.getDurataSecunde();
            } else if (!p.getFormatie().equals(OutList.getLast().getFormatie()) && !p.getGen().equals(OutList.getLast().getGen())) {
                OutList.add(p);
                durataLista += p.getDurataSecunde();
            }
        }
        if(durataLista < 900){
            throw new Exception("Lista nu a putut fi creata");
        }
        else{
            repo.createList(nume);
            for(Piesa p : OutList){
                repo.addToList(p, nume);
            }
        }
    }

}
