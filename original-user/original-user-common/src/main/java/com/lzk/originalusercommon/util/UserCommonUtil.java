package com.lzk.originalusercommon.util;

import com.lzk.originalusercommon.model.LoginUser;

/**
 * @author
 * @module
 * @date 2022/6/27 11:04
 */
public class UserCommonUtil {

    private static final ThreadLocal<LoginUser> THREAD_LOCAL = new ThreadLocal<>();

    private UserCommonUtil(){
    }

    public static LoginUser getLoginUser(){
        return THREAD_LOCAL.get();
    }
}
