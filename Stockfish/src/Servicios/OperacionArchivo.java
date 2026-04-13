package Servicios;

import modelo.Evento;
import java.util.List;

public interface OperacionArchivo {
	public String serializar(List<Evento> Trajes, String path, String name);

	public List<Evento> deserializar(String path, String name);

}
