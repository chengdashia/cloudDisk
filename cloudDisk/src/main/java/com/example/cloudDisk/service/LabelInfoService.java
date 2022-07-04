package com.example.cloudDisk.service;

import com.example.cloudDisk.common.result.R;
import com.example.cloudDisk.pojo.LabelInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 成大事
 * @since 2022-07-01 11:54:54
 */
@Transactional
public interface LabelInfoService extends IService<LabelInfo> {

    /**
     * 随机获取二十条标签信息
     * @return  R
     */
    R<Object> getLabelInfoRandom20();
}
