package com.yimo.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimo.common.PageRequest;
import com.yimo.common.PageResult;
import com.yimo.common.Result;
import com.yimo.entity.Feedback;
import com.yimo.exception.BusinessException;
import com.yimo.mapper.FeedbackMapper;
import com.yimo.service.FeedbackService;
import com.yimo.utils.MetaGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 反馈服务实现
 *
 * @author yimo-team
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackMapper feedbackMapper;

    @Override
    public Result<Feedback> getById(Long id) {
        Feedback feedback = feedbackMapper.selectById(id);
        if (feedback == null) {
            throw new BusinessException(404, "反馈不存在");
        }
        return Result.success(feedback);
    }

    @Override
    public Result<PageResult<Feedback>> list(PageRequest request) {
        Page<Feedback> page = new Page<>(request.getPage(), request.getSize());
        LambdaQueryWrapper<Feedback> wrapper = new LambdaQueryWrapper<>();

        // Keyword search on content
        if (StrUtil.isNotBlank(request.getKeyword())) {
            wrapper.like(Feedback::getContent, request.getKeyword());
        }

        // isHandled filter (passed as status)
        if (request.getStatus() != null) {
            wrapper.eq(Feedback::getIsHandled, request.getStatus());
        }

        wrapper.orderByDesc(Feedback::getCreateTime);

        Page<Feedback> result = feedbackMapper.selectPage(page, wrapper);
        return Result.success(MetaGenerator.fromPage(result));
    }

    @Override
    public Result<Feedback> create(Feedback feedback) {
        if (feedback.getIsHandled() == null) {
            feedback.setIsHandled(0);
        }
        feedbackMapper.insert(feedback);
        log.info("创建反馈成功: id={}", feedback.getId());
        return Result.success(feedback);
    }

    @Override
    public Result<Void> markAsHandled(Long id, String handleNote) {
        Feedback feedback = feedbackMapper.selectById(id);
        if (feedback == null) {
            throw new BusinessException(404, "反馈不存在");
        }
        feedback.setIsHandled(1);
        feedback.setHandleNote(handleNote);
        feedbackMapper.updateById(feedback);
        log.info("标记反馈已处理: id={}", id);
        return Result.success();
    }

    @Override
    public Result<Void> markAsHandledBatch(List<Long> ids, String handleNote) {
        if (ids == null || ids.isEmpty()) {
            throw new BusinessException(400, "请选择要处理的反馈");
        }
        for (Long id : ids) {
            Feedback feedback = feedbackMapper.selectById(id);
            if (feedback != null) {
                feedback.setIsHandled(1);
                feedback.setHandleNote(handleNote);
                feedbackMapper.updateById(feedback);
            }
        }
        log.info("批量标记反馈已处理: ids={}", ids);
        return Result.success();
    }

    @Override
    public Result<Void> delete(Long id) {
        Feedback feedback = feedbackMapper.selectById(id);
        if (feedback == null) {
            throw new BusinessException(404, "反馈不存在");
        }
        feedbackMapper.deleteById(id);
        log.info("删除反馈成功: id={}", id);
        return Result.success();
    }
}
