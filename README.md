# Documentación de Estilos de Programación Usados en el Proyecto de Foro

## 1. Cookbook
**Descripción**: método createPost es una "receta" completa para crear un post

**Ejemplo**:

```java
@Override
@Transactional
public Post createPost(Long idUser, String title, String content) {
    try {
        String user = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        ForoUser userFound = userService.getUserByUsername(user);
        Entry entrySaved = entryService.createEntry(userFound, content);

        Post postCreated = new Post();
        postCreated.setEntry(entrySaved);
        postCreated.setTitle(title);

        return postRepository.save(postCreated);
    } catch (Exception e) {
        throw new CreationException("No se pudo crear el post");
    }
}
```

## 2. Pipeline
**Descripción**: El código sigue una secuencia de operaciones que se encadenan una tras otra


**Ejemplo**:

```java
@Override
public List<Post> searchWord(String query) {
    SearchSession searchSession = Search.session(entityManager);
    Set<Post> uniqueResults = new HashSet<>(searchSession.search(Post.class)
            .where(f -> f.match()
                    .fields("title")
                    .matching(query)
                    .analyzer("multilingual")
                    .fuzzy(2))
            .sort(f -> f.score())
            .fetchHits(20));
    return new ArrayList<>(uniqueResults);
}


```
## 3. Persistent-Tables
**Descripción**: @Entity: esta clase es una entidad JPA, se mapeará a una tabla en la base de datos, @Table: Especifica el nombre de la tabla en la base de datos.

**Ejemplo**:

```java

@Entity
@Table(name = "post")
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(cascade = CascadeType.REMOVE)
  @JoinColumn(
    name = "id_entry",
    referencedColumnName = "id",
    foreignKey = @ForeignKey(name = "FK_entry_post"),
    nullable = false
  )
  private Entry entry;

  @Column(nullable = false)
  private String title;

  @Column(columnDefinition = "INT DEFAULT 0")
  private int views;

  @Column(columnDefinition = "INT DEFAULT 0")
  private int answers;

}

```



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
