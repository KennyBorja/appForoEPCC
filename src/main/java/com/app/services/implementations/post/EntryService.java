package com.app.services.implementations.post;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.domain.post.Entry;
import com.app.domain.user.ForoUser;
import com.app.repositories.EntryRepositoryImp;
import com.app.services.interfaces.IEntryService;


@Service
public class EntryService implements IEntryService {

  @Autowired
  private EntryRepositoryImp entryRepository;

  @Override
  @Transactional
  public Entry createEntry(ForoUser user, String content) {
    try {
      Entry entryCreated = new Entry();
      entryCreated.setUser(user);
      entryCreated.setContent(content);

      return entryRepository.save(entryCreated);
    } catch (DataAccessException e) {
      throw new IllegalStateException("Error al intentar guardar la entrada en la base de datos", e);
    } catch (Exception e) {
      throw new IllegalStateException("No se pudo crear la entrada debido a un error inesperado", e);
    }
  }

  @Override
  @Transactional
  public void addCommentToEntry(Long idEntry) {
    try {
      Entry entry = entryRepository.findById(idEntry)
          .orElseThrow(() -> new IllegalArgumentException("El entry con ID " + idEntry + " no se encontró"));

      entry.setComments(entry.getComments() + 1);
      entryRepository.save(entry);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("No se encontró el entry con ID " + idEntry, e);
    } catch (DataAccessException e) {
      throw new IllegalStateException("Error al intentar actualizar el contador de comentarios", e);
    } catch (Exception e) {
      throw new IllegalStateException("Error inesperado al añadir comentario al entry con ID " + idEntry, e);
    }
  }
}