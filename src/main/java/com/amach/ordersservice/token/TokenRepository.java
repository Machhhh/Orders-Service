package com.amach.ordersservice.token;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
interface TokenRepository extends CrudRepository<Token, Long> {

    Set<Token> findAllByClientIdAndExpiredFalse(Long userId);

    Token findByToken(String token);

    Token findByClientLogin(String login);
}
