package com.example.cloudDisk.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cloudDisk.common.result.R;
import com.example.cloudDisk.mapper.LabelInfoMapper;
import com.example.cloudDisk.pojo.LabelInfo;
import com.example.cloudDisk.service.LabelInfoService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 成大事
 * @since 2022-07-01 11:54:54
 */
@Service
public class LabelInfoServiceImpl extends ServiceImpl<LabelInfoMapper, LabelInfo> implements LabelInfoService {


    /**
     * 随机获取二十条标签信息
     * @return  R
     */
    @Override
    public R<Object> getLabelInfoRandom20() {
        try {
            int count = Math.toIntExact(this.baseMapper.selectCount(new QueryWrapper<LabelInfo>().select("1")));
            int random = RandomUtil.randomInt(0, (count - 20)/10);
            Page<Map<String, Object>> mapPage = this.baseMapper.selectMapsPage(new Page<>(random, 20,false),
                    new QueryWrapper<LabelInfo>()
                            .select("interest_label_id", "label_name"));
            System.out.println("随机获取二十条标签");
            if(mapPage != null){
                return R.ok(mapPage);
            }
            return R.error();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }
}
