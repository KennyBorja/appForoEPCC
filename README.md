# Proyecto de Foro

Este proyecto es una implementación de un foro donde los usuarios pueden comentar en las entradas. La clase `Post` es  parte fundamental de esta implementación.

## Convenciones de Codificación

se a seguido las siguientes convenciones en la implementacion:

1. **Nombres Claros y Descriptivos:**
    - Nombres de clases, métodos y variables son claros y reflejan su propósito.
    - Ejemplo: La clase `Post` representa una publicación en el foro.

2. **Funciones con Responsabilidades Claras:**
    - Cada método y función tiene una única responsabilidad, lo que facilita su lectura y mantenimiento.
    - Ejemplo: Los métodos de acceso (`getters` y `setters`) siguen esta práctica.

## Prácticas de Codificación Legible

### Nombres

uso de nombres claros y descriptivos para la clase y variables, lo que facilita la comprensión del código.

### excepciones

un mejor manejo en las excepciones

## -----------------------------------------------------------------------------------------------------------

## principios SOLID

1. **inyectar dependencias**

en `PostService` se usa en PostService  @Autowired para, la inyección de dependencias de Spring. 
implicando que PostService no crea instancias directamente, sino que depende de abstracciones gestionadas por el contenedor de Spring. 

2. **Principio de Sustitución de Liskov**

el uso de la inyección de dependencias y el hecho de que PostService pueda utilizar cualquier implementación de `EntryService`, `PostRepositoryImp` y `UserService`. permitiendo que PostService funcione correctamente independientemente de las implementaciones concretas.

3. **Principio de Responsabilidad Única**

`Post` está principalmente enfocado en la gestión de posts, específicamente en su creación y recuperación. Aunque se delegan tareas como la creación de una entrada y la obtención de un usuario, estas operaciones son parte integral de la creación de un post.

4. **Principio de Encapsulamiento**

Los atributos de la clase `Post` son privados y se accede a ellos a través de métodos públicos (getters y setters). Esto asegura que los datos estén encapsulados y solo se puedan modificar de forma controlada, lo que protege la integridad del objeto.
