package com.utad.veterinaria.application;

import Modelo.Dueño;
import Modelo.Mascota;
import Modelo.Visita;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

public class ConsultaDAO {
	// Fábrica de EntityManager
	private EntityManagerFactory emf;

	public ConsultaDAO(EntityManagerFactory emf) {
		this.emf = emf;
	}

	// Método para encontrar todas las mascotas por su tipo específico
	@SuppressWarnings("exports")
	void encontrarMascotasPorTipo(String tipo) {
	    // Creamos una EntityManager
	    EntityManager em = emf.createEntityManager();
	    try {
	        // Consulta para encontrar mascotas por tipo
	        List<Mascota> mascotasTipo = em.createQuery("SELECT m FROM Mascota m WHERE m.tipo = :tipo", Mascota.class)
	                .setParameter("tipo", tipo) // Parámetro para filtrar por tipo
	                .getResultList();
	        
	        if (mascotasTipo.isEmpty()) {
	            System.out.println("No hay registros.");
	            return;
	        }

	        // Encabezados de la tabla
	        System.out.println(String.format("| %-5s | %-20s | %-10s | %-15s |", "ID", "Nombre", "Tipo", "Raza"));
	        System.out.println("-------------------------------------------------------");

	        // Imprimir los datos de las mascotas encontradas
	        for (Mascota m : mascotasTipo) {
	            System.out.println(String.format("| %-5d | %-20s | %-10s | %-15s |", m.getId(), m.getNombre(), m.getTipo(), m.getRaza()));
	        }
	    } finally {
	        em.close();
	    }
	}

	// Método para encontrar todos los dueños que tienen más de dos mascotas
	@SuppressWarnings("exports")
	void encontrarDueñosConMasDeDosMascotas() {
	    // Crear EntityManager
	    EntityManager em = emf.createEntityManager();
	    try {
	        // Consulta para encontrar dueños con más de dos mascotas
	        List<Dueño> dueñosDosMascotas = em.createQuery("SELECT d FROM Dueño d WHERE SIZE(d.mascotas) > 2", Dueño.class)
	                .getResultList();

	        if (dueñosDosMascotas.isEmpty()) {
	            System.out.println("No hay registros.");
	            return;
	        }

	        // Encabezados de la tabla
	        System.out.printf("%-5s %-20s %-10s %-15s%n", "ID", "Nombre", "Dirección", "Teléfono");
	        System.out.println("-------------------------------------------------------");

	        for (Dueño d : dueñosDosMascotas) {
	            // Imprimir detalles del dueño
	            System.out.printf("%-5d %-20s %-10s %-15s%n", d.getId(), d.getNombre(), d.getDireccion(), d.getTelefono());
	            System.out.println("Mascotas:");

	            // Imprimir detalles de las mascotas del dueño
	            for (Mascota mascota : d.getMascotas()) {
	                System.out.printf("   - %-20s %-10s %-15s%n", mascota.getNombre(), mascota.getTipo(), mascota.getRaza());
	            }
	        }
	    } finally {
	        em.close();
	    }
	}


	// Método para encontrar todas las visitas de una mascota específica por su
	// nombre
	@SuppressWarnings("exports")
	void encontrarVisitasDeMascotaPorNombre(String nombreMascota) {
	    // Crear EntityManager
	    EntityManager em = emf.createEntityManager();
	    try {
	        // Consulta para encontrar visitas de una mascota por su nombre
	        List<Visita> mascotaVisitas = em.createQuery("SELECT v FROM Visita v WHERE v.mascota.nombre = :nombreMascota", Visita.class)
	                .setParameter("nombreMascota", nombreMascota)
	                .getResultList();

	        if (mascotaVisitas.isEmpty()) {
	            System.out.println("No hay registros.");
	            return;
	        }

	        // Encabezados de la tabla
	        System.out.println(String.format("| %-5s | %-20s | %-15s | %-20s |", "ID", "Fecha", "Motivo consulta", "Diagnóstico"));
	        System.out.println("---------------------------------------------------------------");

	        // Mostrar las visitas de la mascota en forma de tabla
	        for (Visita v : mascotaVisitas) {
	            System.out.println(String.format("| %-5d | %-20s | %-15s | %-20s |", v.getId(), v.getFecha(), v.getMotivoConsulta(), v.getDiagnostico()));
	        }
	    } finally {
	        em.close();
	    }
	}


	// Método para encontrar todas las visitas que fueron por "Vacunación"
	void encontrarVisitasPorVacunacion() {
	    // Crear EntityManager
	    EntityManager em = emf.createEntityManager();
	    try {
	        // Consultar las visitas por motivo de consulta "Vacunación"
	        List<Visita> visitasVacunacion = em.createQuery("SELECT v FROM Visita v WHERE v.motivoConsulta = :motivoConsulta", Visita.class)
	                .setParameter("motivoConsulta", "Vacunación")
	                .getResultList();

	        if (visitasVacunacion.isEmpty()) {
	            System.out.println("No hay registros.");
	            return;
	        }
	        
	        // Encabezados de la tabla
	        System.out.println(String.format("| %-5s | %-20s | %-20s | %-20s | %-20s |", "ID", "Mascota", "Fecha", "Motivo consulta", "Diagnóstico"));
	        System.out.println("------------------------------------------------------------------------------------------------");

	        // Mostrar las visitas de vacunación en forma de tabla
	        for (Visita v : visitasVacunacion) {
	            System.out.println(String.format("| %-5d | %-20s | %-15s | %-20s | %-20s |", v.getId(), v.getMascota().getNombre(), v.getFecha(), v.getMotivoConsulta(), v.getDiagnostico()));
	        }
	    } finally {
	        em.close();
	    }
	}


}
