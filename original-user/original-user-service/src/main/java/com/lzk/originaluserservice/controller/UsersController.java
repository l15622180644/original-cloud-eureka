package com.lzk.originaluserservice.controller;


import com.lzk.originalemailapi.model.SendEmailParam;
import com.lzk.originalemailapi.service.EmailService;
import com.lzk.originaluserservice.common.base.BaseParam;
import com.lzk.originaluserservice.common.base.BaseResult;
import com.lzk.originaluserservice.entity.Users;
import com.lzk.originaluserservice.service.UsersService;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统用户
 *
 * @author
 * @since 2022-03-20
 */

@RestController
@RequestMapping("/userService")
public class UsersController {

    @Resource
    private UsersService usersService;
    @Resource
    private EmailService emailService;

    /**
     * 查询列表
     */
    @PostMapping("/getUsersPage")
    public BaseResult<List<Users>> getPage(@RequestBody BaseParam param){
        return usersService.getUsersPage(param);
    }

    /**
     * 获取详情
     */
    @PostMapping("/getUsersOne")
    public BaseResult<Users> getOne(@RequestBody BaseParam param){
        return usersService.getUsersOne(param);
    }

    /**
     * 新增
     */
    @PostMapping("/addUsers")
    @Transactional
    public BaseResult<Boolean> add(@RequestBody Users users){
        return usersService.addUsers(users);
    }

    /**
     * 修改
     */
    @PostMapping("/updateUsers")
    @Transactional
    public BaseResult<Boolean> update(@RequestBody Users users){
        return usersService.updateUsers(users);
    }

    /**
     * 删除
     */
    @PostMapping("/delUsers")
    @Transactional
    public BaseResult<Boolean> del(@RequestBody BaseParam param){
        return usersService.delUsers(param);
    }

    /**
     * 批量删除
     */
    @PostMapping("/bathDelUsers")
    @Transactional
    public BaseResult<Boolean> bathDel(@RequestBody BaseParam param){
        return usersService.bathDelUsers(param);
    }

    /**
     * 登录
     * @param param
     * @return
     */
    @PostMapping("/login")
    public BaseResult<String> login(@RequestBody @Validated BaseParam param){
        return usersService.login(param);
    }


    @PostMapping("/sendEmailToSomeone")
    public BaseResult<Boolean> sendEmailToSomeone(@RequestBody SendEmailParam param){
        return usersService.sendEmailToSomeone(param);
    }

    @GetMapping("/testApi")
    public BaseResult<String> testApi(){
        com.lzk.originalemailapi.base.BaseResult<String> result = emailService.testApi();
        BaseResult<String> baseResult = new BaseResult<>();
        BeanUtils.copyProperties(result,baseResult);
        return baseResult;
    }

}
