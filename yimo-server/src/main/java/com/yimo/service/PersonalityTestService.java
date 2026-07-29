package com.yimo.service;

import com.yimo.common.Result;

import java.util.List;
import java.util.Map;

/**
 * 性格测试服务接口 - 提供测试题目获取、答案提交、结果查询及测试历史功能
 *
 * @author yimo-team
 */
public interface PersonalityTestService {

    /**
     * 获取所有测试题目及其选项
     *
     * @return 包含题目信息及对应选项列表的Map列表
     */
    Result<List<Map<String, Object>>> getQuestions();

    /**
     * 提交测试答案并计算结果
     *
     * @param answers      用户答案（题目ID -> 选项分数）
     * @param testDuration 测试耗时（秒）
     * @param userId       用户ID（可为null，匿名用户）
     * @return 包含匹配生肖、性格分析、维度分数等测试结果的Map
     */
    Result<Map<String, Object>> submitTest(Map<Long, Integer> answers, Integer testDuration, Long userId);

    /**
     * 根据记录ID获取测试结果详情
     *
     * @param recordId 测试记录ID
     * @return 包含生肖信息、性格分析、维度分数等详细结果的Map
     */
    Result<Map<String, Object>> getResult(Long recordId);

    /**
     * 获取用户的测试历史记录
     *
     * @param userId 用户ID（可为null）
     * @return 测试历史记录列表，每项包含测试ID、生肖名称、生肖图片、测试时间
     */
    Result<List<Map<String, Object>>> getTestHistory(Long userId);
}
