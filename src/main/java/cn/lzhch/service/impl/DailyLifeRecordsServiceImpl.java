package cn.lzhch.service.impl;


import cn.lzhch.entity.DailyLifeRecords;
import cn.lzhch.mapper.DailyLifeRecordsMapper;
import cn.lzhch.service.IDailyLifeRecordsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 日常生活记录表 ServiceImpl
 * <p>
 * author: lzhch
 * version: v1.0
 * date: 2024/11/19 16:45
 */

@Slf4j
@Service
public class DailyLifeRecordsServiceImpl extends ServiceImpl<DailyLifeRecordsMapper, DailyLifeRecords> implements IDailyLifeRecordsService {
}
