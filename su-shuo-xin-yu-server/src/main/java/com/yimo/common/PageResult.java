package com.yimo.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 分页响应结果
 *
 * @author yimo-team
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "分页响应结果")
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "总记录数", example = "100")
    private Long total;

    @Schema(description = "当前页码", example = "1")
    private Long page;

    @Schema(description = "每页大小", example = "10")
    private Long size;

    @Schema(description = "总页数", example = "10")
    private Long pages;

    @Schema(description = "数据列表")
    private List<T> records;

    public static <T> PageResult<T> of(Long total, Long page, Long size, Long pages, List<T> records) {
        return new PageResult<>(total, page, size, pages, records);
    }
}
