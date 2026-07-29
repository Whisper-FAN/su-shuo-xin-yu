package com.yimo.utils;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.yimo.config.OssConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 阿里云OSS工具类
 *
 * @author yimo-team
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OssUtils {

    private final OssConfig ossConfig;

    /**
     * 上传文件到OSS
     *
     * @param file   文件
     * @param folder 文件夹路径
     * @return 文件URL
     */
    public String upload(MultipartFile file, String folder) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String fileName = folder + "/" + IdUtil.fastSimpleUUID() + extension;

        OSS ossClient = new OSSClientBuilder().build(
                ossConfig.getEndpoint(),
                ossConfig.getAccessKeyId(),
                ossConfig.getAccessKeySecret());

        try (InputStream inputStream = file.getInputStream()) {
            byte[] bytes = IoUtil.readBytes(inputStream);
            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    ossConfig.getBucket(), fileName,
                    new ByteArrayInputStream(bytes));
            ossClient.putObject(putObjectRequest);
        } finally {
            ossClient.shutdown();
        }

        return "https://" + ossConfig.getBucket() + "." + ossConfig.getEndpoint() + "/" + fileName;
    }
}
