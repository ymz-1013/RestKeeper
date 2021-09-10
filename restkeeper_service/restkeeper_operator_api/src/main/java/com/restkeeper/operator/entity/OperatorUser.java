package com.restkeeper.operator.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 运营端管理员
 * </p>
 */
@Data
@Accessors(chain = true)
@TableName("t_operator_user")
public class OperatorUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private String uid;

    @TableField(value = "loginname") //自动映射：1）字段与属性名称相同 2）login_name -> loginName
    private String loginname;

    @TableField(value = "loginpass")
    private String loginpass;


}
