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
 * 导航类别实体类
 * <p>
 * author: lzhch
 * version: v1.0
 * date: 2025/6/16 11:34
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NavigationCategory extends BaseEntity {
    @Serial
    private static final long serialVersionUID = -3063328518951375569L;

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

}