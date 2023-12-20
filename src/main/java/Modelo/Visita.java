package Modelo;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.*;

@Entity
public class Visita {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(name = "fecha")
	    private String fecha;
	    
	    @Column(name = "motivoConsulta")
	    private String motivoConsulta;

	    @Column(name = "diagnostico")
	    private String diagnostico;
	    
	    // Relación de muchos a uno con la entidad Mascota
	    @ManyToOne(fetch = FetchType.EAGER)
	    @JoinColumn(name = "mascota_id") // Nombre de la columna que hace referencia al dueño
	    private Mascota mascota;
	
	    
	    // Constructor vacío (necesario para JPA)
	    public Visita() {
	    }

	    // Constructor con fecha, motivo de consulta, diagnóstico y mascota
	    public Visita(String fecha, String motivoConsulta, String diagnostico, Mascota mascota) {
	        this.fecha = fecha;
	        this.motivoConsulta = motivoConsulta;
	        this.diagnostico = diagnostico;
	        this.mascota = mascota; // Establece la relación bidireccional
	        mascota.agregarVisita(this);
	    }


		 // Getters y setters
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getFecha() {
			return fecha;
		}

		public void setFecha(String fecha) {
			this.fecha = fecha;
		}

		public String getMotivoConsulta() {
			return motivoConsulta;
		}

		public void setMotivoConsulta(String motivoConsulta) {
			this.motivoConsulta = motivoConsulta;
		}

		public String getDiagnostico() {
			return diagnostico;
		}

		public void setDiagnostico(String diagnostico) {
			this.diagnostico = diagnostico;
		}
		

		public Mascota getMascota() {
			return mascota;
		}

		public void setMascota(Mascota mascota) {
			this.mascota = mascota;
		}

		@Override
		public String toString() {
			return "Visita [id=" + id + ", fecha=" + fecha + ", motivoConsulta=" + motivoConsulta + ", diagnostico="
					+ diagnostico + "]";
		}


}
