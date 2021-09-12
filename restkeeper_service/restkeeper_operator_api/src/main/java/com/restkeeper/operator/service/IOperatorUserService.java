package com.restkeeper.operator.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.restkeeper.operator.entity.OperatorUser;

public interface IOperatorUserService extends IService<OperatorUser> {
    IPage<OperatorUser> queryPageByName(int pageNum, int pageSize, String name);

}
