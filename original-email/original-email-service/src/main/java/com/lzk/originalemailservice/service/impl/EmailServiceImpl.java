package com.lzk.originalemailservice.service.impl;

import com.lzk.originalemailapi.base.BaseResult;
import com.lzk.originalemailapi.model.SendEmailParam;
import com.lzk.originalemailapi.service.EmailService;
import com.lzk.originalemailservice.common.status.Status;
import com.lzk.originalemailservice.common.util.EmailUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author
 * @module
 * @date 2022/6/27 15:57
 */

@RestController
public class EmailServiceImpl implements EmailService {

    @Override
    public BaseResult<Boolean> sendEmail(@RequestBody  SendEmailParam param) {
        if(param.getRecipients().isEmpty() || param.getContent().isEmpty()) return new BaseResult<>(Status.PARAMEXCEPTION.getCode(),Status.PARAMEXCEPTION.getMsg(),false);
        EmailUtil emailUtil = new EmailUtil();
        List<String> recipients = param.getRecipients();
        try {
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        for(int i=0;i<recipients.size();i++){
//            emailUtil.sendTextEmail(recipients.get(i),"test-email",param.getContent().get(i));
//        }
        return new BaseResult<>(Status.SUCCESS.getCode(),Status.SUCCESS.getMsg(),true);
    }

    @Override
    public BaseResult<String> testApi() {
        try {
            Thread.sleep(5500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        BaseResult<String> baseResult = new BaseResult<>(200,"请求成功", "哦豁");
        return baseResult;
    }
}
