package com.example.accounts;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Account Repository 를 가지고 있게 끔, 빈으로 등록되게 끔만
 * 라우팅에서 처리 할 것들
 */
@Service
@Transactional // 안에 있는 모든 퍼블릭 메서드는 @Transactional 어노테이션이 적용이 된다.
@Slf4j
public class AccountService {

//  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private ModelMapper modelMapper;

  @Transactional
  public Account createAccount(AccountDto.Create dto) {
//    Account account = new Account();
//    account.setUserName(dto.getUsername());
//    account.setPassword(dto.getPassword());

    Account account = modelMapper.map(dto, Account.class);


    // TODO 유효한 username인지 판단
    String userName = dto.getUsername();
    if (accountRepository.findByUserName(userName) != null) {
      log.error("user duplicated exceptions. {}", userName);
//      logger.error("user duplicated exceptions. {}", userName);
      throw new UserDuplicatedException(userName);
    }

    // TODO password 해싱
    account.setPassword(passwordEncoder.encode(account.getPassword()));


//    Account account = modelMapper.map(dto, Account.class);
//    Account account = new Account();
//    BeanUtils.copyProperties(dto, account);
    Date now = new Date();
    account.setJoined(now);
    account.setUpdated(now);

    return accountRepository.save(account);

  }

  public Account updateAccount(Long id, AccountDto.Update updateDto) {
    Account account = getAccount(id);
    account.setPassword(passwordEncoder.encode(account.getPassword())); // password 해싱
//    account.setPassword(updateDto.getPassword());
    account.setFullName(updateDto.getFullName());
    return accountRepository.save(account);
  }

  public Account getAccount(Long id) {
    Account account = accountRepository.findOne(id);
    if (account == null) {
      throw new AccountNotFoundException(id);
    }
    return account;
  }

  public void deleteAccount(Long id) {
    accountRepository.delete(getAccount(id));
  }
}
