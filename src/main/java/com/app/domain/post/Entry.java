package com.app.domain.post;

import java.time.LocalDateTime;

import com.app.domain.user.ForoUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "entry")
public class Entry {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @ManyToOne
  @JoinColumn(name = "id_user",referencedColumnName = "id",foreignKey = @ForeignKey(name = "FK_user_entry"),nullable = false)
  private ForoUser user;

  @Column(nullable = false,columnDefinition = "TEXT")
  private String content;

  @Column(columnDefinition = "INT DEFAULT 0")
  private int up_votes;

  @Column(columnDefinition = "INT DEFAULT 0")
  private int down_votes;

  @Column(columnDefinition = "INT DEFAULT 0")
  private int comments;

  @Column(nullable = false,updatable = false,columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private LocalDateTime created_at;

  @Column(nullable = false,columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private LocalDateTime last_update;

  public Long getId() {
    return id;
  }

  public ForoUser getUser() {
    return user;
  }

  public String getContent() {
    return content;
  }

  public int getUp_votes() {
    return up_votes;
  }

  public int getDown_votes() {
    return down_votes;
  }

  public int getComments() {
    return comments;
  }

  public LocalDateTime getCreated_at() {
    return created_at;
  }

  public LocalDateTime getLast_update() {
    return last_update;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setUp_votes(int up_votes) {
    this.up_votes = up_votes;
  }

  public void setDown_votes(int down_votes) {
    this.down_votes = down_votes;
  }

  public void setComments(int comments) {
    this.comments = comments;
  }

  public void setLast_update(LocalDateTime last_update) {
    this.last_update = last_update;
  }

  public void setUser(ForoUser user) {
    this.user = user;
  }
  
}
