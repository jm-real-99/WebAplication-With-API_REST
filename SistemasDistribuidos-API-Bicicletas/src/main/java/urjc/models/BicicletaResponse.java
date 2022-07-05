package urjc.models;

//Esta clase sirve porque es lo que vamos a devolver en el JSON, al tener todos los parámetros en String es más fácil.
//Además no siempre interesa devolver todos los datos. Se controla en esta clase
public class BicicletaResponse {
	
	private long id;
	private String numSerie;
	private String modelo;
	private String fecha;
	private String estado;
	private String estacion;
	
	public BicicletaResponse() {}
	
	public BicicletaResponse(String numSerie, String modelo, String fecha, EstadoBici estado) {
		this.numSerie = numSerie;
		this.modelo = modelo;
		this.fecha = fecha;
		this.estado = estado.toString();
	}
	
	public BicicletaResponse(Bicicleta bici) {
		this.id = bici.getId();
		this.numSerie = bici.getNumSerie();
		this.modelo = bici.getModelo();
		this.fecha=bici.getFecha();
		this.estado = bici.getEstado().toString();
		this.estacion = bici.getEstacion().toString();
	}

	public BicicletaResponse(String numSerie, String modelo) {
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Bicicleta [id=" + id + ", numSerie=" + numSerie + ", modelo=" + modelo + ", fecha=" + fecha
				+ ", estado=" + estado +"]";
	}

	public String getEstacion() {
		return estacion;
	}

	public void setEstacion(String estacion) {
		this.estacion = estacion;
	}
}
