package Servicios;

import modelo.Evento;
import java.util.ArrayList;
import java.util.List;

public class ImplementacionOperacionCRUD implements OperacionCRUD {

    private List<Evento> eventos = new ArrayList<>();

    @Override
    public String crear(Evento objeto) {
        eventos.add(objeto);
        return "Evento Creado";
    }

    @Override
    public Evento mostrarUno(String id_Evento) {
        for (Evento e : eventos) {
            if (e != null && e.getId().equals(id_Evento)) {
                return e;
            }
        }
        return null;
    }

    @Override
    public List<Evento> mostrarTodos() {
        return eventos;
    }

    @Override
    public String modificar(String id_Evento, Evento objeto) {
        for (int i = 0; i < eventos.size(); i++) {
            if (eventos.get(i) != null && eventos.get(i).getId().equals(id_Evento)) {
                eventos.set(i, objeto);
                return "Evento modificado";
            }
        }
        return "Evento no encontrado";
    }

    @Override
    public Evento eliminar(String id_Evento) {
        for (int i = 0; i < eventos.size(); i++) {
            if (eventos.get(i) != null && eventos.get(i).getId().equals(id_Evento)) {
                return eventos.remove(i);
            }
        }
        return null;
    }
}