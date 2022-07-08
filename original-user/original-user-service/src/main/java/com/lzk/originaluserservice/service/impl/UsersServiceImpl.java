package com.lzk.originaluserservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzk.originalemailapi.model.SendEmailParam;
import com.lzk.originalemailapi.service.EmailService;
import com.lzk.originaluserservice.common.base.BaseParam;
import com.lzk.originaluserservice.common.base.BaseResult;
import com.lzk.originaluserservice.common.exception.CustomException;
import com.lzk.originaluserservice.common.status.Status;
import com.lzk.originaluserservice.common.util.AesUtil;
import com.lzk.originaluserservice.common.util.JwtUtil;
import com.lzk.originaluserservice.common.util.TimeHelper;
import com.lzk.originaluserservice.entity.Users;
import com.lzk.originaluserservice.mapper.UsersMapper;
import com.lzk.originaluserservice.service.UsersService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author
 * @since 2022-03-20
 */

@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    @Resource
    private EmailService emailService;


    @Override
    public BaseResult getUsersPage(BaseParam param){
        Page<Users> page = lambdaQuery()
                .select(Users.class,v->!v.getColumn().equals("password"))
                .eq(param.getStatus()!=null,Users::getStatus,param.getStatus())
                .orderBy(true,param.getIsASC()!=null?param.getIsASC():false, Users::getCreateTime)
                .page(param.getPage(entityClass));
        return BaseResult.returnResult(page);
    }

    @Override
    public BaseResult<Users> getUsersOne(BaseParam param){
        if (param.getId() == null) return BaseResult.error(Status.PARAMEXCEPTION);
        Users users = getById(param.getId());
//        if(users!=null){
//            users.setPassword(null);
//            users.setRoleList(roleService.getRoleByUser(users.getId()));
//        }
        return BaseResult.returnResult(users);
    }

    @Override
    public BaseResult<Boolean> addUsers(Users users){
        if(count(Wrappers.lambdaQuery(entityClass).eq(Users::getLoginName,users.getLoginName()))>0) return BaseResult.error("账号已存在");
//        users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));
        save(users);
        List<Long> roleIds = users.getRoleIds();
//        if(roleIds!=null&&users.getId()!=null){
//            roleIds.forEach(v->{
//                if(userRoleMapper.insert(new UserRole(users.getId(),v))!=1) throw new CustomException(Status.OPFAIL);
//            });
//        }
        return BaseResult.returnResult(users.getId()!=null,users.getId());
    }

    @Override
    public BaseResult<Boolean> updateUsers(Users users){
        if (users.getId() == null) return BaseResult.error(Status.PARAMEXCEPTION);
        if(count(Wrappers.lambdaQuery(entityClass).eq(Users::getLoginName,users.getLoginName()).ne(Users::getId,users.getId()))>0) return BaseResult.error("账号已存在");
        users.setPassword(null);
        List<Long> roleIds = users.getRoleIds();
//        if(roleIds!=null){
//            userRoleMapper.delete(Wrappers.lambdaQuery(UserRole.class).eq(UserRole::getUserId,users.getId()));
//            roleIds.forEach(v->{
//                if(userRoleMapper.insert(new UserRole(users.getId(),v))!=1) throw new CustomException(Status.OPFAIL);
//            });
//        }
        boolean b = updateById(users);
        if(b) reloadUserCache(users.getId());
        return BaseResult.returnResult(b);
    }

    @Override
    public BaseResult<Boolean> delUsers(BaseParam param){
        if (param.getId() == null) return BaseResult.error(Status.PARAMEXCEPTION);
        boolean b = removeById(param.getId());
        if(b) reloadUserCache(param.getId());
        return BaseResult.returnResult(b);
    }

    @Override
    public BaseResult<Boolean> bathDelUsers(BaseParam param){
        if (param.getIds() == null || param.getIds().isEmpty()) return BaseResult.error(Status.PARAMEXCEPTION);
        boolean b = removeByIds(param.getIds());
        if(b) param.getIds().forEach(this::reloadUserCache);
        return BaseResult.returnResult(b);
    }

    @Override
    public BaseResult<String> login(BaseParam param) {
        if (StringUtils.isBlank(param.getLoginName()) || StringUtils.isBlank(param.getPassword()))
            return BaseResult.error(Status.LOGINFAILCAUSEPWD);
        Users users = getOne(Wrappers.lambdaQuery(Users.class).eq(Users::getLoginName, param.getLoginName()).eq(Users::getPassword, param.getPassword()));
        if(users==null) return BaseResult.error(Status.LOGINFAILCAUSEPWD);
        String enCodeToken = AesUtil.enCode(users.getId().toString());
        redisTemplate.opsForValue().set(enCodeToken,JSON.toJSONString(users),30,TimeUnit.MINUTES);
        return BaseResult.returnResult(Status.LOGINSUCCESS,JwtUtil.createToken(enCodeToken));
    }

    @Override
    public BaseResult<Boolean> resetPassword() {
        /*if (param.getId() == null) return BaseResult.error(Status.PARAMEXCEPTION);
//        Users users1 = getById(param.getId());
//        if(!bCryptPasswordEncoder.matches(param.getPassword(),users1.getPassword())) return BaseResult.error("原密码错误");
        Users users = new Users();
        users.setId(param.getId());
        users.setPassword(bCryptPasswordEncoder.encode(param.getNewPassword()));
        return BaseResult.returnResult(updateById(users));*/
        return null;
    }

    @Override
    public void reloadUserCache(Long userId) {
        Set<String> keys = redisTemplate.keys(AesUtil.enCode(userId.toString()) + "-*");
        Users users = getById(userId);
//        if(users!=null) users.setRoleList(roleService.getRoleByUser(users.getId()));
        keys.forEach(key->{
            Long expire = redisTemplate.getExpire(key);
            if(expire>0){
                String s = (String)redisTemplate.opsForValue().get(key);
                Users user = JSONObject.parseObject(s, Users.class);
                if(users==null || users.getStatus()==0) {
                    redisTemplate.delete(key);
                    return;
                }
                redisTemplate.opsForValue().set(key,JSON.toJSONString(user),expire/60,TimeUnit.MINUTES);
            }
        });
    }

    @Override
    public BaseResult<Boolean> sendEmailToSomeone(SendEmailParam param) {
        com.lzk.originalemailapi.base.BaseResult<Boolean> result = emailService.sendEmail(param);
        BaseResult baseResult = new BaseResult();
        BeanUtils.copyProperties(result,baseResult);
        return baseResult;
    }


}
