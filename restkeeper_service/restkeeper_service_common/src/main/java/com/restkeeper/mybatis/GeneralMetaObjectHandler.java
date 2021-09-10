package com.restkeeper.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

/**
 * 通用字段处理
 */
@Component
public class GeneralMetaObjectHandler implements MetaObjectHandler {


	@Override
	public void insertFill(MetaObject metaObject) {
		try {
			setFieldValByName("lastUpdateTime",LocalDateTime.now(),metaObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		insertFill(metaObject);
	}

}
