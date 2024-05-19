package com.wxy.auth.domain.converter;

import com.wxy.auth.domain.entity.AuthRoleBO;
import com.wxy.auth.infra.entity.AuthRole;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @program: YunJuClub-Flex
 * @description: AuthRoleBO转化器
 * @author: 32115
 * @create: 2024-05-18 16:33
 */
@Mapper
public interface AuthRoleBOConverter {

    AuthRoleBOConverter CONVERTER = Mappers.getMapper(AuthRoleBOConverter.class);

    // AuthRoleBO 转化成 AuthRole
    AuthRole converterBOToEntity(AuthRoleBO authRoleBO);
}
