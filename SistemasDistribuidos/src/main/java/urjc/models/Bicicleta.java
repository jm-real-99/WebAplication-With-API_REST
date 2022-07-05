package urjc.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Bicicleta {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String numSerie;
	private String modelo;
	private String fecha;
	private EstadoBici estado;
	
	@Column
    @ElementCollection(targetClass=EstadoBici.class)
	private List<EstadoBici> estados= new ArrayList<>();
	
	public Bicicleta() {}

	public Bicicleta(String numSerie, String modelo, String fecha, EstadoBici estado, List<EstadoBici> estados) {
		this.numSerie = numSerie;
		this.modelo = modelo;
		this.fecha = fecha;
		this.estado = estado;
		this.estados=estados;
	}
	
	public Bicicleta(String numSerie, String modelo, String fecha, EstadoBici estado) {
		this.numSerie = numSerie;
		this.modelo = modelo;
		this.fecha = fecha;
		this.estado = estado;
	}

	public Bicicleta(String numSerie, String modelo) {
		this.numSerie = numSerie;
		this.modelo = modelo;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNumSerie() {
		return numSerie;
	}

	public void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public EstadoBici getEstado() {
		return estado;
	}

	public void setEstado(EstadoBici estado) {
		this.estado = estado;
	}

	
	
	public List<EstadoBici> getEstados() {
		return estados;
	}

	public void setEstados(List<EstadoBici> estados) {
		this.estados = estados;
	}

	@Override
	public String toString() {
		return "Bicicleta [id=" + id + ", numSerie=" + numSerie + ", modelo=" + modelo + ", fecha=" + fecha
				+ ", estado=" + estado + ", estacion=" + estados + "]";
	}

}
