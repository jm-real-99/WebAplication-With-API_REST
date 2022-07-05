package urjc.models;


public class UserResponse {
    
    private String id;
	private String contraseña;
	private String nombre;
	private String foto;
	private String fecha;
	private String estado;
	private String saldo;
	
	
	public UserResponse(String id, String contraseña, String nombre, String foto, String fecha, String estado, String saldo) {
		this.id = id;
		this.contraseña = contraseña;
		this.nombre = nombre;
		this.foto = foto;
		this.fecha = fecha;
		this.estado = estado;
		this.saldo = saldo;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getContraseña() {
		return contraseña;
	}


	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getFoto() {
		return foto;
	}


	public void setFoto(String foto) {
		this.foto = foto;
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


	public String getSaldo() {
		return saldo;
	}


	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}


	@Override
	public String toString() {
		return "Usuario [id=" + id + ", contraseña=" + contraseña + ", nombre=" + nombre + ", foto=" + foto + ", fecha="
				+ fecha + ", estado=" + estado + ", saldo=" + saldo + "]";
	}	
}
