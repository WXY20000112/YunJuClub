package com.wxy.oss.entity;

import lombok.Data;

/**
 * @program: YunJuClub
 * @description: 封装文件的名称、大小等信息
 * @author: 32115
 * @create: 2024-03-23 10:51
 */
@Data
public class FileInfo {

    // 文件名称
    private String fileName;

    // 文件大小
    private Long fileSize;

    // 是否是目录
    private Boolean isDirectory;

    // 文件tag
    private String etag;
}
