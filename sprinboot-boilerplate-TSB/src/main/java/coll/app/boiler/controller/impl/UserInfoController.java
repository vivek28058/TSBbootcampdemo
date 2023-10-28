package coll.app.boiler.controller.impl;

import coll.app.boiler.controller.UserInfoApiVersion;
import coll.app.boiler.model.request.userInfo.UserInfoRequest;
import coll.app.boiler.model.response.userInfo.UserInfoResponse;
import coll.app.boiler.service.UserInfoService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
public class UserInfoController implements UserInfoApiVersion {
    private final UserInfoService userInfoService;

    UserInfoController(UserInfoService userInfoService){
     this.userInfoService = userInfoService;
    }

    @PostMapping("/user-info")
    public Mono<UserInfoResponse> userInfoController(@RequestBody UserInfoRequest request){
        return userInfoService.getUserInfo(request);
    }
}
