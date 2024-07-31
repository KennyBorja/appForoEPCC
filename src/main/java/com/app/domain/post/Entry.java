package com.app.domain.post;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.app.domain.user.ForoUser;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "entry")
public class Entry {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_user",referencedColumnName = "id",foreignKey = @ForeignKey(name = "FK_user_entry"),nullable = false)
  private ForoUser user;

  @Column(nullable = false,columnDefinition = "TEXT")
  private String content;

  @Column(columnDefinition = "INT DEFAULT 0")
  private int upVotes;

  @Column(columnDefinition = "INT DEFAULT 0")
  private int downVotes;

  @Column(columnDefinition = "INT DEFAULT 0")
  private int comments;

  @CreationTimestamp
  @Column(name="created_at",updatable = false, nullable = false)
  private LocalDateTime createdAt;

  @CreationTimestamp
  @Column(name = "last_update",nullable = false)
  private LocalDateTime lastUpdate;
  
}