package cn.lzhch.dto.navigation;

import cn.lzhch.common.dto.BaseEntity;
import cn.lzhch.entity.NavigationWebsite;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.util.List;

/**
 * 导航站类别和网站资源 DTO
 * <p>
 * author: lzhch
 * version: v1.0
 * date: 2025/6/16 17:52
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class WebsitesByCategoryResDto extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 4109527031183934244L;

    /**
     * 主键 ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /**
     * 类别名称
     */
    private String categoryName;

    /**
     * 类别排序
     */
    private Integer categorySort;

    /**
     * 导航网站列表
     */
    private List<NavigationWebsite> websiteList;

}
