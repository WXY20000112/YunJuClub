package com.wxy.oss.adapter;

import com.wxy.oss.entity.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * @program: YunJuClub
 * @description: 阿里云oss服务工具
 * @author: 32115
 * @create: 2024-03-23 14:10
 */
public class AliYunAdapter implements StorageAdapter{

    /**
     * @author: 32115
     * @description: 创建bucket桶
     * @date: 2024/3/23
     * @param: bucketName
     * @return: void
     */
    @Override
    public void createBucket(String bucketName) {

    }

    /**
     * @author: 32115
     * @description: 上传文件
     * @date: 2024/3/23
     * @param: file
     * @param: bucketName
     * @param: objectName
     * @return: String
     */
    @Override
    public String uploadFile(MultipartFile file, String bucketName) {
        return null;
    }

    /**
     * @author: 32115
     * @description: 获取所有桶的名称
     * @date: 2024/3/23
     * @return: List<String>
     */
    @Override
    public List<String> getAllBucket() {
        return null;
    }

    /**
     * @author: 32115
     * @description: 获取当前桶的所有文件
     * @date: 2024/3/23
     * @param: bucket
     * @return: List<FileInfo>
     */
    @Override
    public List<FileInfo> getBucketFile(String bucketName) {
        return null;
    }

    /**
     * @author: 32115
     * @description: 下载文件
     * @date: 2024/3/23
     * @param: bucketName
     * @param: objectName
     * @return: InputStream
     */
    @Override
    public InputStream downloadFile(String bucketName, String objectName) {
        return null;
    }

    /**
     * @author: 32115
     * @description: 删除文件
     * @date: 2024/3/23
     * @param: buketName
     * @param: objectName
     * @return: void
     */
    @Override
    public void deleteFile(String buketName, String objectName) {

    }

    /**
     * @author: 32115
     * @description: 删除桶
     * @date: 2024/3/23
     * @param: bucketName
     * @return: void
     */
    @Override
    public void deleteBucket(String bucketName) {

    }

    @Override
    public String getUrl(String bucket, String objectName) {
        return "";
    }
}
