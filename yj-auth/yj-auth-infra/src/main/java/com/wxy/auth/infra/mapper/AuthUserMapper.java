package com.wxy.auth.infra.mapper;

import com.mybatisflex.core.BaseMapper;
import com.wxy.auth.infra.entity.AuthUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: YunJuClub-Flex
 * @description: AuthUserMapper
 * @author: 32115
 * @create: 2024-05-17 14:32
 */
@Mapper
public interface AuthUserMapper extends BaseMapper<AuthUser> {
}
