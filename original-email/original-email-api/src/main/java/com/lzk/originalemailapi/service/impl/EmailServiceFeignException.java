package com.lzk.originalemailapi.service.impl;

import com.lzk.originalemailapi.base.BaseResult;
import com.lzk.originalemailapi.model.SendEmailParam;
import com.lzk.originalemailapi.service.EmailService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author
 * @module
 * @date 2022/6/28 10:11
 */
@Service
public class EmailServiceFeignException implements EmailService {
    @Override
    public BaseResult<Boolean> sendEmail(SendEmailParam param) {
        return new BaseResult<>(-10,"抱歉，该服务已暂停",null);
    }

    @Override
    public BaseResult<String> testApi() {
        return new BaseResult<>(-10,"抱歉，该服务已暂停1",null);
    }
}
