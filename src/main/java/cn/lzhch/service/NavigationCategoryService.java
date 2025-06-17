package cn.lzhch.service;


import cn.lzhch.dto.navigation.CategoryWebsiteListResDto;
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
 * 导航分类服务
 * <p>
 * author: lzhch
 * version: v1.0
 * date: 2025/6/16 14:20
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class NavigationCategoryService extends ServiceImpl<NavigationCategoryMapper, NavigationCategory> {

    private final NavigationWebsiteMapper navigationWebsiteMapper;

    /**
     * 获取带有网站列表的导航类别
     *
     * @return List<CategoryWebsiteListResDto>
     */
    public List<CategoryWebsiteListResDto> listWithWebsites() {
        List<NavigationCategory> categoryList = super.list();

        List<NavigationWebsite> websiteList = this.navigationWebsiteMapper.selectList(Wrappers.lambdaQuery());
        Map<Long, List<NavigationWebsite>> websiteMap = Optional.ofNullable(websiteList)
                .orElse(Collections.emptyList())
                .stream()
                .collect(Collectors.groupingBy(NavigationWebsite::getCategoryId));

        return categoryList.stream()
                .map(category -> {
                    List<NavigationWebsite> websites = websiteMap.getOrDefault(category.getId(), Collections.emptyList());
                    return CategoryWebsiteListResDto.builder()
                            .id(category.getId())
                            .categoryName(category.getCategoryName())
                            .categorySort(category.getCategorySort())
                            .websiteList(websites)
                            .build();
                })
                .collect(Collectors.toList());
    }

}
