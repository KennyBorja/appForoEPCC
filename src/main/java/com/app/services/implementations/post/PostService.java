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
  public Post createPost(Long id_user,String title,String content){
    try {
      ForoUser user = userService.getUserbyId(id_user);

      Entry entrySaved = entryService.createEntry(user, content);

      Post postCreated = new Post();
      postCreated.setEntry(entrySaved);
      postCreated.setTitle(title);

      return postRepository.save(postCreated);
      
    } catch(Exception e){
      throw new RuntimeException("No se pudo crear el post");
    }
  }

  @Transactional(readOnly = true)
  public Post getPostById(Long id_post){
    return postRepository.findById(id_post)
      .orElseThrow();
  }

}