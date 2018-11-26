package com.amach.ordersservice.token;

interface TokenService {

    String createToken(String login);

    void resetPassword(String token, String password);

    boolean checkIsTokenExpired(String uuid);

    Token findTokenByUuid(String uuid);
}
