package com.wxy.circle.server.converter;

import com.wxy.circle.server.dto.ShareCommentReplyDto;
import com.wxy.circle.server.entity.ShareCommentReply;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: ShareCommentReplyConverter
 * @author: 32115
 * @create: 2024-06-10 11:34
 */
@Mapper
public interface ShareCommentReplyConverter {

    ShareCommentReplyConverter CONVERTER = Mappers.getMapper(ShareCommentReplyConverter.class);

    List<ShareCommentReplyDto> converterEntityListToDtoList(List<ShareCommentReply> shareCommentReplyList);
}
