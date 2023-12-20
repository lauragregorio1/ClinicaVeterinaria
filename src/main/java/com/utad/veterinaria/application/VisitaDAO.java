package com.utad.veterinaria.application;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import Modelo.Dueño;
import Modelo.Visita;
import jakarta.persistence.Query;

public class VisitaDAO {
	// Fábrica de EntityManager
	private EntityManagerFactory emf;

    public VisitaDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    // Método para crear una visita
    public Visita crearVisita(Visita visita) {
        // Crear EntityManager
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(visita); // Se persiste la nueva visita en la base de datos
            em.getTransaction().commit(); // Se confirma la transacción
            return visita; // Se devuelve la visita creada
        } finally {
            em.close(); // Se cierra el EntityManager
        }
    }


    // Método para listar a las visitas
    void listVisitas(EntityManager em) {
        // Se obtienen todas las visitas de la base de datos
        List<Visita> visitas = em.createQuery("SELECT v FROM Visita v", Visita.class).getResultList();
        // Si no se encuentran resultados, se avisa por consola
        if (visitas.isEmpty()) {
            System.out.println("No hay visitas registradas.");
            return;
        }

        // Encabezados de la tabla
        System.out.println(String.format("| %-5s | %-20s | %-15s | %-20s | %-20s |", "ID", "Fecha", "Motivo consulta", "Diagnóstico", "Mascota"));
        System.out.println("-----------------------------------------------------------------------------------------");

        // Se recorren todas las visitas
        for (Visita v : visitas) {
            // Se imprime la información de cada visita en formato de tabla
            System.out.println(String.format("| %-5d | %-20s | %-15s | %-20s | %-20s |", v.getId(), v.getFecha(), v.getMotivoConsulta(), v.getDiagnostico(), v.getMascota().getNombre()));
        }
    }


    // Método para actulizar una visita
    public Visita actualizarVisita(String nombreMascota, String motivoConsulta, String nuevaFecha, String nuevoMotivo, String nuevoDiagnostico) {
        // Crear un EntityManager 
        EntityManager em = emf.createEntityManager();
        try {
            // Iniciar una transacción para las operaciones en la base de datos
            em.getTransaction().begin();

            // Buscar la visita por el nombre de la mascota y el motivo de consulta
            javax.persistence.Query query = em.createQuery("SELECT v FROM Visita v JOIN v.mascota m WHERE m.nombre = :nombreMascota AND v.motivoConsulta = :motivoConsulta");
            query.setParameter("nombreMascota", nombreMascota);
            query.setParameter("motivoConsulta", motivoConsulta);

            // Obtener el resultado de la consulta
            List<Visita> visitas = query.getResultList();

            // Verificar si se encontraron visitas para la mascota con el motivo de consulta especificado
            if (visitas.isEmpty()) {
                System.out.println("No se encontraron visitas para la mascota con el motivo de consulta especificado.");
                return null; // Devolver null si no se encuentran visitas
            }

            // Tomar la primera visita encontrada (se puede ajustar la lógica para manejar múltiples visitas)
            Visita visita = visitas.get(0);

            // Actualizar los atributos de la visita con los nuevos valores proporcionados
            visita.setFecha(nuevaFecha);
            visita.setMotivoConsulta(nuevoMotivo);
            visita.setDiagnostico(nuevoDiagnostico);

            // Fusionar la visita actualizada con la base de datos para reflejar los cambios
            Visita visitaActualizada = em.merge(visita);

            // Confirmar la transacción para aplicar los cambios permanentemente
            em.getTransaction().commit();

            // Devolver la visita actualizada
            return visitaActualizada;
        } finally {
            // Cerrar el EntityManager para liberar recursos
            em.close();
        }
    }


    // Método para eliminar una visita
    public void eliminarVisita(String nombreMascota, String motivoConsulta) {
        // Crear un EntityManager 
        EntityManager em = emf.createEntityManager();
        try {
            // Iniciar una transacción para las operaciones en la base de datos
            em.getTransaction().begin();
            
            // Crear una consulta para eliminar las visitas asociadas a una mascota con un motivo de consulta específico
            javax.persistence.Query query = em.createQuery(
                "DELETE FROM Visita v WHERE v.mascota IN " +
                "(SELECT m FROM Mascota m WHERE m.nombre = :nombreMascota) AND v.motivoConsulta = :motivoConsulta"
            );

            // Establecer los parámetros de la consulta
            query.setParameter("nombreMascota", nombreMascota);
            query.setParameter("motivoConsulta", motivoConsulta);

            // Ejecutar la consulta y obtener la cantidad de visitas eliminadas
            int deletedCount = query.executeUpdate();

            // Confirmar la transacción para aplicar los cambios permanentemente
            em.getTransaction().commit();

            // Verificar si se eliminaron visitas y mostrar un mensaje apropiado
            if (deletedCount > 0) {
                System.out.println("Visitas eliminadas correctamente");
            } else {
                System.out.println("No se encontraron visitas para la mascota con el motivo de consulta especificado");
            }
        } finally {
            // Cerrar el EntityManager para liberar recursos
            em.close();
        }
    }




}
