package com.yimo.controller;

import com.yimo.common.Result;
import com.yimo.entity.ZodiacStory;
import com.yimo.service.ZodiacStoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 生肖故事控制器
 *
 * @author yimo-team
 */
@RestController
@RequestMapping("/api/story")
@RequiredArgsConstructor
@Tag(name = "生肖故事管理", description = "生肖故事的增删改查接口")
public class ZodiacStoryController {

    private final ZodiacStoryService zodiacStoryService;

    @GetMapping("/list")
    @Operation(summary = "根据生肖ID获取故事列表", description = "根据生肖ID获取关联的故事列表(公开)")
    public Result<List<ZodiacStory>> list(
            @Parameter(description = "生肖ID") @RequestParam Long zodiacId) {
        return zodiacStoryService.listByZodiacId(zodiacId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取故事详情", description = "根据ID获取生肖故事详情")
    public Result<ZodiacStory> getById(
            @Parameter(description = "故事ID") @PathVariable Long id) {
        return zodiacStoryService.getById(id);
    }

    @PostMapping
    @Operation(summary = "新增生肖故事", description = "管理员新增生肖故事")
    public Result<ZodiacStory> create(
            @Parameter(description = "故事信息") @RequestBody ZodiacStory story) {
        return zodiacStoryService.create(story);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新生肖故事", description = "管理员更新指定生肖故事")
    public Result<ZodiacStory> update(
            @Parameter(description = "故事ID") @PathVariable Long id,
            @Parameter(description = "故事信息") @RequestBody ZodiacStory story) {
        story.setId(id);
        return zodiacStoryService.update(story);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除生肖故事", description = "管理员逻辑删除指定生肖故事")
    public Result<Void> delete(
            @Parameter(description = "故事ID") @PathVariable Long id) {
        return zodiacStoryService.delete(id);
    }
}
