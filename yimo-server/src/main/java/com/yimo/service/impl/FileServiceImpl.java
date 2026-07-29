package com.yimo.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.yimo.common.Result;
import com.yimo.entity.FileStorage;
import com.yimo.exception.BusinessException;
import com.yimo.mapper.FileStorageMapper;
import com.yimo.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 文件服务实现
 *
 * @author yimo-team
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileStorageMapper fileStorageMapper;

    private static final String UPLOAD_DIR = "./uploads/";
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("jpg", "jpeg", "png", "gif", "webp", "svg", "bmp");
    private static final long MAX_FILE_SIZE = 50 * 1024 * 1024; // 50MB

    @Override
    public Result<Map<String, Object>> upload(MultipartFile file) {
        if (file.isEmpty()) {
            throw new BusinessException(400, "文件不能为空");
        }

        String originalName = file.getOriginalFilename();
        String extension = FileUtil.extName(originalName != null ? originalName : "unknown");
        if (!ALLOWED_EXTENSIONS.contains(extension.toLowerCase())) {
            throw new BusinessException(400, "不支持的文件格式: " + extension);
        }
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new BusinessException(400, "文件大小超过50MB限制");
        }

        // Ensure upload directory exists
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Generate unique filename
        String fileName = IdUtil.fastSimpleUUID() + "." + extension;
        String filePath = UPLOAD_DIR + fileName;

        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            log.error("文件保存失败", e);
            throw new BusinessException(500, "文件保存失败");
        }

        // Save file record to database
        FileStorage storage = new FileStorage();
        storage.setOriginalName(originalName);
        storage.setFileUrl("/uploads/" + fileName);
        storage.setFileType(extension.toLowerCase());
        storage.setFileSize(file.getSize());
        storage.setMimeType(file.getContentType());
        fileStorageMapper.insert(storage);

        log.info("文件上传成功: id={}, originalName={}, url={}", storage.getId(), originalName, storage.getFileUrl());

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("id", storage.getId());
        result.put("url", storage.getFileUrl());
        result.put("originalName", originalName);
        result.put("fileSize", file.getSize());
        result.put("mimeType", file.getContentType());

        return Result.success(result);
    }

    @Override
    public Result<FileStorage> getById(Long id) {
        FileStorage storage = fileStorageMapper.selectById(id);
        if (storage == null) {
            throw new BusinessException(404, "文件不存在");
        }
        return Result.success(storage);
    }

    @Override
    public Result<Void> delete(Long id) {
        FileStorage storage = fileStorageMapper.selectById(id);
        if (storage == null) {
            throw new BusinessException(404, "文件不存在");
        }
        // Delete physical file
        try {
            String filePath = "." + storage.getFileUrl();
            FileUtil.del(filePath);
        } catch (Exception e) {
            log.warn("物理文件删除失败: {}", storage.getFileUrl());
        }
        // Delete database record
        fileStorageMapper.deleteById(id);
        log.info("文件删除成功: id={}", id);
        return Result.success();
    }
}
