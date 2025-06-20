package cn.lzhch.controller;


import cn.lzhch.common.response.Result;
import cn.lzhch.common.response.ResultHelper;
import cn.lzhch.entity.NavigationCategory;
import cn.lzhch.service.NavigationCategoryService;
import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 导航分类控制器
 * <p>
 * author: lzhch
 * version: v1.0
 * date: 2025/6/16 14:19
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/navigationCategory")
public class NavigationCategoryController {

    private final NavigationCategoryService navigationCategoryService;

    /**
     * 保存导航类别
     */
    @PostMapping(value = "/save")
    public Result<Boolean> save(@RequestBody NavigationCategory navigationCategory) {
        log.info("保存导航类别: {}", JSON.toJSONString(navigationCategory));
        boolean isSaved = this.navigationCategoryService.save(navigationCategory);

        return ResultHelper.success(isSaved);
    }

    /**
     * 删除导航类别
     */
    @PostMapping(value = "/delete")
    public Result<Boolean> delete(Long id) {
        log.info("删除导航类别, id: {}", id);
        boolean isDeleted = this.navigationCategoryService.removeById(id);

        return ResultHelper.success(isDeleted);
    }

    /**
     * 更新导航类别
     */
    @PostMapping(value = "/update")
    public Result<Boolean> update(@RequestBody NavigationCategory navigationCategory) {
        log.info("更新导航类别: {}", JSON.toJSONString(navigationCategory));
        boolean isUpdated = this.navigationCategoryService.updateById(navigationCategory);

        return ResultHelper.success(isUpdated);
    }

    /**
     * 获取导航类别列表
     */
    @GetMapping(value = "/list")
    public Result<?> list() {
        log.info("获取导航类别列表");
        return ResultHelper.success(this.navigationCategoryService.list());
    }

}
