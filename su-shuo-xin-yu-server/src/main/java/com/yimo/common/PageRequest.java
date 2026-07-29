package com.yimo.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 通用分页请求
 *
 * @author yimo-team
 */
@Data
@Schema(description = "分页请求参数")
public class PageRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "当前页码", example = "1")
    private Long page = 1L;

    @Schema(description = "每页大小", example = "10")
    private Long size = 10L;

    @Schema(description = "排序字段")
    private String sortField;

    @Schema(description = "排序方向: asc/desc", example = "desc")
    private String sortOrder = "desc";

    @Schema(description = "搜索关键词")
    private String keyword;

    @Schema(description = "分类ID")
    private Long categoryId;

    @Schema(description = "状态筛选")
    private Integer status;
}
