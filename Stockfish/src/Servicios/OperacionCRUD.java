package Servicios;

import modelo.*;
import java.util.List;

public interface OperacionCRUD {
	public String crear(Evento objeto);

	public Evento mostrarUno(String numero_traje);

	public List<Evento> mostrarTodos();

	public String modificar(String numero_traje, Evento objeto);
	
	public Evento eliminar(String numero_traje);
}
