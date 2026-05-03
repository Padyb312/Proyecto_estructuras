package Servicios;

import modelo.Usuario;

import java.util.List;

public interface OperacionArchivo {
	public String serializar(List<Usuario> Trajes, String path, String name) throws Exception;

	public List<Usuario> deserializar(String path, String name) throws Exception;

}
