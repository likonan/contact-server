package com.swpu.contactserver.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

/**
 * 自动填充
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    /**
     * 新增操作自动填充
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        Object createTime = getFieldValByName("createTime", metaObject);

        //如果createTime为空，表示用户没有对相应字段进行操作
        if (ObjectUtils.isEmpty(createTime)) {
            setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        }
    }

    /**
     * 更新操作自动填充
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        Object updateTime = getFieldValByName("updateTime", metaObject);
        if (ObjectUtils.isEmpty(updateTime)) {
            setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        }
    }
}
