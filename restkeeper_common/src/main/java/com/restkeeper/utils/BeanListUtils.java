package com.restkeeper.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 对象深度copy支持
 * DO到DTO转换利用spring 的BeanUtils.copyProperties()
 */

public class BeanListUtils {

    public static <T> List<T> copy(Object sourceList, Class<?> beanClass) throws Exception {
        List<Object> sList = (List<Object>) sourceList;
        List<Object> tList = new ArrayList<Object>();
        for (Object t : sList) {
            Object dto = beanClass.newInstance();
            org.springframework.beans.BeanUtils.copyProperties(t, dto);
            tList.add(dto);
        }
        return (List<T>) tList;
    }

}
