package cn.lzhch.service;


import cn.hutool.core.collection.CollUtil;
import cn.lzhch.dto.navigation.WebsitesByCategoryResDto;
import cn.lzhch.entity.NavigationCategory;
import cn.lzhch.entity.NavigationWebsite;
import cn.lzhch.mapper.NavigationCategoryMapper;
import cn.lzhch.mapper.NavigationWebsiteMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 导航网站服务类
 * <p>
 * author: lzhch
 * version: v1.0
 * date: 2025/6/16 15:16
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class NavigationWebsiteService extends ServiceImpl<NavigationWebsiteMapper, NavigationWebsite> {

    private final NavigationCategoryMapper navigationCategoryMapper;

    /**
     * 根据分类获取网站列表
     *
     * @return 分类下的网站列表
     */
    public List<WebsitesByCategoryResDto> listByCategory() {
        // 获取所有分类
        List<NavigationCategory> categoryList = this.navigationCategoryMapper.selectList(Wrappers.lambdaQuery());
        if (CollUtil.isEmpty(categoryList)) {
            return Collections.emptyList();
        }

        List<NavigationWebsite> websiteList = super.list();
        Map<Long, List<NavigationWebsite>> websiteMap = Optional.ofNullable(websiteList)
                .orElse(Collections.emptyList())
                .stream()
                .collect(Collectors.groupingBy(NavigationWebsite::getCategoryId));

        return categoryList.stream()
                .map(category -> {
                    List<NavigationWebsite> websites = websiteMap.getOrDefault(category.getId(), Collections.emptyList());
                    return WebsitesByCategoryResDto.builder()
                            .id(category.getId())
                            .categoryName(category.getCategoryName())
                            .categorySort(category.getCategorySort())
                            .websiteList(websites)
                            .build();
                })
                .collect(Collectors.toList());
    }


}
