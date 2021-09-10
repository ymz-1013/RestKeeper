package com.restkeeper.operator;

import com.restkeeper.operator.entity.OperatorUser;
import com.restkeeper.operator.service.IOperatorUserService;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OperatorUserTest {

    @Reference(version = "1.0.0",check = false)
    private IOperatorUserService operatorUserService;

    //新增用户
    @Test
    @Rollback(false)
    public void addTest(){
        OperatorUser operatorUser = new OperatorUser();
        operatorUser.setLoginname("wangwu");
        String crypt = Md5Crypt.md5Crypt("123456".getBytes());
        operatorUser.setLoginpass(crypt);
        operatorUserService.save(operatorUser);
    }
}
