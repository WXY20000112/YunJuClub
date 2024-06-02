package com.wxy.oss.controller;

import com.wxy.oss.service.FileService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: YunJuClub
 * @description: minio oss controller类
 * @author: 32115
 * @create: 2024-03-23 11:13
 */

@RestController
public class FileController {

    @Resource
    private FileService fileService;

    /**
     * @author: 32115
     * @description: 上传文件
     * @date: 2024/4/9
     * @param: file
     * @param: bucket
     * @param: objectName
     * @return: String
     */
    @RequestMapping("/upload")
    public String upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("bucket") String bucket) {
        return fileService.upload(file, bucket);
    }

    @RequestMapping("/getUrl")
    public String getUrl(
            @RequestParam("bucketName") String bucketName,
            @RequestParam("objectName") String objectName) {
        return fileService.getUrl(bucketName, objectName);
    }
}
