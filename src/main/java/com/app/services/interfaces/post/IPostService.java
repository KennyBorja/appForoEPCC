package com.app.services.interfaces.post;

import com.app.domain.post.Post;

public interface IPostService {
  public Post createPost (Long userId,String title,String content);
  public boolean index();
  public List<Post> searchWord(String query);
}