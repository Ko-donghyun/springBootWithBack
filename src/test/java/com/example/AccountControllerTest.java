package com.example;

import com.example.accounts.AccountDto;
import com.example.accounts.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by kodonghyeon on 2016. 6. 6..
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(loader = SpringApplicationContextLoader.class, classes = Application.class) 아래랑 같은 의미
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional // 자동으로 롤백이 되는 트랜잭션, 에러가 나든 나지 않든. 다른 테스트에 영향이 미치지 않도록 한다!
//@TransactionConfiguration(defaultRollback = true) 없어지고
//@Rollback 이게 가능
public class AccountControllerTest {

  @Autowired
  WebApplicationContext wac;

  // JSON 으로 바꾸기 위해
  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  AccountService accountService;

  MockMvc mockMvc;

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
  }

  @Test
//  @Rollback(false) // 롤백하지 않도록
  public void createAccount() throws Exception {
    AccountDto.Create createDto = new AccountDto.Create();
    createDto.setUsername("kkk001");
    createDto.setPassword("aaaaaa");

    ResultActions result = mockMvc.perform(post("/accounts")
           .contentType(MediaType.APPLICATION_JSON)
           .content(objectMapper.writeValueAsString(createDto)));

    result.andDo(print());
    result.andExpect(status().isCreated());
    // TODO JSON PATH
    result.andExpect(jsonPath("$.username", is("kkk001")));

    // 중복 테스트
    result = mockMvc.perform(post("/accounts")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(createDto)));

    result.andDo(print());
    result.andExpect(status().isBadRequest());
    result.andExpect(jsonPath("$.code", is("duplicated.userName.exception")));
  }

  @Test
  public void createAccount_BadRequest() throws Exception {
    AccountDto.Create createDto = new AccountDto.Create();
    createDto.setUsername("   ");
    createDto.setPassword("12");

    ResultActions resultActions = mockMvc.perform(post("/accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(createDto)));

    resultActions.andDo(print());
    resultActions.andExpect(status().isBadRequest());
    resultActions.andExpect(jsonPath("$.code", is("bad.request")));
  }

  @Test
  public void getAccounts() throws Exception {
    AccountDto.Create createDto = new AccountDto.Create();
    createDto.setUsername("kkkkkk");
    createDto.setPassword("password");
    accountService.createAccount(createDto);

    ResultActions result = mockMvc.perform(get("/accounts"));

    // Body = {
    // "content":
    // [{"id":1,
    // "username":"kkkkkk",
    // "fullName":null,
    // "joined":1465202017782,
    // "updated":1465202017782}],
    // "totalElements":1,
    // "last":true,
    // "totalPages":1,
    // "size":20,
    // "number":0,
    // "first":true,
    // "sort":null,
    // "numberOfElements":1
    // }
    result.andDo(print());
    result.andExpect(status().isOk());
  }


}
