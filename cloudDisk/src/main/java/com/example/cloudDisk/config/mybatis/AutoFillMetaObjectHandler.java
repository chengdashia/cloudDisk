package com.example.cloudDisk.config.mybatis;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * @author 成大事
 * @since 2022/5/25 18:46
 */
@Component
public class AutoFillMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", DateUtil.date(),metaObject);
        this.setFieldValByName("updateTime",DateUtil.date(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 更新时间，取当前时间，也可以自定义
        this.setFieldValByName("updateTime",DateUtil.date(),metaObject);
    }

}
