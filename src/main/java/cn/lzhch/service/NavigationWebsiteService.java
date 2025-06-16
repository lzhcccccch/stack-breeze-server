package cn.lzhch.service;


import cn.lzhch.entity.NavigationWebsite;
import cn.lzhch.mapper.NavigationWebsiteMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
