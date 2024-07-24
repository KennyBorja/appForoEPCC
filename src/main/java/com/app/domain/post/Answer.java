package com.app.domain.post;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "answer")
public class Answer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(cascade = CascadeType.REMOVE)
  @JoinColumn(name = "id_entry", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_entry_answer"), nullable = false)
  private Entry entry;
  
  @ManyToOne
  @JoinColumn(name = "id_post",referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_post_answer"), nullable = false)
  private Post post;

  public Long getId() {
    return id;
  }

  public Entry getEntry() {
    return entry;
  }

  public Post getPost() {
    return post;
  }

  public void setEntry(Entry entry) {
    this.entry = entry;
  }

  public void setPost(Post post) {
    this.post = post;
  }

}