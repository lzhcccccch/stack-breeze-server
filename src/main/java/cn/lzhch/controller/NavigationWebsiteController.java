package cn.lzhch.controller;


import cn.lzhch.common.response.Result;
import cn.lzhch.common.response.ResultHelper;
import cn.lzhch.dto.navigation.WebsitesByCategoryResDto;
import cn.lzhch.entity.NavigationWebsite;
import cn.lzhch.service.NavigationWebsiteService;
import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 导航网站控制器
 * <p>
 * author: lzhch
 * version: v1.0
 * date: 2025/6/16 15:16
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/navigationWebsite")
public class NavigationWebsiteController {

    private final NavigationWebsiteService navigationWebsiteService;

    /**
     * 保存导航网站
     */
    @PostMapping(value = "/save")
    public Result<Boolean> save(@RequestBody NavigationWebsite navigationWebsite) {
        log.info("保存导航网站: {}", JSON.toJSONString(navigationWebsite));
        boolean isSaved = this.navigationWebsiteService.save(navigationWebsite);

        return ResultHelper.success(isSaved);
    }

    /**
     * 删除导航网站
     */
    @PostMapping(value = "/delete")
    public Result<Boolean> delete(Long id) {
        log.info("删除导航网站, id: {}", id);
        boolean isDeleted = this.navigationWebsiteService.removeById(id);

        return ResultHelper.success(isDeleted);
    }

    /**
     * 更新导航网站
     */
    @PutMapping(value = "/update")
    public Result<Boolean> update(@RequestBody NavigationWebsite navigationWebsite) {
        log.info("更新导航网站: {}", JSON.toJSONString(navigationWebsite));
        boolean isUpdated = this.navigationWebsiteService.updateById(navigationWebsite);

        return ResultHelper.success(isUpdated);
    }

    /**
     * 批量更新导航网站
     */
    @PostMapping(value = "/batchUpdate")
    public Result<Boolean> batchUpdate(@RequestBody List<NavigationWebsite> navigationWebsites) {
        log.info("批量更新导航网站: {}", JSON.toJSONString(navigationWebsites));
        boolean isUpdated = this.navigationWebsiteService.updateBatchById(navigationWebsites);

        return ResultHelper.success(isUpdated);
    }

    /**
     * 获取导航网站列表
     */
    @GetMapping(value = "/list")
    public Result<List<?>> list() {
        log.info("获取导航网站列表");
        return ResultHelper.success(this.navigationWebsiteService.list());
    }

    /**
     * 获取导航网站列表（按类别分组）
     */
    @GetMapping(value = "/listByCategory")
    public Result<List<WebsitesByCategoryResDto>> listByCategory() {
        log.info("获取导航网站列表（按类别分组）");
        List<WebsitesByCategoryResDto> list = this.navigationWebsiteService.listByCategory();
        return ResultHelper.success(list);
    }

}
