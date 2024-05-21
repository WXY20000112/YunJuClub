package com.wxy.oss.utils;

import com.wxy.oss.entity.FileInfo;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: YunJuClub
 * @description: Minio上传、下载等工具类
 * @author: 32115
 * @create: 2024-03-23 10:38
 */
@Component
public class MinioUtil {

    @Resource
    private  MinioClient minioClient;

    /**
     * @author: 32115
     * @description: 创建bucket桶
     * @date: 2024/3/23
     * @param: bucketName
     * @return: void
     */
    @SneakyThrows
    public void createBucket(String bucketName) {
        // 调用minio客户端检查要创建的桶是否存在
        boolean result = minioClient.bucketExists(BucketExistsArgs
                .builder().bucket(bucketName).build());
        // 若桶不存在在进行创建
        if (!result) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
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
    @SneakyThrows
    public void uploadFile(InputStream file, String bucketName, String objectName) {
        // 根据bucketName、objectName上传文件到服务器
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName).object(objectName)
                .stream(file, file.available(), -1).build());
    }

    /**
     * @author: 32115
     * @description: 获取所有桶的名称
     * @date: 2024/3/23
     * @return: List<String>
     */
    @SneakyThrows
    public List<String> getAllBucket() {
        // 获取所有bucket列表
        List<Bucket> bucketList = minioClient.listBuckets();
        // 返回所有bucket的名称
        return bucketList.stream().map(Bucket::name).collect(Collectors.toList());
    }

    /**
     * @author: 32115
     * @description: 获取当前桶的所有文件
     * @date: 2024/3/23
     * @param: bucket
     * @return: List<FileInfo>
     */
    @SneakyThrows
    public List<FileInfo> getBucketFile(String bucketName) {
        // 获取所有当前桶下的文件对象
        Iterable<Result<Item>> listObjects = minioClient
                .listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
        List<FileInfo> fileInfoList = new ArrayList<>();
        // 循环文件对象列表
        for (Result<Item> listObject : listObjects) {
            // 创建FileInfo对象存储要封装的信息
            FileInfo fileInfo = new FileInfo();
            // 获取每一个Item文件对象
            Item item = listObject.get();
            // 封装文件名
            fileInfo.setFileName(item.objectName());
            // 封装文件大小
            fileInfo.setFileSize(item.size());
            fileInfo.setIsDirectory(item.isDir());
            fileInfo.setEtag(item.etag());
            fileInfoList.add(fileInfo);
        }
        return fileInfoList;
    }

    /**
     * @author: 32115
     * @description: 下载文件
     * @date: 2024/3/23
     * @param: bucketName
     * @param: objectName
     * @return: InputStream
     */
    @SneakyThrows
    public InputStream downloadFile(String bucketName, String objectName) {
        // 根据bucketName、objectName下载文件
        return minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucketName).object(objectName).build());
    }

    /**
     * @author: 32115
     * @description: 删除文件
     * @date: 2024/3/23
     * @param: buketName
     * @param: objectName
     * @return: void
     */
    @SneakyThrows
    public void deleteFile(String buketName, String objectName) {
        minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket(buketName).object(objectName).build());
    }

    /**
     * @author: 32115
     * @description: 删除桶
     * @date: 2024/3/23
     * @param: bucketName
     * @return: void
     */
    @SneakyThrows
    public void deleteBucket(String bucketName) {
        minioClient.removeBucket(RemoveBucketArgs.builder()
                .bucket(bucketName).build());
    }

    /**
     * @author: 32115
     * @description: 获取文件URL
     * @date: 2024/4/9
     * @param: bucketName
     * @param: objectName
     * @return: String
     */
    @SneakyThrows
    public String getFileUrl(String bucketName, String objectName) {
        return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket(bucketName).object(objectName).build());
    }

}
