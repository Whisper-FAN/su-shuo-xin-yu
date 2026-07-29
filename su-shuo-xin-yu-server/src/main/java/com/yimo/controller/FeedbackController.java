package com.yimo.controller;

import com.yimo.common.PageRequest;
import com.yimo.common.PageResult;
import com.yimo.common.Result;
import com.yimo.entity.Feedback;
import com.yimo.service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 反馈控制器
 *
 * @author yimo-team
 */
@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
@Tag(name = "反馈管理", description = "用户反馈的提交与处理接口")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping
    @Operation(summary = "提交反馈", description = "用户提交意见反馈(公开)")
    public Result<Feedback> submit(
            @Parameter(description = "反馈信息") @RequestBody Feedback feedback,
            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if (userId != null) {
            feedback.setUserId(userId);
        }
        return feedbackService.create(feedback);
    }

    @GetMapping("/list")
    @Operation(summary = "分页查询反馈列表", description = "管理员分页查询所有反馈")
    public Result<?> list(@Parameter(description = "当前页码") @RequestParam(required = false, defaultValue = "1") Long page,
                          @Parameter(description = "每页大小") @RequestParam(required = false, defaultValue = "10") Long size,
                          @Parameter(description = "是否已处理") @RequestParam(required = false) Integer isHandled) {
        PageRequest request = new PageRequest();
        request.setPage(page);
        request.setSize(size);
        if (isHandled != null) request.setStatus(isHandled);
        return feedbackService.list(request);
    }

    @PutMapping("/{id}/handle")
    @Operation(summary = "标记反馈为已处理", description = "管理员处理反馈，填写处理备注")
    public Result<Void> handle(@Parameter(description = "反馈ID") @PathVariable Long id,
                                @Parameter(description = "处理备注") @RequestParam(required = false) String handleNote) {
        return feedbackService.markAsHandled(id, handleNote != null ? handleNote : "");
    }
}
