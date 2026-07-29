package com.yimo.service;

import com.yimo.common.Result;
import com.yimo.entity.FileStorage;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 文件服务接口 - 提供文件上传、删除功能
 *
 * @author yimo-team
 */
public interface FileService {

    /**
     * 上传文件（图片）
     *
     * @param file 上传的文件
     * @return 包含文件ID、原始文件名、访问URL、文件大小等信息的Map
     */
    Result<Map<String, Object>> upload(MultipartFile file);

    /**
     * 根据ID查询文件信息
     *
     * @param id 文件ID
     * @return 文件存储实体
     */
    Result<FileStorage> getById(Long id);

    /**
     * 删除文件
     *
     * @param id 文件ID
     * @return 操作结果
     */
    Result<Void> delete(Long id);
}
