package com.wxy.oss.service;

import com.wxy.oss.adapter.StorageAdapter;
import com.wxy.oss.entity.FileInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * @program: YunJuClub
 * @description: oss服务controller与工具类交互工具
 * @author: 32115
 * @create: 2024-03-23 14:33
 */
@Service
public class FileService {

    private final StorageAdapter storageAdapter;

    public FileService(StorageAdapter storageAdapter){
        this.storageAdapter = storageAdapter;
    }

    /**
     * @author: 32115
     * @description: 创建bucket桶
     * @date: 2024/3/23
     * @param: bucketName
     * @return: void
     */
    public void createBucket(String bucketName){
        storageAdapter.createBucket(bucketName);
    }

    /**
     * @author: 32115
     * @description: 上传文件
     * @date: 2024/3/23
     * @param: file
     * @param: bucketName
     * @param: objectName
     * @return: void
     */
    public String upload(MultipartFile file, String bucket) {
        return storageAdapter.uploadFile(file, bucket);
    }

    /**
     * @author: 32115
     * @description: 获取所有桶的名称
     * @date: 2024/3/23
     * @return: List<String>
     */
    public List<String> getAllBucket(){
        return storageAdapter.getAllBucket();
    }

    /**
     * @author: 32115
     * @description: 获取当前桶的所有文件
     * @date: 2024/3/23
     * @param: bucket
     * @return: List<FileInfo>
     */
    public List<FileInfo> getBucketFile(String bucketName){
        return storageAdapter.getBucketFile(bucketName);
    }

    /**
     * @author: 32115
     * @description: 下载文件
     * @date: 2024/3/23
     * @param: bucketName
     * @param: objectName
     * @return: InputStream
     */
    public InputStream downloadFile(String bucketName, String objectName){
        return storageAdapter.downloadFile(bucketName, objectName);
    }

    /**
     * @author: 32115
     * @description: 删除文件
     * @date: 2024/3/23
     * @param: buketName
     * @param: objectName
     * @return: void
     */
    public void deleteFile(String buketName, String objectName){
        storageAdapter.deleteFile(buketName, objectName);
    }

    /**
     * @author: 32115
     * @description: 删除桶
     * @date: 2024/3/23
     * @param: bucketName
     * @return: void
     */
    public void deleteBucket(String bucketName){
        storageAdapter.deleteBucket(bucketName);
    }

    /**
     * @author: 32115
     * @description: 获取文件url
     * @date: 2024/4/11
     * @param: bucketName
     * @param: objectName
     * @return: String
     */
    public String getUrl(String bucketName, String objectName) {
        return storageAdapter.getUrl(bucketName, objectName);
    }
}
