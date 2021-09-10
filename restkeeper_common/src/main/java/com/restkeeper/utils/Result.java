package com.restkeeper.utils;

import lombok.Data;

/**
 * 返回结果通用封装
 */
@Data
public class Result {
    // 返回状态
    private int status;
    // 状态描述
    private String desc;
    // 返回数据
    private Object data;

    private String token;
}