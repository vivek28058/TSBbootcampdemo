package coll.app.boiler.dto.mapper;

import coll.app.boiler.dto.response.userinfo.UserInfoDto;
import coll.app.boiler.model.response.UserInfo;

import java.util.function.Function;

public class UserInfoDtoMapper implements Function< UserInfo,UserInfoDto> {
    @Override
    public UserInfoDto apply(UserInfo userInfo) {
        return new UserInfoDto(
                userInfo.getTitle(),
                userInfo.getCompleted()
        );
    }

}
