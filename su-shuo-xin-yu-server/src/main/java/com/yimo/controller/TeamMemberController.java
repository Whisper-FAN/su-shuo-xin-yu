package com.yimo.controller;

import com.yimo.common.Result;
import com.yimo.entity.TeamMember;
import com.yimo.service.TeamMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 团队成员控制器
 *
 * @author yimo-team
 */
@RestController
@RequestMapping("/api/team-member")
@RequiredArgsConstructor
@Tag(name = "团队成员管理", description = "团队成员的增删改查接口")
public class TeamMemberController {

    private final TeamMemberService teamMemberService;

    @GetMapping("/list")
    @Operation(summary = "获取团队成员列表", description = "获取所有团队成员，按sortOrder排序")
    public Result<List<TeamMember>> list() {
        return teamMemberService.listAll();
    }

    @PostMapping
    @Operation(summary = "新增团队成员", description = "管理员新增团队成员")
    public Result<TeamMember> create(
            @Parameter(description = "团队成员信息") @RequestBody TeamMember member) {
        return teamMemberService.create(member);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新团队成员", description = "管理员更新指定团队成员信息")
    public Result<TeamMember> update(
            @Parameter(description = "团队成员ID") @PathVariable Long id,
            @Parameter(description = "团队成员信息") @RequestBody TeamMember member) {
        member.setId(id);
        return teamMemberService.update(member);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除团队成员", description = "管理员逻辑删除指定团队成员")
    public Result<Void> delete(
            @Parameter(description = "团队成员ID") @PathVariable Long id) {
        return teamMemberService.delete(id);
    }
}
