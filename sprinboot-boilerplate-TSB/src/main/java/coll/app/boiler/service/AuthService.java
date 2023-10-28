package coll.app.boiler.service;

import coll.app.boiler.model.response.token.TokenResponse;
import reactor.core.publisher.Mono;

public interface AuthService {

    Mono<TokenResponse> getToken();
}
