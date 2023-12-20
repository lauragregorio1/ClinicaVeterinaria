# <a name="_toc153786224"></a>1. Introducción:

Esta documentación técnica tiene como objetivo brindar una descripción detallada del sistema de gestión de clínica veterinaria, presentando su estructura, modelo de datos, consultas JPQL, configuración y posibles mejoras.

El sistema se desarrolló para gestionar información de dueños, mascotas y visitas a una clínica veterinaria. Utiliza una arquitectura basada en Java con tecnologías JPA y Hibernate para la persistencia de datos.

# <a name="_toc153786225"></a>2. Arquitectura del Sistema:

El sistema sigue un enfoque de capas, con separación de clases DAO (Acceso a Datos) y entidades de modelo. 

-Capa de Persistencia:

En esta capa, se encuentran las clases DAO (Acceso a Datos) que interactúan directamente con la base de datos. Estas clases, como ConsultaDAO, DueñoDAO, MascotaDAO y VisitaDAO, encapsulan la lógica para recuperar, actualizar, eliminar y crear registros en la base de datos. Están diseñadas para ofrecer una abstracción de las operaciones de persistencia, lo que permite desacoplar la lógica de acceso a datos del resto del sistema.

-Capa de Modelo:

Las entidades del modelo, como Dueño, Mascota y Visita, representan los objetos de datos y definen su estructura, atributos y relaciones. Estas entidades, anotadas con metadatos de JPA, actúan como representaciones de las tablas en la base de datos y forman la base del modelo de datos del sistema. Están interconectadas mediante relaciones definidas (como One-to-Many y Many-to-One) para reflejar la lógica de negocio y las interacciones entre ellas.











**Diagrama de clases:**

![Diagrama

Descripción generada automáticamente](Aspose.Words.193a04fc-08d5-4242-929f-579cfb5c7123.004.png)
# <a name="_toc153786226"></a>3. Modelo de Datos:

Entidades:

1. Dueño:
- Atributos:

  ID: Identificador único del dueño. (Long)

Nombre: Nombre del dueño. (String)

Dirección: Dirección de residencia del dueño. (String)

Teléfono: Número de teléfono de contacto del dueño. (Integer)

- Relaciones y anotaciones:

  @Entity: Indica que esta clase es una entidad y se debe mapear a una tabla en la base de datos.

@Id: Define el atributo como la clave primaria de la tabla.

@GeneratedValue: Especifica cómo se generará automáticamente el valor para la clave primaria, en este caso, con GenerationType.IDENTITY, donde la base de datos asigna automáticamente valores únicos.

@Column: Permite definir propiedades de la columna en la base de datos, como el nombre de la columna.

@OneToMany: Establece la relación entre Dueño y Mascota, indicando que un dueño puede tener múltiples mascotas. El atributo mappedBy especifica el nombre del campo en la clase Mascota que mapea esta relación.

1. Mascota:

- Atributos:

  ID: Identificador único de la mascota. (Long)

Nombre: Nombre de la mascota. (String)

Tipo: Tipo de mascota (ejemplo: perro, gato, pájaro). (String)

Raza: Raza de la mascota. (String)

- Relaciones y anotaciones:

@Entity: Indica que esta clase es una entidad y se debe mapear a una tabla en la base de datos.

@Id: Define el atributo como la clave primaria de la tabla.

@GeneratedValue: Especifica cómo se generará automáticamente el valor para la clave primaria.

@Column: Define propiedades de la columna en la base de datos, como el nombre de la columna.

@ManyToOne: Establece la relación Many-to-One con Dueño, indicando que muchas mascotas pueden pertenecer a un solo dueño. Esto se refleja en el atributo dueño de la clase Mascota.

@OneToMany: Define la relación One-to-Many con Visita, indicando que una mascota puede tener múltiples visitas. El atributo mappedBy especifica el nombre del campo en la clase Visita que mapea esta relación.

1. Visita:

- Atributos:

  ID: Identificador único de la visita. (Long)

Fecha: Fecha en la que se realizó la visita. (String)

MotivoConsulta: Razón o motivo de la visita médica. (String)

Diagnóstico: Diagnóstico de la mascota durante la visita.

- Relación y anotaciones:

  @Entity: Indica que esta clase es una entidad y se debe mapear a una tabla en la base de datos.

@Id: Define el atributo como la clave primaria de la tabla.

@GeneratedValue: Especifica cómo se generará automáticamente el valor para la clave primaria.

@Column: Define propiedades de la columna en la base de datos, como el nombre de la columna.

@ManyToOne: Establece la relación Many-to-One con Mascota, indicando que muchas visitas pueden estar asociadas a una mascota. Esto se refleja en el atributo mascota de la clase Visita.
1. # <a name="_toc153786227"></a>Consultas JPQL:

Lista de consultas JPQL utilizadas en el proyecto:

- Consulta 1 - Encontrar mascotas por tipo:

**SELECT m FROM Mascota m WHERE m.tipo = :tipo**

Descripción: Recupera todas las mascotas de un tipo específico.

Uso en el sistema: Permite buscar y listar mascotas por tipo.

- Consulta 2 - Encontrar dueños con más de dos mascotas:

**SELECT d FROM Dueño d WHERE SIZE(d.mascotas) > 2**

Descripción: Obtiene todos los dueños que tienen más de dos mascotas.

Uso en el sistema: Muestra los dueños que tienen una cantidad específica de mascotas para análisis o gestión.


- Consulta 3 - Encontrar visitas de una mascota por su nombre:

**SELECT v FROM Visita v WHERE v.mascota.nombre = :nombreMascota**

Descripción: Recupera todas las visitas de una mascota por su nombre.

Uso en el sistema: Permite visualizar el historial médico de una mascota específica.

- Consulta 4 - Encontrar visitas por motivo "Vacunación":

**SELECT v FROM Visita v WHERE v.motivoConsulta = :motivoConsulta**

Descripción: Recupera todas las visitas que fueron por motivo de "Vacunación".

Uso en el sistema: Ayuda a identificar las visitas específicas para vacunación de las mascotas.

1. # <a name="_toc153786228"></a>Configuración y Despliegue:

**Configuración del proyecto:**

El proyecto está desarrollado en Java utilizando tecnologías como JPA y Hibernate para la persistencia de datos. 

Lo primero que he hecho es crear un proyecto Maven, ya que así se crea un archivo Pom.xml en el que puedo poner las dependencias necesarias y varias carpetas en las que puedo separar código y recursos.

Esta es la estructura de mi proyecto. Dentro de src/main/java he creado dos paquetes diferentes, en ‘com’ se encuentran las clases que implementan consultas JPQL y la clase Main. En ‘Modelo’ tengo las entidades que se crean en la base de datos.

![Interfaz de usuario gráfica, Texto

Descripción generada automáticamente](Aspose.Words.193a04fc-08d5-4242-929f-579cfb5c7123.005.png)

-Archivo pom.xml

El archivo pom.xml se encarga de gestionar las dependencias del proyecto y su estructura general. En él, se definen las dependencias necesarias para la persistencia de datos, pruebas unitarias y otras funcionalidades:

![Texto

Descripción generada automáticamente](Aspose.Words.193a04fc-08d5-4242-929f-579cfb5c7123.006.png)

-Archivo persistence.xml

El archivo persistence.xml se encarga de la configuración de persistencia del sistema. Define la unidad de persistencia, proporciona información sobre las clases de entidad utilizadas y establece la configuración de la conexión a la base de datos:

![Texto

Descripción generada automáticamente](Aspose.Words.193a04fc-08d5-4242-929f-579cfb5c7123.007.png)

Estos archivos son esenciales para gestionar las dependencias del proyecto, conectar con la base de datos y definir las entidades que forman parte del modelo de datos del sistema.

Es importante abrir el puerto de MySQL (3306) para poder realizar la conexión. Yo he gestionado la base con PhpMyAdmin.


1. # <a name="_toc153786229"></a>Conclusiones y Posibles Mejoras:

**Reflexiones sobre el proceso de desarrollo:**

El proceso de desarrollo ha tenido varios picos. La configuración del proyecto y todas sus dependencias han sido al principio un reto al ser la primera vez que trabajo de esta manera. Acostumbrarme a JPA también llevó su tiempo de investigación y entender bien. Una vez hecho esto, la realización del proyecto ha ido sobre ruedas, sin casi ningún incidente y pudiendo terminarlo a tiempo. Estoy bastante satisfecha con el resultado, de cara al usuario, es fácil de usar e intuitivo. 

**Áreas de mejora y características adicionales:**

-Interfaz de usuario: Implementar una interfaz gráfica para facilitar la interacción con los usuarios con JavaFX, por ejemplo.

-Pruebas unitarias: implementar pruebas unitarias con Junit por ejemplo para agilizar el trabajo en futuras refactorizaciones de código.

-Mejoras en consultas: Optimizar las consultas para mejorar el rendimiento del sistema.

-Funcionalidades adicionales: Agregar funcionalidades como recordatorios de citas, historial médico de mascotas, entre otros.
