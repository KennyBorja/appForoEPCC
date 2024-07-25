package com.app.services.interfaces.post;

import com.app.domain.post.Post;

public interface IPostService {
  public Post createPost (Long id_user,String title,String content);

  //public Post updatePost(Long id_post,Post modifiedPost);
  
}
