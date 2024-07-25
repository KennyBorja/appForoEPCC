package com.app.repositories.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.domain.post.Answer;

@Repository
public interface AnswerRepositoryImp extends JpaRepository<Answer,Long>{

}