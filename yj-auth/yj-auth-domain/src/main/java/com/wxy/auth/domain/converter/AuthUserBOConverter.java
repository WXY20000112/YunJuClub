package com.wxy.auth.domain.converter;

import com.wxy.auth.domain.entity.AuthUserBO;
import com.wxy.auth.infra.entity.AuthUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @program: YunJuClub-Flex
 * @description: AuthUserBOConverter
 * @author: 32115
 * @create: 2024-05-19 17:35
 */
@Mapper
public interface AuthUserBOConverter {

    AuthUserBOConverter CONVERTER = Mappers.getMapper(AuthUserBOConverter.class);

    // Bo -> 实体类
    AuthUser converterBOToEntity(AuthUserBO authUserBO);
}
