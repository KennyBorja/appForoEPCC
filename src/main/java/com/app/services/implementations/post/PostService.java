package com.app.services.implementations.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.domain.post.Entry;
import com.app.domain.post.Post;
import com.app.domain.user.ForoUser;
import com.app.repositories.post.PostRepositoryImp;
import com.app.services.interfaces.post.IPostService;



@Service
public class PostService implements IPostService{

  @Autowired
  private EntryService entryService;

  @Autowired
  private PostRepositoryImp postRepository;

  @Autowired
  private UserService userService;

  @Override
  @Transactional
  
  public Post createPost(Long userId, String title, String content) {
    try {
        // Validación de argumentos
        if (userId == null || title == null || content == null) {
            throw new IllegalArgumentException("Los argumentos 'userId', 'title' y 'content' no pueden ser nulos.");
        }

        // Obtener el usuario
        ForoUser user = userService.getUserbyId(userId);
        if (user == null) {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + userId);
        }
        // Crear la entrada
        Entry entrySaved = entryService.createEntry(user, content);

        // Crear y guardar el post
        Post postCreated = new Post();
        postCreated.setEntry(entrySaved);
        postCreated.setTitle(title);

        return postRepository.save(postCreated);

    } catch (IllegalArgumentException e) {
        // Lanza una excepción con un mensaje claro si los argumentos son inválidos
        throw e;  // Re-lanzar la excepción de argumento inválido
    } catch (DataAccessException e) {
        // Captura problemas específicos de acceso a datos (como errores de base de datos)
        throw new IllegalStateException("Error al acceder a la base de datos al crear el post.", e);
    } catch (Exception e) {
        // Captura cualquier otra excepción general
        throw new IllegalStateException("No se pudo crear el post. Detalles: " + e.getMessage(), e);
    }
}


  @Transactional(readOnly = true)
  public Post getPostById(Long postId){
    return postRepository.findById(id_post)
      .orElseThrow();
  }

}