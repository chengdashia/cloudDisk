package com.example.cloudDisk.service;

import com.example.cloudDisk.common.result.R;
import com.example.cloudDisk.pojo.FolderInfo;
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
public interface FolderInfoService extends IService<FolderInfo> {

    /**
     * 创建文件夹
     * @param folderName     文件夹名称
     * @param folderDesc     文件夹描述
     * @param parentFolderId 父文件夹id
     * @return R
     */
    R<Object> createFolder(String folderName, String parentFolderId, String folderDesc);

    /**
     * 删除文件夹
     * @param folderId 文件id
     * @return R
     */
    R<Object> deleteFolder(String folderId);

    /**
     * 更改文件夹名称
     * @return  R
     */
    R<Object> updateFolderName(String folderId, String folderName);

    /**
     * 更改文件夹备注
     * @param folderId 文件夹id
     * @param folderRemark 文件夹的备注
     * @return  R
     */
    R<Object>
    updateFolderRemark(String folderId, String folderRemark);
}
