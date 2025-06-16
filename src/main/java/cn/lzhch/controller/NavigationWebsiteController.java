package cn.lzhch.controller;


import cn.lzhch.common.response.Result;
import cn.lzhch.common.response.ResultHelper;
import cn.lzhch.entity.NavigationWebsite;
import cn.lzhch.service.NavigationWebsiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 导航网站控制器
 * <p>
 * author: lzhch
 * version: v1.0
 * date: 2025/6/16 15:16
 */

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
        boolean isSaved = this.navigationWebsiteService.save(navigationWebsite);

        return ResultHelper.success(isSaved);
    }

    /**
     * 删除导航网站
     */
    @PostMapping(value = "/delete")
    public Result<Boolean> delete(Long id) {
        boolean isDeleted = this.navigationWebsiteService.removeById(id);

        return ResultHelper.success(isDeleted);
    }

    /**
     * 更新导航网站
     */
    @PostMapping(value = "/update")
    public Result<Boolean> update(NavigationWebsite navigationWebsite) {
        boolean isUpdated = this.navigationWebsiteService.updateById(navigationWebsite);

        return ResultHelper.success(isUpdated);
    }

    /**
     * 获取导航网站列表
     */
    @GetMapping(value = "/list")
    public Result<List<?>> list() {
        return ResultHelper.success(this.navigationWebsiteService.list());
    }

}
