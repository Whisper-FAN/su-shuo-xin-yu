package com.yimo.controller;

import com.yimo.common.Result;
import com.yimo.entity.FileStorage;
import com.yimo.service.FileService;

import java.util.Map;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件控制器
 *
 * @author yimo-team
 */
@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
@Tag(name = "文件管理", description = "文件上传与删除接口")
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    @Operation(summary = "上传文件", description = "上传图片文件到本地存储，返回文件信息")
    public Result<Map<String, Object>> upload(
            @Parameter(description = "上传的文件") @RequestParam("file") MultipartFile file) {
        return fileService.upload(file);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除文件", description = "根据文件ID删除文件记录及物理文件")
    public Result<Void> delete(
            @Parameter(description = "文件ID") @PathVariable Long id) {
        return fileService.delete(id);
    }
}
