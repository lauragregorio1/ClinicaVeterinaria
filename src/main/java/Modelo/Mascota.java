package Modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Mascota {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "tipo")
	private String tipo;

	@Column(name = "raza")
	private String raza;


	// Relación de muhos a uno con la entidad Dueño
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "dueño_id") // Nombre de la columna que hace referencia al dueño
	private Dueño dueño;

	// Relación de uno a muchos con la entidad Visita
	@OneToMany(mappedBy = "mascota", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@Column(name = "listaVisitas")
	private List<Visita> listaVisitas = new ArrayList();

	// Constructor vacío (necesario para JPA)
	public Mascota() {
	}

	// Constructor con nombre, tipo, raza y dueño
	public Mascota(String nombre, String tipo, String raza, Dueño dueño) {
		this.nombre = nombre;
		this.tipo = tipo;
		this.raza = raza;
		this.dueño = dueño;
		dueño.agregarMascota(this);
	}

	// Método para agregar una visita
	public void agregarVisita(Visita visita) {
		listaVisitas.add(visita);
		visita.setMascota(this);
	}

	// Método para eliminar una visita
	public void eliminarVisita(Visita visita) {
		listaVisitas.remove(visita);
		visita.setMascota(null);
	}

	// Getters y setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getRaza() {
		return raza;
	}

	public void setRaza(String raza) {
		this.raza = raza;
	}

	public List<Visita> getListaVisitas() {
		return listaVisitas;
	}

	public void setListaVisitas(List<Visita> listaVisitas) {
		this.listaVisitas = listaVisitas;
	}

	public Dueño getDueño() {
		return dueño;
	}

	public void setDueño(Dueño dueño) {
		this.dueño = dueño;
	}

	// Método toString
	@Override
	public String toString() {
		return "Mascota [id=" + id + ", nombre=" + nombre + ", tipo=" + tipo + ", raza=" + raza + ", listaVisitas="
				+ listaVisitas + "]";
	}

}
