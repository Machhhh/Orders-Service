package com.amach.ordersservice.token;

import com.amach.ordersservice.client.Client;
import com.amach.ordersservice.client.ClientFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
class TokenServiceImpl implements TokenService {

    private TokenRepository tokenRepository;
    private ClientFacade clientFacade;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public TokenServiceImpl(TokenRepository tokenRepository,
                            ClientFacade clientFacade,
                            PasswordEncoder passwordEncoder) {
        this.tokenRepository = tokenRepository;
        this.clientFacade = clientFacade;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String createToken(String login) {
        Client client = clientFacade.getClientByLogin(login);
        if (client != null) {
            Token token = new Token(UUID.randomUUID().toString(), false, client);
            token = tokenRepository.save(token);
            return token.getToken();
        }
        return UUID.randomUUID().toString();
    }

    @Override
    public void resetPassword(String token, String password) {
        Token token1 = tokenRepository.findByToken(token);
        if (token1 == null) {
            return;
        }
        token1.getClient().setPassword(passwordEncoder.encode(password));
        token1.setExpired(true);
        tokenRepository.save(token1);
        Set<Token> tokens = tokenRepository.findAllByClientIdAndExpiredFalse(token1.getClient().getId());
        tokens.forEach(t -> t.setExpired(true));
        tokenRepository.save(tokens);
    }

    @Override
    public boolean checkIsTokenExpired(String uuid) {
        return tokenRepository.findByToken(uuid).isExpired();
    }

    @Override
    public Token findTokenByUuid(String uuid) {
        return tokenRepository.findByToken(uuid);
    }
}
