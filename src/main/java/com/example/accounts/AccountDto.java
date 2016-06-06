package com.example.accounts;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by kodonghyeon on 2016. 6. 6..
 */
public class AccountDto {
  // 여러개의 Dto 를 사용할 수 있다.
  // Command F12 - 롬복이 만들어 주는 것

  @Data
  public static class Create {
    // 회원가입을 할 때 필요한 부분
    @NotBlank
    @Size(min = 5)
    private String username;

    @NotBlank
    @Size(min = 5)
    private String password;
  }

// TODO 비밀번호 처리를 해야 한다.
  @Data
  public static class Response {
    private Long id;
    private String username;
    private String fullName;
    private Date joined;
    private Date updated;
  }

}
