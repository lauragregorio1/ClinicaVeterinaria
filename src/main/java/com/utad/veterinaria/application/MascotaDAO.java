package com.utad.veterinaria.application;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import Modelo.Dueño;
import Modelo.Mascota;
import Modelo.Visita;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

public class MascotaDAO {
	// Fábrica de EntityManager
	private EntityManagerFactory emf;

    public MascotaDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    // Método para encontrar una mascota
    public Mascota encontrarMascotaPorNombre(String nombre) {
        // Crear EntityManager
        EntityManager em = emf.createEntityManager();
        try {
            // Crear una consulta para buscar la mascota por su nombre
            javax.persistence.TypedQuery<Mascota> query = em.createQuery(
                "SELECT m FROM Mascota m WHERE m.nombre = :nombre", Mascota.class)
                .setParameter("nombre", nombre);
            
            // Intentar obtener la mascota por su nombre
            return query.getSingleResult();
        } catch (Exception e) {
            // Manejar la excepción si no se encuentra la mascota
            return null;
        } finally {
            em.close(); // Cerrar el EntityManager
        }
    }

    // Método para crear una mascota
    public Mascota crearMascota(Mascota mascota) {
        // Crear EntityManager
        EntityManager em = emf.createEntityManager();
        try {
            // Iniciar la transacción para crear una nueva mascota
            em.getTransaction().begin();
            
            // Persistir la nueva mascota en la base de datos
            em.persist(mascota);
            
            // Confirmar la transacción
            em.getTransaction().commit();
            
            // Devolver la mascota creada
            return mascota;
        } catch (NoResultException e) {
            System.out.println("Dueño no encontrado");
        
        } finally {
            // Cerrar el EntityManager
            em.close();
        }
		return mascota;
    }


    // Método para listar a las mascotas
    void listMascotas(EntityManager em) {
        // Obtener todas las mascotas de la base de datos
        List<Mascota> mascotas = em.createQuery("SELECT m FROM Mascota m", Mascota.class).getResultList();

        // Verificar si no hay mascotas registradas
        if (mascotas.isEmpty()) {
            System.out.println("No hay mascotas registradas.");
            return;
        }

        // Encabezados de la tabla
        System.out.println(String.format("| %-5s | %-20s | %-10s | %-15s | %-20s | %-15s |", "ID", "Nombre", "Tipo", "Raza", "Dueño", "Visitas"));
        System.out.println("------------------------------------------------------------------------------------------------");

        // Iterar sobre cada mascota
        for (Mascota m : mascotas) {
            // Obtener la lista de visitas de la mascota y contar la cantidad de visitas
            List<Visita> visitas = m.getListaVisitas();
            int cantidadVisitas = visitas.size();

            // Mostrar la información de la mascota en formato de tabla
            System.out.println(String.format("| %-5d | %-20s | %-10s | %-15s | %-20s | %-15s |", m.getId(), m.getNombre(), m.getTipo(), m.getRaza(), m.getDueño().getNombre(), cantidadVisitas));
        }
    }

    
    // Método para actualizar a una mascota
    public Mascota actualizarMascota(Long id, String nuevoNombre, String nuevoTipo, String nuevaRaza) {
        // Crear EntityManager
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            // Buscar la mascota por su ID
            Mascota mascota = em.find(Mascota.class, id);

            // Verificar si la mascota existe
            if (mascota != null) {
                // Actualizar los atributos de la mascota con los nuevos valores
                mascota.setNombre(nuevoNombre);
                mascota.setTipo(nuevoTipo);
                mascota.setRaza(nuevaRaza);

                // Confirmar la transacción y refrescar la entidad
                em.getTransaction().commit();
                em.refresh(mascota);

                // Devolver la mascota actualizada
                return mascota;
            }
            return null; // Si no se encuentra la mascota, devuelve null
        } finally {
            em.close();  // Cerrar el EntityManager
        }
    }



    // Método para eliminar a una mascota
    public void eliminarMascota(String nombreMascota) {
        // Crear EntityManager
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            // Buscar la mascota por su nombre
            javax.persistence.TypedQuery<Mascota> query = em.createQuery("SELECT m FROM Mascota m WHERE m.nombre = :nombreMascota", Mascota.class);
            query.setParameter("nombreMascota", nombreMascota);

            try {
                Mascota mascota = query.getSingleResult();

                // Si se encuentra la mascota
                if (mascota != null) {
                    // Desvincular la mascota del dueño
                    mascota.getDueño().getMascotas().remove(mascota);
                    mascota.setDueño(null);

                    // Eliminar la mascota
                    em.remove(mascota);
                    em.getTransaction().commit();
                    System.out.println("Mascota eliminada correctamente");
                } else {
                    System.out.println("Mascota no encontrada");
                }
            } catch (Exception e) {
                System.out.println("Mascota no encontrada");
            }
        } finally {
            em.close(); // Cerrar el EntityManager
        }
    }



}
