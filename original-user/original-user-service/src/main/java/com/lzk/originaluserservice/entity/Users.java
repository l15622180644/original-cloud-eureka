package com.lzk.originaluserservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 用户表
 *
 * @author
 * @since 2022-03-20
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /** 登录账号 */
    private String loginName;

    /** 登录密码 */
    private String password;

    /** 昵称 */
    private String nickname;

    /** 头像 */
    private String avatar;

    /** 电话 */
    private String phone;

    /** 状态（0停用，1正常） */
    private Integer status;

    /** 创建时间 */
    private Long createTime;

    /** 修改时间 */
    private Long updateTime;

    /** 是否已删除（0否，1是） */
    @TableLogic
    private Integer deleted;

    @TableField(exist = false)
    private List<Long> roleIds;

//    @TableField(exist = false)
//    private List<Role> roleList;

}
