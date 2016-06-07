package com.example.accounts;

import com.example.commons.ErrorResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Account 컨트롤러
 * 라우팅 설정!
 */
// @Controller
// @ResponseBody 클래스 안의 모든 public 메소드에 @ResponseBody 를 적용한다.
// @RestController = @Controller + @ResponseBody
// 요즘에는 API 개발로 바뀌어서 @RestController로 쓴다.
@RestController
//@RequestMapping("/api/") 프리픽스 붙이기
public class AccountController {

  @Autowired
  private AccountService accountService;

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private ModelMapper modelMapper;

  @RequestMapping("/hello")
  // @ResponseBody 리턴타입의 어노테이션이다라는 느낌
  // public @ResponseBody String hello() { 이와 같이 써도 된다.
  public String hello() {
    return "Hello Spring Boot";
  }


//  @RequestMapping(value = "/accounts", method = RequestMethod.POST)
//  public RequestEntity createAccount(@ModelAttribute Account account,
//                                     @RequestParam ) {
//
//  }

  @RequestMapping(value = "/accounts", method = POST)
  public ResponseEntity createAccount(@RequestBody @Valid AccountDto.Create create,
                                     BindingResult result) {
    // 서버 사이드 개발이 Rest API 로 가서 @RequestBody 를 주로 쓰는데
    // Request 본문에 들어오는 걸 파싱하는 게 많아짐
    // 메시지 컨버터가 동작을 한다. JSON to MessageConvert 가 JSON 데이터를 객체로 바인딩을 해준다.

    // Account 에 어떤걸 받았는지를 판단하기 어려움. 기억을 해야 함. username / password 만 필요할 수 있다.
    // 그래서 DTO 를 사용함

    if (result.hasErrors()) {
      // 에러 응답 본문 추가하기
      ErrorResponse errorResponse = new ErrorResponse();
      errorResponse.setMessage("잘못된 요청입니다.");
      errorResponse.setCode("bad.request");
      // TODO result 안에 들어있는 에러 정보 사용하기.
      return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

//    예외 처리 방법
//    1. 리턴 타입으로 판단
//    Account newAccount = accountService.createAccount(create);
//    if (newAccount == null) {
//      return new ResponseEntity(HttpStatus.BAD_REQUEST); // 에러 응답
//    }

//    2. 파라미터 이용
//    에러인지 아닌지를 알 수 있는 객체를 하나 생성 해서 넘겨줘야 함. 그리고 이후 서비스를 호출해서 다시 판단해야 함.
//    Account newAccount = accountService.createAccount(create, result);
//    if (result.hasErrors()) {
//      return new ResponseEntity(HttpStatus.BAD_REQUEST); // 에러 응답
//    }

//    3. 서비스 내에서 처리하고, 이후에는 잘 넘어온 것이라 판단하고 코드를 짠다.
//    Account newAccount = accountService.createAccount(create);

//    4. 자바스크립트 처럼 처리
//    accountService.createAccount(create)
//              .onSuccess(account -> {
//                new ResponseEntity<>(modelMapper.map(newAccount, AccountDto.Response.class), HttpStatus.CREATED);
//              })
//              .onFailure(e -> handleUserDuplicatedException(e));

    // 서비스 호출
    Account newAccount = accountService.createAccount(create);
    return new ResponseEntity<>(modelMapper.map(newAccount, AccountDto.Response.class), HttpStatus.CREATED);

  }


  // TODO HATEOAS
  // TODO 뷰
  // No SPA : 1. JSP, 2. Thymeleaf
  // SPA : 3. 앵귤러, 4. 리액트
  @RequestMapping(value = "/accounts", method = GET)
  public ResponseEntity getAccounts(Pageable pageable) {
    // 스프링에서 제공해주는 페이징 처리
    // 서비스를 거쳐갈 것인가?, 리파짓 토리를 바로 쓸 것인가?
    Page<Account> page = accountRepository.findAll(pageable);
    List<AccountDto.Response> content = page.getContent().parallelStream()
        .map(account -> modelMapper.map(account, AccountDto.Response.class))
        .collect(Collectors.toList());

    PageImpl<AccountDto.Response> result = new PageImpl<>(content, pageable, page.getTotalElements());
    return new ResponseEntity<>(result, HttpStatus.OK);
  }


  @RequestMapping(value = "/accounts/{id}", method = GET)
  public ResponseEntity getAccount(@PathVariable Long id) {
    Account account = accountService.getAccount(id);
    // 서비스로 만들어냄.
//    Account account = accountRepository.findOne(id);
//    if (account == null) {
//      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

    // 모델 매퍼로 응답 DTO로 변환 시키자
    AccountDto.Response result = modelMapper.map(account, AccountDto.Response.class);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  // 전체 업데이트(userName, password, fullName 이 한 번에) vs 부분 업데이트(userName 만 들어올 수도 있다.)
  @RequestMapping(value = "/accounts/{id}", method = PUT)
  public ResponseEntity updateAccount(@PathVariable Long id,
                                      @RequestBody @Valid AccountDto.Update updateDto,
                                      BindingResult result) {
    if (result.hasErrors()) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Account updatedAccount = accountService.updateAccount(id, updateDto);
    return new ResponseEntity<>(modelMapper.map(updatedAccount, AccountDto.Response.class), HttpStatus.OK);
  }


  @RequestMapping(value = "/accounts/{id}", method = DELETE)
  public ResponseEntity deleteAccount(@PathVariable Long id) {
    accountService.deleteAccount(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }


  // 잡고 싶은 exception 을 작성, 예외를 잡는다!
  // 전역 처리를 위해 새로운 클래스를 만들어서 옮긴다. ExceptionHandlers
  @ExceptionHandler(UserDuplicatedException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleUserDuplicatedException(UserDuplicatedException e) {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setMessage(e.getUserName() + "중복 이메일 입니다");
    errorResponse.setCode("duplicated.userName.exception");
    return errorResponse;
  }

//  @ExceptionHandler(UserDuplicatedException.class)
//  public ResponseEntity handleUserDuplicatedException(UserDuplicatedException e) {
//    ErrorResponse errorResponse = new ErrorResponse();
//    errorResponse.setMessage(e.getUserName() + "중복 이메일 입니다");
//    errorResponse.setCode("duplicated.userName.exception");
//    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//  }

  @ExceptionHandler(AccountNotFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleAccountNotFoundException(AccountNotFoundException e) {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setMessage(e.getId() + "에 해당하는 계정이 없다.");
    errorResponse.setCode("account.not.found.exception");
    return errorResponse;
  }


}
