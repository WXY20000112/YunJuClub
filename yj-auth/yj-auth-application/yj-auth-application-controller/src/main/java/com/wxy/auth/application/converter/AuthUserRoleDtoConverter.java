package com.wxy.auth.application.converter;

import com.wxy.auth.application.dto.AuthUserRoleDto;
import com.wxy.auth.domain.entity.AuthUserRoleBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @program: YunJuClub-Flex
 * @description: AuthUserRoleDtoConverter
 * @author: 32115
 * @create: 2024-05-21 14:50
 */
@Mapper
public interface AuthUserRoleDtoConverter {

    AuthUserRoleDtoConverter CONVERTER = Mappers.getMapper(AuthUserRoleDtoConverter.class);

    // dto 转 bo
    AuthUserRoleBO converterDtoToBo(AuthUserRoleDto authUserRoleDto);
}
