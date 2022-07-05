package urjc.models;

public class UserDTO {

	private String contraseña;
	private String nombre;
	private String foto;
	private Estado estado;
	private String saldo;
	
	
	public UserDTO(String contraseña, String nombre, String foto, Estado estado, String saldo) {
		this.contraseña = contraseña;
		this.nombre = nombre;
		this.foto = foto;
		this.estado = estado;
		this.saldo = saldo;
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

	public Estado getEstado() {
		return estado;
	}


	public void setEstado(Estado estado) {
		this.estado = estado;
	}


	public String getSaldo() {
		return saldo;
	}


	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}

}
