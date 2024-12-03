package cn.lzhch.controller;


import cn.lzhch.entity.DailyLifeRecords;
import cn.lzhch.service.IDailyLifeRecordsService;
import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 日常生活记录表 Controller
 * <p>
 * author: lzhch
 * version: v1.0
 * date: 2024/11/19 16:46
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/dailyLifeRecords")
public class DailyLifeRecordsController {

    private final IDailyLifeRecordsService dailyLifeRecordsService;

    /**
     * 查询全部数据
     */
    @RequestMapping(value = "/list")
    public List<DailyLifeRecords> list() {
        // DailyLifeRecords dailyLifeRecords = DailyLifeRecords.builder()
        //         .id(1L)
        //         .content("今天是一个阳光明媚的日子，适合出去散步。")
        //         .createTime(LocalDateTime.now())
        //         .build();
        // list.add(dailyLifeRecords);

        return dailyLifeRecordsService.list();
    }

    /**
     * 新增数据
     */
    @RequestMapping(value = "/save")
    public String save(@RequestBody DailyLifeRecords dailyLifeRecords) {
        dailyLifeRecords.setDelFlag("0");
        dailyLifeRecords.setCreateTime(LocalDateTime.now());
        boolean save = dailyLifeRecordsService.save(dailyLifeRecords);

        return JSON.toJSONString(save);
    }

    /**
     * 删除数据
     */
    @RequestMapping(value = "/remove/{id}")
    public String remove(@PathVariable(value = "id") String id) {
        boolean remove = dailyLifeRecordsService.removeById(Long.parseLong(id));

        return JSON.toJSONString(remove);
    }

    /**
     * 更新数据
     */
    @RequestMapping(value = "/update")
    public String update(@RequestBody DailyLifeRecords dailyLifeRecords) {
        dailyLifeRecords.setUpdateTime(LocalDateTime.now());
        boolean update = dailyLifeRecordsService.updateById(dailyLifeRecords);

        return JSON.toJSONString(update);
    }

}
