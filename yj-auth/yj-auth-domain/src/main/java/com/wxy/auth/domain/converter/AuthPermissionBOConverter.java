package com.wxy.auth.domain.converter;

import com.wxy.auth.domain.entity.AuthPermissionBO;
import com.wxy.auth.infra.entity.AuthPermission;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: AuthPermissionBO转换器
 * @author: 32115
 * @create: 2024-05-17 15:20
 */
@Mapper
public interface AuthPermissionBOConverter {

    AuthPermissionBOConverter CONVERTER = Mappers.getMapper(AuthPermissionBOConverter.class);

    // BO 转 实体类
    AuthPermission converterBOToEntity(AuthPermissionBO authPermissionBO);

    // 实体类 转 BO
    List<AuthPermissionBO> converterEntityToBO(List<AuthPermission> authPermissionList);
}
