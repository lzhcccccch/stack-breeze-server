package cn.lzhch.entity;


import cn.lzhch.common.dto.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;

/**
 * 导航网站实体类
 * <p>
 * author: lzhch
 * version: v1.0
 * date: 2025/6/16 14:49
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NavigationWebsite extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -7612647129422224191L;

    /**
     * 主键 ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /**
     * 网站名称
     */
    private String siteName;

    /**
     * 网站地址
     */
    private String siteUrl;

    /**
     * 网站图标
     */
    private String siteIcon;

    /**
     * 网站概览
     */
    private String siteOverview;

    /**
     * 网站排序
     */
    private Integer siteSort;

    /**
     * 类别 id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long categoryId;

}