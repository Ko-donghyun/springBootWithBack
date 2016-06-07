package com.example;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 메인 메소드가 들어가는 핵심 클래스
 * 구동의 진입점.
 */
@SpringBootApplication
public class Application {
  public static void main(String[] args) {
    // @SpringBootApplication 이 붙어있는 클래스와 Command argument 를 run 으로 넘겨준다!!!!
    // @SpringBootApplication 이 붙어있는 클래스와 run 하는 클래스를 따로 분리해도 되나 보통 같이 쓴다.
    // 메인 메소드를 실행하면 내장 톰캣이 실행이 되고, 필요한 기본 설정들이 적용이 되고,
    // 아까 작성한 Account 컨트롤러가 빈으로 등록이 되고, RequestMapping 이 적용이 된다.
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
