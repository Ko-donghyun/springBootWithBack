package com.example.accounts;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * 유저를 위한 클래스
 * 도메인? 빈?
 */
@Entity
@Getter
@Setter
public class Account {

  @Id @GeneratedValue
  private Long id;

  @Column(unique = true)
  private String userName;

  private String fullName;

  private String password;

  private String email;


  @Temporal(TemporalType.TIMESTAMP)
  private Date joined;

  @Temporal(TemporalType.TIMESTAMP)
  private Date updated;

  private boolean isAdmin;

}
