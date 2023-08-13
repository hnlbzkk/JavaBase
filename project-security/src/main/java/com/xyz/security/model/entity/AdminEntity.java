package com.xyz.security.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author ZKKzs
 * @since 2023-08-13
 */
@TableName("db_admin")
public class AdminEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户等级
     */
    private String adminLevel;

    /**
     * 权限升级时间
     */
    private LocalDateTime permissionTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(String adminLevel) {
        this.adminLevel = adminLevel;
    }

    public LocalDateTime getPermissionTime() {
        return permissionTime;
    }

    public void setPermissionTime(LocalDateTime permissionTime) {
        this.permissionTime = permissionTime;
    }

    @Override
    public String toString() {
        return "DbAdmin{" +
        "id = " + id +
        ", userId = " + userId +
        ", adminLevel = " + adminLevel +
        ", permissionTime = " + permissionTime +
        "}";
    }
}
