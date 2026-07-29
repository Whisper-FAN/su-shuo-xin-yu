package com.yimo.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimo.common.PageRequest;
import com.yimo.common.PageResult;
import com.yimo.common.Result;
import com.yimo.entity.TeamMember;
import com.yimo.exception.BusinessException;
import com.yimo.mapper.TeamMemberMapper;
import com.yimo.service.TeamMemberService;
import com.yimo.utils.MetaGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 团队成员服务实现
 *
 * @author yimo-team
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TeamMemberServiceImpl implements TeamMemberService {

    private final TeamMemberMapper teamMemberMapper;

    @Override
    public Result<TeamMember> getById(Long id) {
        TeamMember member = teamMemberMapper.selectById(id);
        if (member == null) {
            throw new BusinessException(404, "团队成员不存在");
        }
        return Result.success(member);
    }

    @Override
    public Result<PageResult<TeamMember>> list(PageRequest request) {
        Page<TeamMember> page = new Page<>(request.getPage(), request.getSize());
        LambdaQueryWrapper<TeamMember> wrapper = new LambdaQueryWrapper<>();

        if (StrUtil.isNotBlank(request.getKeyword())) {
            wrapper.and(w -> w.like(TeamMember::getName, request.getKeyword())
                    .or()
                    .like(TeamMember::getTitle, request.getKeyword()));
        }
        if (request.getStatus() != null) {
            wrapper.eq(TeamMember::getStatus, request.getStatus());
        }
        wrapper.orderByAsc(TeamMember::getSortOrder);

        Page<TeamMember> result = teamMemberMapper.selectPage(page, wrapper);
        return Result.success(MetaGenerator.fromPage(result));
    }

    @Override
    public Result<List<TeamMember>> listAll() {
        List<TeamMember> members = teamMemberMapper.selectList(
                new LambdaQueryWrapper<TeamMember>()
                        .orderByAsc(TeamMember::getSortOrder));
        return Result.success(members);
    }

    @Override
    public Result<List<TeamMember>> listCore() {
        List<TeamMember> members = teamMemberMapper.selectList(
                new LambdaQueryWrapper<TeamMember>()
                        .eq(TeamMember::getIsCore, 1)
                        .eq(TeamMember::getStatus, 1)
                        .orderByAsc(TeamMember::getSortOrder));
        return Result.success(members);
    }

    @Override
    public Result<TeamMember> create(TeamMember member) {
        teamMemberMapper.insert(member);
        log.info("创建团队成员成功: id={}, name={}", member.getId(), member.getName());
        return Result.success(member);
    }

    @Override
    public Result<TeamMember> update(TeamMember member) {
        TeamMember existing = teamMemberMapper.selectById(member.getId());
        if (existing == null) {
            throw new BusinessException(404, "团队成员不存在");
        }
        teamMemberMapper.updateById(member);
        log.info("更新团队成员成功: id={}", member.getId());
        return Result.success(member);
    }

    @Override
    public Result<Void> delete(Long id) {
        TeamMember existing = teamMemberMapper.selectById(id);
        if (existing == null) {
            throw new BusinessException(404, "团队成员不存在");
        }
        teamMemberMapper.deleteById(id);
        log.info("删除团队成员成功: id={}", id);
        return Result.success();
    }

    @Override
    public Result<Void> updateStatus(Long id, Integer status) {
        TeamMember member = teamMemberMapper.selectById(id);
        if (member == null) {
            throw new BusinessException(404, "团队成员不存在");
        }
        member.setStatus(status);
        teamMemberMapper.updateById(member);
        log.info("更新团队成员状态成功: id={}, status={}", id, status);
        return Result.success();
    }
}
