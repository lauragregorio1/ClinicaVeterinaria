package Modelo;
import java.util.*;
import javax.persistence.*;

@Entity
public class Dueño {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(name = "nombre")
	    private String nombre;
	    
	    @Column(name = "direccion")
	    private String direccion;

	    @Column(name = "telefono")
	    private int telefono;

	    
	    @OneToMany(mappedBy = "dueño", cascade = CascadeType.ALL, orphanRemoval = true,  fetch = FetchType.EAGER)
	    @Column(name = "listaMascotas")
	    private List<Mascota> mascotas = new ArrayList();
	    
	 
	    
	    // Constructor vacío (necesario para JPA)
	    public Dueño() {
	    }

	    // Constructor con nombre, direccion y telefono
	    public Dueño(String nombre, String direccion, int telefono) {
	        this.nombre = nombre;
	        this.direccion = direccion;
	        this.telefono = telefono;
	    }
	    
	    // Método par agregar una mascota
	    public void agregarMascota(Mascota mascota) {
	        mascotas.add(mascota);
	        mascota.setDueño(this);
	    }

	    // Método para eliminar una mascota
	    public void eliminarMascota(Mascota mascota) {
	        mascotas.remove(mascota);
	        mascota.setDueño(null);
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
	    

	    public String getDireccion() {
			return direccion;
		}

		public void setDireccion(String direccion) {
			this.direccion = direccion;
		}

		public int getTelefono() {
			return telefono;
		}

		public void setTelefono(int telefono) {
			this.telefono = telefono;
		}

		public List<Mascota> getMascotas() {
			return mascotas;
		}

		public void Mascotas(List<Mascota> mascotas) {
			this.mascotas = mascotas;
		}

		// Método toString
		@Override
		public String toString() {
			return "Dueño [id=" + id + ", nombre=" + nombre + ", direccion=" + direccion + ", telefono=" + telefono
					+ ", listaMascotas=" + mascotas + "]";
		}


}
