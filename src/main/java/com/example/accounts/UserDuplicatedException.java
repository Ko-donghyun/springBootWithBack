package com.example.accounts;

/**
 * Created by kodonghyeon on 2016. 6. 6..
 */
public class UserDuplicatedException extends RuntimeException {

  String userName;

  public UserDuplicatedException(String userName) {
      this.userName = userName;
  }


  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }
}
