package com.lzk.originalusercommon.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author
 * @module
 * @date 2022/6/27 10:11
 */
@Data
public class LoginUser implements Serializable {

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
    private Integer deleted;

    private List<Long> roleIds;

//    @TableField(exist = false)
//    private List<Role> roleList;
}
