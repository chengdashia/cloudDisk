package com.example.cloudDisk.service;

import com.example.cloudDisk.common.result.R;
import com.example.cloudDisk.pojo.FolderFileInfo;
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
public interface FolderFileInfoService extends IService<FolderFileInfo> {

    /**
     * 根据文件夹id查询文件夹下的文件
     *
     * @param folderId 文件夹id
     * @return R
     */
    R<Object> getFolderFileByFolderId(String folderId);
}
