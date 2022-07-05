package urjc.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String contraseña;
	private String nombre;
	private String foto;
	private String fecha;
	private Estado estado;
	private double saldo;
	
	
	public User(){}
	
	public User(long id, String contraseña, String nombre, String foto, String fecha, Estado estado, double saldo) {
		this.id = id;
		this.contraseña = contraseña;
		this.nombre = nombre;
		this.foto = foto;
		this.fecha = fecha;
		this.estado = estado;
		this.saldo = saldo;
	}

	public User(String contraseña, String nombre, String foto, String fecha, Estado estado, double saldo) {
		this.contraseña = contraseña;
		this.nombre = nombre;
		this.foto = foto;
		this.fecha=fecha;
		this.estado = estado;
		this.saldo = saldo;
	}

	public User(String contraseña, String nombre, String foto, Estado estado, double saldo) {
		this.contraseña = contraseña;
		this.nombre = nombre;
		this.foto = foto;
		this.estado = estado;
		this.saldo = saldo;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
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


	public Estado getEstado() {
		return estado;
	}


	public void setEstado(Estado estado) {
		this.estado = estado;
	}


	public double getSaldo() {
		return saldo;
	}


	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}


	@Override
	public String toString() {
		return "Usuario [id=" + id + ", contraseña=" + contraseña + ", nombre=" + nombre + ", foto=" + foto + ", fecha="
				+ fecha + ", estado=" + estado + ", saldo=" + saldo + "]";
	}	
}
