package com.wxy.circle.server.rpc.feign;

import com.wxy.auth.api.entity.AuthUserDto;
import com.wxy.auth.api.entity.Result;
import com.wxy.auth.api.feign.AuthUserFeignService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @program: YunJuClub-Flex
 * @description: AuthUserRpc
 * @author: 32115
 * @create: 2024-05-23 18:15
 */
@Component
public class AuthUserRpc {

    @Resource
    private AuthUserFeignService authUserFeignService;

    public AuthUserDto getUserInfo(String userName) {
        AuthUserDto authUserDTO = new AuthUserDto();
        authUserDTO.setUserName(userName);
        Result<AuthUserDto> result =
                authUserFeignService.getUserInfo(authUserDTO);
        if (!result.getSuccess()) return new AuthUserDto();
        return result.getData();
    }

    /**
     * @author: 32115
     * @description: 根据用户名批量查询用户信息
     * @date: 2024/6/9
     * @param: userNameList
     * @return: Map<String, AuthUserDto>
     */
    public Map<String, AuthUserDto> getUserInfoList(List<String> userNameList) {
        if (CollectionUtils.isEmpty(userNameList)) return Collections.emptyMap();
        // 调用远程服务查询
        Result<List<AuthUserDto>> authUserDtoList =
                authUserFeignService.getUserInfoList(userNameList);
        if (Objects.isNull(authUserDtoList) ||
                !authUserDtoList.getSuccess() ||
                Objects.isNull(authUserDtoList.getData())) {
            return Collections.emptyMap();
        }
        // 将列表对象转换为map
        Map<String, AuthUserDto> authUserDtoMap = new HashMap<>();
        for (AuthUserDto authUserDto : authUserDtoList.getData()) {
            AuthUserDto userInfo = new AuthUserDto();
            userInfo.setUserName(authUserDto.getUserName());
            userInfo.setNickName(authUserDto.getNickName());
            userInfo.setAvatar(authUserDto.getAvatar());
            authUserDtoMap.put(userInfo.getUserName(), userInfo);
        }
        return authUserDtoMap;
    }
}
