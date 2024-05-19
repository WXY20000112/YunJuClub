package com.wxy.auth.application.converter;

import com.wxy.auth.application.dto.AuthRoleDto;
import com.wxy.auth.domain.entity.AuthRoleBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @program: YunJuClub-Flex
 * @description: AuthRoleDto转换器
 * @author: 32115
 * @create: 2024-05-18 16:28
 */
@Mapper
public interface AuthRoleDtoConverter {

    AuthRoleDtoConverter CONVERTER = Mappers.getMapper(AuthRoleDtoConverter.class);

    // AuthRoleDto 转换 AuthRoleBo
    AuthRoleBO converterDtoToBo(AuthRoleDto authRoleDto);
}
