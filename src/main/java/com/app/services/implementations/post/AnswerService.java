package com.app.services.implementations.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.domain.post.Answer;
import com.app.domain.post.Entry;
import com.app.domain.post.Post;
import com.app.domain.user.ForoUser;
import com.app.repositories.AnswerRepositoryImp;
import com.app.services.interfaces.IAnswerService;

@Service
public class AnswerService implements IAnswerService{
  @Autowired
  private UserService userService;

  @Autowired
  private PostService postService;

  @Autowired
  private EntryService entryService;

  @Autowired
  private AnswerRepositoryImp answerRepository;

  @Override
  @Transactional
  public Answer createAnswer(Long postIdToReply,Long userId,String content){
    try{
      ForoUser user = userService.getUserbyId(userId);
      Entry entryCreated = entryService.createEntry(user, content);
      Post postToReply = postService.getPostById(postIdToReply);
  
      Answer answerCreated = new Answer();
      answerCreated.setEntry(entryCreated);
      answerCreated.setPost(postToReply);
      
      return answerRepository.save(answerCreated);
    }catch(Exception e){
      throw new RuntimeException("No se pudo crear la respuesta");
    }
  };

  @Override
  @Transactional(readOnly = true)
  public Answer getAnswerById(Long answerId){
    return answerRepository.findById(answerId)
      .orElseThrow()
  };

}