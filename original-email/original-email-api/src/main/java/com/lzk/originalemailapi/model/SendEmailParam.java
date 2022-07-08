package com.lzk.originalemailapi.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author
 * @module
 * @date 2022/6/27 16:03
 */
@Data
public class SendEmailParam implements Serializable {

    private List<String> recipients;

    private List<String> content;
}
