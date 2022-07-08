package com.lzk.originalemailapi.service;

import com.lzk.originalemailapi.base.BaseResult;
import com.lzk.originalemailapi.model.SendEmailParam;
import com.lzk.originalemailapi.service.impl.EmailServiceFeignException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 * @module
 * @date 2022/6/27 16:57
 */
@FeignClient(
        value = "original-email-service",
        fallback = EmailServiceFeignException.class
//        url = "localhost:10002"
)
public interface EmailService {

    @PostMapping("/api/email-service/sendEmail")
    BaseResult<Boolean> sendEmail(@RequestBody SendEmailParam param);

    @GetMapping("/test")
    BaseResult<String> testApi();
}
