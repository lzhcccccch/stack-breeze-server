package cn.lzhch.mapper;


import cn.lzhch.entity.DailyLifeRecords;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 日常生活记录表 Mapper
 * <p>
 * author: lzhch
 * version: v1.0
 * date: 2024/11/19 16:38
 */

@Mapper
public interface DailyLifeRecordsMapper extends BaseMapper<DailyLifeRecords> {

    /**
     * 分页查询指定行数据
     *
     * @param page    分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<DailyLifeRecords> selectByPage(IPage<DailyLifeRecords> page, @Param(Constants.WRAPPER) Wrapper<DailyLifeRecords> wrapper);

}
