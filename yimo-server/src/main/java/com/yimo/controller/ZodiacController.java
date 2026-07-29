package com.yimo.controller;

import com.yimo.common.Result;
import com.yimo.entity.Zodiac;
import com.yimo.service.ZodiacService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 生肖控制器
 *
 * @author yimo-team
 */
@RestController
@RequestMapping("/api/zodiac")
@RequiredArgsConstructor
@Tag(name = "生肖管理", description = "十二生肖信息的增删改查接口")
public class ZodiacController {

    private final ZodiacService zodiacService;

    @GetMapping("/list")
    @Operation(summary = "获取生肖列表", description = "获取所有生肖列表(公开)")
    public Result<List<Zodiac>> list() {
        return zodiacService.listAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取生肖详情", description = "根据ID获取生肖详情及其关联故事，并自动增加浏览次数")
    public Result<Map<String, Object>> getById(
            @Parameter(description = "生肖ID") @PathVariable Long id) {
        zodiacService.incrementViewCount(id);
        return zodiacService.getWithStories(id);
    }

    @PostMapping
    @Operation(summary = "新增生肖", description = "管理员新增生肖信息")
    public Result<Zodiac> create(
            @Parameter(description = "生肖信息") @RequestBody Zodiac zodiac) {
        return zodiacService.create(zodiac);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新生肖", description = "管理员更新指定生肖信息")
    public Result<Zodiac> update(
            @Parameter(description = "生肖ID") @PathVariable Long id,
            @Parameter(description = "生肖信息") @RequestBody Zodiac zodiac) {
        zodiac.setId(id);
        return zodiacService.update(zodiac);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除生肖", description = "管理员逻辑删除指定生肖")
    public Result<Void> delete(
            @Parameter(description = "生肖ID") @PathVariable Long id) {
        return zodiacService.delete(id);
    }
}
