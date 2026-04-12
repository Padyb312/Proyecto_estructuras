package Servicios;

import modelo.Evento;
import java.util.ArrayList;
import java.util.List;
//ParaGuardar
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ImplementacionOperacionCRUD implements OperacionCRUD , OperacionArchivo{

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
    @Override
    public String serializar(List<Evento> trajes, String path, String name) {
        try {
            FileOutputStream fos = new FileOutputStream(path + name);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(trajes);
            oos.close();
            fos.close();
            return "File created!!";
        } catch (IOException ioe) {
            return "Error file " + ioe.getMessage();
        }
    }

    @Override
    public List<Evento> deserializar(String path, String name) {
        List<Evento> lista = null;
        try {
            FileInputStream fis = new FileInputStream(path + name);
            ObjectInputStream ois = new ObjectInputStream(fis);
            lista = (List<Evento>) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        } catch (ClassNotFoundException c) {
            System.err.println(c.getMessage());
        }
        return lista;
    } 
    
   
}