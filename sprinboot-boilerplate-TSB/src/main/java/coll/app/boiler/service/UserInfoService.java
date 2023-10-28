package coll.app.boiler.service;

import coll.app.boiler.model.request.userInfo.UserInfoRequest;
import coll.app.boiler.model.response.userInfo.UserInfoResponse;
import reactor.core.publisher.Mono;

public interface UserInfoService {
    Mono<UserInfoResponse> getUserInfo(UserInfoRequest request);
}
