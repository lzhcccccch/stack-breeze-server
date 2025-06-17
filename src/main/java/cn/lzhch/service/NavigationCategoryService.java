package cn.lzhch.service;


import cn.lzhch.entity.NavigationCategory;
import cn.lzhch.mapper.NavigationCategoryMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

}
