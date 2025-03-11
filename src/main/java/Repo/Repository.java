package Repo;
import java.util.ArrayList;
import java.util.Arrays;

import Domain.*;

public class Repository<T extends Piesa> {
    ArrayList<T> lista = new ArrayList<>();

    public ArrayList<T> getLista() {
        return lista;
    }

    public void add(T element){
        lista.add(element);
    }

    public void remove(T element){
        lista.remove(element);
    }

    public void update(T element){
        lista.set(lista.indexOf(getByID(element.getId())), element);
    }

    public boolean findByID(int id){
        for(T element: lista){
            if(element.getId() == id){
                return true;
            }
        }
        return false;
    }

    public T getByID(int id){
        for(T element: lista){
            if(element.getId() == id){
                return element;
            }
        }
        return null;
    }

    public void removeByID(int id){
        if(findByID(id)){
            lista.remove(getByID(id));
        }
    }

    public int size(){
        return lista.size();
    }


}
