package com.example.accounts;

/**
 * Created by kodonghyeon on 2016. 6. 7..
 */
public class AccountNotFoundException extends RuntimeException {

  Long id;

  public AccountNotFoundException(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
