package com.lzk.originaluserservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzk.originalemailapi.model.SendEmailParam;
import com.lzk.originaluserservice.common.base.BaseParam;
import com.lzk.originaluserservice.common.base.BaseResult;
import com.lzk.originaluserservice.entity.Users;

/**
 *
 * @author
 * @since 2022-03-20
 */

public interface UsersService extends IService<Users> {

    BaseResult getUsersPage(BaseParam param);

    BaseResult<Users> getUsersOne(BaseParam param);

    BaseResult<Boolean> addUsers(Users users);

    BaseResult<Boolean> updateUsers(Users users);

    BaseResult<Boolean> delUsers(BaseParam param);

    BaseResult<Boolean> bathDelUsers(BaseParam param);

    BaseResult<String> login(BaseParam param);

    BaseResult<Boolean> resetPassword();

    void reloadUserCache(Long userId);

    BaseResult<Boolean> sendEmailToSomeone(SendEmailParam param);

}
