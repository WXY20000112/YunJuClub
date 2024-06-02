package com.wxy.auth.application.converter;

import com.wxy.auth.application.dto.AuthPermissionDto;
import com.wxy.auth.domain.entity.AuthPermissionBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: AuthPermissionDTO 转换 AuthPermissionBO
 * @author: 32115
 * @create: 2024-06-01 14:36
 */
@Mapper
public interface AuthPermissionDtoConverter {

    AuthPermissionDtoConverter CONVERTER = Mappers.getMapper(AuthPermissionDtoConverter.class);

    // AuthPermissionDTO 转换 AuthPermissionBO
    AuthPermissionBO converterDtoToBO(AuthPermissionDto authPermissionDto);

    // AuthPermissionBO 转换 AuthPermissionDTO
    List<AuthPermissionDto> converterBOListToDtoList(List<AuthPermissionBO> authPermissionBOList);
}
