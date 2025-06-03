package cn.lzhch.entity;

import cn.lzhch.common.dto.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;

/**
 * 用户实体类
 * <p>
 * 设计说明：
 * 1. 使用MyBatis-Plus的BaseMapper，简化CRUD操作
 * 2. 采用雪花算法生成ID，保证分布式环境下的唯一性
 * 3. 密码字段使用@JsonIgnore，防止序列化时泄露
 * 4. 软删除设计，使用del_flag标识，便于数据恢复和审计
 * 5. 包含创建时间、更新时间等审计字段，便于追踪数据变更
 * <p>
 * author: lzhch
 * version: v1.0
 * date: 2024/12/19
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键 ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /**
     * 用户名
     * 数据库层面有唯一索引约束
     */
    private String username;

    /**
     * 邮箱
     * 数据库层面有唯一索引约束，确保一个邮箱只能注册一个账户
     */
    private String email;

    /**
     * 密码（BCrypt加密后的哈希值）
     */
    @JsonIgnore
    private String password;

}
