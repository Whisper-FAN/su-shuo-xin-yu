package com.yimo.service;

import com.yimo.common.Result;
import com.yimo.entity.Zodiac;

import java.util.List;
import java.util.Map;

/**
 * 生肖服务接口 - 提供生肖的增删改查、故事关联查询及浏览次数统计功能
 *
 * @author yimo-team
 */
public interface ZodiacService {

    /**
     * 根据ID查询生肖详情
     *
     * @param id 生肖ID
     * @return 生肖实体
     */
    Result<Zodiac> getById(Long id);

    /**
     * 查询所有生肖列表
     *
     * @return 所有生肖列表
     */
    Result<List<Zodiac>> listAll();

    /**
     * 查询生肖详情及其关联的故事列表
     *
     * @param id 生肖ID
     * @return 包含生肖信息和故事列表的Map
     */
    Result<Map<String, Object>> getWithStories(Long id);

    /**
     * 新增生肖
     *
     * @param zodiac 生肖实体
     * @return 新增后的生肖实体
     */
    Result<Zodiac> create(Zodiac zodiac);

    /**
     * 更新生肖信息
     *
     * @param zodiac 生肖实体（需包含ID）
     * @return 更新后的生肖实体
     */
    Result<Zodiac> update(Zodiac zodiac);

    /**
     * 删除生肖（逻辑删除）
     *
     * @param id 生肖ID
     * @return 操作结果
     */
    Result<Void> delete(Long id);

    /**
     * 增加生肖浏览次数
     *
     * @param id 生肖ID
     * @return 操作结果
     */
    Result<Void> incrementViewCount(Long id);

    /**
     * 更新生肖状态
     *
     * @param id     生肖ID
     * @param status 状态值（0-禁用 1-启用）
     * @return 操作结果
     */
    Result<Void> updateStatus(Long id, Integer status);
}
