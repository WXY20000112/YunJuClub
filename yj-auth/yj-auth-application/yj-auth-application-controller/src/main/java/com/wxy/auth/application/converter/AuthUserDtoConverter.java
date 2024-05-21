package com.wxy.auth.application.converter;

import com.wxy.auth.application.dto.AuthUserDto;
import com.wxy.auth.domain.entity.AuthUserBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @program: YunJuClub-Flex
 * @description: AuthUserDtoConverter
 * @author: 32115
 * @create: 2024-05-19 17:02
 */
@Mapper
public interface AuthUserDtoConverter {

    AuthUserDtoConverter CONVERTER = Mappers.getMapper(AuthUserDtoConverter.class);

    // AuthUserDto转AuthUserBO
    AuthUserBO converterDtoToBo(AuthUserDto authUserDto);

    // AuthUserBO转AuthUserDto
    AuthUserDto converterBoToDto(AuthUserBO authUserBO);
}
