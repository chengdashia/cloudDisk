package com.example.cloudDisk.service;

import com.example.cloudDisk.common.result.R;
import com.example.cloudDisk.pojo.FileHistory;
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
public interface FileHistoryService extends IService<FileHistory> {

    /**
     * 添加浏览记录 不使用redis
     * @param fileId   文件id
     * @return           R
     */
    R<Object> addMyFileHistory(String fileId);


    /**
     * 获取自己浏览的文件信息
     * @return  R
     */
    R<Object> getMyFileHistory();

    /**
     * 删除自己浏览的文件信息
     * @return  R
     * @param historyId  文件id
     */
    R<Object> delMyFileHistory(String historyId);
}
