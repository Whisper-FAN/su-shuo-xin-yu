package com.yimo.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yimo.common.PageResult;

import java.util.List;

/**
 * MyBatis Plus 分页结果转换工具
 *
 * @author yimo-team
 */
public class MetaGenerator {

    /**
     * 将 MyBatis Plus 的 IPage 转为通用 PageResult
     */
    public static <T> PageResult<T> fromPage(IPage<T> page) {
        return PageResult.of(
                page.getTotal(),
                page.getCurrent(),
                page.getSize(),
                page.getPages(),
                page.getRecords()
        );
    }

    /**
     * 手动构建 PageResult
     */
    public static <T> PageResult<T> buildPage(long total, long page, long size, List<T> records) {
        long pages = total % size == 0 ? total / size : total / size + 1;
        return PageResult.of(total, page, size, pages, records);
    }
}
