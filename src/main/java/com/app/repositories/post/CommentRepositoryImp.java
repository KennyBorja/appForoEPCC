package com.app.repositories.post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.domain.post.Comment;

@Repository
public interface CommentRepositoryImp extends JpaRepository<Comment,Long>{
    

}