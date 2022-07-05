package urjc.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class Estacion {


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private int numSerie;
	private String coordenadas;
	private int capacidad;
	private String fecha;
	private Estado estado;
	private int espacios;
	
	@OneToMany
	private List<Bicicleta> bicicletas = new ArrayList<Bicicleta>();

	public Estacion() {}

	public Estacion(int numSerie, String coordenadas, int capacidad) {
		this.numSerie = numSerie;
		this.coordenadas = coordenadas;
		this.capacidad = capacidad;
		this.espacios = capacidad;
	}
	
	public Estacion(int numSerie, String coordenadas, int capacidad, String fecha, Estado estado) {
		this.numSerie = numSerie;
		this.coordenadas = coordenadas;
		this.capacidad = capacidad;
		this.fecha = fecha;
		this.estado = estado;
		this.espacios = capacidad;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getNumSerie() {
		return numSerie;
	}

	public void setNumSerie(int numSerie) {
		this.numSerie = numSerie;
	}

	public String getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(String coordenadas) {
		this.coordenadas = coordenadas;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public int getEspacios() {
		return espacios;
	}

	public void setEspacios(int espacios) {
		this.espacios = espacios;
	}
	
	public List<Bicicleta> getBicicletas() {
		return bicicletas;
	}

	public void setBicicletas(List<Bicicleta> bicicletas) {
		this.bicicletas = bicicletas;
	}
	
	public boolean retirarBicicleta(Bicicleta bici) {
		this.espacios ++;
		return this.bicicletas.remove(bici);
	}
	
	@Override
	public String toString() {
		return "Estacion [id=" + id + ", numSerie=" + numSerie + ", coordenadas=" + coordenadas + ", capacidad="
				+ capacidad + ", fecha=" + fecha + ", estado=" + estado + ", espacios=" + espacios +"]";
	}

	
}
