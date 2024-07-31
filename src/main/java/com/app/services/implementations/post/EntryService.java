package com.app.services.implementations.post;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.domain.post.Entry;
import com.app.domain.user.ForoUser;
import com.app.repositories.post.EntryRepositoryImp;
import com.app.services.interfaces.post.IEntryService;

@Service
public class EntryService implements IEntryService{

  // @Autowired
  // private UserServiceImp userService;
  @Autowired
  private EntryRepositoryImp entryRepository;

  @Override
  @Transactional
  public Entry createEntry(ForoUser user,String content){
    try{
      Entry entryCreated = new Entry();
      entryCreated.setUser(user);
      entryCreated.setContent(content);

      return entryRepository.save(entryCreated);
    }catch(Exception e){
      throw new RuntimeException("No se pudo crear el entry");
    }
  };

  @Override
  @Transactional
  public void addCommentToEntry(Long id_entry){
    if(!entryRepository.existsById(id_entry)){
      throw new RuntimeException("Nos e puede añadir el contador de comentarios");
    }
    Optional<Entry> entryO = entryRepository.findById(id_entry);
    Entry entry = entryO.get();
    entry.setComments(entry.getComments()+1);
    entryRepository.save(entry);
  };

}