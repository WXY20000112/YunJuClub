package com.wxy.circle.server.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @program: YunJuClub-Flex
 * @description: SensitiveWords实体类
 * @author: 32115
 * @create: 2024-06-09 16:17
 */
@Data
@Table(value = "sensitive_words")
public class SensitiveWords implements Serializable {

    @Serial
    private static final long serialVersionUID = 538446931485847072L;

    /**
     * id
     */
    @Id(keyType = KeyType.None)
    private Long id;

    /**
     * 内容
     */
    private String words;

    /**
     * 1=黑名单 2=白名单
     */
    private Integer type;

    /**
     * 是否被删除 0为删除 1已删除
     */
    private Integer isDeleted;
}
