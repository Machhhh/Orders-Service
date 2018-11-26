package com.amach.ordersservice.token;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
class TokenServiceImpl implements TokenService {
    @Override
    public String createToken(String login) {
        return null;
    }

    @Override
    public void resetPassword(String token, String password) {

    }

    @Override
    public boolean checkIsTokenExpired(String uuid) {
        return false;
    }

    @Override
    public Token findTokenByUuid(String uuid) {
        return null;
    }
}
