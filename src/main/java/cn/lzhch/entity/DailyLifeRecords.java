package cn.lzhch.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 日常生活记录表
 * <p>
 * author: lzhch
 * version: v1.0
 * date: 2024/11/19 16:21
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyLifeRecords implements Serializable {
    @Serial
    private static final long serialVersionUID = 4027617599661307698L;

    /**
     * 主键 ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /**
     * 内容
     */
    private String content;

    /**
     * 删除标识： 0 未删除； 1 已删除
     */
    private String delFlag;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 描述
     */
    private String remark;

}
