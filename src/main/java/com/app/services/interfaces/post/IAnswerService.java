package com.app.services.interfaces.post;

import com.app.domain.post.Answer;

public interface IAnswerService {

  public Answer createAnswer(Long postIdToReply,Long userId,String content);

  public Answer getAnswerById(Long answerId);

}