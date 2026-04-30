package Servicios;

import java.time.LocalDate;
import java.util.List;

import modelo.Evento;

public interface OperacionFecha {
	public List<Evento> mostrarPorFecha(LocalDate fecha);
}
