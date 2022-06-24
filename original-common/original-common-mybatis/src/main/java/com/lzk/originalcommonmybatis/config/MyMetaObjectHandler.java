package com.lzk.originalcommonmybatis.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;


@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
//        JSONObject o = JSON.parseObject(JSONObject.toJSONString(metaObject.getOriginalObject()));
        if (metaObject.hasSetter("createTime"))
            this.setFieldValByName("createTime", System.currentTimeMillis()/1000, metaObject);
        if (metaObject.hasSetter("updateTime"))
            this.setFieldValByName("updateTime", System.currentTimeMillis()/1000, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (metaObject.hasSetter("updateTime"))
            this.setFieldValByName("updateTime", System.currentTimeMillis()/1000, metaObject);
    }
}
