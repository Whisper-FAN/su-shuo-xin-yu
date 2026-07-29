package com.yimo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文件存储实体
 *
 * @author yimo-team
 */
@Data
@TableName("file_storage")
public class FileStorage implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String originalName;

    private String fileUrl;

    private String fileType;

    private Long fileSize;

    private String mimeType;

    private Integer width;

    private Integer height;

    private Long uploadBy;

    private Integer deleted;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
