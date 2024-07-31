package com.app.services.interfaces.post;

import com.app.domain.post.Entry;
import com.app.domain.user.ForoUser;

public interface IEntryService {

  public Entry createEntry(ForoUser user,String content);

  public void addCommentToEntry(Long id_entry);
  
}
