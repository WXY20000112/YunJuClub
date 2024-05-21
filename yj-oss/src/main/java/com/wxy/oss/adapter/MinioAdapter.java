package com.wxy.oss.adapter;

import com.wxy.oss.entity.FileInfo;
import com.wxy.oss.utils.MinioUtil;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @program: YunJuClub
 * @description: minio对象存储服务工具
 * @author: 32115
 * @create: 2024-03-23 14:09
 */
public class MinioAdapter implements StorageAdapter{

    @Resource
    private MinioUtil minioUtil;

    /**
     * @author: 32115
     * @description: 创建bucket桶
     * @date: 2024/3/23
     * @param: bucketName
     * @return: void
     */
    @Override
    @SneakyThrows
    public void createBucket(String bucketName) {
        minioUtil.createBucket(bucketName);
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
    @Override
    @SneakyThrows
    public String uploadFile(MultipartFile file, String bucketName) {
        // 获取文件的原名称
        String originalFilename = file.getOriginalFilename();

        // 裁剪名称，获取文件的后缀名“.jpg”
        String substring = null;
        if (originalFilename != null) {
            substring = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        // 保证文件名唯一，去掉uuid中的'-'
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String fileName = uuid + substring;

        // 把文件按日期分类，构建日期路径：avatar/2019/02/26/文件名
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String datePath = df.format(date);

        // 拼接
        fileName = datePath + "/" + fileName;

        // 上传文件
        minioUtil.uploadFile(file.getInputStream(), bucketName, fileName);

        // 返回上传后的文件路径
        return minioUtil.getFileUrl(bucketName, fileName).split("\\?")[0];
    }

    /**
     * @author: 32115
     * @description: 获取所有桶的名称
     * @date: 2024/3/23
     * @return: List<String>
     */
    @Override
    @SneakyThrows
    public List<String> getAllBucket() {
        return minioUtil.getAllBucket();
    }

    /**
     * @author: 32115
     * @description: 获取当前桶的所有文件
     * @date: 2024/3/23
     * @param: bucket
     * @return: List<FileInfo>
     */
    @Override
    @SneakyThrows
    public List<FileInfo> getBucketFile(String bucketName) {
        return minioUtil.getBucketFile(bucketName);
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
    @SneakyThrows
    public InputStream downloadFile(String bucketName, String objectName) {
        return minioUtil.downloadFile(bucketName, objectName);
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
    @SneakyThrows
    public void deleteFile(String buketName, String objectName) {
        minioUtil.deleteFile(buketName, objectName);
    }

    /**
     * @author: 32115
     * @description: 获取文件url
     * @date: 2024/4/9
     * @param: bucket
     * @param: objectName
     * @return: String
     */
    @Override
    public String getUrl(String bucket, String objectName) {
        return minioUtil.getFileUrl(bucket, objectName).split("\\?")[0];
    }

    /**
     * @author: 32115
     * @description: 删除桶
     * @date: 2024/3/23
     * @param: bucketName
     * @return: void
     */
    @Override
    @SneakyThrows
    public void deleteBucket(String bucketName) {
        minioUtil.deleteBucket(bucketName);
    }
}
