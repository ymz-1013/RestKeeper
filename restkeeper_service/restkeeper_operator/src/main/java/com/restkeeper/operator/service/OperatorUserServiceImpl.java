package com.restkeeper.operator.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.restkeeper.operator.entity.OperatorUser;
import com.restkeeper.operator.mapper.OperatorUserMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.util.StringUtils;

//@Service("operatorUserService")
@Service(version = "1.0.0",protocol = "dubbo")
/**
 * dubbo中支持的协议
 * dubbo 默认
 * rmi
 * hessian
 * http
 * webservice
 * thrift
 * memcached
 * redis
 */
public class OperatorUserServiceImpl extends ServiceImpl<OperatorUserMapper, OperatorUser> implements IOperatorUserService{
    @Override
    public IPage<OperatorUser> queryPageByName(int pageNum, int pageSize, String name) {
        IPage<OperatorUser> page = new Page<>(pageNum,pageSize);
        QueryWrapper<OperatorUser> queryWrapper = null;
        if(!StringUtils.isEmpty(name)){
            queryWrapper = new QueryWrapper<>();
            queryWrapper.like("loginname", name);
        }
        return this.page(page, queryWrapper);
    }
}
