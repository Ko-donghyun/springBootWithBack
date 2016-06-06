package com.example;

import com.example.accounts.Account;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by kodonghyeon on 2016. 6. 6..
 */
public class AccountTest {
  @Test
  public void getterSetter() {
    Account account = new Account();
    account.setUserName("kodong");
    account.setPassword("password");
    assertThat(account.getUserName(), is("kodong"));
  }
}
