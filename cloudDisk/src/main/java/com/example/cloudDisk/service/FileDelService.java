package com.example.cloudDisk.service;

import com.example.cloudDisk.common.result.R;
import com.example.cloudDisk.pojo.FileDel;
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
public interface FileDelService extends IService<FileDel> {

    /**
     * 获取回收站  自己删除的文件信息
     * @return  R
     */
    R<Object> getRecycleMyFileList();

    /**
     * 彻底删除回收站  自己删除的文件信息
     * @param fileId  文件id
     * @return  R
     */
    R<Object> delRecycleMyFile(String fileId);

    /**
     * 从回收站恢复 自己删除的文件信息
     * @param fileId  文件id
     * @return  R
     */
    R<Object> recoveryMyFile(String fileId);
}
