//package com.example.commons;
//
//import com.example.accounts.UserDuplicatedException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
///**
// * 모든 컨트롤러에 적용되는 예외 핸들러, 지금은 그냥 이렇게 안쓰자.
// */
//@ControllerAdvice
//public class ExceptionHandlers {
//
//  @ExceptionHandler(UserDuplicatedException.class)
//  public ResponseEntity handleUserDuplicatedException(UserDuplicatedException e) {
//    ErrorResponse errorResponse = new ErrorResponse();
//    errorResponse.setMessage(e.getUserName() + "중복 이메일 입니다");
//    errorResponse.setCode("duplicated.userName.exception");
//    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//  }
//}
