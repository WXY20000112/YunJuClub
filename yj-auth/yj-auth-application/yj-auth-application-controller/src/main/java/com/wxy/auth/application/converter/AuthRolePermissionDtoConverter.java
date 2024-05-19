package com.wxy.auth.application.converter;

import com.wxy.auth.application.dto.AuthRolePermissionDto;
import com.wxy.auth.domain.entity.AuthRolePermissionBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @program: YunJuClub-Flex
 * @description: AuthRolePermissionDtoConverter
 * @author: 32115
 * @create: 2024-05-19 15:31
 */
@Mapper
public interface AuthRolePermissionDtoConverter {

    AuthRolePermissionDtoConverter CONVERTER = Mappers.getMapper(AuthRolePermissionDtoConverter.class);

    // Dto -> BO
    AuthRolePermissionBO converterDtoToBo(AuthRolePermissionDto authRolePermissionDto);
}
