package com.lzk.originaluserservice.common.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class BaseParam implements Serializable {

    private Integer page;

    private Integer limit;

    private Long id;

    private List<Long> ids;

    private Integer type;

    private List types;

    private Integer status;

    private Boolean isASC;

    private String name;

    private String phone;

    private String loginName;

    private String password;

    public <T> Page<T> getPage(Class<T> t) {
        return new Page<T>(page != null ? page : 1, limit != null ? limit : 15);
    }

}
