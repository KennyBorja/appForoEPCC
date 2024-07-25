package com.app.repositories.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.domain.post.Post;

@Repository
public interface PostRepositoryImp extends JpaRepository<Post,Long>{

}