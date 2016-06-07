package com.example.accounts;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * AccountRepository
 * 디비의 값을 가져오는?!
 */
// Jpa <Entity, PK>
// 특정한 인터페이스 타입의 클래스를 찾아서 구현체를 프록시 객체 만들어주는 것처럼 만든 후 빈으로 등록을 해준다.
public interface AccountRepository extends JpaRepository<Account, Long> {
  Account findByUserName(String userName);
}
