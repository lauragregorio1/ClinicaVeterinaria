package com.utad.veterinaria.application;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import Modelo.Dueño;
import Modelo.Mascota;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

public class DueñoDAO {
	// Fábrica de EntityManager
	private EntityManagerFactory emf;

    public DueñoDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    // Método para encontrar a un Dueño
    public Dueño encontrarDueñoPorNombre(String nombre) {
        // Crear EntityManager
        EntityManager em = emf.createEntityManager();
        try {
            // Consultar el dueño por su nombre
            javax.persistence.TypedQuery<Dueño> query = em.createQuery(
                    "SELECT d FROM Dueño d WHERE d.nombre = :nombre", Dueño.class)
                    .setParameter("nombre", nombre);
            
            // Obtener y devolver el resultado de la consulta
            return query.getSingleResult();
        } catch (Exception e) {
            return null; // Si no se encuentra el dueño, devuelve null
        } finally {
            em.close(); // Cerrar el EntityManager
        }
    }

    // Método para crear un duaño
    public Dueño crearDueño(Dueño dueño) {
        // Crear EntityManager
        EntityManager em = emf.createEntityManager();
        try {
            // Iniciar la transacción
            em.getTransaction().begin();

            // Persistir el objeto dueño en la base de datos
            em.persist(dueño);

            // Commit de la transacción
            em.getTransaction().commit();

            // Devolver el dueño creado
            return dueño;
        } finally {
            em.close(); // Cerrar el EntityManager
        }
    }

    // Método para listar a los dueños
    void listDueños(EntityManager em) {
        // Obtener la lista de dueños
        List<Dueño> dueños = em.createQuery("SELECT d FROM Dueño d", Dueño.class).getResultList();
        
        // Verificar si la lista está vacía
        if (dueños.isEmpty()) {
            System.out.println("No hay dueños registrados.");
            return;
        }

        // Encabezados de la tabla
        System.out.println(String.format("| %-5s | %-20s | %-15s | %-10s | %-15s |", "ID", "Nombre", "Dirección", "Teléfono", "Mascotas"));
        System.out.println("------------------------------------------------------------------------------------------");

        // Iterar sobre cada dueño en la lista
        for (Dueño d : dueños) {
            StringBuilder mascotasDueño = new StringBuilder();

            // Iterar sobre las mascotas del dueño actual
            for (Mascota m : d.getMascotas()) {
                mascotasDueño.append(m.getNombre()).append(", ");
            }

            // Imprimir información del dueño y sus mascotas
            System.out.println(String.format("| %-5d | %-20s | %-15s | %-10s | %-15s |", d.getId(), d.getNombre(), d.getDireccion(), d.getTelefono(), mascotasDueño));
        }
    }

    // Método para actualizar un dueño
    public Dueño actualizarDueño(Long id, String nuevoNombre, String nuevaDireccion, int nuevoTelefono) {
        // Crear EntityManager
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            // Buscar el dueño por su ID
            Dueño dueño = em.find(Dueño.class, id);
            
            // Verificar si el dueño existe
            if (dueño != null) {
                // Actualizar los atributos del dueño
                dueño.setNombre(nuevoNombre);
                dueño.setDireccion(nuevaDireccion);
                dueño.setTelefono(nuevoTelefono);
                
                // Confirmar los cambios en la transacción
                em.getTransaction().commit();
                
                return dueño;
            }
            return null; // O lanza una excepción si lo prefieres
        } finally {
            em.close(); // Cerrar el EntityManager
        }
    }



    // Método para eliminar un dueño
    public void eliminarDueño(String nombreDueño) {
        // Crear EntityManager
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            // Buscar el dueño por su nombre
            TypedQuery<Dueño> query = em.createQuery("SELECT d FROM Dueño d WHERE d.nombre = :nombreDueño", Dueño.class);
            query.setParameter("nombreDueño", nombreDueño);
            Dueño dueño = null;
            
            try {
                // Intentar obtener un solo resultado
                dueño = query.getSingleResult();
            } catch (Exception e) {
                // Manejar la excepción si no se encuentra el dueño
                System.out.println("Dueño no encontrado");
            }

            if (dueño != null) {
                // Eliminar el dueño si se encontró
                em.remove(dueño);
                em.getTransaction().commit();
                System.out.println("Dueño eliminado correctamente");
            } 
        } finally {
            em.close(); // Cerrar el EntityManager
        }
    }

}
