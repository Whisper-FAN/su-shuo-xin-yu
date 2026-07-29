package com.yimo.controller;

import com.yimo.common.Result;
import com.yimo.service.PersonalityTestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 性格测试控制器
 *
 * @author yimo-team
 */
@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
@Tag(name = "性格测试", description = "十二生肖性格测试相关接口")
public class PersonalityTestController {

    private final PersonalityTestService personalityTestService;

    @GetMapping("/questions")
    @Operation(summary = "获取所有题目", description = "获取24道性格测试题目及选项")
    public Result<List<Map<String, Object>>> getQuestions() {
        return personalityTestService.getQuestions();
    }

    @PostMapping("/submit")
    @Operation(summary = "提交答案", description = "提交测试答案，返回匹配的生肖结果")
    public Result<Map<String, Object>> submitTest(
            @Parameter(description = "答案Map: questionId -> optionScore") @RequestBody Map<Long, Integer> answers,
            @Parameter(description = "测试耗时(秒)") @RequestParam(required = false, defaultValue = "0") Integer testDuration,
            @Parameter(description = "用户ID(可选)") @RequestParam(required = false) Long userId) {
        return personalityTestService.submitTest(answers, testDuration, userId);
    }

    @GetMapping("/result/{recordId}")
    @Operation(summary = "获取测试结果", description = "根据测试记录ID获取历史结果")
    public Result<Map<String, Object>> getResult(
            @Parameter(description = "测试记录ID") @PathVariable Long recordId) {
        return personalityTestService.getResult(recordId);
    }

    @GetMapping("/history")
    @Operation(summary = "获取测试历史", description = "获取用户的测试历史记录列表")
    public Result<List<Map<String, Object>>> getTestHistory(
            @Parameter(description = "用户ID(可选)") @RequestParam(required = false) Long userId) {
        return personalityTestService.getTestHistory(userId);
    }
}
