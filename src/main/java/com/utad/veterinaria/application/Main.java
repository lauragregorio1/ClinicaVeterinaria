package com.utad.veterinaria.application;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import javax.persistence.*;

import Modelo.Dueño;
import Modelo.Mascota;
import Modelo.Visita;

public class Main {
	public static void main(String[] args) {
		// Crear un EntityManagerFactory basado en la unidad de persistencia
		// "miUnidadDePersistencia"
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadDePersistencia");

		// Crear un EntityManager a partir del EntityManagerFactory para interactuar con
		// la base de datos
		EntityManager em = emf.createEntityManager();

		// Crear DAOs para cada entidad (Consulta, Dueño, Mascota, Visita) que
		// interactúan con la base de datos
		ConsultaDAO consultaDao = new ConsultaDAO(emf);
		DueñoDAO dueñoDao = new DueñoDAO(emf);
		MascotaDAO mascotaDao = new MascotaDAO(emf);
		VisitaDAO visitaDao = new VisitaDAO(emf);

		Scanner scanner = new Scanner(System.in);
		int opcion;

		// Este es el menú principal por el cual el usuario podrá elegir que acción
		// quiere realizar
		do {
			System.out.println("Bienvenido al menú de nuestra clínica veterinaria:");
			System.out.println("+--------------------------------+");
			System.out.println("|  Opción  |       Descripción    |");
			System.out.println("+--------------------------------+");
			System.out.println("|     1    |   Hacer consultas    |");
			System.out.println("|     2    |   Gestionar Mascotas |");
			System.out.println("|     3    |   Gestionar Dueños   |");
			System.out.println("|     4    |   Gestionar Visitas  |");
			System.out.println("|     0    |         Salir        |");
			System.out.println("+--------------------------------+");
			System.out.print("Seleccione una opción: ");

			opcion = scanner.nextInt();
			scanner.nextLine(); // Consumir el salto de línea
			// Según la opción que elija se ejecutará una acción u otra
			switch (opcion) {
			case 1:
				// En la opción 1 se presenta la posibilidad de hacer estas 4 consultas
				int subOpcion;
				do {
					System.out.println("Consultas disponibles:");
					System.out.println("1. Consulta todas las mascotas de un tipo específico");
					System.out.println("2. Consulta todos los dueños que tienen más de 2 mascotas");
					System.out.println("3. Consulta todas las visitas de una mascota por su nombre");
					System.out.println("4. Consulta todas las visitas que fueron por 'Vacunación'");
					System.out.println("0. Volver al menú anterior");
					System.out.print("Seleccione una consulta: ");

					subOpcion = scanner.nextInt();
					scanner.nextLine(); // Consumir el salto de línea
					// Submenú para elegir la consulta
					switch (subOpcion) {
					case 1:
						// La primera consulta lista las mascotas que tengan el tipo que el usuario
						// introduce
						System.out.println("Consulta seleccionada: Consulta todas las mascotas de un tipo específico");
						System.out.println("Introduce el tipo:");
						String tipo = scanner.nextLine();
						consultaDao.encontrarMascotasPorTipo(tipo);
						break;
					case 2:
						// La segunda consulta lista los dueños que tienen más de dos mascotas
						System.out.println(
								"Consulta seleccionada: Consulta todos los dueños que tienen más de 2 mascotas");
						consultaDao.encontrarDueñosConMasDeDosMascotas();
						break;
					case 3:
						// La tercera consulta lista las visitas de una mascota cuyo nombre el usuario
						// introduce
						System.out.println(
								"Consulta seleccionada: Consulta todas las visitas de una mascota por su nombre");
						System.out.println("Introduce el nombre de la mascota:");
						String nombre = scanner.nextLine();
						consultaDao.encontrarVisitasDeMascotaPorNombre(nombre);
						break;
					case 4:
                    	// La cuarta consulta lista las visitas cuyo motivo de consulta fue 'Vacunación'
						System.out.println(
								"Consulta seleccionada: Consulta todas las visitas que fueron por 'Vacunación'");
						consultaDao.encontrarVisitasPorVacunacion();
						break;
					case 0:
						// En caso de que se introduzca 0 se vuelve al menú anterior
						System.out.println("Volviendo al menú anterior...");
						break;
					default:
						System.out.println("Opción inválida, por favor seleccione una opción válida.");
						break;
					}
				} while (subOpcion != 0);
				break;
			case 2:
				// En la opción 2 se presenta la posibilidad de gestionar a las mascotas
				System.out.println("Opción: Gestionar Mascotas");
				int subOpcion2;
				do {
					System.out.println("Gestionar Mascotas:");
					System.out.println("1. Crear mascota");
					System.out.println("2. Listar mascotas");
					System.out.println("3. Actualizar mascota");
					System.out.println("4. Eliminar mascota");
					System.out.println("0. Volver al menú anterior");
					System.out.print("Seleccione una opción: ");

					subOpcion2 = scanner.nextInt();
					scanner.nextLine(); // Consumir el salto de línea

					switch (subOpcion2) {
					case 1:
					    System.out.println("Opción: Crear mascota");
					    // Solicita el nombre del dueño para asociarlo a la nueva mascota
					    System.out.println("Introduce el nombre del dueño:");
					    String nombreDueño = scanner.nextLine();

					    // Busca el dueño en la base de datos
					    Dueño dueño = dueñoDao.encontrarDueñoPorNombre(nombreDueño);

					    // Si el dueño no se encuentra en la base de datos, notifica al usuario
					    if (dueño == null) {
					        System.out.println("El dueño no se encontró en la base de datos.");
					        break;
					    }

					    // Solicita información de la nueva mascota
					    System.out.println("Introduce el nombre:");
					    String nombreMascota = scanner.nextLine();
					    System.out.println("Introduce el tipo de animal:");
					    String tipoMascota = scanner.nextLine();
					    System.out.println("Introduce la raza:");
					    String razaMascota = scanner.nextLine();
					    
					    // Crea una nueva mascota con los datos proporcionados y el dueño encontrado
					    Mascota mascota = new Mascota(nombreMascota, tipoMascota, razaMascota, dueño);
					    mascota = mascotaDao.crearMascota(mascota);
					    break;

					case 2:
					    System.out.println("Opción: Listar mascotas");
					    // Muestra la lista de mascotas registradas en la base de datos
					    mascotaDao.listMascotas(em);
					    break;

					case 3:
					    System.out.println("Opción: Actualizar mascota");
					    // Solicita el nombre de la mascota a actualizar
					    System.out.println("Introduce el nombre de la mascota que quieras actualizar:");
					    String nombreActual = scanner.nextLine();

					    // Busca la mascota por nombre en la base de datos
					    Mascota mascotaActualizar = mascotaDao.encontrarMascotaPorNombre(nombreActual);

					    // Si la mascota existe, solicita y actualiza la información de la mascota
					    if (mascotaActualizar != null) {
					        System.out.println("Introduce el nuevo nombre:");
					        String nuevoNombre = scanner.nextLine();
					        System.out.println("Introduce el nuevo tipo de animal:");
					        String nuevoTipo = scanner.nextLine();
					        System.out.println("Introduce la nueva raza:");
					        String nuevaRaza = scanner.nextLine();

					        // Actualiza los datos de la mascota en la base de datos
					        mascotaActualizar = mascotaDao.actualizarMascota(mascotaActualizar.getId(), nuevoNombre, nuevoTipo, nuevaRaza);

					        // Notifica al usuario sobre el resultado de la actualización
					        if (mascotaActualizar != null) {
					            System.out.println("Mascota actualizada correctamente.");
					        } else {
					            System.out.println("No se pudo actualizar a la mascota.");
					        }
					    } else {
					        // Si la mascota no se encuentra, notifica al usuario
					        System.out.println("Mascota no encontrada.");
					    }
					    break;

					case 4:
					    System.out.println("Opción: Eliminar mascota");
					    // Solicita el nombre de la mascota a eliminar
					    System.out.println("Introduce el nombre de la mascota que deseas eliminar:");
					    String nombre = scanner.nextLine();
					    
					    // Elimina la mascota por nombre de la base de datos
					    mascotaDao.eliminarMascota(nombre);
					    break;

					case 0:
						// En caso de que se introduzca 0 se vuelve al menú anterior
						System.out.println("Volviendo al menú anterior...");
						break;
					default:
						System.out.println("Opción inválida, por favor seleccione una opción válida.");
						break;
					}
				} while (subOpcion2 != 0);
				break;
			case 3:
				// En la opción 2 se presenta la posibilidad de gestionar a los dueños
				System.out.println("Opción: Gestionar Dueños");
				int subOpcion3;
				do {
					System.out.println("Gestionar Dueños:");
					System.out.println("1. Crear dueño");
					System.out.println("2. Listar dueños");
					System.out.println("3. Actualizar dueño");
					System.out.println("4. Eliminar dueño");
					System.out.println("0. Volver al menú anterior");
					System.out.print("Seleccione una opción: ");

					subOpcion3 = scanner.nextInt();
					scanner.nextLine(); // Consumir el salto de línea

					switch (subOpcion3) {
				    case 1:
				        System.out.println("Opción: Crear dueño");
				        // Lógica para crear dueño

				        // Solicita los datos del nuevo dueño
				        System.out.println("Introduce el nombre:");
				        String nombreDueño = scanner.nextLine();
				        System.out.println("Introduce la dirección:");
				        String direccionDueño = scanner.nextLine();
				        System.out.println("Introduce el teléfono:");
				        // scanner.nextLine();
				        int telefonoDueño = scanner.nextInt();

				        // Crea un nuevo dueño con los datos proporcionados
				        Dueño dueño = new Dueño(nombreDueño, direccionDueño, telefonoDueño);
				        dueño = dueñoDao.crearDueño(dueño);
				        break;

				    case 2:
				        System.out.println("Opción: Listar dueños");
				        // Lógica para consultar y listar dueños
				        dueñoDao.listDueños(em);
				        break;

				    case 3:
				        System.out.println("Opción: Actualizar dueño");
				        // Lógica para actualizar dueño
				        System.out.println("Introduce el nombre del dueño que quieras actualizar:");
				        String nombreActual = scanner.nextLine();

				        // Busca al dueño por nombre en la base de datos
				        Dueño dueñoActualizar = dueñoDao.encontrarDueñoPorNombre(nombreActual);

				        if (dueñoActualizar != null) {
				            // Solicita y actualiza la información del dueño
				            System.out.println("Introduce el nuevo nombre:");
				            String nuevoNombre = scanner.nextLine();
				            System.out.println("Introduce la nueva dirección:");
				            String nuevaDireccion = scanner.nextLine();
				            System.out.println("Introduce el nuevo teléfono:");
				            int nuevoTelefono = scanner.nextInt();

				            // Actualiza los datos del dueño en la base de datos
				            dueñoActualizar = dueñoDao.actualizarDueño(dueñoActualizar.getId(), nuevoNombre, nuevaDireccion, nuevoTelefono);
				            if (dueñoActualizar != null) {
				                System.out.println("Dueño actualizado correctamente.");
				            } else {
				                System.out.println("No se pudo actualizar al dueño.");
				            }
				        } else {
				            System.out.println("Dueño no encontrado.");
				        }
				        break;

				    case 4:
				        System.out.println("Opción: Eliminar dueño");
				        // Lógica para eliminar dueño
				        System.out.println("Introduce el nombre del dueño que deseas eliminar:");
				        String nombreEliminar = scanner.nextLine();

				        // Elimina al dueño por nombre de la base de datos
				        dueñoDao.eliminarDueño(nombreEliminar);
				        break;
				

					case 0:
						// En caso de que se introduzca 0 se vuelve al menú anterior
						System.out.println("Volviendo al menú anterior...");
						break;
					default:
						System.out.println("Opción inválida, por favor seleccione una opción válida.");
						break;
					}
				} while (subOpcion3 != 0);
				break;
			case 4:
				// En la opción 2 se presenta la posibilidad de gestionar a las visitas
				System.out.println("Opción: Gestionar Visitas");
				int subOpcion4;
				do {
					System.out.println("Gestionar Visitas:");
					System.out.println("1. Crear visita");
					System.out.println("2. Listar visitas");
					System.out.println("3. Actualizar visita");
					System.out.println("4. Eliminar visita");
					System.out.println("0. Volver al menú anterior");
					System.out.print("Seleccione una opción: ");

					subOpcion4 = scanner.nextInt();
					scanner.nextLine(); // Consumir el salto de línea

					switch (subOpcion4) {
				    case 1:
				        System.out.println("Opción: Crear visita");
				        // Lógica para crear visita

				        // Solicita el nombre de la mascota para asociar la visita
				        System.out.println("Introduce el nombre de la mascota:");
				        String nombreMascota = scanner.nextLine();

				        // Busca la mascota por nombre en la base de datos
				        Mascota mascota = mascotaDao.encontrarMascotaPorNombre(nombreMascota);

				        if (mascota == null) {
				            System.out.println("La mascota no se encontró en la base de datos.");
				            break;
				        }

				        // Solicita la información para crear la visita
				        System.out.println("Introduce la fecha:");
				        String fechaVisita = scanner.nextLine();
				        System.out.println("Introduce el motivo de la consulta:");
				        String motivoVisita = scanner.nextLine();
				        System.out.println("Introduce el diagnóstico:");
				        String diagnosticoVisita = scanner.nextLine();

				        // Crea la visita asociada a la mascota encontrada
				        Visita visita = new Visita(fechaVisita, motivoVisita, diagnosticoVisita, mascota);
				        visita = visitaDao.crearVisita(visita);
				        break;

				    case 2:
				        System.out.println("Opción: Listar visitas");
				        // Lógica para consultar y listar visitas
				        visitaDao.listVisitas(em);
				        break;

				    case 3:
				        System.out.println("Opción: Actualizar visita");
				        // Lógica para actualizar visita
				        System.out.println("Introduce el nombre de la mascota cuya visita quieras actualizar:");
				        String nombreActual = scanner.nextLine();
				        // Busca la mascota por nombre en la base de datos
					    Mascota mascotaActualizar = mascotaDao.encontrarMascotaPorNombre(nombreActual);

					    // Si la mascota existe, solicita y actualiza la información de la mascota
					    if (mascotaActualizar != null) {
					        System.out.println("Introduce el motivo que tuvo esa visita:");
					        String motivoActual = scanner.nextLine();
					        System.out.println("Introduce la nueva fecha:");
					        String fechaNueva = scanner.nextLine();
					        System.out.println("Introduce el nuevo motivo de consulta:");
					        String motivoNuevo = scanner.nextLine();
					        System.out.println("Introduce el nuevo diagnóstico:");
					        String diagnosticoNuevo = scanner.nextLine();

					        // Actualiza los datos de la visita en la base de datos
					        visitaDao.actualizarVisita(nombreActual, motivoActual, fechaNueva, motivoNuevo, diagnosticoNuevo);

					        // Notifica al usuario sobre el resultado de la actualización
					       
					    } else {
					        // Si la mascota no se encuentra, notifica al usuario
					        System.out.println("Mascota no encontrada.");
					    }
				

				        // Actualiza la información de la visita
				        break;

				    case 4:
				        System.out.println("Opción: Eliminar visita");
				        // Lógica para eliminar visita
				        System.out.println("Introduce el nombre de la mascota cuya visita deseas eliminar:");
				        String nombreEliminar = scanner.nextLine();
				        System.out.println("Introduce el motivo de consulta de esa visita:");
				        String motivoEliminar = scanner.nextLine();

				        // Elimina la visita asociada a la mascota y motivo especificados
				        visitaDao.eliminarVisita(nombreEliminar, motivoEliminar);
				        break;
				

					case 0:
						// En caso de que se introduzca 0 se vuelve al menú anterior
						System.out.println("Volviendo al menú anterior...");
						break;
					default:
						System.out.println("Opción inválida, por favor seleccione una opción válida.");
						break;
					}
				} while (subOpcion4 != 0);

				break;
			case 0:
				// En caso de que se introduzca 0 se cierra el programa
				System.out.println("Saliendo del programa...");
				break;
			default:
				System.out.println("Opción inválida, por favor seleccione una opción válida.");
				break;
			}
		} while (opcion != 0);

		scanner.close();

		em.close(); // Se cierra en EntityManager
		emf.close(); // Se cierra en EntityManagerFactory
	}
}