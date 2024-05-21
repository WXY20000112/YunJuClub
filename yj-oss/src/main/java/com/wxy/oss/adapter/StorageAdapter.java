package com.wxy.oss.adapter;

import com.wxy.oss.entity.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * @program: YunJuClub
 * @description: 设配器接口
 * @author: 32115
 * @create: 2024-03-23 14:06
 */
public interface StorageAdapter {

    /**
     * @author: 32115
     * @description: 创建bucket桶
     * @date: 2024/3/23
     * @param: bucketName
     * @return: void
     */
    void createBucket(String bucketName);

    /**
     * @author: 32115
     * @description: 上传文件
     * @date: 2024/3/23
     * @param: file
     * @param: bucketName
     * @param: objectName
     * @return: String
     */
    String uploadFile(MultipartFile file, String bucketName);

    /**
     * @author: 32115
     * @description: 获取所有桶的名称
     * @date: 2024/3/23
     * @return: List<String>
     */
    List<String> getAllBucket();

    /**
     * @author: 32115
     * @description: 获取当前桶的所有文件
     * @date: 2024/3/23
     * @param: bucket
     * @return: List<FileInfo>
     */
    List<FileInfo> getBucketFile(String bucketName);

    /**
     * @author: 32115
     * @description: 下载文件
     * @date: 2024/3/23
     * @param: bucketName
     * @param: objectName
     * @return: InputStream
     */
    InputStream downloadFile(String bucketName, String objectName);

    /**
     * @author: 32115
     * @description: 删除文件
     * @date: 2024/3/23
     * @param: buketName
     * @param: objectName
     * @return: void
     */
    void deleteFile(String buketName, String objectName);

    /**
     * @author: 32115
     * @description: 删除桶
     * @date: 2024/3/23
     * @param: bucketName
     * @return: void
     */
    void deleteBucket(String bucketName);

    /**
     * @author: 32115
     * @description: 获取文件url
     * @date: 2024/4/9
     * @param: bucket
     * @param: objectName
     * @return: String
     */
    String getUrl(String bucket, String objectName);
}
