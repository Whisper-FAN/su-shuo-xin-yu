package com.yimo.controller;

import com.yimo.common.Result;
import com.yimo.entity.Partner;
import com.yimo.service.PartnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 合作伙伴控制器
 *
 * @author yimo-team
 */
@RestController
@RequestMapping("/api/partner")
@RequiredArgsConstructor
@Tag(name = "合作伙伴管理", description = "合作伙伴的增删改查接口")
public class PartnerController {

    private final PartnerService partnerService;

    @GetMapping("/list")
    @Operation(summary = "获取启用的合作伙伴列表", description = "获取所有已启用的合作伙伴，按sortOrder排序(公开)")
    public Result<List<Partner>> listEnabled() {
        return partnerService.listEnabled();
    }

    @PostMapping
    @Operation(summary = "新增合作伙伴", description = "管理员新增合作伙伴")
    public Result<Partner> create(
            @Parameter(description = "合作伙伴信息") @RequestBody Partner partner) {
        return partnerService.create(partner);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新合作伙伴", description = "管理员更新指定合作伙伴信息")
    public Result<Partner> update(
            @Parameter(description = "合作伙伴ID") @PathVariable Long id,
            @Parameter(description = "合作伙伴信息") @RequestBody Partner partner) {
        partner.setId(id);
        return partnerService.update(partner);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除合作伙伴", description = "管理员逻辑删除指定合作伙伴")
    public Result<Void> delete(
            @Parameter(description = "合作伙伴ID") @PathVariable Long id) {
        return partnerService.delete(id);
    }
}
