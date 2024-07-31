package com.app.services.implementations.post;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.domain.post.Entry;
import com.app.domain.post.Post;
import com.app.domain.user.ForoUser;
import com.app.repositories.post.PostRepositoryImp;
import com.app.services.interfaces.post.IPostService;

import jakarta.persistence.EntityManager;


@Service
public class PostService implements IPostService{

  private EntryService entryService;
  private PostRepositoryImp postRepository;
  private UserService userService;
  private EntityManager entityManager;

  public PostService (EntryService entryService, PostRepositoryImp postRepository ,UserService userService,EntityManager entityManager) {
    this.entryService = entryService;
    this.postRepository = postRepository;
    this.userService = userService;
    this.entityManager= entityManager;
  }
  
  @Override
  @Transactional
  public Post createPost(Long idUser,String title,String content){
    try {
      String user = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
      ForoUser userFound = userService.getUserByUsername(user);
      Entry entrySaved = entryService.createEntry(userFound, content);

      Post postCreated = new Post();
      postCreated.setEntry(entrySaved);
      postCreated.setTitle(title);

      return postRepository.save(postCreated);
      
    } catch (UsernameNotFoundException e) {
        throw new CreationException("No se pudo crear el post: Usuario no encontrado", e);
    } catch (EntryCreationException e) {
        throw new CreationException("No se pudo crear el post: Error al crear la entrada", e);
    } catch (DataIntegrityViolationException e) {
        throw new CreationException("No se pudo crear el post: Error de integridad de datos", e);
    } catch (PersistenceException e) {
        throw new CreationException("No se pudo crear el post: Error de persistencia", e);
    }
  }




  @Transactional(readOnly = true)
  public Post getPostById(Long postId){
    return postRepository.findById(id_post)
      .orElseThrow();
  }

}

 @Override
  public boolean index() {
    try {
      SearchSession searchSession = Search.session(entityManager);
      searchSession.massIndexer()
                .startAndWait();
      return true;
    } catch (InterruptedException ie) {
      System.out.println(ie.getMessage());
      return false;
    }
  };


  @Override
  public List<Post> searchWord(String query) {
    SearchSession searchSession = Search.session(entityManager);
    Set<Post>uniqueResults = new HashSet<>(searchSession.search(Post.class)
            .where(f -> f.match()
                    .fields("title")
                    .matching(query)
                    .analyzer("multilingual")
                    .fuzzy(2))
            .sort(f -> f.score())
            .fetchHits(20));
    return new ArrayList<>(uniqueResults);
  }