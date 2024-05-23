package com.wxy.auth.application.converter;

import com.wxy.auth.application.dto.AuthPermissionDto;
import com.wxy.auth.domain.entity.AuthPermissionBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @program: YunJuClub-Flex
 * @description: AuthPermissionDTO转换器
 * @author: 32115
 * @create: 2024-05-17 15:13
 */
@Mapper
public interface AuthPermissionDtoConverter {

    AuthPermissionDtoConverter CONVERTER = Mappers.getMapper(AuthPermissionDtoConverter.class);

    // AuthPermissionDTO 转换 AuthPermissionBO
    AuthPermissionBO converterDtoToBO(AuthPermissionDto authPermissionDto);

    // AuthPermissionBO 转换 AuthPermissionDTO
    // List<AuthPermissionDto> converterBOToDto(List<AuthPermissionBO> authPermissionBOList);
}
