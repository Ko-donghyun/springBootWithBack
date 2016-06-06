package com.example.accounts;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Account Repository 를 가지고 있게 끔, 빈으로 등록되게 끔만
 */
@Service
@Transactional // 안에 있는 모든 퍼블릭 메서드는 @Transactional 어노테이션이 적용이 된다.
public class AccountService {

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Transactional
  public Account createAccount(AccountDto.Create dto) {
//    Account account = new Account();
//    account.setUserName(dto.getUsername());
//    account.setPassword(dto.getPassword());

    // TODO 유효한 username인지 판단
    String userName = dto.getUsername();
    if (accountRepository.findByUserName(userName) != null) {
      throw new UserDuplicatedException(userName);
    }

    // TODO password 해싱

    Account account = modelMapper.map(dto, Account.class);
//    Account account = new Account();
//    BeanUtils.copyProperties(dto, account);
    Date now = new Date();
    account.setJoined(now);
    account.setUpdated(now);

    return accountRepository.save(account);

  }
}
